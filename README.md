# jadepool-sdk-java

## Example

```
import com.jadepool.sdk.*;

public class Main {

    public static void main(String[] args) throws Exception {

        long sequence = 100;

        //initialization
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001", "p8BMNxtcp8Xy+ofvxhyHbBbYDC/x0JtYBb0HJWBG2OI=", "base64", "en", true, "a7c04c371b5ca7c5f2fa87efc61c876c16d80c2ff1d09b5805bd07256046d8e2", "hex");
        HttpApi api = new HttpApi(config);

        // request a withdrawal
        Order withdrawal = api.requestWithdrawal(sequence, "BTC", "0.01", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null, null);

        // get a new address
        Address newAddr = api.newAddress("BTC");

        // verify if an address is valid
        boolean valid = api.verifyAddress("BTC", newAddr.getAddress());

        // request an audit
        String auditId = api.requestAudit("BTC", 1546070108000L);

        // query a order
        Order order = api.queryOrder(withdrawal.getId());

        // query an audit order
        Audit audit = api.queryAudit(auditId);

        // query wallet balance
        WalletBalance balance = api.getBalance("BTC");

        // authorize coin
        api.authCoin("BTC", "BTC", "BTC", "BTC", 8, null);
    }
}

```