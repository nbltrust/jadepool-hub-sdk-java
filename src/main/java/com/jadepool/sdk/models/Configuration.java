package com.jadepool.sdk.models;

import com.jadepool.sdk.Utils;
import org.apache.commons.codec.binary.Base64;

/**
 * 初始化
 */
public class Configuration {
    private String appId;
    private String url;
    private String jadePubKey;
    private String eccKey;
    private String eccKeyEncoder;
    private String authKey;
    private String authKeyEncoder;
    private String language;

    /**
     * 初始化设置
     * @param appId 通信ID，在ADMIN设置
     * @param url 瑶池URL
     * @param jadePubKey 验签瑶池公钥
     * @param eccKey ECC通信私钥
     * @param eccKeyEncoder ECC通信私钥编码方式，可以是HEX或BASE64
     * @param authKey 授权币种私钥
     * @param authKeyEncoder 授权币种私钥编码方式，可以是HEX或BASE64
     * @param language 报错语言
     * @throws Exception
     */
    public Configuration(String appId, String url, String jadePubKey, String language, String eccKey, String eccKeyEncoder, String authKey, String authKeyEncoder) throws Exception {
        if (appId == null
           || url == null
           || jadePubKey == null
           || eccKey == null
           || eccKeyEncoder == null
           || (authKey != null && authKeyEncoder == null)) {
            throw new Exception("Invalid parameter...");
        }
        this.appId = appId;
        this.url = url;
        if (!language.equals("cn") && !language.equals("en")) {
            throw new Exception("Only English and Chinese are supported for now...");
        }
        this.language = language;
        if (!Utils.isHex(jadePubKey) && !Utils.isBase64(jadePubKey)) {
            throw new Exception("Invalid Jadepool Public Key...");
        }
        this.jadePubKey = jadePubKey;

        if (eccKeyEncoder.equalsIgnoreCase("hex") && Utils.isHex(eccKey)) {
            this.eccKey = eccKey;
        } else if (eccKeyEncoder.equalsIgnoreCase("base64") && Utils.isBase64(eccKey)) {
            this.eccKey = Utils.byteArrayToHex(Base64.decodeBase64(eccKey));
        } else {
            throw new Exception("Invalid key or encoder...");
        }

        if (authKey != null) {
            if (authKeyEncoder.equalsIgnoreCase("hex") && Utils.isHex(authKey)) {
                this.authKey = authKey;
            } else if (authKeyEncoder.equalsIgnoreCase("base64") && Utils.isBase64(authKey)) {
                this.authKey = Utils.byteArrayToHex(Base64.decodeBase64(authKey));
            } else {
                throw new Exception("Invalid key or encoder...");
            }
        }

//        if (!language.equals("cn") && !language.equals("en")) {
//            throw new Exception("Only English and Chinese are supported for now...");
//        }
//        this.language = language;
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

    public String getJadePubKey() {
        return jadePubKey;
    }

    public void setJadePubKey(String jadePubKey) {
        this.jadePubKey = jadePubKey;
    }

    public String getEccKeyEncoder() {
        return eccKeyEncoder;
    }

    public void setEccKeyEncoder(String eccKeyEncoder) {
        this.eccKeyEncoder = eccKeyEncoder;
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
