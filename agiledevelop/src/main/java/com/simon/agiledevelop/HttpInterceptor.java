package com.simon.agiledevelop;


import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {
    private static String TAG = HttpInterceptor.class.getSimpleName();
    private Headers mHeaders;
    private Map<String, String> mMapHeaders;

    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装headers
        Request request;

        Request.Builder builder = chain.request().newBuilder();
        if (mHeaders != null) {
            Set<String> set = mHeaders.names();
            for (String name : set) {
                builder.addHeader(name, mHeaders.get(name));
            }
        } else if (mMapHeaders != null) {
            for (String name : mMapHeaders.keySet()) {
                builder.addHeader(name, mMapHeaders.get(name));
            }
        }
        request = builder
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        Response response = chain.proceed(request);

        return response;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers headers) {
        this.mHeaders = headers;
    }

    public void setMapHeaders(Map<String, String> mapHeaders) {
        this.mMapHeaders = mapHeaders;
    }
}
