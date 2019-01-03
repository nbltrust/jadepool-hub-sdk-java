package com.jadepool.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 瑶池订单model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String id;
    private long sequence;
    private String state;
    private String bizType;
    private String type;
    private String subType;
    private String coinType;
    private String from;
    private String to;
    private String value;
    private String hash;
    private long block;
    private String fee;
    private long confirmations;
    private String callback;
    private String extraData;
    private boolean sendAgain;
    private long create_at;
    private long update_at;

    public String getId() {
        return id;
    }

    public long getSequence() {
        return sequence;
    }

    public String getState() {
        return state;
    }

    public String getBizType() {
        return bizType;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getCoinType() {
        return coinType;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public String getHash() {
        return hash;
    }

    public long getBlock() {
        return block;
    }

    public String getFee() {
        return fee;
    }

    public long getConfirmations() {
        return confirmations;
    }

    public String getCallback() {
        return callback;
    }

    public String getExtraData() {
        return extraData;
    }

    public boolean isSendAgain() {
        return sendAgain;
    }

    public long getCreate_at() {
        return create_at;
    }

    public long getUpdate_at() {
        return update_at;
    }
}
