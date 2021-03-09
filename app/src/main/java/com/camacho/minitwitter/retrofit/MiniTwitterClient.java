package com.camacho.minitwitter.retrofit;

import com.camacho.minitwitter.common.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {
    private static MiniTwitterClient instance = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MiniTwitterClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_MINI_TWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    public static MiniTwitterClient getInstance() {
        if (instance == null) {
            instance = new MiniTwitterClient();
        }
        return instance;
    }

    public MiniTwitterService getMiniTwitterService() {
        return miniTwitterService;
    }
}
