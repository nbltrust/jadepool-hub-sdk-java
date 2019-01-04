package com.jadepool.sdk;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class AuthBuilderTest {

    String authKey = "a7c04c371b5ca7c5f2fa87efc61c876c16d80c2ff1d09b5805bd07256046d8e2";

    @Test
    public void testAuthCoin() throws IOException {
        String coinAuth = AuthBuilder.buildCoinAuth(authKey, "BTC", "BTC", "BTC", "BTC", 8, null);
        Assert.assertEquals("0100210300425443030042544303004254430300425443000001003840004ae53d9b12e03f94260ae385c2220023488e13dc0933f3f06c6ffc0e9aa906997663f893265b4f34bd12fcaed8d3de8b21c631a0d25d268e1c507a6cece4eb47", coinAuth);
    }

    @Test
    public void testAuthWithdrawal() throws IOException {
        String withdrawalAuth = AuthBuilder.buildWithdrawalAuth(authKey, 8888, "BTC", "0.01", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null);
        Assert.assertEquals("010021b822000000000000030042544322006d6732626659646669693247473133484b39346a5842595050435357526d536941530400302e3031000040007cd4e1727a4543487efcfc0add2dc3720dd98e1990b8844a574a73a4416ae832788393f4a856e425adc374c7f9df5b7af23c89c329dc3d2433b45aae7a68c6b6", withdrawalAuth);
    }
}
