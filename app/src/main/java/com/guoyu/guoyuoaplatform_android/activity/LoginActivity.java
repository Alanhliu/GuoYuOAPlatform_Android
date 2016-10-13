package com.guoyu.guoyuoaplatform_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guoyu.guoyuoaplatform_android.constant.GYIMConfigureConstant;
import com.guoyu.guoyuoaplatform_android.R;
import com.tencent.TIMManager;

import tencent.tls.platform.TLSHelper;

public class LoginActivity extends Activity {

    static private final String tag = "LoginActivityTag";
    private Button mLoginButton;
    private Button mRegistButton;
    private EditText mUserNameET;
    private EditText mPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegistButton = (Button) findViewById(R.id.login_regist_button);
        mUserNameET = (EditText) findViewById(R.id.usename_et);
        mPasswordET = (EditText) findViewById(R.id.password_et);

        initIMSDK();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegist();
            }
        });
    }

    private void initIMSDK() {
        Context context = this.getApplicationContext();
        TIMManager.getInstance().init(context);
        TLSHelper.getInstance().init(context,Integer.parseInt(GYIMConfigureConstant.SdkAppId));
    }

    private void toRegist() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegistActivity.class);
        this.startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);

//        TIMUser user = new TIMUser();
//        user.setAccountType(GYIMConfigureConstant.AccountType);
//        user.setAppIdAt3rd(GYIMConfigureConstant.SdkAppId);
//        user.setIdentifier(GYIMConfigureConstant.SdkAppId);
//
//        TIMManager.getInstance().login(
//                GYIMConfigureConstant.SdkAppId,//sdkAppId，由腾讯分配
//                user,
//                userSig,//用户帐号签名，由私钥加密获得，具体请参考文档
//                new TIMCallBack() {//回调接口
//
//                    @Override
//                    public void onSuccess() {//登录成功
//                        Log.d(tag, "login succ");
//                    }
//
//                    @Override
//                    public void onError(int code, String desc) {//登录失败
//
//                        //错误码code和错误描述desc，可用于定位请求失败原因
//                        //错误码code含义请参见错误码表
//                        Log.d(tag, "login failed. code: " + code + " errmsg: " + desc);
//                    }
//                });
    }
}
