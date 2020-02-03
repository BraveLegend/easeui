package com.hyphenate.easeui.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.hyphenate.easeui.EaseUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EasePreferenceManager {
    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;
    private static final String KEY_AT_GROUPS = "AT_GROUPS";
    private static final String KEY_NOTICE_STATUS = "NOTICE_STATUS";

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

    //保存用户状态
    public void setNoticeUserStatus(String id, String isOnline) {
        synchronized (this) {
            Set<String> noticeUserStatus = getNoticeUsers();
            if (noticeUserStatus == null) {
                noticeUserStatus = new HashSet<>();
            }
            noticeUserStatus.add(id);
            editor.remove(KEY_NOTICE_STATUS);
            editor.putStringSet(KEY_NOTICE_STATUS, noticeUserStatus);
            editor.putBoolean(id, "online".equalsIgnoreCase(isOnline));
            editor.apply();
        }
    }

    //从通知自己的用户列表中移除用户
    public void removeNoticeUser(String id) {
        synchronized (this) {
            Set<String> noticeUserStatus = getNoticeUsers();
            if (noticeUserStatus == null) {
                noticeUserStatus = new HashSet<>();
            }
            editor.remove(KEY_NOTICE_STATUS);
            noticeUserStatus.remove(id);
            editor.putStringSet(KEY_NOTICE_STATUS, noticeUserStatus);
            editor.remove(id);
            editor.apply();
        }
    }

    //移除所有通知自己的用户集合
    public void removeNoticeUsers() {
        synchronized (this) {
            Set<String> noticeUsers = getNoticeUsers();
            for (String id : noticeUsers) {
                editor.remove(id);
            }
            editor.remove(KEY_NOTICE_STATUS);
            editor.apply();
        }
    }

    //获取所有通知自己状态的用户集合
    public Set<String> getNoticeUsers() {
        return mSharedPreferences.getStringSet(KEY_NOTICE_STATUS, null);
    }

    //获取用户状态
    public boolean getNoticeUserStatus(String id) {
        return mSharedPreferences.getBoolean(id, false);
    }

}
