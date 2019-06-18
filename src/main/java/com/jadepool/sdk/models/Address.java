package com.jadepool.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 瑶池地址model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private long id;

    private String address;

    private String appid;

    private String wallet;

    private String type;

    private String mode;

    private String state;

    public String getAddress() {
        return this.address;
    }

    public long getId() {
        return id;
    }

    public String getAppid() {
        return appid;
    }

    public String getWallet() {
        return wallet;
    }

    public String getType() {
        return type;
    }

    public String getMode() {
        return mode;
    }

    public String getState() {
        return state;
    }
}
