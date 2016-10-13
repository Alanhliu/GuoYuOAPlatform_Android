package com.guoyu.guoyuoaplatform_android.RestService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by siasun on 16/9/19.
 */
public class OKHttpRxSupport {

    private static OkHttpClient mOkHttpClient;

    private static OKHttpRxSupport ourInstance = new OKHttpRxSupport();

    public static OKHttpRxSupport getInstance() {
        return ourInstance;
    }

    private OKHttpRxSupport() {
        mOkHttpClient = new OkHttpClient();
    }

    private void okHttpCallBack (Request request, final Subscriber<? super Object> subscriber) {
        try {

            okhttp3.Call call = mOkHttpClient.newCall(request);

            call.enqueue(new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e) {
                    subscriber.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    subscriber.onNext(response);
                }
            });

        } catch (Exception e) {
            subscriber.onError(e);
        }
    }

    /**
     * get method
     * @param url
     */
    public Observable<Object> rx_get(final String url) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .get().build();

                okHttpCallBack(request,subscriber);
            }
        });
    }

    /**
     * post method
     * @param url String
     * @param builer FormBody.Builder
     *
     * FormBody.Builder formEncodingBuilder = new FormBody.Builder();
     * formEncodingBuilder.add("username", account);
     * formEncodingBuilder.add("password", password);
     *
     */
    public Observable<Object> rx_post(final String url, final FormBody.Builder builer) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                RequestBody formBody = builer.build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody).build();
                okHttpCallBack(request,subscriber);
            }
        });
    }
}
