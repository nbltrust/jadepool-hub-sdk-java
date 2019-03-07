package com.jadepool.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.jadepool.sdk.models.Audit;
import com.jadepool.sdk.models.Configuration;
import com.jadepool.sdk.models.Order;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.web3j.crypto.Sign;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class CallbackParser {

    private Configuration config;

    public CallbackParser(Configuration config) {
        this.config = config;
    }

    public Order orderCallbackParser(String callback) throws Exception {
        if (callback == null) {
            throw new Exception("Invalid callback!");
        }
        JSONParser parser = new JSONParser();
        JSONObject callbackObject = (JSONObject)parser.parse(callback);

        verify(callbackObject, this.config.getJadePubKey());

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
        if (callback == null) {
            throw new Exception("Invalid callback!");
        }
        JSONParser parser = new JSONParser();
        JSONObject callbackObject = (JSONObject)parser.parse(callback);

        verify(callbackObject, this.config.getJadePubKey());

        JSONObject resultObject = (JSONObject)callbackObject.get("result");
        if (resultObject == null) {
            throw new Exception("Callback does not have valid body!");
        }
        String resultObjectString = resultObject.toJSONString();
        ObjectMapper mapper = new ObjectMapper();
        Audit audit = mapper.readValue(resultObjectString, Audit.class);

        return audit;
    }

    private void verify(JSONObject callbackObject, String pubKey) throws Exception {
        JSONObject resultObject = (JSONObject) callbackObject.get("result");
        Long timestamp = (Long) callbackObject.get("timestamp");
        JSONObject sigObject = (JSONObject) callbackObject.get("sig");
        Long vObject = (Long) sigObject.get("v");
        String rObject = (String) sigObject.get("r");
        String sObject = (String) sigObject.get("s");

        String resultObjectString = resultObject.toJSONString();

        String res = null;
        try {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            JsonParser p = new JsonParser();

            JsonObject inputObj  = g.fromJson(resultObjectString, JsonObject.class);
            inputObj.addProperty("timestamp", timestamp.toString());

            String newStr = g.toJson(inputObj);

            JsonElement e = p.parse(newStr);

            sort(e);

            res = toStr(e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        byte v = vObject.byteValue();
        byte[] r = Base64.decodeBase64(rObject);
        byte[] s = Base64.decodeBase64(sObject);
        Sign.SignatureData signature = new Sign.SignatureData(v, r, s);

        BigInteger pubKeyRecovered = Sign.signedMessageToKey(res.getBytes(), signature);
        BigInteger correctPubKey = new BigInteger(1, Arrays.copyOfRange(Base64.decodeBase64(pubKey), 1, Base64.decodeBase64(pubKey).length));


        if (!correctPubKey.equals(pubKeyRecovered)) {
            throw new Exception("Signature verification failed!");
        }
    }

    private static void sort(JsonElement e) {
        if (e.isJsonNull())
        {
            return;
        }

        if (e.isJsonPrimitive())
        {
            return;
        }

        if (e.isJsonArray())
        {
            JsonArray a = e.getAsJsonArray();
            for (Iterator<JsonElement> it = a.iterator(); it.hasNext();)
            {
                sort(it.next());
            }
            return;
        }

        if (e.isJsonObject())
        {
            Map<String, JsonElement> tm = new TreeMap<String, JsonElement>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet())
            {
                tm.put(en.getKey(), en.getValue());
            }

            for (Map.Entry<String, JsonElement> en : tm.entrySet())
            {
                e.getAsJsonObject().remove(en.getKey());
                e.getAsJsonObject().add(en.getKey(), en.getValue());
                sort(en.getValue());
            }
            return;
        }
    }

    private static Comparator<String> getComparator() {
        Comparator<String> c = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };

        return c;
    }

    private static String toStr(JsonElement e){

        StringBuilder sb = new StringBuilder();
        if (e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            int counter = 0;
            for (int i = 0; i < a.size(); i++) {
                sb.append(counter);
                sb.append(toStr(a.get(i)));
                counter++;
            }
        } else if (e.isJsonPrimitive()) {
            if (e.getAsJsonPrimitive().isNumber()) {
                BigDecimal bd = new BigDecimal(e.toString());
                sb.append(bd.toString());
            } else {
                sb.append(e.getAsString());
            }

        } else if (e.isJsonObject()) {
            Map<String, JsonElement> tm = new TreeMap<String, JsonElement>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet())
            {
                tm.put(en.getKey(), en.getValue());
            }

            for (Map.Entry<String, JsonElement> en : tm.entrySet())
            {
                sb.append(en.getKey());
                sb.append(toStr(en.getValue()));
            }
        } else {
            sb.append(e.getAsString());
        }

        return sb.toString();
    }
}
