package com.railway.wm.response;

import java.io.Serializable;
import java.util.List;

public class BaseResponse implements Serializable {
    String ret;
    String message;
    List date;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getDate() {
        return date;
    }

    public void setDate(List date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "ret='" + ret + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
