
package com.example.nidailyas.fitme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithingsData {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("body")
    @Expose
    private Body body;

    public WithingsData() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}
