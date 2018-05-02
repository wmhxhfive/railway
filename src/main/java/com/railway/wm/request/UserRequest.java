package com.railway.wm.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserRequest implements Serializable {
    @NotNull
    private String name;
    private String moblie;
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
