
package com.example.nidailyas.fitme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Measuregrp {

    @SerializedName("grpid")
    @Expose
    private Integer grpid;
    @SerializedName("attrib")
    @Expose
    private Integer attrib;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("brand")
    @Expose
    private Integer brand;
    @SerializedName("modified")
    @Expose
    private Integer modified;
    @SerializedName("deviceid")
    @Expose
    private Object deviceid;
    @SerializedName("measures")
    @Expose
    private List<Measure> measures;

    public Measuregrp() {
    }

    public Integer getGrpid() {
        return grpid;
    }

    public void setGrpid(Integer grpid) {
        this.grpid = grpid;
    }

    public Integer getAttrib() {
        return attrib;
    }

    public void setAttrib(Integer attrib) {
        this.attrib = attrib;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }

    public Object getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Object deviceid) {
        this.deviceid = deviceid;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

}
