package com.camacho.minitwitter.retrofit;

import com.camacho.minitwitter.retrofit.request.RequestLogin;
import com.camacho.minitwitter.retrofit.response.ResponseAuth;
import com.camacho.minitwitter.retrofit.request.RequestSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {

    @POST("auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);

    @POST("auth/signup")
    Call<ResponseAuth> doSignUp(@Body RequestSignUp requestSignUp);
}
