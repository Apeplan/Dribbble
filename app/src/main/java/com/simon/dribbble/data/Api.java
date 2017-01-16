package com.simon.dribbble.data;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2016/12/28
 * @email hanzx1024@gmail.com
 */

public class Api {

    public static final int EVENT_BEGIN = 0x520;
    public static final int EVENT_REFRESH = 0x521;
    public static final int EVENT_MORE = 0x522;
    public static final int EVENT_OTHER = 0x523;

    public static final int EVENT_ADD_DATA = EVENT_BEGIN + 100;// 添加数据
    public static final int EVENT_DELETE_DATA = EVENT_BEGIN + 101;// 删除数据
    public static final int EVENT_MODIFY_DATA = EVENT_BEGIN + 102;// 修改数据
    public static final int EVENT_QUERY_DATA = EVENT_BEGIN + 103;// 查询数据
    public static final int EVENT_COMMIT_DATA = EVENT_BEGIN + 104;// 查询数据


    public static final String SIGNIN_URL = "https://dribbble.com/";
    public static final String DRIBBBLE_BASE_URL = "https://api.dribbble.com/v1/";

    public static final String CLIENT_ID =
            "f8918c16dc8db1eb74f1377271ad291c876d3a4edc90c473590a9e2f6df1fa5f";
    public static final String CLIENT_SECRET =
            "6c7ae68e0c2fbcdf7634d24f04d6ff7998ce1da1536157f2cb4e94ccdf5efb22";
    public static final String ACCESS_TOKEN =
                "d78228e73c647fd3d24009e19ebe33c5da909a0039ef0722fcf3fc96f654862e";

    public static final long USER_ID = 1057621;

    public static final String PARAM_STATE = "state";
    public static final String PARAM_CODE = "code";
    public static final String OAUTH_ACCESS_TOKEN = "oauth_access_token";

    /**
     * 用户认证url
     * 参数： client_id Required. The client ID you received from Dribbble when you registered.
     * redirect_uri
     * scope
     * state
     */
    public static final String AUTHORIZE_URL = "https://dribbble.com/oauth/authorize?client_id=%s";
    /**
     * 回调地址
     */
    public static final String CALLBACK_URL = "https://github.com/HZ90";
}
