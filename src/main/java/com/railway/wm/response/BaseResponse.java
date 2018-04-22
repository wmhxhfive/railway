package com.railway.wm.response;

import java.io.Serializable;
import java.util.List;

public class BaseResponse implements Serializable {
    String ret;
    String message;


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


}
