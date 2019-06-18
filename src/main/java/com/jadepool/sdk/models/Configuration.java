package com.jadepool.sdk.models;

import com.jadepool.sdk.Utils;

/**
 * 初始化
 */
public class Configuration {
    private String appId;
    private String url;
    private String jadePubKey;
    private String eccKey;
    private String authKey;

    /**
     * 初始化设置
     * @param appId application ID
     * @param url Jadepool server URL
     * @param eccKey your ECC private key, hex or base64
     * @throws Exception
     */
    public Configuration(String appId, String url, String eccKey) throws Exception {
        if (appId == null
           || url == null) {
            throw new Exception("Invalid parameter...");
        }
        this.appId = appId;
        this.url = url;
        if (!Utils.isBase64(eccKey) || eccKey == null) {
            throw new Exception("Invalid ecc private Key...");
        }
        this.eccKey = eccKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId( String appId) {
        this.appId = appId;
    }

    public String getEccKey() {
        return this.eccKey;
    }

    public void setEccKey( String eccKey) {
        this.eccKey = eccKey;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(String authKey) throws Exception {
        if (!Utils.isBase64(authKey) || authKey == null) {
            throw new Exception("Invalid auth private Key...");
        }
        this.authKey = authKey;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl( String url) {
        this.url = url;
    }

    public String getJadePubKey() {
        return jadePubKey;
    }

    public void setJadePubKey(String jadePubKey) {
        this.jadePubKey = jadePubKey;
    }
}
