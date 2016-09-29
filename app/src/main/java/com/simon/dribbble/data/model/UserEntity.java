package com.simon.dribbble.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录成功后获取用户信息
 */
public class UserEntity implements Parcelable{
    /**
     * 用户ID
     */
    public long id;
    public int likes_count;
    public int followers_count;
    public int shots_count;
    /**
     * 用户真实姓名
     */
    public String name;
    /**
     * 用户名称
     */
    public String username;
    public String html_url;
    /**
     * 用户头像
     */
    public String avatar_url;

    public String bio;
    public String shots_url;
    public String location;


    private LinksEntity links;
    private int buckets_count;
    private int comments_received_count;
    private int followings_count;
    private int likes_received_count;
    private int projects_count;
    private int rebounds_received_count;
    private int teams_count;
    private boolean can_upload_shot;
    private String type;
    private boolean pro;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String projects_url;
    private String teams_url;
    private String created_at;
    private String updated_at;

    protected UserEntity(Parcel in) {
        id = in.readLong();
        likes_count = in.readInt();
        followers_count = in.readInt();
        shots_count = in.readInt();
        name = in.readString();
        username = in.readString();
        html_url = in.readString();
        avatar_url = in.readString();
        bio = in.readString();
        shots_url = in.readString();
        location = in.readString();
        links = in.readParcelable(LinksEntity.class.getClassLoader());
        buckets_count = in.readInt();
        comments_received_count = in.readInt();
        followings_count = in.readInt();
        likes_received_count = in.readInt();
        projects_count = in.readInt();
        rebounds_received_count = in.readInt();
        teams_count = in.readInt();
        can_upload_shot = in.readByte() != 0;
        type = in.readString();
        pro = in.readByte() != 0;
        buckets_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        likes_url = in.readString();
        projects_url = in.readString();
        teams_url = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(likes_count);
        dest.writeInt(followers_count);
        dest.writeInt(shots_count);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(html_url);
        dest.writeString(avatar_url);
        dest.writeString(bio);
        dest.writeString(shots_url);
        dest.writeString(location);
        dest.writeParcelable(links, flags);
        dest.writeInt(buckets_count);
        dest.writeInt(comments_received_count);
        dest.writeInt(followings_count);
        dest.writeInt(likes_received_count);
        dest.writeInt(projects_count);
        dest.writeInt(rebounds_received_count);
        dest.writeInt(teams_count);
        dest.writeByte((byte) (can_upload_shot ? 1 : 0));
        dest.writeString(type);
        dest.writeByte((byte) (pro ? 1 : 0));
        dest.writeString(buckets_url);
        dest.writeString(followers_url);
        dest.writeString(following_url);
        dest.writeString(likes_url);
        dest.writeString(projects_url);
        dest.writeString(teams_url);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
