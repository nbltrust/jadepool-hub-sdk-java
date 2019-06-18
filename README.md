# jadepool-sdk-java

## API Example

```java
import com.jadepool.sdk.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // If you are using Jadepool v0.12+, make sure to use unique sequence.
        // If you are using versions below v0.12, any number will be accepted.
        long sequence = 100;

        // Initialization
        // You can set appid and public key of your server on Jadepool Admin.
        // Jadepool public key can be found on Admin.
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001","BKzjJTLJBlLhuukWJI5CenqxCu7qEGeUlmmj9NoQll75DXKX9TjyMAajH5T9z67Z6N04yFun4oX3J0MDMpJa7+U=", "cn", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64");
        HttpApi api = new HttpApi(config);

        // request a withdrawal
        Order withdrawal = api.requestWithdrawal(sequence, "BTC", "0.01", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null, null);

        // Request new address
        Address newAddr = api.newAddress("BTC");

        // Verify if an address is valid
        boolean valid = api.verifyAddress("BTC", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS");

        // Request an audit
        String auditId = api.requestAudit("BTC", 1546070108000L);

        // Query a order
        Order order = api.queryOrder("123");

        // Query an audit order
        Audit audit = api.queryAudit("5c6298f436d03c1751d47ffa");

        // Query wallet balance
        WalletBalance balance = api.getBalance("BTC");

        // Authorize coin
        // This API is only available on v0.12+
        api.authCoin("BTC", "BTC", "BTC", "BTC", 8, null);

        // Pass any Jadepool callbacks to the callback parser.
        CallbackParser callbackParser = new CallbackParser(config);

        // Pseudo code: Your callback server received Jadepool's notification
        String orderCallback = request.body

        // Order callback will be parsed as a Order class object.
        Order orderParsed = callbackParser.orderCallbackParser(orderCallback);

        // or
        String auditCallback = request.body

        // Audit callback will be parsed as a Audit class object.
        Audit auditParsed = callbackParser.auditCallbackParser(auditCallback);
    }
}

```

## Callback Parser Example

```java
import com.jadepool.sdk.*;

public class Main {

    public static void main(String[] args) throws Exception {
        
        String eccPrivateKey = "BK8H2i2V1QarXSWIK8kVhMCkdtaUR8LLFVxe6TtV7yWE4xsgwkCOENEUTD62YYuckuju/QivwJHaFlRY45GCxiE=";

        CallbackParser callbackParser = new CallbackParser(eccPrivateKey);

        // Pass any Jadepool callbacks to the callback parser.
        // Pseudo code: Your callback server received Jadepool's notification
        String orderCallback = request.body;

        // Order callback will be parsed as a Order class object.
        Order orderParsed = callbackParser.orderCallbackParser(orderCallback);

        // or
        String auditCallback = request.body

        // Audit callback will be parsed as a Audit class object.
        Audit auditParsed = callbackParser.auditCallbackParser(auditCallback);
    }
}

```