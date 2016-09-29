package com.simon.dribbble.data.model;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 15:38
 */

public class UserLikeEntity {

    private long id;
    private String created_at;

    private ShotEntity shot;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ShotEntity getShot() {
        return shot;
    }

    public void setShot(ShotEntity shot) {
        this.shot = shot;
    }

}
