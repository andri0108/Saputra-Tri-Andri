package com.appsnipp.loginsamples;

import android.renderscript.Sampler;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/daftar.php")
    Call<Value> daftar(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/tambahkerjaan.php")

    Call<Value> insert(
            @Field("nama") String nama,
            @Field("kerjaan") String kerjaan);


}
