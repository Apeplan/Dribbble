package com.simon.dribbble.data.model;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/2/25 10:10
 */

public class ShotEntity {
    /**
     * id
     */
    private long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * normal 图片的宽度
     */
    private int width;
    /**
     * normal 图片的高度
     */
    private int height;

    /**
     * 图片地址：hdpi、normal、teaser
     */
    private ImagesEntity images;
    private int views_count;
    private int likes_count;
    private int comments_count;
    private int attachments_count;
    private int rebounds_count;
    private int buckets_count;
    /**
     * 创建时间
     */
    private String created_at;
    /**
     * 更新时间
     */
    private String updated_at;
    private String html_url;
    private String attachments_url;
    private String buckets_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String rebounds_url;
    private boolean animated;


    private UserEntity user;
    private TeamEntity team;
    private String[] tags;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setAttachments_count(int attachments_count) {
        this.attachments_count = attachments_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setAttachments_url(String attachments_url) {
        this.attachments_url = attachments_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public void setProjects_url(String projects_url) {
        this.projects_url = projects_url;
    }

    public void setRebounds_url(String rebounds_url) {
        this.rebounds_url = rebounds_url;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public int getViews_count() {
        return views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getAttachments_count() {
        return attachments_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAttachments_url() {
        return attachments_url;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public String getRebounds_url() {
        return rebounds_url;
    }

    public boolean isAnimated() {
        return animated;
    }

    public UserEntity getUser() {
        return user;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public String[] getTags() {
        return tags;
    }

}
