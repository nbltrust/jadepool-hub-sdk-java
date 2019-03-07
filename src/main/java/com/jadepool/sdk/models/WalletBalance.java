package com.jadepool.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 瑶池余额model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletBalance {
    private String balance;
    private String balanceAvailable;
    private String balanceUnavailable;
    private long update_at;

    public String getBalance() {
        return balance;
    }

    public String getBalanceAvailable() {
        return balanceAvailable;
    }

    public String getBalanceUnavailable() {
        return balanceUnavailable;
    }

    public long getUpdate_at() {
        return update_at;
    }
}
