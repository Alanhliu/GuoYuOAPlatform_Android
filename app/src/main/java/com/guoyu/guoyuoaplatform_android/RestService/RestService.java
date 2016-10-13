package com.guoyu.guoyuoaplatform_android.RestService;

import android.os.AsyncTask;
import android.util.Log;


import com.google.common.eventbus.Subscribe;
import com.guoyu.guoyuoaplatform_android.bean.GetIpInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by siasun on 16/9/19.
 */
public class RestService {
    private static final String tag = "RestService";

    private MyTask mTask;

    private OKHttpRxSupport okHttpRXSupport;

    private static RestService ourInstance = new RestService();

    public static RestService getInstance() {
        return ourInstance;
    }

    private RestService() {
        okHttpRXSupport = OKHttpRxSupport.getInstance();
    }


    private static final String ENDPOINT = "http://ip.taobao.com";

//    http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33
    public void testRetrofit (){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://106.3.227.33/pulamsi/")
//                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
//                //增加返回值为Gson的支持(以实体类返回)
//                .addConverterFactory(GsonConverterFactory.create())
//                //增加返回值为Oservable<T>的支持
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ENDPOINT)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        ApiService requestSerives = retrofit.create(ApiService.class);
//        Call<GetIpInfoResponse> call = requestSerives.getIpInfo("192.168.155.223");
//
//        call.enqueue(new Callback<GetIpInfoResponse>() {
//            @Override
//            public void onResponse(Call<GetIpInfoResponse> call, Response<GetIpInfoResponse> response) {
//                GetIpInfoResponse getIpInfoResponse = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<GetIpInfoResponse> call, Throwable t) {
//                System.out.print(t.toString());
//            }
//        });


        RetrofitManager.getInstance().getService().getIpInfo2("192.168.155.223")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetIpInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print(e.toString());
                    }

                    @Override
                    public void onNext(GetIpInfoResponse getIpInfoResponse) {
                        System.out.print(getIpInfoResponse.toString());
                    }
                });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        ApiService requestSerives = retrofit.create(ApiService.class);
        Observable<GetIpInfoResponse> call = requestSerives.getIpInfo2("192.168.155.223");

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetIpInfoResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.toString());
            }

            @Override
            public void onNext(GetIpInfoResponse getIpInfoResponse) {
                System.out.print(getIpInfoResponse.toString());
            }
        });
    }

    public void test() {

        mTask = new MyTask();
        mTask.execute("https://www.baidu.com");

        okHttpRXSupport.rx_get("https://www.baidu.com").map(new Func1<Object, Object>() {
            @Override
            public Object call(Object o) {
                return "123456";
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(tag,o.toString());
            }
        });

        Observable ob1 = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("1");
                subscriber.onCompleted();
            }
        });

        Observable ob2 = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("2");
                subscriber.onCompleted();
            }
        });

        Observable.combineLatest(ob1, ob2, new Func2() {
            @Override
            public Object call(Object o, Object o2) {
                String str = o.toString()+o2.toString();
                return str;
            }
        }).doOnNext(new Action1() {
            @Override
            public void call(Object o) {

            }
        });

        Observable.merge(ob1,ob2).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(tag,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag,e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.d(tag,o.toString());
            }
        });
    }

    private class MyTask extends AsyncTask<String,Integer,String> {
        public MyTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
