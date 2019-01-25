package com.jadepool.sdk;

import org.apache.commons.codec.binary.Base64;

/**
 * 初始化
 */
public class Configuration {
    private String appId;
    private String url;
    private String eccKey;
    private String eccKeyEncoder;
    private boolean hsm;
    private String authKey;
    private String authKeyEncoder;
    private String language;

    /**
     * 初始化设置
     * @param appId 通信ID，在ADMIN设置
     * @param url 瑶池URL
     * @param eccKey ECC通信私钥
     * @param eccKeyEncoder ECC通信私钥编码方式，可以是HEX或BASE64
     * @param hsm 是否使用密码机
     * @param authKey 授权币种私钥
     * @param authKeyEncoder 授权币种私钥编码方式，可以是HEX或BASE64
     * @param language 报错语言
     * @throws Exception
     */
    public Configuration(String appId, String url, String eccKey, String eccKeyEncoder, String language, boolean hsm, String authKey, String authKeyEncoder) throws Exception {
        if (appId == null
                || url == null
                || eccKey == null
                || eccKeyEncoder == null
                || (hsm == true && (authKey == null || authKeyEncoder == null))
                || language == null) {
            throw new Exception("Invalid parameter...");
        }
        this.appId = appId;
        this.url = url;
        if (!language.equals("cn") && !language.equals("en")) {
            throw new Exception("Only English and Chinese are supported for now...");
        }
        this.language = language;
        if (eccKeyEncoder.equalsIgnoreCase("hex")) {
            if (!Utils.isHex(eccKey)) {
                throw new Exception("Invalid ecc key format...");
            }
            this.eccKey = eccKey;
        } else if (eccKeyEncoder.equalsIgnoreCase("base64")) {
            if (!Utils.isBase64(eccKey)) {
                throw new Exception("Invalid ecc key format...");
            }
            this.eccKey = Utils.byteArrayToHex(Base64.decodeBase64(eccKey));
        } else {
            throw new Exception("Only hex and base64 key formats are supported...");
        }
        if (hsm == true) {
            if (authKeyEncoder.equalsIgnoreCase("hex")) {
                if (!Utils.isHex(authKey)) {
                    throw new Exception("Invalid auth key format...");
                }
                this.authKey = authKey;
            } else if (authKeyEncoder.equalsIgnoreCase("base64")) {
                if (!Utils.isBase64(authKey)) {
                    throw new Exception("Invalid auth key format...");
                }
                this.authKey = Utils.byteArrayToHex(Base64.decodeBase64(authKey));
            } else {
                throw new Exception("Only hex and base64 key formats are supported...");
            }
        }
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(final String appId) {
        this.appId = appId;
    }

    public String getEccKey() {
        return this.eccKey;
    }

    public void setEccKey(final String eccKey) {
        this.eccKey = eccKey;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(final String authKey) {
        this.authKey = authKey;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getEccKeyEncoder() {
        return eccKeyEncoder;
    }

    public void setEccKeyEncoder(String eccKeyEncoder) {
        this.eccKeyEncoder = eccKeyEncoder;
    }

    public boolean isHsm() {
        return hsm;
    }

    public void setHsm(boolean hsm) {
        this.hsm = hsm;
    }

    public String getAuthKeyEncoder() {
        return authKeyEncoder;
    }

    public void setAuthKeyEncoder(String authKeyEncoder) {
        this.authKeyEncoder = authKeyEncoder;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
