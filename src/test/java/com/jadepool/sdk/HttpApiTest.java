package com.jadepool.sdk;

public class HttpApiTest {

    public static void main(String[] args) throws Exception {

        //initialization
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001", "p8BMNxtcp8Xy+ofvxhyHbBbYDC/x0JtYBb0HJWBG2OI=", "base64", "en");
        HttpApi api = new HttpApi(config);

        // request a withdrawal
        Order withdrawal = api.requestWithdrawal("BTC", "0.01", "mmtQyBvZggpXR88iMwk2vnrq9T3LNnSab9", null, null);

        // get a new address
        Address newAddr = api.newAddress("BTC");

        // verify if an address is valid
        boolean valid = api.verifyAddress("BTC", newAddr.getAddress());

        try {
            api.getBalance("CYB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // request an audit
        String auditId = api.requestAudit("BTC", 1546070108000L);

        // query a order
        Order order = api.queryOrder(withdrawal.getId());

        // query an audit order
        Audit audit = api.queryAudit(auditId);

        // query wallet balance
        WalletBalance balance = api.getBalance("BTC");
    }
}
