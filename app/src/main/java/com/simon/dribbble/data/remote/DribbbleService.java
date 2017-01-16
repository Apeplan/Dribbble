package com.simon.dribbble.data.remote;

import android.support.annotation.StringDef;

import com.simon.agiledevelop.ServiceHelper;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.SearchConverter;
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

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/22 14:09
 */

public interface DribbbleService {

    /**
     * 获取用户已授权的token
     */
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<TokenEntity> getToken(@Field("client_id") String client_id, @Field("client_secret")
            String client_secret, @Field("code") String code, @Field("redirect_uri") String
                                             redirect_uri);

    /**
     * 获取登陆用户信息
     */
    @GET("user")
    Observable<User> getUserInfo();

    /**
     * 返回 Shots 列表
     *
     * @param page      获取第几页数据
     * @param list      什么类型数据的列表
     * @param timeframe 哪个时间段
     * @param sort      什么顺序
     * @return
     */
    @GET("shots")
    Observable<List<ShotEntity>> getShots(@Query("page") int page, @Query("list") @ShotType
            String list, @Query("timeframe") @ShotTimeframe String timeframe, @Query("sort")
                                          @ShotSort String sort);

    /**
     * 返回单个 Shot 信息
     *
     * @param shotId
     * @return
     */
    @GET("shots/{id}")
    Observable<ShotEntity> getShot(@Path("id") long shotId);

    /**
     * 根据 shotId 获取评论
     *
     * @param shotId
     * @return
     */
    @GET("shots/{id}/{type}")
    Observable<List<CommentEntity>> getShotComments(@Path("id") long shotId, @Path("type")
            String type, @Query("page") int page);

    /**
     * 更新一条评论
     *
     * @param shotId
     * @param cid
     * @param comment
     * @return
     */
    @PUT("shots/{id}/comments/{cid}")
    Observable<CommentEntity> updateComment(@Path("id") long shotId, @Path("cid") int cid, @Part
            ("comment") String comment);

    /**
     * 创建一条评论
     *
     * @param shotId
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("shots/{id}/comments")
    Observable<CommentEntity> createComment(@Path("id") long shotId, @Field("body") String content);

    /**
     * 获取用户的 buckets
     *
     * @return
     */
    @GET("user/buckets")
    Observable<List<BucketEntity>> getUserBuckets();

    /**
     * 获取用户的 Shots
     *
     * @return
     */
    @GET("user/shots")
    Observable<List<ShotEntity>> getUserShots(@Query("page") int page);

    /**
     * 获取用户的 Followers
     *
     * @return
     */
    @GET("user/followers")
    Observable<List<FollowersEntity>> getUserFollowers(@Query("page") int page);

    /**
     * 获取用户的 Likes
     *
     * @return
     */
    @GET("user/likes")
    Observable<List<UserLikeEntity>> getUserLikes(@Query("page") int page);

    /**
     * 获取用户的 Likes
     *
     * @return
     */
    @GET("user/projects")
    Observable<List<ProjectEntity>> getUserProjects();

    /**
     * 获取用户的 Likes
     *
     * @return
     */
    @GET("user/teams")
    Observable<List<TeamEntity>> getUserTeams();

    /**
     * 根据用户的Id获取用户的信息
     *
     * @return
     */
    @GET("users/{usersId}")
    Observable<User> getUsers(@Path("usersId") long id);


    /**
     * 将 Shot 添加为喜欢
     *
     * @return
     */
    @POST("shots/{id}/like")
    @FormUrlEncoded
    Observable<LikeEntity> addLike(@Path("id") long shotId, @Field("empty") Long em);

    @GET("shots/{id}/likes")
    Observable<List<LikeEntity>> getShotLikes(@Path("id") long shotId, @Query("page") int page);

    @GET("shots/{id}/attachments")
    Observable<List<AttachmentEntity>> getShotAttach(@Path("id") long shotId, @Query("page") int
            page);

    @GET("{type}/{id}/buckets")
    Observable<List<BucketEntity>> getBuckets(@Path("type") String type, @Path("id") long
            shotId, @Query("page") int page);

    @GET("search")
    Observable<List<ShotEntity>> search(@Query("q") String query,
                                        @Query("page") Integer page,
                                        @Query("per_page") Integer pageSize,
                                        @Query("s") @SortOrder String sort);


     /* Magic Constants */

    String SHOT_TYPE_ANIMATED = "animated";
    String SHOT_TYPE_ATTACHMENTS = "attachments";
    String SHOT_TYPE_DEBUTS = "debuts";
    String SHOT_TYPE_PLAYOFFS = "playoffs";
    String SHOT_TYPE_REBOUNDS = "rebounds";
    String SHOT_TYPE_TEAMS = "teams";
    String SHOT_TIMEFRAME_NOW = "now";
    String SHOT_TIMEFRAME_WEEK = "week";
    String SHOT_TIMEFRAME_MONTH = "month";
    String SHOT_TIMEFRAME_YEAR = "year";
    String SHOT_TIMEFRAME_EVER = "ever";
    String SHOT_SORT_COMMENTS = "comments";
    String SHOT_SORT_RECENT = "recent";
    String SHOT_SORT_VIEWS = "views";
    String SHOT_SORT_POPULARITY = "popularity";

    String SORT_POPULAR = "";
    String SORT_RECENT = "latest";

    // Shot Type
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_TYPE_ANIMATED,
            SHOT_TYPE_ATTACHMENTS,
            SHOT_TYPE_DEBUTS,
            SHOT_TYPE_PLAYOFFS,
            SHOT_TYPE_REBOUNDS,
            SHOT_TYPE_TEAMS
    })
    @interface ShotType {
    }

    // Shot timeframe
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_TIMEFRAME_NOW,
            SHOT_TIMEFRAME_WEEK,
            SHOT_TIMEFRAME_MONTH,
            SHOT_TIMEFRAME_YEAR,
            SHOT_TIMEFRAME_EVER
    })
    @interface ShotTimeframe {
    }

    // Short sort order
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SHOT_SORT_COMMENTS,
            SHOT_SORT_RECENT,
            SHOT_SORT_VIEWS,
            SHOT_SORT_POPULARITY
    })
    @interface ShotSort {
    }

    /**
     * magic constants
     **/

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SORT_POPULAR,
            SORT_RECENT
    })
    @interface SortOrder {
    }

    /**
     * 设置一个新服务
     */
    class Creator {
        public static DribbbleService dribbbleApi() {
//            String token = DribbbleApp.spHelper().getString(Api.OAUTH_ACCESS_TOKEN);
            Headers.Builder headers = new Headers.Builder();
            Headers authorization = headers.add("Authorization", "Bearer " + Api.ACCESS_TOKEN)
                    .build();

            return ServiceHelper.getInstance().creator(Api.DRIBBBLE_BASE_URL, DribbbleService
                    .class, authorization);
        }

        public static DribbbleService signIn() {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response proceed = chain.proceed(request);
                    LLog.d("intercept: " + proceed.body());
                    return proceed;
                }
            });
            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.SIGNIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(DribbbleService.class);
        }

        public static DribbbleService searchApi() {
            return new Retrofit.Builder()
                    .baseUrl(Api.SIGNIN_URL)
                    .addConverterFactory(new SearchConverter.Factory())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create((DribbbleService.class));
        }

    }

}
