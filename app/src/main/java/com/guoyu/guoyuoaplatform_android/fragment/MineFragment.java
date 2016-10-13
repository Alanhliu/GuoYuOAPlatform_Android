package com.guoyu.guoyuoaplatform_android.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoyu.guoyuoaplatform_android.RestService.Notify;
import com.guoyu.guoyuoaplatform_android.activity.CountryActivity;
import com.guoyu.guoyuoaplatform_android.activity.CountrySectionActivity;
import com.guoyu.guoyuoaplatform_android.R;
import com.guoyu.guoyuoaplatform_android.RestService.RestService;
import com.jakewharton.rxbinding.view.RxMenuItem;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TAG = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String tag = "GYLogTag";
    private OnFragmentInteractionListener mListener;

    File[] folders;
    OkHttpClient client;
    SimpleDraweeView simpleDraweeView;
    private LinearLayout phoneLayout;
    private ImageView mineImageView;
    private EditText mineEditText;

    private ImageView mAvatarIV;
    private int index;
    private TextView textView;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        simpleDraweeView = (SimpleDraweeView) getActivity().findViewById(R.id.frsco_img1);
        Uri uri = Uri.parse("http://img2.imgtn.bdimg.com/it/u=3145304103,1465502637&fm=11&gp=0.jpg");
        simpleDraweeView.setImageURI(uri,this);

        textView = (TextView) getActivity().findViewById(R.id.mine_avatar_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent();
                intent.setClass(activity,CountrySectionActivity.class);
                startActivityForResult(intent, TAG);
            }
        });

        onObservable();

        testOKHttp();

        mAvatarIV = (ImageView) getActivity().findViewById(R.id.imageView);
        Picasso.with(getActivity()).load("http://www.xiufa.com/BJUI/plugins/kindeditor_4.1.10/attached/image/20160427/20160427020327_69298.png").into(mAvatarIV);
        mAvatarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent();
                intent.setClass(activity,CountryActivity.class);
//                解决方法就是在Fragment中直接调用startActivityForResult()方法，而不是调用 getActivity().startActivityForResult()。
//                activity.startActivityForResult(intent,TAG);
                startActivityForResult(intent,TAG);
            }
        });

        phoneLayout = (LinearLayout) getActivity().findViewById(R.id.mine_phone_linearLayout);
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        mineImageView = (ImageView) getActivity().findViewById(R.id.mine_imageview);
        mineEditText = (EditText) getActivity().findViewById(R.id.mine_edittext);


        testRxBinding();
        testNotify();
    }

    private void testNotify() {
//        Notify.simpleNotify(this.getActivity());
//        Notify.progressNotify(this.getActivity());
//        Notify.sendCustomerNotification(this.getActivity(),1);
    }

    private void testRxBinding (){
        RxTextView.textChanges(mineEditText).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                System.out.print(charSequence.toString());
            }
        });


        RxView.clicks(mAvatarIV).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MineFragment.TAG && resultCode == CountryActivity.TAG) {
            String result_value = data.getStringExtra("result");
            Log.i(tag, result_value);
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ContentResolver resolver = getActivity().getContentResolver();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, selectedImage);
                mineImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm,150,150));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void testOKHttp() {//crash

        RestService.getInstance().test();
        RestService.getInstance().testRetrofit();

        OkHttpClient mOkHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();

        okhttp3.Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(tag,e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(tag,response.toString());
            }
        });
    }

    private void logNum() {
        String[] names = {"1","2","3","4","5"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {

                        Toast.makeText(getContext(), name,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onObservable () {

        final String tag = "tag";

//        String[] names = {"Hello", "Hi", "Aloha"};
//        Observable.from(names)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String name) {
//                        Log.d(tag, name);
//                    }
//                });



//        Observable justObservable = Observable.just("Hello", "Hi", "Aloha");
//        justObservable.subscribe(new Subscriber() {
//            @Override
//            public void onCompleted() {
//                Log.d(tag,"completed");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                String name = (String)o;
//                Log.d(tag, name);
//            }
//        });



//        Observable.just("hellow").map(new Func1<String,Integer>() {
//            @Override
//            public Integer call(String s) {
//                return 2015;
//            }
//        }).map(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return String.valueOf(integer);
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.i(tag,s);
//            }
//        });


//        Observable observable = Observable.just(index);
//        observable.subscribe(new Subscriber() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Integer integer = (Integer) o;
//                Log.i(tag, String.valueOf(integer.intValue()));
//            }
//        });


        // 注意这里的参数是 query所返回的Observable的输出,并且返会一个Observable<String>
        query().flatMap(new Func1<List<String>, Observable<List<String>>>() {
            @Override
            public Observable<List<String>> call(final List<String> strings) {

                Observable.just(strings).map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        return null;
                    }
                }).toList();

                Observable observable = Observable.create(new Observable.OnSubscribe<List>() {
                    @Override
                    public void call(Subscriber<? super List> subscriber) {

                        for (int i = 0 ;i<strings.size(); i++) {
                            String s = "flatMap"+strings.get(i);
                            strings.set(i,s);
                        }
                        subscriber.onNext(strings);
                        subscriber.onCompleted();
                    }
                });

                return observable;
            }
        }).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                for (String s : strings)
                    Log.i(tag,s);
            }
        });
    }

    static Observable<List<String>> query() {
        List<String> s = Arrays.asList("Java", "Android", "Ruby", "Ios", "Swift");
        return Observable.just(s);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
