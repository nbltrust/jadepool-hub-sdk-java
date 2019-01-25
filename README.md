# jadepool-sdk-java

## Example

```
import com.jadepool.sdk.*;

public class Main {

    public static void main(String[] args) throws Exception {

        //initialization
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001", "p8BMNxtcp8Xy+ofvxhyHbBbYDC/x0JtYBb0HJWBG2OI=", "base64", "cn");
        HttpApi api = new HttpApi(config);

        // get a new address
        Address newAddr = api.newAddress("BTC");

        // verify if an address is valid
        boolean valid = api.verifyAddress("BTC", newAddr.getAddress());

        // request a withdrawal
        Order withdrawal = api.requestWithdrawal("BTC", "0.01", "mmtQyBvZggpXR88iMwk2vnrq9T3LNnSab9", null, null);

        // query a order
        Order order = api.queryOrder(withdrawal.getId());

        // request an audit
        String auditId = api.requestAudit("BTC", 1546070108000L);

        // query an audit order
        Audit audit = api.queryAudit(auditId);

        // query wallet balance
        WalletBalance balance = api.getBalance("BTC");
    }
}

```