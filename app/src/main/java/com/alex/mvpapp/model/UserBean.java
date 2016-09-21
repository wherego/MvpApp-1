package com.alex.mvpapp.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
public class UserBean implements Parcelable {
    public String phone;
    public String pwd;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.pwd);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.phone = in.readString();
        this.pwd = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
