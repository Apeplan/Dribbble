package com.simon.dribbble.data.remote;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.ApiResponse;
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

import net.quickrecyclerview.utils.log.LLog;

import java.io.IOException;
import java.util.List;

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

public interface DribbbleApi {

    String SIGNIN_URL = "https://dribbble.com/";
    String DRIBBBLE_BASE_URL = "https://api.dribbble.com/v1/";


    @GET("cook/list")
    Observable<ApiResponse> getCooks();

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
    Observable<List<ShotEntity>> getShots(@Query("page") int page, @Query("list") String list,
                                          @Query("timeframe") String timeframe, @Query("sort")
                                                  String sort);

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
     *  根据用户的Id获取用户的信息
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

    @GET("search")
    Observable<Object> search(@Query("q") String key);


    @GET("shots/{id}/likes")
    Observable<List<LikeEntity>> getShotLikes(@Path("id") long shotId, @Query("page") int page);

    @GET("shots/{id}/attachments")
    Observable<List<AttachmentEntity>> getShotAttach(@Path("id") long shotId, @Query("page") int
            page);

    @GET("{type}/{id}/buckets")
    Observable<List<BucketEntity>> getBuckets(@Path("type") String type, @Path("id") long
            shotId, @Query("page") int page);


    /**
     * 设置一个新服务
     */
    class Creator {
        public static DribbbleApi dribbbleApi() {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain)
                        throws IOException {
                    Request original = chain.request();
//                    String token = DribbbleApp.spHelper().getString(Api.OAUTH_ACCESS_TOKEN);
                    String token = Api.ACCESS_TOKEN;
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DribbbleApi.DRIBBBLE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(DribbbleApi.class);
        }

        public static DribbbleApi signIn() {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response proceed = chain.proceed(request);
                    LLog.d("Simon", "intercept: " + proceed.body());
                    return proceed;
                }
            });
            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DribbbleApi.SIGNIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(DribbbleApi.class);
        }

    }
}
