package com.jadepool.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadepool.sdk.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.web3j.crypto.*;
import java.math.BigInteger;
import java.sql.Timestamp;

public class HttpApi{

    private Configuration config;
    private short version = 1;
    private byte algorithmId = 33;

    public HttpApi(Configuration config) {
        this.config = config;
    }

    /**
     * Request withdrawal请求提现
     * @param sequence 提现唯一序列号
     * @param coinId 币种唯一识别名
     * @param value 提现金额
     * @param to 提现目标地址
     * @param memo 提现交易备注（EOS和CYB专用）
     * @param extraData 提现额外信息
     * @return 订单信息
     * @throws Exception
     */
    public Order requestWithdrawal(long sequence, String coinId, String value, String to, String memo, String extraData) throws Exception {
        if (coinId == null || value == null || to == null) {
            throw new Exception("missing required parameter");
        }
        byte[] preHash = authWithdrawalPreHash(sequence, coinId, value, to, memo);
        byte[] sha256ByteArr = Utils.sha256(preHash);
        String withdrawalAuth = sign(sha256ByteArr, this.config.getAuthKey());

        byte[] WithdrawalAuthByteArr = Utils.hexStringToByteArray(withdrawalAuth);
        short WithdrawalAuthLength = (short) WithdrawalAuthByteArr.length;
        byte[] WithdrawalAuthLengthByteArr = Utils.shortToByteArr(WithdrawalAuthLength);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preHash);
        outputStream.write(WithdrawalAuthLengthByteArr);
        outputStream.write(WithdrawalAuthByteArr);
        String finalAuth = Utils.byteArrayToHex(outputStream.toByteArray());

        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("auth" + finalAuth);
        if( extraData != null) {
            preSignJsonMessage.append("extraData" + extraData);
        }
        if( memo != null) {
            preSignJsonMessage.append("memo" + memo);
        }
        preSignJsonMessage.append("sequence" + sequence);
        preSignJsonMessage.append("timestamp" + timestamp);
        preSignJsonMessage.append("to" + to);
        preSignJsonMessage.append("type" + coinId);
        preSignJsonMessage.append("value" + value);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("sequence", sequence);
        data.put("type", coinId);
        data.put("value", value);
        data.put("to", to);
        if(memo != null) data.put("memo", memo);
        if(extraData != null) data.put("extraData", extraData);
        data.put("auth", finalAuth);

        String request = buildRequest(timestamp, data, sig);
        String response = Utils.post(this.config.getUrl() + "/api/v1/transactions", request);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)jsonObject.get("result")).toJSONString();
        Order order = mapper.readValue(result, Order.class);

        return order;
    }

    /**
     * 请求新充值地址
     * @param coinType 币种
     * @return 地址信息
     * @throws Exception
     */
    public Address newAddress(String coinType) throws Exception {
        if (coinType == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        preSignJsonMessage.append("type" + coinType);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        String response = Utils.post(this.config.getUrl() + "/api/v1/addresses/new", request);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)jsonObject.get("result")).toJSONString();
        Address address = mapper.readValue(result, Address.class);

        return address;
    }

    /**
     * 验证地址有效性
     * @param coinType 币种
     * @param address 地址
     * @return boolean
     * @throws Exception
     */
    public boolean verifyAddress(String coinType, String address) throws Exception {
        if (coinType == null || address == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        preSignJsonMessage.append("type" + coinType);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        String response = Utils.post(this.config.getUrl() + "/api/v1/addresses/" + address + "/verify", request);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        JSONObject result = (JSONObject)jsonObject.get("result");
        boolean isValid = (Boolean)result.get("valid");

        return isValid;
    }

    /**
     * 生成新审计订单
     * @param coinType 币种
     * @param auditTime 审计时间戳
     * @return 审计订单号
     * @throws Exception
     */
    public String requestAudit(String coinType, long auditTime) throws Exception {
        if (coinType == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("audittime" + auditTime);
        preSignJsonMessage.append("timestamp" + timestamp);
        preSignJsonMessage.append("type" + coinType);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("audittime", auditTime);
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        String response = Utils.post(this.config.getUrl() + "/api/v1/audits", request);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        JSONObject result = (JSONObject)jsonObject.get("result");
        JSONObject currentAudit = (JSONObject)result.get("current");
        String auditId = (String)currentAudit.get("id");
        return auditId;
    }

    /**
     * 查询订单
     * @param orderId 订单号
     * @return 订单信息
     * @throws Exception
     */
    public Order queryOrder(String orderId) throws Exception {
        if (orderId == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());
        String response = Utils.get(this.config.getUrl() + "/api/v1/transactions/" + orderId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)jsonObject.get("result")).toJSONString();
        Order order = mapper.readValue(result, Order.class);

        return order;
    }

    /**
     * 查询审计订单
     * @param auditId 审计订单号
     * @return 审计信息
     * @throws Exception
     */
    public Audit queryAudit(String auditId) throws Exception {
        if (auditId == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());

        String response = Utils.get(this.config.getUrl() + "/api/v1/audits/" + auditId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)jsonObject.get("result")).toJSONString();
        Audit audit = mapper.readValue(result, Audit.class);

        return audit;
    }

    /**
     * 查询余额
     * @param coinType 币种
     * @return 余额信息
     * @throws Exception
     */
    public WalletBalance getBalance(String coinType) throws Exception {
        if (coinType == null) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());

        String response = Utils.get(this.config.getUrl() + "/api/v1/wallet/" + coinType + "/status?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(response);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)jsonObject.get("result")).toJSONString();
        WalletBalance balance = mapper.readValue(result, WalletBalance.class);

        return balance;
    }

    /**
     * 授权币种
     * @param coinId 币种唯一识别名
     * @param coinType 币种类型
     * @param chain 币种区块链
     * @param token 币种链上真实名
     * @param decimal 币种精度
     * @param contract 智能合约
     * @throws Exception
     */
    public void authCoin(String coinId, String coinType, String chain, String token, int decimal, String contract) throws Exception {
        if (coinId == null || coinType == null || chain == null || token == null) {
            throw new Exception("missing required parameter");
        }
        byte[] preHash = authCoinPreHash(coinId, coinType, chain, token, decimal, contract);
        byte[] sha256ByteArr = Utils.sha256(preHash);
        String coinAuth = sign(sha256ByteArr, this.config.getAuthKey());

        byte[] coinAuthByteArr = Utils.hexStringToByteArray(coinAuth);
        short coinAuthLength = (short) coinAuthByteArr.length;
        byte[] coinAuthLengthByteArr = Utils.shortToByteArr(coinAuthLength);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preHash);
        outputStream.write(coinAuthLengthByteArr);
        outputStream.write(coinAuthByteArr);
        String finalAuth = Utils.byteArrayToHex(outputStream.toByteArray());

        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("authToken" + finalAuth);
        preSignJsonMessage.append("timestamp" + timestamp);

        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = sign(preSignJsonMessageSha256, this.config.getEccKey());

        JSONObject data = new JSONObject();
        data.put("authToken", finalAuth);

        String request = buildRequest(timestamp, data, sig);
        Utils.patch(this.config.getUrl() + "/api/v1/wallet/" + coinId + "/token", request);
    }

    private byte[] authCoinPreHash(String coinId, String coinType, String chain, String token, int decimal, String contract) throws IOException {
        byte[] versionByteArr = Utils.shortToByteArr(version);
        byte[] algorithmIdByteArr = Utils.byteToByteArr(algorithmId);

        byte[] coinIdByteArr = Utils.stringToByteArr(coinId);
        short coinIdLength = (short) coinIdByteArr.length;
        byte[] coinIdLengthByteArr = Utils.shortToByteArr(coinIdLength);

        byte[] coinTypeByteArr = Utils.stringToByteArr(coinType);
        short coinTypeLength = (short) coinTypeByteArr.length;
        byte[] coinTypeLengthByteArr = Utils.shortToByteArr(coinTypeLength);

        byte[] chainByteArr = Utils.stringToByteArr(chain);
        short chainLength = (short) chainByteArr.length;
        byte[] chainLengthByteArr = Utils.shortToByteArr(chainLength);

        byte[] tokenByteArr = Utils.stringToByteArr(token);
        short tokenLength = (short) tokenByteArr.length;
        byte[] tokenLengthByteArr = Utils.shortToByteArr(tokenLength);

        String convertedDecimal = String.valueOf(decimal);
        byte[] decimalByteArr = Utils.stringToByteArr(convertedDecimal);
        short decimalLength = (short) decimalByteArr.length;
        byte[] decimalLengthByteArr = Utils.shortToByteArr(decimalLength);

        byte[] contractByteArr = null;
        short contractLength = 0;
        if (contract != null) {
            contractByteArr = Utils.stringToByteArr(contract);
            contractLength = (short) contractByteArr.length;
        }
        byte[] contractLengthByteArr = Utils.shortToByteArr(contractLength);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(versionByteArr);
        outputStream.write(algorithmIdByteArr);
        outputStream.write(coinIdLengthByteArr);
        outputStream.write(coinIdByteArr);
        outputStream.write(coinTypeLengthByteArr);
        outputStream.write(coinTypeByteArr);
        outputStream.write(chainLengthByteArr);
        outputStream.write(chainByteArr);
        outputStream.write(tokenLengthByteArr);
        outputStream.write(tokenByteArr);
        outputStream.write(contractLengthByteArr);
        if (contract != null) {
            outputStream.write(contractByteArr);
        }
        outputStream.write(decimalLengthByteArr);
        outputStream.write(decimalByteArr);

        byte[] preHash = outputStream.toByteArray();
        return preHash;
    }

    private byte[] authWithdrawalPreHash(long sequence, String coinId, String value, String to, String memo) throws IOException {
        byte[] versionByteArr = Utils.shortToByteArr(version);
        byte[] algorithmIdByteArr = Utils.byteToByteArr(algorithmId);
        byte[] sequenceByteArr = Utils.longToByteArr(sequence);
        byte[] coinIdByteArr = Utils.stringToByteArr(coinId);
        short coinIdLength = (short) coinIdByteArr.length;
        byte[] coinIdLengthByteArr = Utils.shortToByteArr(coinIdLength);
        byte[] toByteArr = Utils.stringToByteArr(to);
        short toLength = (short) toByteArr.length;
        byte[] toLengthByteArr = Utils.shortToByteArr(toLength);
        byte[] amountByteArr = Utils.stringToByteArr(value);
        short amountLength = (short) amountByteArr.length;
        byte[] amountLengthByteArr = Utils.shortToByteArr(amountLength);
        byte[] memoByteArr = null;
        short memoLength = 0;
        if (memo != null) {
            memoByteArr = Utils.stringToByteArr(memo);
            memoLength = (short) memoByteArr.length;
        }
        byte[] memoLengthByteArr = Utils.shortToByteArr(memoLength);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(versionByteArr);
        outputStream.write(algorithmIdByteArr);
        outputStream.write(sequenceByteArr);
        outputStream.write(coinIdLengthByteArr);
        outputStream.write(coinIdByteArr);
        outputStream.write(toLengthByteArr);
        outputStream.write(toByteArr);
        outputStream.write(amountLengthByteArr);
        outputStream.write(amountByteArr);
        outputStream.write(memoLengthByteArr);
        if (memo != null) {
            outputStream.write(memoByteArr);
        }
        byte[] preHash = outputStream.toByteArray();
        return preHash;
    }

    private String sign (byte[] byteArr, String key) {
        BigInteger privKey = new BigInteger(key, 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
        Sign.SignatureData signature = Sign.signMessage(byteArr, keyPair, false);
        String r = Utils.byteArrayToHex(signature.getR());
        String s = Utils.byteArrayToHex(signature.getS());
        String hex = r + s;
        return hex;
    }

    private String buildRequest (long timestamp, JSONObject data, String sig) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("timestamp", timestamp);
        requestParams.put("data", data);
        requestParams.put("sig", sig);
        requestParams.put("appid", this.config.getAppId());
        requestParams.put("crypto", "ecc");
        requestParams.put("hash", "sha256");
        requestParams.put("encode", "hex");
        String request = requestParams.toJSONString();

        return request;
    }

}
