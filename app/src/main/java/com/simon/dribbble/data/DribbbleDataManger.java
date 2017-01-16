package com.simon.dribbble.data;

import com.simon.agiledevelop.ServiceHelper;
import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.data.model.FollowersEntity;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.model.ProjectEntity;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.model.TeamEntity;
import com.simon.dribbble.data.model.TokenEntity;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.data.model.UserLikeEntity;
import com.simon.dribbble.data.remote.DribbbleService;
import com.simon.dribbble.util.DribbblePrefs;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class DribbbleDataManger {

    private final DribbbleService mDribbbleService;

    private DribbbleDataManger() {
        mDribbbleService = DribbbleService.Creator.dribbbleApi();
    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder {
        private static final DribbbleDataManger INSTANCE = new DribbbleDataManger();
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static DribbbleDataManger getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 认证用户，并获取用户信息
     *
     * @param code
     * @return
     */
    public Observable<User> getTokenAndUser(String code) {

        return DribbbleService.Creator.signIn().getToken(Api.CLIENT_ID, Api.CLIENT_SECRET, code,
                Api.CALLBACK_URL).concatMap(new Func1<TokenEntity,
                Observable<? extends User>>() {

            @Override
            public Observable<? extends User> call(TokenEntity tokenEntity) {

                if (null != tokenEntity) {
                    String access_token = tokenEntity.access_token;
                    DribbblePrefs.getInstance().setAccessToken(access_token);
                }

                DribbbleService creator = ServiceHelper.getInstance().creator(Api.SIGNIN_URL,
                        DribbbleService.class);
                return creator.getUserInfo();
            }
        });
    }

    /**
     * 获取 Shots 列表
     *
     * @param page      请求的页数
     * @param list      类型限制：animated、attachments、debuts、playoffs、rebounds、teams
     * @param timeframe 时间：week、month、year、ever
     * @param sort      排序：comments、recent、views
     * @return
     */
    public Observable<List<ShotEntity>> getShotsList(int page, @DribbbleService.ShotType String
            list, String timeframe, String sort) {
        return mDribbbleService.getShots(page, list, timeframe, sort);
    }

    /**
     * 根据 id 获取 Shot 信息
     *
     * @param shotId id
     * @return
     */
    public Observable<ShotEntity> getShot(long shotId) {
        return mDribbbleService.getShot(shotId);
    }

    /**
     * 获取评论列表
     *
     * @param shotId
     * @param page
     * @return
     */
    public Observable<List<CommentEntity>> getComments(long shotId, String type, int page) {
        return mDribbbleService.getShotComments(shotId, type, page);
    }

    public Observable<CommentEntity> createComment(long shotId, String content) {
        return mDribbbleService.createComment(shotId, content);
    }

    /**
     * 获取用户的 Buckets
     *
     * @return
     */
    public Observable<List<BucketEntity>> getUserBuckets() {
        return mDribbbleService.getUserBuckets();
    }

    /**
     * 获取用的 Followers
     *
     * @param page
     * @return
     */
    public Observable<List<FollowersEntity>> getUserFollowers(int page) {
        return mDribbbleService.getUserFollowers(page);
    }

    /**
     * 获取用户的 Likes
     *
     * @return
     */
    public Observable<List<ShotEntity>> getUserLikes(int page) {
        return mDribbbleService.getUserLikes(page).map(new Func1<List<UserLikeEntity>, List<ShotEntity>>() {
                    @Override
                    public List<ShotEntity> call(List<UserLikeEntity> userLikeEntities) {
                        List<ShotEntity> shots = new ArrayList<ShotEntity>();
                        for (UserLikeEntity ue : userLikeEntities) {
                            shots.add(ue.getShot());
                        }
                        return shots;
                    }
                });
    }

    /**
     * 获取用户的 Projects
     *
     * @return
     */
    public Observable<List<ProjectEntity>> getUserProjects() {
        return mDribbbleService.getUserProjects();
    }

    /**
     * 获取用户的 Shots
     *
     * @param page
     * @return
     */
    public Observable<List<ShotEntity>> getUserShots(int page) {
        return mDribbbleService.getUserShots(page);
    }

    /**
     * 获取用户的 Teams
     *
     * @return
     */
    public Observable<List<TeamEntity>> getUserTeams() {
        return mDribbbleService.getUserTeams();
    }

    public Observable<LikeEntity> addLike(long shotId) {
        return mDribbbleService.addLike(shotId, null);
    }

    public Observable<List<LikeEntity>> getShotLikes(long id, int page) {
        return mDribbbleService.getShotLikes(id, page);
    }

    public Observable<List<AttachmentEntity>> getShotAttach(long id, int page) {
        return mDribbbleService.getShotAttach(id, page);
    }

    public Observable<List<BucketEntity>> getBuckets(String type, long id, int page) {
        return mDribbbleService.getBuckets(type, id, page);
    }


    public Observable<User> getUsersInfo(long userId) {
        return mDribbbleService.getUsers(userId);
    }

    public Observable<List<ShotEntity>> search(String key, int resultPage, @DribbbleService
            .SortOrder String sort) {

        return ServiceHelper.getInstance().creator(Api.SIGNIN_URL, DribbbleService.class)
                .search(key, resultPage, 12, sort);
    }

}
