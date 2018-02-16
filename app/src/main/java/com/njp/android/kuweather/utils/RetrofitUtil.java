package com.njp.android.kuweather.utils;

import com.njp.android.kuweather.bean.Weather;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 网络请求工具类
 */

public class RetrofitUtil {

    public static final String KEY = "7eb186ac666b41cf9bb1b2cac8499105";

    private static OkHttpClient sClient = new OkHttpClient.Builder().build();

    private static Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl("https://free-api.heweather.com/s6/")
            .client(sClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public interface RetrofitService {

        @GET("weather")
        Observable<Weather> getWeather(
                @Query("location") String location,
                @Query("key") String key
        );

    }

    public static RetrofitService getService() {
        return sRetrofit.create(RetrofitService.class);
    }


}
