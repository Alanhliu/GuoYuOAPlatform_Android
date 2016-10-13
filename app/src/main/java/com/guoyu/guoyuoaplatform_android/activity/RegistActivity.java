package com.guoyu.guoyuoaplatform_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guoyu.guoyuoaplatform_android.constant.GYIMConfigureConstant;
import com.guoyu.guoyuoaplatform_android.R;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegistActivity extends Activity {

    private EditText mUserNameET;
    private EditText mPasswordET;
    private Button mRegistButton;
    private static final String tag = "RegistActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        mUserNameET = (EditText) findViewById(R.id.regist_username);
        mPasswordET = (EditText) findViewById(R.id.regist_password);
        mRegistButton = (Button) findViewById(R.id.regist_button);

        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });
    }

    private void regist() {
        String username = mUserNameET.getText().toString().trim();
        String password = mPasswordET.getText().toString().trim();
        TLSAccountHelper.getInstance().init(this.getApplicationContext(),Integer.parseInt(GYIMConfigureConstant.SdkAppId) , Integer.parseInt(GYIMConfigureConstant.AccountType),"1.0.0.0");
        TLSAccountHelper.getInstance().TLSStrAccReg(username, password, new TLSStrAccRegListener() {
            @Override
            public void OnStrAccRegSuccess(TLSUserInfo tlsUserInfo) {
                String identifier = tlsUserInfo.identifier;
            }

            @Override
            public void OnStrAccRegFail(TLSErrInfo tlsErrInfo) {
                Log.i(tag,tlsErrInfo.Msg);
            }

            @Override
            public void OnStrAccRegTimeout(TLSErrInfo tlsErrInfo) {
                Log.i(tag,tlsErrInfo.Msg);
            }
        });
    }
}
