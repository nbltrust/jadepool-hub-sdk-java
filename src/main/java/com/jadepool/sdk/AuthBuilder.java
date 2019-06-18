package com.jadepool.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class AuthBuilder {
    private static short version = 1;
    private static byte algorithmId = 33;

    static String buildWithdrawalAuth(String authKey, long sequence, String coinId, String value, String to, String memo) throws IOException {
        byte[] preHash = authWithdrawalPreHash(sequence, coinId, value, to, memo);
        byte[] sha256ByteArr = Utils.sha256(preHash);
        String withdrawalAuth = Utils.sign(sha256ByteArr, authKey);
        byte[] WithdrawalAuthByteArr = Utils.hexStringToByteArray(withdrawalAuth);
        short WithdrawalAuthLength = (short) WithdrawalAuthByteArr.length;
        byte[] WithdrawalAuthLengthByteArr = Utils.shortToByteArr(WithdrawalAuthLength);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preHash);
        outputStream.write(WithdrawalAuthLengthByteArr);
        outputStream.write(WithdrawalAuthByteArr);
        String Auth = Utils.byteArrayToHex(outputStream.toByteArray());
        return Auth;
    }

    static String buildCoinAuth(String authKey, String coinId, String coinType, String chain, String token, int decimal, String contract) throws IOException {
        byte[] preHash = authCoinPreHash(coinId, coinType, chain, token, decimal, contract);
        byte[] sha256ByteArr = Utils.sha256(preHash);
        String coinAuth = Utils.sign(sha256ByteArr, authKey);

        byte[] coinAuthByteArr = Utils.hexStringToByteArray(coinAuth);
        short coinAuthLength = (short) coinAuthByteArr.length;
        byte[] coinAuthLengthByteArr = Utils.shortToByteArr(coinAuthLength);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preHash);
        outputStream.write(coinAuthLengthByteArr);
        outputStream.write(coinAuthByteArr);
        String Auth = Utils.byteArrayToHex(outputStream.toByteArray());
        return Auth;
    }

    private static byte[] authCoinPreHash(String coinId, String coinType, String chain, String token, int decimal, String contract) throws IOException {
        byte[] versionByteArr = Utils.shortToByteArr(version);
        byte[] algorithmIdByteArr = Utils.byteToByteArr(algorithmId);

        byte[] coinIdByteArr = Utils.stringToByteArr(coinId);
        short coinIdLength = (short) coinIdByteArr.length;
        byte[] coinIdLengthByteArr = Utils.shortToByteArr(coinIdLength);

        byte[] coinTypeByteArr = Utils.stringToByteArr(coinType);
        short coinTypeLength = (short) coinTypeByteArr.length;
        byte[] coinTypeLengthByteArr = Utils.shortToByteArr(coinTypeLength);

        byte[] chainByteArr = Utils.stringToByteArr(chain);
        short chainLength = (short) chainByteArr.length;
        byte[] chainLengthByteArr = Utils.shortToByteArr(chainLength);

        byte[] tokenByteArr = Utils.stringToByteArr(token);
        short tokenLength = (short) tokenByteArr.length;
        byte[] tokenLengthByteArr = Utils.shortToByteArr(tokenLength);

        String convertedDecimal = String.valueOf(decimal);
        byte[] decimalByteArr = Utils.stringToByteArr(convertedDecimal);
        short decimalLength = (short) decimalByteArr.length;
        byte[] decimalLengthByteArr = Utils.shortToByteArr(decimalLength);

        byte[] contractByteArr = null;
        short contractLength = 0;
        if (contract != null) {
            contractByteArr = Utils.stringToByteArr(contract);
            contractLength = (short) contractByteArr.length;
        }
        byte[] contractLengthByteArr = Utils.shortToByteArr(contractLength);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(versionByteArr);
        outputStream.write(algorithmIdByteArr);
        outputStream.write(coinIdLengthByteArr);
        outputStream.write(coinIdByteArr);
        outputStream.write(coinTypeLengthByteArr);
        outputStream.write(coinTypeByteArr);
        outputStream.write(chainLengthByteArr);
        outputStream.write(chainByteArr);
        outputStream.write(tokenLengthByteArr);
        outputStream.write(tokenByteArr);
        outputStream.write(contractLengthByteArr);
        if (contract != null) {
            outputStream.write(contractByteArr);
        }
        outputStream.write(decimalLengthByteArr);
        outputStream.write(decimalByteArr);

        byte[] preHash = outputStream.toByteArray();
        return preHash;
    }

    private static byte[] authWithdrawalPreHash(long sequence, String coinId, String value, String to, String memo) throws IOException {
        byte[] versionByteArr = Utils.shortToByteArr(version);
        byte[] algorithmIdByteArr = Utils.byteToByteArr(algorithmId);
        byte[] sequenceByteArr = Utils.longToByteArr(sequence);
        byte[] coinIdByteArr = Utils.stringToByteArr(coinId);
        short coinIdLength = (short) coinIdByteArr.length;
        byte[] coinIdLengthByteArr = Utils.shortToByteArr(coinIdLength);
        byte[] toByteArr = Utils.stringToByteArr(to);
        short toLength = (short) toByteArr.length;
        byte[] toLengthByteArr = Utils.shortToByteArr(toLength);
        byte[] amountByteArr = Utils.stringToByteArr(value);
        short amountLength = (short) amountByteArr.length;
        byte[] amountLengthByteArr = Utils.shortToByteArr(amountLength);
        byte[] memoByteArr = null;
        short memoLength = 0;
        if (memo != null) {
            memoByteArr = Utils.stringToByteArr(memo);
            memoLength = (short) memoByteArr.length;
        }
        byte[] memoLengthByteArr = Utils.shortToByteArr(memoLength);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(versionByteArr);
        outputStream.write(algorithmIdByteArr);
        outputStream.write(sequenceByteArr);
        outputStream.write(coinIdLengthByteArr);
        outputStream.write(coinIdByteArr);
        outputStream.write(toLengthByteArr);
        outputStream.write(toByteArr);
        outputStream.write(amountLengthByteArr);
        outputStream.write(amountByteArr);
        outputStream.write(memoLengthByteArr);
        if (memo != null) {
            outputStream.write(memoByteArr);
        }
        byte[] preHash = outputStream.toByteArray();
        return preHash;
    }
}
