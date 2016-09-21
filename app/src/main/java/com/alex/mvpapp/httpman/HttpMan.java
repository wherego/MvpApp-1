package com.alex.mvpapp.httpman;

import com.alex.mvpapp.model.UserBean;
import com.alex.mvpapp.model.qianguan.LoginBean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
public interface HttpMan {

    @GET("login")
    Observable<LoginBean> loginGet(@Query("phone") String phone, @Query("pwd") String pwd);

    @GET("login")
    Observable<LoginBean> loginGet(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("login")
    Observable<LoginBean> loginPost(@FieldMap Map<String, String> params);

    @POST("login")
    Observable<LoginBean> loginPost(@Body UserBean bean);

    //@HEAD("Content-Type: text/plain; charset=utf-8")
    @FormUrlEncoded
    @POST("login")
    Observable<LoginBean> loginPost(@Field("phone") String phone, @Field("pwd") String pwd);

    @Multipart
    @POST("login")
    Observable<LoginBean> loginPost(@Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);

    @Multipart
    @POST("upload")
    Observable<LoginBean> upLoad(@Part MultipartBody.Part userLogo, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);

    @Multipart
    @POST("upload")
    Observable<LoginBean> upLoad2(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("login")
    Observable<JSONObject> postJson(@Field("userInfo") String content);


    @Multipart
    @POST("upload")
    Observable<String> upLoad3(@Part List<MultipartBody.Part> userLogoList, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);
}
