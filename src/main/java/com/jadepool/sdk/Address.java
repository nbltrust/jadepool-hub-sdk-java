package com.jadepool.sdk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 瑶池地址model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String address;

    public String getAddress() {
        return this.address;
    }
}
