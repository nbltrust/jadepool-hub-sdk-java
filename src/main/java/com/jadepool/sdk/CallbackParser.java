package com.jadepool.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadepool.ecc.Ecc;
import com.jadepool.sdk.models.Audit;
import com.jadepool.sdk.models.Order;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CallbackParser {

    Ecc ecc = new Ecc();

    /**
     * 初始化设置
     * @param jadePubKey jadepool's ECC public key
     * @throws Exception
     */
    public CallbackParser(String jadePubKey) throws Exception {
        if (!Utils.isBase64(jadePubKey) || jadePubKey == null) {
            throw new Exception("Invalid Jadepool Public Key...");
        }
        ecc.setJadePubKey(jadePubKey);
    }

    public Order orderCallbackParser(String callback) throws Exception {
        if (callback == null) throw new Exception("Invalid callback!");
        if (!ecc.verify(callback)) throw new Exception("Signature validation failed!");

        JSONParser parser = new JSONParser();
        JSONObject callbackObject = (JSONObject)parser.parse(callback);

        JSONObject resultObject = (JSONObject)callbackObject.get("result");
        if (resultObject == null) {
            throw new Exception("Callback does not have valid body!");
        }
        String resultObjectString = resultObject.toJSONString();
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(resultObjectString, Order.class);
        JSONObject dataObject = (JSONObject)resultObject.get("data");
        if (dataObject != null) {
            String dataObjectString = dataObject.toJSONString();
            Order.TxData txData = mapper.readValue(dataObjectString, Order.TxData.class);
            order.setTxData(txData);
        }
        return order;
    }

    public Audit auditCallbackParser(String callback) throws Exception {
        if (callback == null) throw new Exception("Invalid callback!");
        if (!ecc.verify(callback)) throw new Exception("Signature validation failed!");

        JSONParser parser = new JSONParser();
        JSONObject callbackObject = (JSONObject)parser.parse(callback);

        JSONObject resultObject = (JSONObject)callbackObject.get("result");
        if (resultObject == null) {
            throw new Exception("Callback does not have valid body!");
        }
        String resultObjectString = resultObject.toJSONString();
        ObjectMapper mapper = new ObjectMapper();
        Audit audit = mapper.readValue(resultObjectString, Audit.class);

        return audit;
    }
}
