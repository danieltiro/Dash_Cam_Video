package com.rodimisas.dash_cam_video.Responce;

public class LoginPermiso {
    public int code;
    public String data;
    public String msg;

    public LoginPermiso() {
    }

    public LoginPermiso(int code, String data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
