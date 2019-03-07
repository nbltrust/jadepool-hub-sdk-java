package com.jadepool.sdk.models;

import org.json.simple.JSONObject;

public class Resolver {
    private Long code;
    private String message;
    private JSONObject object;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }
}
