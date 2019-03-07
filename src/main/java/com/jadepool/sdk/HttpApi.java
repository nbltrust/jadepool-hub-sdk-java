package com.jadepool.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadepool.sdk.models.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URLEncoder;
import java.sql.Timestamp;

public class HttpApi{

    private Configuration config;

    ObjectMapper mapper = new ObjectMapper();

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
    public Result<Order> requestWithdrawal(long sequence, String coinId, String value, String to, String memo, String extraData) throws Exception {
        if (coinId == null || value == null || to == null
        || coinId.length() == 0 || value.length() == 0 || to.length() == 0) {
            throw new Exception("missing required parameter");
        }
        String withdrawalAuth = AuthBuilder.buildWithdrawalAuth(this.config.getAuthKey(), sequence, coinId, value, to, memo);


        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("auth" + withdrawalAuth);
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
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        JSONObject data = new JSONObject();
        data.put("sequence", sequence);
        data.put("type", coinId);
        data.put("value", value);
        data.put("to", to);
        if(memo != null) data.put("memo", memo);
        if(extraData != null) data.put("extraData", extraData);
        data.put("auth", withdrawalAuth);

        String request = buildRequest(timestamp, data, sig);
        HttpResponse httpResponse = Utils.post(this.config.getUrl() + "/api/v1/transactions?lang=" + this.config.getLanguage(), request);
        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Order> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            String resultObject = ((JSONObject)o.get("result")).toJSONString();
            Order order = mapper.readValue(resultObject, Order.class);
            result.setObject(order);
        }
        return result;
    }

    /**
     * 请求新充值地址
     * @param coinType 币种
     * @return 地址信息
     * @throws Exception
     */
    public Result<Address> newAddress(String coinType) throws Exception {
        if (coinType == null || coinType.length() == 0) {
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
        HttpResponse httpResponse = Utils.post(this.config.getUrl() + "/api/v1/addresses/new?lang=" + this.config.getLanguage(), request);
        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Address> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            String resultObject = ((JSONObject)o.get("result")).toJSONString();
            Address address = mapper.readValue(resultObject, Address.class);
            result.setObject(address);
        }
        return result;
    }

    /**
     * 验证地址有效性
     * @param coinType 币种
     * @param address 地址
     * @return boolean
     * @throws Exception
     */
    public Result<Boolean> verifyAddress(String coinType, String address) throws Exception {
        if (coinType == null || address == null || coinType.length() == 0 || address.length() == 0) {
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
        HttpResponse httpResponse = Utils.post(this.config.getUrl() + "/api/v1/addresses/" + URLEncoder.encode(address, "utf-8") + "/verify?lang=" + this.config.getLanguage(), request);

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Boolean> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            JSONObject resultObject = (JSONObject) o.get("result");
            Boolean valid = (Boolean)resultObject.get("valid");
            result.setObject(valid);
        }
        return result;
    }

    /**
     * 生成新审计订单
     * @param coinType 币种
     * @param auditTime 审计时间戳
     * @return 审计订单号
     * @throws Exception
     */
    public Result<String> requestAudit(String coinType, long auditTime) throws Exception {
        if (coinType == null || coinType.length() == 0) {
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
        HttpResponse httpResponse = Utils.post(this.config.getUrl() + "/api/v1/audits?lang=" + this.config.getLanguage(), request);

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<String> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            JSONObject resultObject = (JSONObject)o.get("result");
            JSONObject currentAudit = (JSONObject)resultObject.get("current");
            String auditId = (String)currentAudit.get("id");
            result.setObject(auditId);
        }
        return result;
    }

    /**
     * 查询订单
     * @param orderId 订单号
     * @return 订单信息
     * @throws Exception
     */
    public Result<Order> queryOrder(String orderId) throws Exception {
        if (orderId == null || orderId.length() == 0) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());
        HttpResponse httpResponse = Utils.get(this.config.getUrl() + "/api/v1/transactions/" + orderId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Order> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            String resultObject = ((JSONObject)o.get("result")).toJSONString();
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(resultObject, Order.class);
            result.setObject(order);
        }
        return result;
    }

    /**
     * 查询审计订单
     * @param auditId 审计订单号
     * @return 审计信息
     * @throws Exception
     */
    public Result<Audit> queryAudit(String auditId) throws Exception {
        if (auditId == null || auditId.length() == 0) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());

        HttpResponse httpResponse = Utils.get(this.config.getUrl() + "/api/v1/audits/" + auditId + "?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Audit> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            String resultObject = ((JSONObject)o.get("result")).toJSONString();
            ObjectMapper mapper = new ObjectMapper();
            Audit audit = mapper.readValue(resultObject, Audit.class);
            result.setObject(audit);
        }
        return result;
    }

    /**
     * 查询余额
     * @param coinType 币种
     * @return 余额信息
     * @throws Exception
     */
    public Result<WalletBalance> getBalance(String coinType) throws Exception {
        if (coinType == null || coinType.length() == 0) {
            throw new Exception("missing required parameter");
        }
        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("timestamp" + timestamp);
        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());

        HttpResponse httpResponse = Utils.get(this.config.getUrl() + "/api/v1/wallet/" + coinType + "/status?crypto=ecc&appid=" + this.config.getAppId() + "&timestamp=" + timestamp + "&encode=hex&hash=sha256&sig=" + sig + "&lang=" + this.config.getLanguage());

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<WalletBalance> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        if(resolver.getCode() == 0){
            JSONObject o = resolver.getObject();
            String resultObject = ((JSONObject)o.get("result")).toJSONString();
            ObjectMapper mapper = new ObjectMapper();
            WalletBalance balance = mapper.readValue(resultObject, WalletBalance.class);
            result.setObject(balance);
        }
        return result;
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
    public Result<Boolean> authCoin(String coinId, String coinType, String chain, String token, int decimal, String contract) throws Exception {
        if (coinId == null || coinType == null || chain == null || token == null
        || coinId.length() == 0 || coinType.length() == 0 || chain.length() == 0 || token.length() == 0) {
            throw new Exception("missing required parameter");
        }
        String coinAuth = AuthBuilder.buildCoinAuth(this.config.getAuthKey(), coinId, coinType, chain, token, decimal, contract);

        long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        StringBuilder preSignJsonMessage = new StringBuilder();
        preSignJsonMessage.append("authToken" + coinAuth);
        preSignJsonMessage.append("timestamp" + timestamp);

        byte[] preSignJsonMessageByteArr = Utils.stringToByteArr(preSignJsonMessage.toString());
        byte[] preSignJsonMessageSha256 = Utils.sha256(preSignJsonMessageByteArr);
        String sig = Utils.sign(preSignJsonMessageSha256, this.config.getEccKey());

        JSONObject data = new JSONObject();
        data.put("authToken", coinAuth);

        String request = buildRequest(timestamp, data, sig);
        HttpResponse httpResponse = Utils.patch(this.config.getUrl() + "/api/v1/wallet/" + coinId + "/token?lang=" + this.config.getLanguage(), request);

        Resolver resolver = resolveHttpResponse(httpResponse);
        Result<Boolean> result = new Result<>();
        result.setCode(resolver.getCode());
        result.setMessage(resolver.getMessage());
        result.setObject(resolver.getCode() == 0 ? new Boolean(true) : new Boolean(false));
        return result;
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

    private static Resolver resolveHttpResponse(HttpResponse httpResponse) throws Exception{
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            String response = EntityUtils.toString(httpEntity, "utf-8");
            JSONParser parser = new JSONParser();
            JSONObject o = (JSONObject)parser.parse(response);
            Long code = (Long) o.get("code");
            String msg = (String) o.get("message");
            Resolver resolver = new Resolver();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                resolver.setCode(code);
                resolver.setMessage(msg);
                if (code == 0) {
                    resolver.setObject(o);
                }
                return resolver;
            } else {
                if (code != null && msg != null) {
                    resolver.setCode(code);
                    resolver.setMessage(msg);
                    return resolver;
                } else {
                    throw new Exception("Connection failed!");
                }
            }
        } else {
            throw new Exception("HTTP response does not have entity!");
        }
    }
}
