
package com.example.nidailyas.fitme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("updatetime")
    @Expose
    private Integer updatetime;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("measuregrps")
    @Expose
    private List<Measuregrp> measuregrps;

    public Body() {
    }

    public Integer getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Measuregrp> getMeasuregrps() {
        return measuregrps;
    }

    public void setMeasuregrps(List<Measuregrp> measuregrps) {
        this.measuregrps = measuregrps;
    }

}
