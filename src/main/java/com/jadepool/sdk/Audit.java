package com.jadepool.sdk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 瑶池审计结果model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {
    private boolean calculated;
    private String id;
    private String type;
    private long blocknumber;
    private long timestamp;
    private long calc_order_num;
    private String create_at;
    private String update_at;

    private String deposit_total;
    private long deposit_num;
    private String withdraw_total;
    private long withdraw_num;
    private String sweep_total;
    private long sweep_num;
    private String sweep_internal_total;
    private long sweep_internal_num;
    private String airdrop_total;
    private long airdrop_num;
    private String recharge_total;
    private long recharge_num;
    private String recharge_internal_total;
    private long recharge_internal_num;
    private String recharge_unknown_total;
    private long recharge_unknown_num;
    private String recharge_special_total;
    private long recharge_special_num;

    private String fee_type;
    private String fee_total;
    private String sweep_fee;
    private String sweep_internal_fee;
    private String internal_fee;
    private long internal_num;
    private String extend_fee_total;
    private String extend_sweep_fee_total;
    private String extend_sweep_internal_fee_total;
    private String extend_internal_fee_total;

    private String failed_fee_withdraw;
    private long failed_withdraw_num;
    private String failed_sweep_fee;
    private long failed_sweep_num;
    private String failed_sweep_internal_fee;
    private long failed_sweep_internal_num;
    private String failed_fee_internal;
    private long failed_internal_num;
    private String extend_failed_fee_withdraw;
    private String extend_failed_sweep_fee;
    private String extend_failed_sweep_internal_fee;
    private String extend_failed_fee_internal;

    public boolean isCalculated() {
        return calculated;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getBlocknumber() {
        return blocknumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getCalc_order_num() {
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

    public long getDeposit_num() {
        return deposit_num;
    }

    public String getWithdraw_total() {
        return withdraw_total;
    }

    public long getWithdraw_num() {
        return withdraw_num;
    }

    public String getSweep_total() {
        return sweep_total;
    }

    public long getSweep_num() {
        return sweep_num;
    }

    public String getSweep_internal_total() {
        return sweep_internal_total;
    }

    public long getSweep_internal_num() {
        return sweep_internal_num;
    }

    public String getAirdrop_total() {
        return airdrop_total;
    }

    public long getAirdrop_num() {
        return airdrop_num;
    }

    public String getRecharge_total() {
        return recharge_total;
    }

    public long getRecharge_num() {
        return recharge_num;
    }

    public String getRecharge_internal_total() {
        return recharge_internal_total;
    }

    public long getRecharge_internal_num() {
        return recharge_internal_num;
    }

    public String getRecharge_unknown_total() {
        return recharge_unknown_total;
    }

    public long getRecharge_unknown_num() {
        return recharge_unknown_num;
    }

    public String getRecharge_special_total() {
        return recharge_special_total;
    }

    public long getRecharge_special_num() {
        return recharge_special_num;
    }

    public String getFee_type() {
        return fee_type;
    }

    public String getFee_total() {
        return fee_total;
    }

    public String getSweep_fee() {
        return sweep_fee;
    }

    public String getSweep_internal_fee() {
        return sweep_internal_fee;
    }

    public String getInternal_fee() {
        return internal_fee;
    }

    public long getInternal_num() {
        return internal_num;
    }

    public String getExtend_fee_total() {
        return extend_fee_total;
    }

    public String getExtend_sweep_fee_total() {
        return extend_sweep_fee_total;
    }

    public String getExtend_sweep_internal_fee_total() {
        return extend_sweep_internal_fee_total;
    }

    public String getExtend_internal_fee_total() {
        return extend_internal_fee_total;
    }

    public String getFailed_fee_withdraw() {
        return failed_fee_withdraw;
    }

    public long getFailed_withdraw_num() {
        return failed_withdraw_num;
    }

    public String getFailed_sweep_fee() {
        return failed_sweep_fee;
    }

    public long getFailed_sweep_num() {
        return failed_sweep_num;
    }

    public String getFailed_sweep_internal_fee() {
        return failed_sweep_internal_fee;
    }

    public long getFailed_sweep_internal_num() {
        return failed_sweep_internal_num;
    }

    public String getFailed_fee_internal() {
        return failed_fee_internal;
    }

    public long getFailed_internal_num() {
        return failed_internal_num;
    }

    public String getExtend_failed_fee_withdraw() {
        return extend_failed_fee_withdraw;
    }

    public String getExtend_failed_sweep_fee() {
        return extend_failed_sweep_fee;
    }

    public String getExtend_failed_sweep_internal_fee() {
        return extend_failed_sweep_internal_fee;
    }

    public String getExtend_failed_fee_internal() {
        return extend_failed_fee_internal;
    }
}