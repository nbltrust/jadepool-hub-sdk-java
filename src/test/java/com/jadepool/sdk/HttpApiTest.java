package com.jadepool.sdk;

import com.jadepool.sdk.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class HttpApiTest {
//        priKey: 'Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=',
//        pubKey: 'AojUtSkVqzO6v+SoTXYV5oCy/LZQT3/knn80U1iUrFTy',
//        pubKeyUnCompressed: 'BIjUtSkVqzO6v+SoTXYV5oCy/LZQT3/knn80U1iUrFTybtv9rh4GAC4jc3RwQ/vCT/D8IZWxk5/ttkMyemMGssI='


    @Test
    public void testHttpApiV11() throws Exception {
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001","BKzjJTLJBlLhuukWJI5CenqxCu7qEGeUlmmj9NoQll75DXKX9TjyMAajH5T9z67Z6N04yFun4oX3J0MDMpJa7+U=", "cn", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64");

        HttpApi api = new HttpApi(config);

        // request a withdrawal
        Result<Order> withdrawal = api.requestWithdrawal(0, "BTC", "0.01", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null, null);
        assertEquals(0, withdrawal.getCode().longValue());
        assertEquals("OK", withdrawal.getMessage());
        assertEquals(Order.class, withdrawal.getObject().getClass());

        Result<Order> invalidWithdrawal = api.requestWithdrawal(0, "ABC", "0.01", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null, null);
        assertNotEquals(0, invalidWithdrawal.getCode().longValue());
        assertEquals(20000, invalidWithdrawal.getCode().longValue());
        assertNotEquals("OK", invalidWithdrawal.getMessage());
        assertEquals("不支持该币种类型", invalidWithdrawal.getMessage());
        assertNull(invalidWithdrawal.getObject());

        //get a new address
        Result<Address> newAddr = api.newAddress("BTC");
        assertEquals(0, newAddr.getCode().longValue());
        assertEquals("OK", newAddr.getMessage());
        assertEquals(Address.class, newAddr.getObject().getClass());

        Result<Address> invalidAddr = api.newAddress("ABC");
        assertNotEquals(0, invalidAddr.getCode().longValue());
        assertEquals(20000, invalidAddr.getCode().longValue());
        assertNotEquals("OK", invalidAddr.getMessage());
        assertEquals("不支持该币种类型", invalidAddr.getMessage());
        assertNull(invalidAddr.getObject());

        // verify if an address is valid
        Result<Boolean> valid = api.verifyAddress("BTC", newAddr.getObject().getAddress());
        assertEquals(0, valid.getCode().longValue());
        assertEquals("OK", valid.getMessage());
        assertEquals(true, valid.getObject().booleanValue());

        Result<Boolean> invalidVerify = api.verifyAddress("BTC", "hdsdjasdlk");
        assertNotEquals(0, invalidVerify.getCode().longValue());
        assertEquals(20003, invalidVerify.getCode().longValue());
        assertNotEquals("OK", invalidVerify.getMessage());
        assertEquals("地址与类型不匹配", invalidVerify.getMessage());
        assertNull(invalidVerify.getObject());

        // request an audit
        Result<String> auditId = api.requestAudit("BTC", 1551909603000L);
        assertEquals(0, auditId.getCode().longValue());
        assertEquals("OK", auditId.getMessage());
        assertEquals(String.class, auditId.getObject().getClass());

        Result<String> invalidAuditId = api.requestAudit("ABC", 1551909603000L);
        assertNotEquals(0, invalidAuditId.getCode().longValue());
        assertEquals(20000, invalidAuditId.getCode().longValue());
        assertNotEquals("OK", invalidAuditId.getMessage());
        assertEquals("不支持该币种类型", invalidAuditId.getMessage());
        assertNull(invalidAuditId.getObject());

        // query a order
        Result<Order> order = api.queryOrder(withdrawal.getObject().getId());
        assertEquals(0, order.getCode().longValue());
        assertEquals("OK", order.getMessage());
        assertEquals(Order.class, order.getObject().getClass());

        Result<Order> invalidOrder = api.queryOrder("-1");
        assertNotEquals(0, invalidOrder.getCode().longValue());
        assertEquals(40400, invalidOrder.getCode().longValue());
        assertNotEquals("OK", invalidOrder.getMessage());
        assertEquals("未找到指定订单号", invalidOrder.getMessage());
        assertNull(invalidOrder.getObject());

        // query an audit order
        Result<Audit> audit = api.queryAudit(auditId.getObject());
        assertEquals(0, audit.getCode().longValue());
        assertEquals("OK", audit.getMessage());
        assertEquals(Audit.class, audit.getObject().getClass());

        Result<Audit> invalidAudit = api.queryAudit("-fje2je2");
        assertNotEquals(0, invalidAudit.getCode().longValue());
        assertEquals(500, invalidAudit.getCode().longValue());
        assertNotEquals("OK", invalidAudit.getMessage());
        assertNull(invalidAudit.getObject());

        Result<Audit> invalidAudit2 = api.queryAudit("5c387ae65a669ac159ba7bcc");
        assertNotEquals(0, invalidAudit2.getCode().longValue());
        assertEquals(40401, invalidAudit2.getCode().longValue());
        assertNotEquals("OK", invalidAudit2.getMessage());
        assertEquals("未找到指定审计信息", invalidAudit2.getMessage());
        assertNull(invalidAudit2.getObject());

        // query wallet balance
        Result<WalletBalance> balance = api.getBalance("BTC");
        assertEquals(0, balance.getCode().longValue());
        assertEquals("OK", balance.getMessage());
        assertEquals(WalletBalance.class, balance.getObject().getClass());

        Result<WalletBalance> invalidBalance = api.getBalance("ABC");
        assertNotEquals(0, invalidBalance.getCode().longValue());
        assertEquals(20000, invalidBalance.getCode().longValue());
        assertNotEquals("OK", invalidBalance.getMessage());
        assertEquals("不支持该币种类型", invalidBalance.getMessage());
        assertNull(invalidBalance.getObject());
    }

    @Test
    public void testHttpApiV12() throws Exception {
        Configuration config = new Configuration("testa", "http://127.0.0.1:7001","BKzjJTLJBlLhuukWJI5CenqxCu7qEGeUlmmj9NoQll75DXKX9TjyMAajH5T9z67Z6N04yFun4oX3J0MDMpJa7+U=", "cn", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64", "Gv/sLcN/Blq64QJ2BI5AZQo8VrAoTOHG/BuyGPQdjNk=", "base64");

        HttpApi api = new HttpApi(config);

        // request a withdrawal
        Result<Order> invalidWithdrawal = api.requestWithdrawal(4341414, "BTC", "0.1", "mg2bfYdfii2GG13HK94jXBYPPCSWRmSiAS", null, null);
        assertNotEquals(0, invalidWithdrawal.getCode().longValue());
        assertEquals(20006, invalidWithdrawal.getCode().longValue());
        assertNotEquals("OK", invalidWithdrawal.getMessage());
        assertEquals("提现序号已使用", invalidWithdrawal.getMessage());
        assertNull(invalidWithdrawal.getObject());

        //get a new address
        Result<Address> newAddr = api.newAddress("BTC");
        assertEquals(0, newAddr.getCode().longValue());
        assertEquals("OK", newAddr.getMessage());
        assertEquals(Address.class, newAddr.getObject().getClass());

        Result<Address> invalidAddr = api.newAddress("ABC");
        assertNotEquals(0, invalidAddr.getCode().longValue());
        assertEquals(20000, invalidAddr.getCode().longValue());
        assertNotEquals("OK", invalidAddr.getMessage());
        assertEquals("不支持该币种类型", invalidAddr.getMessage());
        assertNull(invalidAddr.getObject());

        // verify if an address is valid
        Result<Boolean> valid = api.verifyAddress("BTC", newAddr.getObject().getAddress());
        assertEquals(0, valid.getCode().longValue());
        assertEquals("OK", valid.getMessage());
        assertEquals(true, valid.getObject().booleanValue());

        Result<Boolean> invalidVerify = api.verifyAddress("BTC", "hdsdjasdlk");
        assertEquals(0, invalidVerify.getCode().longValue());
        assertEquals("OK", invalidVerify.getMessage());
        assertEquals(false, invalidVerify.getObject().booleanValue());

        // request an audit
        Result<String> auditId = api.requestAudit("BTC", 1551909603000L);
        assertEquals(0, auditId.getCode().longValue());
        assertEquals("OK", auditId.getMessage());
        assertEquals(String.class, auditId.getObject().getClass());

        Result<String> invalidAuditId = api.requestAudit("ABC", 1551909603000L);
        assertNotEquals(0, invalidAuditId.getCode().longValue());
        assertEquals(20000, invalidAuditId.getCode().longValue());
        assertNotEquals("OK", invalidAuditId.getMessage());
        assertEquals("不支持该币种类型", invalidAuditId.getMessage());
        assertNull(invalidAuditId.getObject());

        // query a order
        Result<Order> invalidOrder = api.queryOrder("-1");
        assertNotEquals(0, invalidOrder.getCode().longValue());
        assertEquals(40400, invalidOrder.getCode().longValue());
        assertNotEquals("OK", invalidOrder.getMessage());
        assertEquals("未找到指定订单号", invalidOrder.getMessage());
        assertNull(invalidOrder.getObject());

        // query an audit order
        Result<Audit> audit = api.queryAudit(auditId.getObject());
        assertEquals(0, audit.getCode().longValue());
        assertEquals("OK", audit.getMessage());
        assertEquals(Audit.class, audit.getObject().getClass());

        Result<Audit> invalidAudit = api.queryAudit("-fje2je2");
        assertNotEquals(0, invalidAudit.getCode().longValue());
        assertEquals(500, invalidAudit.getCode().longValue());
        assertNotEquals("OK", invalidAudit.getMessage());
        assertNull(invalidAudit.getObject());

        Result<Audit> invalidAudit2 = api.queryAudit("5c387ae65a669ac159ba7bcc");
        assertNotEquals(0, invalidAudit2.getCode().longValue());
        assertEquals(40401, invalidAudit2.getCode().longValue());
        assertNotEquals("OK", invalidAudit2.getMessage());
        assertEquals("未找到指定审计信息", invalidAudit2.getMessage());
        assertNull(invalidAudit2.getObject());

        // query wallet balance
        Result<WalletBalance> balance = api.getBalance("BTC");
        assertEquals(0, balance.getCode().longValue());
        assertEquals("OK", balance.getMessage());
        assertEquals(WalletBalance.class, balance.getObject().getClass());

        Result<WalletBalance> invalidBalance = api.getBalance("ABC");
        assertNotEquals(0, invalidBalance.getCode().longValue());
        assertEquals(20000, invalidBalance.getCode().longValue());
        assertNotEquals("OK", invalidBalance.getMessage());
        assertEquals("不支持该币种类型", invalidBalance.getMessage());
        assertNull(invalidBalance.getObject());
    }
}
