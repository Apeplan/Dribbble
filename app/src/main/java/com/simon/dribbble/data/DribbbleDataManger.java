package com.simon.dribbble.data;

import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.data.model.FollowersEntity;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.model.ProjectEntity;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.model.TeamEntity;
import com.simon.dribbble.data.model.TokenEntity;
import com.simon.dribbble.data.model.UserEntity;
import com.simon.dribbble.data.model.UserLikeEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.UIUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class DribbbleDataManger {
    private static DribbbleDataManger INSTANCE;
    private final DribbbleApi mDribbbleApi;

    public static DribbbleDataManger getInstance(DribbbleApi dataService) {
        if (INSTANCE == null) {
            INSTANCE = new DribbbleDataManger(dataService);
        }
        return INSTANCE;
    }

    public DribbbleDataManger(DribbbleApi dataService) {
        mDribbbleApi = dataService;
    }

    /**
     * 认证用户，并获取用户信息
     *
     * @param code
     * @return
     */
    public Observable<UserEntity> getTokenAndUser(String code) {

        return DribbbleApi.Creator.signIn().getToken(Api.CLIENT_ID, Api.CLIENT_SECRET, code,
                Api.CALLBACK_URL).concatMap(new Func1<TokenEntity, Observable<? extends
                UserEntity>>() {

            @Override
            public Observable<? extends UserEntity> call(TokenEntity tokenEntity) {

                if (null != tokenEntity) {
                    String access_token = tokenEntity.access_token;
                    DribbbleApp.spHelper().put(Api.OAUTH_ACCESS_TOKEN, access_token);
                }
                return mDribbbleApi.getUserInfo().concatMap(new Func1<UserEntity, Observable<?
                        extends UserEntity>>() {

                    @Override
                    public Observable<? extends UserEntity> call(final UserEntity userEntity) {
                        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
                            @Override
                            public void call(Subscriber<? super UserEntity> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                try {
                                    if (null == userEntity) {
                                        subscriber.onError(new NullPointerException("Request " +
                                                "failed"));
                                    } else {
                                        subscriber.onNext(userEntity);
                                        subscriber.onCompleted();
                                    }
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
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
    public Observable<List<ShotEntity>> getShotsList(int page, String list, String timeframe,
                                                     String sort) {
        return mDribbbleApi.getShots(page, list, timeframe, sort).
                concatMap(new Func1<List<ShotEntity>, Observable<? extends List<ShotEntity>>>() {

                    @Override
                    public Observable<? extends List<ShotEntity>> call(final List<ShotEntity>
                                                                               shotsEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<ShotEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<ShotEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (!UIUtils.isEmpty(shotsEntities)) {
                                    subscriber.onNext(shotsEntities);
                                } else {
                                    subscriber.onError(new NullPointerException("Request failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 根据 id 获取 Shot 信息
     *
     * @param shotId id
     * @return
     */
    public Observable<ShotEntity> getShot(long shotId) {
        return mDribbbleApi.getShot(shotId)
                .concatMap(new Func1<ShotEntity, Observable<? extends ShotEntity>>() {
                    @Override
                    public Observable<? extends ShotEntity> call(final ShotEntity shotsEntity) {
                        return Observable.create(new Observable.OnSubscribe<ShotEntity>() {
                            @Override
                            public void call(Subscriber<? super ShotEntity> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != shotsEntity) {
                                    subscriber.onNext(shotsEntity);
                                } else {
                                    subscriber.onError(new NullPointerException("Request failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });

    }

    /**
     * 获取评论列表
     *
     * @param shotId
     * @param page
     * @return
     */
    public Observable<List<CommentEntity>> getComments(long shotId, String type, int page) {

        return mDribbbleApi.getShotComments(shotId, type, page)
                .concatMap(new Func1<List<CommentEntity>, Observable<? extends
                        List<CommentEntity>>>() {

                    @Override
                    public Observable<? extends List<CommentEntity>> call(final List<CommentEntity>
                                                                                  commentEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<CommentEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<CommentEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != commentEntities) {
                                    subscriber.onNext(commentEntities);
                                } else {
                                    subscriber.onError(new NullPointerException("Request failed"));
                                }
                            }
                        });
                    }
                });

    }

    public Observable<CommentEntity> createComment(long shotId, String content) {
        return mDribbbleApi.createComment(shotId, content)
                .concatMap(new Func1<CommentEntity, Observable<? extends CommentEntity>>() {
                    @Override
                    public Observable<? extends CommentEntity> call(final CommentEntity
                                                                            commentEntity) {
                        return Observable.create(new Observable.OnSubscribe<CommentEntity>() {
                            @Override
                            public void call(Subscriber<? super CommentEntity> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != commentEntity) {
                                    subscriber.onNext(commentEntity);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用户的 Buckets
     *
     * @return
     */
    public Observable<List<BucketEntity>> getUserBuckets() {
        return mDribbbleApi.getUserBuckets()
                .concatMap(new Func1<List<BucketEntity>, Observable<? extends
                        List<BucketEntity>>>() {

                    @Override
                    public Observable<? extends List<BucketEntity>> call(final List<BucketEntity>
                                                                                 bucketEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<BucketEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<BucketEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;
                                if (null != bucketEntities) {
                                    subscriber.onNext(bucketEntities);
                                } else {
                                    subscriber.onError(new NullPointerException("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用的 Followers
     *
     * @param page
     * @return
     */
    public Observable<List<FollowersEntity>> getUserFollowers(int page) {
        return mDribbbleApi.getUserFollowers(page)
                .concatMap(new Func1<List<FollowersEntity>, Observable<? extends
                        List<FollowersEntity>>>() {

                    @Override
                    public Observable<? extends List<FollowersEntity>> call(final
                                                                            List<FollowersEntity>
                                                                                    followersEntities) {
                        return Observable.create(new Observable
                                .OnSubscribe<List<FollowersEntity>>() {

                            @Override
                            public void call(Subscriber<? super List<FollowersEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != followersEntities) {
                                    subscriber.onNext(followersEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用户的 Likes
     *
     * @return
     */
    public Observable<List<UserLikeEntity>> getUserLikes(int page) {
        return mDribbbleApi.getUserLikes(page)
                .concatMap(new Func1<List<UserLikeEntity>, Observable<? extends
                        List<UserLikeEntity>>>() {

                    @Override
                    public Observable<? extends List<UserLikeEntity>> call(final
                                                                           List<UserLikeEntity>
                                                                                   userLikeEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<UserLikeEntity>>
                                () {

                            @Override
                            public void call(Subscriber<? super List<UserLikeEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != userLikeEntities) {
                                    subscriber.onNext(userLikeEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用户的 Projects
     *
     * @return
     */
    public Observable<List<ProjectEntity>> getUserProjects() {
        return mDribbbleApi.getUserProjects()
                .concatMap(new Func1<List<ProjectEntity>, Observable<? extends
                        List<ProjectEntity>>>() {

                    @Override
                    public Observable<? extends List<ProjectEntity>> call(final List<ProjectEntity>
                                                                                  projectEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<ProjectEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<ProjectEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != projectEntities) {
                                    subscriber.onNext(projectEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用户的 Shots
     *
     * @param page
     * @return
     */
    public Observable<List<ShotEntity>> getUserShots(int page) {
        return mDribbbleApi.getUserShots(page)
                .concatMap(new Func1<List<ShotEntity>, Observable<? extends List<ShotEntity>>>() {
                    @Override
                    public Observable<? extends List<ShotEntity>> call(final List<ShotEntity>
                                                                               shotEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<ShotEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<ShotEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != shotEntities) {
                                    subscriber.onNext(shotEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    /**
     * 获取用户的 Teams
     *
     * @return
     */
    public Observable<List<TeamEntity>> getUserTeams() {
        return mDribbbleApi.getUserTeams()
                .concatMap(new Func1<List<TeamEntity>, Observable<? extends List<TeamEntity>>>() {
                    @Override
                    public Observable<? extends List<TeamEntity>> call(final List<TeamEntity>
                                                                               teamEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<TeamEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<TeamEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != teamEntities) {
                                    subscriber.onNext(teamEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<LikeEntity> addLike(long shotId) {
        return mDribbbleApi.addLike(shotId, null)
                .concatMap(new Func1<LikeEntity, Observable<? extends LikeEntity>>() {
                    @Override
                    public Observable<? extends LikeEntity> call(final LikeEntity likeEntity) {
                        return Observable.create(new Observable.OnSubscribe<LikeEntity>() {
                            @Override
                            public void call(Subscriber<? super LikeEntity> subscriber) {
                                if (subscriber.isUnsubscribed()) return;
                                if (null != likeEntity) {
                                    subscriber.onNext(likeEntity);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<Object> search(String key) {
        return DribbbleApi.Creator.signIn().search(key)
                .concatMap(new Func1<Object, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Object o) {
                        return Observable.create(new Observable.OnSubscribe<Object>() {
                            @Override
                            public void call(Subscriber<? super Object> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != o) {
                                    subscriber.onNext(o);
                                } else {
                                    subscriber.onError(new Exception("ddddd"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<List<LikeEntity>> getShotLikes(long id, int page) {
        return mDribbbleApi.getShotLikes(id, page)
                .concatMap(new Func1<List<LikeEntity>, Observable<? extends List<LikeEntity>>>() {
                    @Override
                    public Observable<? extends List<LikeEntity>> call(final List<LikeEntity>
                                                                               likeEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<LikeEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<LikeEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != likeEntities) {
                                    subscriber.onNext(likeEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<List<AttachmentEntity>> getShotAttach(long id, int page) {
        return mDribbbleApi.getShotAttach(id, page)
                .concatMap(new Func1<List<AttachmentEntity>, Observable<? extends
                        List<AttachmentEntity>>>() {

                    @Override
                    public Observable<? extends List<AttachmentEntity>> call
                            (final List<AttachmentEntity> attachmentEntities) {
                        return Observable.create(new Observable
                                .OnSubscribe<List<AttachmentEntity>>() {

                            @Override
                            public void call(Subscriber<? super List<AttachmentEntity>>
                                                     subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != attachmentEntities) {
                                    subscriber.onNext(attachmentEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<List<BucketEntity>> getBuckets(String type, long id, int page) {
        return mDribbbleApi.getBuckets(type, id, page)
                .concatMap(new Func1<List<BucketEntity>, Observable<? extends
                        List<BucketEntity>>>() {

                    @Override
                    public Observable<? extends List<BucketEntity>> call(final List<BucketEntity>
                                                                                 bucketEntities) {
                        return Observable.create(new Observable.OnSubscribe<List<BucketEntity>>() {
                            @Override
                            public void call(Subscriber<? super List<BucketEntity>> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != bucketEntities) {
                                    subscriber.onNext(bucketEntities);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


    public Observable<UserEntity> getUsersInfo(long userId) {
        return mDribbbleApi.getUsers(userId)
                .concatMap(new Func1<UserEntity, Observable<? extends UserEntity>>() {
                    @Override
                    public Observable<? extends UserEntity> call(final UserEntity userEntity) {
                        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
                            @Override
                            public void call(Subscriber<? super UserEntity> subscriber) {
                                if (subscriber.isUnsubscribed()) return;

                                if (null != userEntity) {
                                    subscriber.onNext(userEntity);
                                } else {
                                    subscriber.onError(new Exception("Request Failed"));
                                }

                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

}
