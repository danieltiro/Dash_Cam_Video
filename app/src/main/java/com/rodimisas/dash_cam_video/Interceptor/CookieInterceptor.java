package com.rodimisas.dash_cam_video.Interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {
    private volatile String cookie;

    public void setSessionCookie(String cookie) {
        this.cookie = cookie;
    }
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        if (this.cookie != null) {
            request = request.newBuilder()
                    .header("Cookie", this.cookie)
                    .build();
        }
        return chain.proceed(request);
    }
}
