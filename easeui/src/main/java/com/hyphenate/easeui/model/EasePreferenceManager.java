package com.hyphenate.easeui.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.EMLog;

import java.util.HashSet;
import java.util.Set;

public class EasePreferenceManager {
    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;
    private static final String KEY_AT_GROUPS = "AT_GROUPS";
    private static final String KEY_SUB_STATUS = "SUB_STATUS";

    @SuppressLint("CommitPrefEdits")
    private EasePreferenceManager() {
        mSharedPreferences = EaseUI.getInstance().getContext().getSharedPreferences("EM_SP_AT_MESSAGE", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    private static EasePreferenceManager instance;

    public synchronized static EasePreferenceManager getInstance() {
        if (instance == null) {
            instance = new EasePreferenceManager();
        }
        return instance;

    }


    public void setAtMeGroups(Set<String> groups) {
        editor.remove(KEY_AT_GROUPS);
        editor.putStringSet(KEY_AT_GROUPS, groups);
        editor.apply();
    }

    public Set<String> getAtMeGroups() {
        return mSharedPreferences.getStringSet(KEY_AT_GROUPS, null);
    }

    //已订阅用户状态
    public void setSubscribedUserStatus(String id, String isOnline) {
        synchronized (this) {
            Set<String> subscribedUserStatus = getSubscribedUsers();
            if (subscribedUserStatus == null) {
                subscribedUserStatus = new HashSet<>();
            }
            subscribedUserStatus.add(id);
            editor.remove(KEY_SUB_STATUS);
            editor.putStringSet(KEY_SUB_STATUS, subscribedUserStatus);
            editor.putBoolean(id, "online".equalsIgnoreCase(isOnline));
            editor.apply();
        }
    }

    //取消订阅
    public void removeSubscribedUser(String id) {
        synchronized (this) {
            Set<String> subscribedUserStatus = getSubscribedUsers();
            if (subscribedUserStatus == null) {
                subscribedUserStatus = new HashSet<>();
            }
            editor.remove(KEY_SUB_STATUS);
            subscribedUserStatus.remove(id);
            editor.putStringSet(KEY_SUB_STATUS, subscribedUserStatus);
            editor.remove(id);
            editor.apply();
        }
    }

    //获取所有已订阅用户
    public Set<String> getSubscribedUsers() {
        return mSharedPreferences.getStringSet(KEY_SUB_STATUS, null);
    }

    //获取用户状态
    public boolean getSubscribedUserStatus(String id) {
        return mSharedPreferences.getBoolean(id, false);
    }
}
