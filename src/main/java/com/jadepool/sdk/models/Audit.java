package com.jadepool.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 瑶池审计结果model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {
    private String id;
    private Boolean calculated;
    private String type;
    private Long blocknumber;
    private Long timestamp;
    private Long calc_order_num;
    private String create_at;
    private String update_at;

    private String deposit_total;
    private Long deposit_num;
    private String withdraw_total;
    private Long withdraw_num;
    private String sweep_total;
    private Long sweep_num;
    private String sweep_internal_total;
    private Long sweep_internal_num;
    private String airdrop_total;
    private Long airdrop_num;
    private String recharge_total;
    private Long recharge_num;
    private String recharge_internal_total;
    private Long recharge_internal_num;
    private String recharge_unknown_total;
    private Long recharge_unknown_num;
    private String recharge_special_total;
    private Long recharge_special_num;
    private Long failed_withdraw_num;
    private Long failed_sweep_num;
    private Long failed_sweep_internal_num;

    private List<Fee> fees;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fee{
        private String fee_type;
        private String withdraw_fee;
        private String sweep_fee;
        private String sweep_internal_fee;
        private String failed_withdraw_fee;
        private String failed_sweep_fee;
        private String failed_sweep_internal_fee;

        public String getFee_type() {
            return fee_type;
        }

        public String getWithdraw_fee() {
            return withdraw_fee;
        }

        public String getSweep_fee() {
            return sweep_fee;
        }

        public String getSweep_internal_fee() {
            return sweep_internal_fee;
        }

        public String getFailed_withdraw_fee() {
            return failed_withdraw_fee;
        }

        public String getFailed_sweep_fee() {
            return failed_sweep_fee;
        }

        public String getFailed_sweep_internal_fee() {
            return failed_sweep_internal_fee;
        }
    }

    public List<Fee> getFees() {
        return fees;
    }

    public Boolean isCalculated() {
        return calculated;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Long getBlocknumber() {
        return blocknumber;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getCalc_order_num() {
        return calc_order_num;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public String getDeposit_total() {
        return deposit_total;
    }

    public Long getDeposit_num() {
        return deposit_num;
    }

    public String getWithdraw_total() {
        return withdraw_total;
    }

    public Long getWithdraw_num() {
        return withdraw_num;
    }

    public String getSweep_total() {
        return sweep_total;
    }

    public Long getSweep_num() {
        return sweep_num;
    }

    public String getSweep_internal_total() {
        return sweep_internal_total;
    }

    public Long getSweep_internal_num() {
        return sweep_internal_num;
    }

    public String getAirdrop_total() {
        return airdrop_total;
    }

    public Long getAirdrop_num() {
        return airdrop_num;
    }

    public String getRecharge_total() {
        return recharge_total;
    }

    public Long getRecharge_num() {
        return recharge_num;
    }

    public String getRecharge_internal_total() {
        return recharge_internal_total;
    }

    public Long getRecharge_internal_num() {
        return recharge_internal_num;
    }

    public String getRecharge_unknown_total() {
        return recharge_unknown_total;
    }

    public Long getRecharge_unknown_num() {
        return recharge_unknown_num;
    }

    public String getRecharge_special_total() {
        return recharge_special_total;
    }

    public Long getRecharge_special_num() {
        return recharge_special_num;
    }

    public Long getFailed_withdraw_num() {
        return failed_withdraw_num;
    }

    public Long getFailed_sweep_num() {
        return failed_sweep_num;
    }

    public Long getFailed_sweep_internal_num() {
        return failed_sweep_internal_num;
    }

}