package com.example.genesis_trainer_device_improved.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ServerResponse {


    @SerializedName("state")
    @Expose
    private String state;


    @SerializedName("login_id")
    @Expose
    private Integer login_id;

    @SerializedName("list_device")
    @Expose
    private List<Object> list_device = new ArrayList<>();

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ServerResponse withState(String state) {
        this.state = state;
        return this;
    }

    public Integer getLogin_id() {
        return login_id;
    }

    public void setLogin_id(Integer login_id) {
        this.login_id = login_id;
    }

    public ServerResponse withLoginId(Integer loginId) {
        this.login_id = loginId;
        return this;
    }

    public List<Object> getList_device() {
        return list_device;
    }
//
//    public void setList_device(List<Object> list_device) {
//        this.list_device = list_device;
//    }

    public ServerResponse withListDevice(List<Object> listDevice) {
//        this.list_device = listDevice;
        return this;
    }

}
