package com.karawalaya.alliantbankapp.POJO_MODEL.user_management;

import java.io.Serializable;

public class OnlineUser implements Serializable {
    private String customerId;
    private int onlineUserId;
    private String userName;
    private String password;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getOnlineUserId() {
        return onlineUserId;
    }

    public void setOnlineUserId(int onlineUserId) {
        this.onlineUserId = onlineUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
