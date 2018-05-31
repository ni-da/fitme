package com.example.nidailyas.fitme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measure {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("unit")
    @Expose
    private Integer unit;
    @SerializedName("algo")
    @Expose
    private Integer algo;
    @SerializedName("fw")
    @Expose
    private Integer fw;
    @SerializedName("fm")
    @Expose
    private Integer fm;



    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getAlgo() {
        return algo;
    }

    public void setAlgo(Integer algo) {
        this.algo = algo;
    }

    public Integer getFw() {
        return fw;
    }

    public void setFw(Integer fw) {
        this.fw = fw;
    }

    public Integer getFm() {
        return fm;
    }

    public void setFm(Integer fm) {
        this.fm = fm;
    }

}
