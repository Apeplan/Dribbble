package com.simon.dribbble.data.model;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/2/25 10:26
 */

public class TeamEntity {

    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;
    /**
     * web : http://dribbble.com
     * twitter : https://twitter.com/dribbble
     */

    private LinksEntity links;
    private int buckets_count;
    private int comments_received_count;
    private int followers_count;
    private int followings_count;
    private int likes_count;
    private int likes_received_count;
    private int members_count;
    private int projects_count;
    private int rebounds_received_count;
    private int shots_count;
    private boolean can_upload_shot;
    private String type;
    private boolean pro;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String members_url;
    private String shots_url;
    private String team_shots_url;
    private String created_at;
    private String updated_at;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLinks(LinksEntity links) {
        this.links = links;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public void setComments_received_count(int comments_received_count) {
        this.comments_received_count = comments_received_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setFollowings_count(int followings_count) {
        this.followings_count = followings_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public void setLikes_received_count(int likes_received_count) {
        this.likes_received_count = likes_received_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }

    public void setProjects_count(int projects_count) {
        this.projects_count = projects_count;
    }

    public void setRebounds_received_count(int rebounds_received_count) {
        this.rebounds_received_count = rebounds_received_count;
    }

    public void setShots_count(int shots_count) {
        this.shots_count = shots_count;
    }

    public void setCan_upload_shot(boolean can_upload_shot) {
        this.can_upload_shot = can_upload_shot;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public void setMembers_url(String members_url) {
        this.members_url = members_url;
    }

    public void setShots_url(String shots_url) {
        this.shots_url = shots_url;
    }

    public void setTeam_shots_url(String team_shots_url) {
        this.team_shots_url = team_shots_url;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public LinksEntity getLinks() {
        return links;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public int getMembers_count() {
        return members_count;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public int getShots_count() {
        return shots_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public boolean isPro() {
        return pro;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getMembers_url() {
        return members_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public String getTeam_shots_url() {
        return team_shots_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public static class LinksEntity {
        private String web;
        private String twitter;

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
    }
}
