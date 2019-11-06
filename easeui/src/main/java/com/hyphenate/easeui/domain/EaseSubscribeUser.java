package com.hyphenate.easeui.domain;

/**
 * @author qby
 * @date 2019/10/28 16:38
 */
public class EaseSubscribeUser extends EaseUser{

    protected boolean isOnLine;
    protected boolean isSubscribe;
    public EaseSubscribeUser(String username) {
        super(username);
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }
}
