package com.guoyu.guoyuoaplatform_android.RestService;

import com.guoyu.guoyuoaplatform_android.bean.GetIpInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by siasun on 16/9/28.
 */

//API Service
public interface ApiService {
    @GET("service/getIpInfo.php")
    Call<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<GetIpInfoResponse> getIpInfo2(@Query("ip") String ip);
}
