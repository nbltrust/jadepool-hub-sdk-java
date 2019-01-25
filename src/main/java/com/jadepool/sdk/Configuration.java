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
    private String language;

    /**
     * 初始化设置
     * @param appId 通信ID，在ADMIN设置
     * @param url 瑶池URL
     * @param eccKey ECC通信私钥
     * @param eccKeyEncoder ECC通信私钥编码方式，可以是HEX或BASE64
     * @throws Exception
     */
    public Configuration(String appId, String url, String eccKey, String eccKeyEncoder, String language) throws Exception {
        if (appId == null || url == null || eccKey == null || eccKeyEncoder == null || language == null) {
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
