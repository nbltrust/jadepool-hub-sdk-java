package com.jadepool.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.sql.Timestamp;

public class HttpApi{

    private Configuration config;

    public HttpApi(Configuration config) {
        this.config = config;
    }

    /**
     * Request withdrawal请求提现
     * @param coinId 币种唯一识别名
     * @param value 提现金额
     * @param to 提现目标地址
     * @param memo 提现交易备注（EOS和CYB专用）
     * @param extraData 提现额外信息
     * @return 订单信息
     * @throws Exception
     */
    public Order requestWithdrawal(String coinId, String value, String to, String memo, String extraData) throws Exception {
        if (coinId == null || value == null || to == null) {
            throw new Exception("missing required parameter");
        }

        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        if( extraData != null) {
            preSignJsonMessage.append("extraData" + extraData);
        }
        if( memo != null) {
            preSignJsonMessage.append("memo" + memo);
        }
        preSignJsonMessage.append("timestamp" + timestamp);
        preSignJsonMessage.append("to" + to);
        preSignJsonMessage.append("type" + coinId);
        preSignJsonMessage.append("value" + value);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("type", coinId);
        data.put("value", value);
        data.put("to", to);
        if(memo != null) data.put("memo", memo);
        if(extraData != null) data.put("extraData", extraData);

        String request = buildRequest(timestamp, data, sig);
        JSONObject response = Utils.post(this.config.getUrl() + "/api/v1/transactions?lang=" + this.config.getLanguage(), request);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)response.get("result")).toJSONString();
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        JSONObject response = Utils.post(this.config.getUrl() + "/api/v1/addresses/new?lang=" + this.config.getLanguage(), request);
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)response.get("result")).toJSONString();
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        JSONObject response = Utils.post(this.config.getUrl() + "/api/v1/addresses/" + URLEncoder.encode(address, "utf-8") + "/verify?lang=" + this.config.getLanguage(), request);
        JSONObject result = (JSONObject)response.get("result");
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("audittime", auditTime);
        data.put("type", coinType);

        String request = buildRequest(timestamp, data, sig);
        JSONObject response = Utils.post(this.config.getUrl() + "/api/v1/audits?lang=" + this.config.getLanguage(), request);
        JSONObject result = (JSONObject)response.get("result");
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject response = Utils.get(this.config.getUrl() + "/api/v1/transactions/" + orderId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)response.get("result")).toJSONString();
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());

        JSONObject response = Utils.get(this.config.getUrl() + "/api/v1/audits/" + auditId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)response.get("result")).toJSONString();
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());

        JSONObject response = Utils.get(this.config.getUrl() + "/api/v1/wallet/" + coinType + "/status?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());
        ObjectMapper mapper = new ObjectMapper();
        String result = ((JSONObject)response.get("result")).toJSONString();
        WalletBalance balance = mapper.readValue(result, WalletBalance.class);

        return balance;
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
