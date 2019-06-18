package com.jadepool.sdk;

import com.jadepool.sdk.models.Audit;
import com.jadepool.sdk.models.Order;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CallbackParserTest {
    @Test
    public void testOrderCallbackParser() throws Exception {
        CallbackParser callbackParser = new CallbackParser("BK8H2i2V1QarXSWIK8kVhMCkdtaUR8LLFVxe6TtV7yWE4xsgwkCOENEUTD62YYuckuju/QivwJHaFlRY45GCxiE=");
        String orderCallback = "{\n" +
                "    \"code\": 0,\n" +
                "    \"status\": 0,\n" +
                "    \"message\": \"OK\",\n" +
                "    \"crypto\": \"ecc\",\n" +
                "    \"timestamp\": 1551926531651,\n" +
                "    \"sig\": {\n" +
                "        \"r\": \"SozZfWiFotVLrcF0/JpiWcl55ckaaqffZT0nt+vAiCY=\",\n" +
                "        \"s\": \"W208ReFXYx44ekmlmbeN4iX5uOMG3+JRjN9O5LRg13Y=\",\n" +
                "        \"v\": 27\n" +
                "    },\n" +
                "    \"result\": {\n" +
                "        \"id\": \"6874\",\n" +
                "        \"state\": \"done\",\n" +
                "        \"bizType\": \"WITHDRAW\",\n" +
                "        \"type\": \"ATOM\",\n" +
                "        \"coinType\": \"ATOM\",\n" +
                "        \"to\": \"cosmos1hztxhgqqwusuze3emmjyyrzsh3glr2qlj7qxmt\",\n" +
                "        \"value\": \"2\",\n" +
                "        \"confirmations\": 124818,\n" +
                "        \"create_at\": 1551176412281,\n" +
                "        \"update_at\": 1551176455757,\n" +
                "        \"from\": \"cosmos1hrnl5pugag20gnu8zy3604jetqrjaf60czkl8u\",\n" +
                "        \"n\": 0,\n" +
                "        \"fee\": \"1\",\n" +
                "        \"fees\": [\n" +
                "            {\n" +
                "                \"_id\": \"5c75130782f73d886a32a38a\",\n" +
                "                \"amount\": \"1\",\n" +
                "                \"nativeName\": \"muon\",\n" +
                "                \"coinName\": \"ATOM\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"_id\": \"5c75130782f73d886a32a389\",\n" +
                "                \"amount\": \"3\",\n" +
                "                \"nativeName\": \"photino\",\n" +
                "                \"coinName\": \"PTNO\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"data\": {\n" +
                "            \"timestampBegin\": 1551176435728,\n" +
                "            \"timestampFinish\": 1551176455644,\n" +
                "            \"timestampHandle\": 1551176435607,\n" +
                "            \"type\": \"Cosmos\",\n" +
                "            \"hash\": \"3579467CEA40F783D5964FAE999692D3B4FDF7111F5035911EE732C1A5D3FE28\",\n" +
                "            \"blockNumber\": 106930,\n" +
                "            \"blockHash\": \"95D28E04AAE4FF50081715D958BBDE871440AE5A39F0C57B19B38139E38BC698\",\n" +
                "            \"from\": [\n" +
                "                {\n" +
                "                    \"address\": \"cosmos1hrnl5pugag20gnu8zy3604jetqrjaf60czkl8u\",\n" +
                "                    \"value\": \"2\",\n" +
                "                    \"tokenName\": \"muon\",\n" +
                "                    \"coinName\": \"ATOM\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"to\": [\n" +
                "                {\n" +
                "                    \"address\": \"cosmos1hztxhgqqwusuze3emmjyyrzsh3glr2qlj7qxmt\",\n" +
                "                    \"value\": \"2\",\n" +
                "                    \"tokenName\": \"muon\",\n" +
                "                    \"coinName\": \"ATOM\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"fee\": \"1,muon,ATOM;3,photino,PTNO\",\n" +
                "            \"confirmations\": 124818,\n" +
                "            \"state\": \"done\"\n" +
                "        },\n" +
                "        \"hash\": \"3579467CEA40F783D5964FAE999692D3B4FDF7111F5035911EE732C1A5D3FE28\",\n" +
                "        \"block\": 106930,\n" +
                "        \"extraData\": \"automation-test\",\n" +
                "        \"memo\": \"\",\n" +
                "        \"sendAgain\": false,\n" +
                "        \"namespace\": \"Cosmos\",\n" +
                "        \"sid\": \"5de9plikE3XKN-4sAAAB\"\n" +
                "    }\n" +
                "}";
        Order order = callbackParser.orderCallbackParser(orderCallback);

        assertEquals("6874", order.getId());
        assertEquals("WITHDRAW", order.getBizType());
        assertEquals("ATOM", order.getCoinType());
        assertEquals("cosmos1hztxhgqqwusuze3emmjyyrzsh3glr2qlj7qxmt", order.getTo());
        assertEquals("done", order.getState());
        assertEquals("ATOM", order.getType());
        assertEquals("2", order.getValue());
        assertEquals(124818, order.getConfirmations().longValue());
        assertEquals(0, order.getN().intValue());
        assertEquals(1551176412281L, order.getCreate_at().longValue());
        assertEquals(1551176455757L, order.getUpdate_at().longValue());
        assertEquals("cosmos1hrnl5pugag20gnu8zy3604jetqrjaf60czkl8u", order.getFrom());
        assertEquals("3579467CEA40F783D5964FAE999692D3B4FDF7111F5035911EE732C1A5D3FE28", order.getHash());
        assertEquals(106930, order.getBlock().longValue());
        assertEquals("automation-test", order.getExtraData());
        assertEquals(false, order.getSendAgain());

        Order.TxData data = order.getTxData();
        List<Order.TxData.Io> froms = data.getFrom();
        List<Order.TxData.Io> tos = data.getTo();

        for (Order.TxData.Io from: froms) {
            if (from.getAddress().equals("cosmos1hrnl5pugag20gnu8zy3604jetqrjaf60czkl8u")) {
                assertEquals("2", from.getValue());
                assertEquals("muon", from.getTokenName());
                assertEquals("ATOM", from.getCoinName());
            }
        }

        for (Order.TxData.Io to: tos) {
            if (to.getAddress().equals("cosmos1hztxhgqqwusuze3emmjyyrzsh3glr2qlj7qxmt")) {
                assertEquals("2", to.getValue());
                assertEquals("muon", to.getTokenName());
                assertEquals("ATOM", to.getCoinName());
            }
        }

        List<Order.Fee> fees = order.getFees();
        for (Order.Fee fee: fees) {
            if (fee.getCoinName().equals("ATOM")) {
                assertEquals("1", fee.getAmount());
                assertEquals("muon", fee.getNativeName());
            } else if (fee.getCoinName().equals("PTNO")) {
                assertEquals("3", fee.getAmount());
                assertEquals("photino", fee.getNativeName());
            }
        }
    }

    @Test
    public void testAuditCallbackParser() throws Exception {
        CallbackParser callbackParser = new CallbackParser("BK8H2i2V1QarXSWIK8kVhMCkdtaUR8LLFVxe6TtV7yWE4xsgwkCOENEUTD62YYuckuju/QivwJHaFlRY45GCxiE=");
        String auditCallback = "{\n" +
                "  \"code\": 0,\n" +
                "  \"status\": 0,\n" +
                "  \"message\": \"OK\",\n" +
                "  \"crypto\": \"ecc\",\n" +
                "  \"timestamp\": 1551283200000,\n" +
                "  \"sig\": {\n" +
                "    \"r\": \"zCyr1fXrApBw1pYqtg0Tlw0kP9ky8OBqOI9Ov+tvZO4=\",\n" +
                "    \"s\": \"ChS7wi3oov5VgV+YV6esbsnAEYuGmEpL0P7f73GuhaA=\",\n" +
                "    \"v\": 27\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"current\": {\n" +
                "      \"id\": \"5c7743ab82f73d886a337e00\",\n" +
                "      \"type\": \"ATOM\",\n" +
                "      \"blocknumber\": 125148,\n" +
                "      \"timestamp\": 1551283200000,\n" +
                "      \"deposit_total\": \"2\",\n" +
                "      \"withdraw_total\": \"2\",\n" +
                "      \"fee_total\": \"0\",\n" +
                "      \"internal_fee\": \"0\",\n" +
                "      \"internal_num\": 0\n" +
                "    },\n" +
                "    \"calculated\": true,\n" +
                "    \"deposit_total\": \"2\",\n" +
                "    \"deposit_num\": 2,\n" +
                "    \"withdraw_total\": \"2\",\n" +
                "    \"withdraw_num\": 2,\n" +
                "    \"sweep_total\": \"0\",\n" +
                "    \"sweep_num\": 0,\n" +
                "    \"sweep_internal_total\": \"0\",\n" +
                "    \"sweep_internal_num\": 0,\n" +
                "    \"airdrop_total\": \"0\",\n" +
                "    \"airdrop_num\": 0,\n" +
                "    \"recharge_total\": \"4\",\n" +
                "    \"recharge_num\": 1,\n" +
                "    \"recharge_internal_total\": \"0\",\n" +
                "    \"recharge_internal_num\": 0,\n" +
                "    \"recharge_unknown_total\": \"8\",\n" +
                "    \"recharge_unknown_num\": 4,\n" +
                "    \"recharge_special_total\": \"0\",\n" +
                "    \"recharge_special_num\": 0,\n" +
                "    \"failed_withdraw_num\": 0,\n" +
                "    \"failed_sweep_num\": 0,\n" +
                "    \"failed_sweep_internal_num\": 0,\n" +
                "    \"fees\": [\n" +
                "      {\n" +
                "        \"withdraw_fee\": \"2\",\n" +
                "        \"sweep_fee\": \"0\",\n" +
                "        \"sweep_internal_fee\": \"0\",\n" +
                "        \"failed_withdraw_fee\": \"0\",\n" +
                "        \"failed_sweep_fee\": \"0\",\n" +
                "        \"failed_sweep_internal_fee\": \"0\",\n" +
                "        \"_id\": \"5c7f7c71ae41209c0fc8417c\",\n" +
                "        \"fee_type\": \"PTNO\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"type\": \"ATOM\",\n" +
                "    \"timestamp\": 1551283200000,\n" +
                "    \"blocknumber\": 125148,\n" +
                "    \"create_at\": \"2019-02-28T02:12:59.048Z\",\n" +
                "    \"update_at\": \"2019-03-06T07:53:21.985Z\",\n" +
                "    \"__v\": 3,\n" +
                "    \"calc_order_num\": 9,\n" +
                "    \"last\": \"5c75f22b82f73d886a32fad5\",\n" +
                "    \"id\": \"5c7743ab82f73d886a337e00\"\n" +
                "  }\n" +
                "}";
        Audit audit = callbackParser.auditCallbackParser(auditCallback);
        assertEquals(true, audit.isCalculated());
        assertEquals("2", audit.getDeposit_total());
        assertEquals(2, audit.getDeposit_num().longValue());
        assertEquals("2", audit.getWithdraw_total());
        assertEquals(2, audit.getWithdraw_num().longValue());
        assertEquals("0", audit.getSweep_total());
        assertEquals(0, audit.getSweep_num().longValue());
        assertEquals("0", audit.getSweep_internal_total());
        assertEquals(0, audit.getSweep_internal_num().longValue());
        assertEquals("0", audit.getAirdrop_total());
        assertEquals(0, audit.getAirdrop_num().longValue());
        assertEquals("4", audit.getRecharge_total());
        assertEquals(1, audit.getRecharge_num().longValue());
        assertEquals("0", audit.getRecharge_internal_total());
        assertEquals(0, audit.getRecharge_internal_num().longValue());
        assertEquals("8", audit.getRecharge_unknown_total());
        assertEquals(4, audit.getRecharge_unknown_num().longValue());
        assertEquals("0", audit.getRecharge_special_total());
        assertEquals(0, audit.getRecharge_special_num().longValue());

        assertEquals(0, audit.getFailed_withdraw_num().longValue());
        assertEquals(0, audit.getFailed_sweep_num().longValue());
        assertEquals(0, audit.getFailed_sweep_internal_num().longValue());

        assertEquals("5c7743ab82f73d886a337e00", audit.getId());
        assertEquals(9, audit.getCalc_order_num().longValue());
        assertEquals(1551283200000l, audit.getTimestamp().longValue());
        assertEquals(125148, audit.getBlocknumber().longValue());
        assertEquals("ATOM", audit.getType());

        List<Audit.Fee> fees = audit.getFees();
        Audit.Fee fee = fees.get(0);
        assertEquals("2", fee.getWithdraw_fee());
        assertEquals("0", fee.getSweep_fee());
        assertEquals("0", fee.getSweep_internal_fee());
        assertEquals("0", fee.getFailed_sweep_fee());
        assertEquals("0", fee.getFailed_withdraw_fee());
        assertEquals("0", fee.getFailed_sweep_internal_fee());
        assertEquals("PTNO", fee.getFee_type());
    }
}
