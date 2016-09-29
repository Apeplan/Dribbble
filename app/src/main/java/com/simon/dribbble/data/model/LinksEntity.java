package com.simon.dribbble.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LinksEntity implements Parcelable {
    private String web;
    private String twitter;

    protected LinksEntity(Parcel in) {
        web = in.readString();
        twitter = in.readString();
    }

    public static final Creator<LinksEntity> CREATOR = new Creator<LinksEntity>() {
        @Override
        public LinksEntity createFromParcel(Parcel in) {
            return new LinksEntity(in);
        }

        @Override
        public LinksEntity[] newArray(int size) {
            return new LinksEntity[size];
        }
    };

    public void setWeb(String web) {
        this.web = web;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWeb() {
        return web;
    }

    public String getTwitter() {
        return twitter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(web);
        dest.writeString(twitter);
    }
}