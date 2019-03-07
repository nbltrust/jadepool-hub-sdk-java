package com.jadepool.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 瑶池订单model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String id;
    private Long sequence;
    private String state;
    private String bizType;
    private String type;
    private String subType;
    private String coinType;
    private String from;
    private String to;
    private Integer n;
    private String value;
    private String hash;
    private Long block;
    private List<Fee> fees;
    private Long confirmations;
    private String callback;
    private String extraData;
    private Boolean sendAgain;
    private Long create_at;
    private Long update_at;
    private TxData txData;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fee {
        private String coinName;
        private String nativeName;
        private String amount;

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getNativeName() {
            return nativeName;
        }

        public void setNativeName(String nativeName) {
            this.nativeName = nativeName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TxData {
        private ArrayList<Io> from;
        private ArrayList<Io> to;

        public ArrayList<Io> getFrom() {
            return from;
        }

        public ArrayList<Io> getTo() {
            return to;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Io {
            private String address;
            private String value;
            private String txid;
            private int n;
            private String asset;
            private String contract;
            private String symbol;
            private String tokenName;
            private String coinName;

            public String getAddress() {
                return address;
            }

            public String getValue() {
                return value;
            }

            public String getTxid() { return txid; }

            public int getN() {
                return n;
            }

            public String getAsset() {
                return asset;
            }

            public String getContract() {
                return contract;
            }

            public String getSymbol() {
                return symbol;
            }

            public String getTokenName() {
                return tokenName;
            }

            public String getCoinName() {
                return coinName;
            }
        }
    }


    public String getId() {
        return id;
    }

    public Integer getN() {
        return n;
    }

    public Boolean getSendAgain() {
        return sendAgain;
    }

    public Long getSequence() {
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

    public Long getBlock() {
        return block;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public Long getConfirmations() {
        return confirmations;
    }

    public String getCallback() {
        return callback;
    }

    public String getExtraData() {
        return extraData;
    }

    public Boolean isSendAgain() {
        return sendAgain;
    }

    public Long getCreate_at() {
        return create_at;
    }

    public Long getUpdate_at() {
        return update_at;
    }

    public TxData getTxData() {
        return txData;
    }

    public void setTxData(TxData txData) {
        this.txData = txData;
    }
}
