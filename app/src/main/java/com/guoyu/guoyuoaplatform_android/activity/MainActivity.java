package com.guoyu.guoyuoaplatform_android.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.guoyu.guoyuoaplatform_android.R;
import com.guoyu.guoyuoaplatform_android.fragment.AnnouncementFragment;
import com.guoyu.guoyuoaplatform_android.fragment.ContactFragment;
import com.guoyu.guoyuoaplatform_android.fragment.ConversationFragment;
import com.guoyu.guoyuoaplatform_android.fragment.FunctionFragment;
import com.guoyu.guoyuoaplatform_android.fragment.MineFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnClickListener,ConversationFragment.OnFragmentInteractionListener,AnnouncementFragment.OnFragmentInteractionListener,FunctionFragment.OnFragmentInteractionListener,ContactFragment.OnFragmentInteractionListener,MineFragment.OnFragmentInteractionListener {

    private static final int BAR_CONVERSATION_TAG = 0;
    private static final int BAR_ANNOUNCEMENT_TAG = 1;
    private static final int BAR_FUNCTION_TAG = 2;
    private static final int BAR_CONTACT_TAG = 3;
    private static final int BAR_MINE_TAG = 4;

    private static int current_tag = -1;

    private ArrayList<Fragment> listData;
    private ViewPager pager;
    private FragmentPagerAdapter fpAdapter;
    private LinearLayout conversationLayout;
    private LinearLayout announcementLayout;
    private LinearLayout functionLayout;
    private LinearLayout contactLayout;
    private LinearLayout mineLayout;

    private TextView conversationTv;
    private TextView announcementTv;
    private TextView functionTv;
    private TextView contactTv;
    private TextView mineTv;

    private ImageView conversationIv;
    private ImageView announcementIv;
    private ImageView functionIv;
    private ImageView contactIv;
    private ImageView mineIv;


    private Context context;
    private ConversationFragment conversationFragment;
    private AnnouncementFragment announcementFragment;
    private FunctionFragment functionFragment;
    private ContactFragment contactFragment;
    private MineFragment mineFragment;

    private android.support.v4.app.FragmentManager fm;
    private android.support.v4.app.FragmentTransaction ft;

    private ImageButton myButton;
    private ArrayList<String> list;

    private ActionBar mActionBar;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        Fresco.initialize(this);

        initView();
        setCurrentTab(BAR_CONVERSATION_TAG);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("My Own Title");

        myButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                initPopWindow();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void initPopWindow(){

        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupwindow, null);
        contentView.setBackgroundColor(Color.BLUE);

        popupWindow = new PopupWindow(getWindow().getDecorView(), 200, 700);
        popupWindow.setContentView(contentView);

        TextView textView = (TextView) contentView.findViewById(R.id.text);
        textView.setText("测试");
        openDir();
        ListView listView = (ListView) contentView.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = list.get(position);
                Toast.makeText(getApplicationContext(), ""+position+" "+text,
                        Toast.LENGTH_LONG).show();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(myButton);
    }

    private void openDir()
    {
//        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File file  = new File(rootPath);
//        File[] files = file.listFiles();
//        name = new String[]{};
//        for(int i=0;i<files.length;i++){
//            name[i]=files[i].getName();
//            System.out.println(name[i]);
//        }

        list = new ArrayList<String>();

        int firstEnglish, lastEnglish;
        char firstE = 'A', lastE = 'Z';      //获取首字母与末字母的值

        firstEnglish = (int)firstE;
        lastEnglish = (int)lastE;

        for(int i = firstEnglish; i <= lastEnglish; ++i)
        {
            char uppercase, lowercase;

            uppercase = (char)i;
            lowercase = (char)(i + 32);

            list.add(""+uppercase+lowercase);
        }
    }

    private void initView() {

        conversationLayout = (LinearLayout) findViewById(R.id.conversation_bar_layout);
        conversationLayout.setOnClickListener(this);
        conversationTv = (TextView) findViewById(R.id.conversation_text);
        conversationIv = (ImageView) findViewById(R.id.conversation_icon);

        announcementLayout = (LinearLayout) findViewById(R.id.announcement_bar_layout);
        announcementLayout.setOnClickListener(this);
        announcementTv = (TextView) findViewById(R.id.announcement_text);
        announcementIv = (ImageView) findViewById(R.id.announcement_icon);

        functionLayout = (LinearLayout) findViewById(R.id.function_bar_layout);
        functionLayout.setOnClickListener(this);
        functionTv = (TextView) findViewById(R.id.function_text);
        functionIv = (ImageView) findViewById(R.id.function_icon);

        contactLayout = (LinearLayout) findViewById(R.id.contact_bar_layout);
        contactLayout.setOnClickListener(this);
        contactTv = (TextView) findViewById(R.id.contact_text);
        contactIv = (ImageView) findViewById(R.id.contact_icon);

        mineLayout = (LinearLayout) findViewById(R.id.mine_bar_layout);
        mineLayout.setOnClickListener(this);
        mineTv = (TextView) findViewById(R.id.mine_text);
        mineIv = (ImageView) findViewById(R.id.mine_icon);

        fm = getSupportFragmentManager();
        conversationFragment = new ConversationFragment();
        announcementFragment = new AnnouncementFragment();
        functionFragment = new FunctionFragment();
        contactFragment = new ContactFragment();
        mineFragment = new MineFragment();

        setViewPager(conversationFragment,announcementFragment,functionFragment,contactFragment,mineFragment);
    }

    private void setViewPager(ConversationFragment f1,AnnouncementFragment f2,FunctionFragment f3,ContactFragment f4,MineFragment f5) {
        //初始化数据
        pager = (ViewPager) findViewById(R.id.pager);
        listData = new ArrayList<Fragment>();
        //布局加入列表
        listData.add(f1);
        listData.add(f2);
        listData.add(f3);
        listData.add(f4);
        listData.add(f5);

        //ViewPager相当于一组件容器 实现页面切换
        fpAdapter = new FragmentPagerAdapter(fm)
        {
            @Override
            public int getCount()
            {
                return listData.size();
            }
            @Override
            public Fragment getItem(int arg0)
            {
                return listData.get(arg0);
            }
        };
        //设置适配器
        pager.setAdapter(fpAdapter);
    }

    @Override
    public void onClick(View v) {
        conversationIv.setImageResource(R.mipmap.tabbar_conversation_normal);
        conversationTv.setTextColor(getResources().getColor(R.color.white));

        announcementIv.setImageResource(R.mipmap.tabbar_announcement_normal);
        announcementTv.setTextColor(getResources().getColor(R.color.white));

        functionIv.setImageResource(R.mipmap.tabbar_function_normal);
        functionTv.setTextColor(getResources().getColor(R.color.white));

        contactIv.setImageResource(R.mipmap.tabbar_contact_normal);
        contactTv.setTextColor(getResources().getColor(R.color.white));

        mineIv.setImageResource(R.mipmap.tabbar_mine_normal);
        mineTv.setTextColor(getResources().getColor(R.color.white));
        if (v == conversationLayout) {
            setCurrentTab(BAR_CONVERSATION_TAG);
            conversationIv.setImageResource(R.mipmap.tabbar_conversation_selected);
            conversationTv.setTextColor(getResources().getColor(R.color.green));
        } else if (v == announcementLayout) {
            setCurrentTab(BAR_ANNOUNCEMENT_TAG);
            announcementIv.setImageResource(R.mipmap.tabbar_announcement_selected);
            announcementTv.setTextColor(getResources().getColor(R.color.green));
        } else if (v == functionLayout) {
            setCurrentTab(BAR_FUNCTION_TAG);
            functionIv.setImageResource(R.mipmap.tabbar_function_selected);
            functionTv.setTextColor(getResources().getColor(R.color.green));
        } else if (v ==  contactLayout) {
            setCurrentTab(BAR_CONTACT_TAG);
            contactIv.setImageResource(R.mipmap.tabbar_contact_selected);
            contactTv.setTextColor(getResources().getColor(R.color.green));
        } else {
            setCurrentTab(BAR_MINE_TAG);
            mineIv.setImageResource(R.mipmap.tabbar_mine_selected);
            mineTv.setTextColor(getResources().getColor(R.color.green));
        }
    }

    private void setCurrentTab(int tab) {
        switchFragmentMethod(tab);

//        switch (tab) {
//
//            case BAR_CONVERSATION_TAG:
//                if (current_tag != BAR_CONVERSATION_TAG) {
//
//                    showFragmentMethod(current_tag, BAR_CONVERSATION_TAG);
//                    current_tag = BAR_CONVERSATION_TAG;
//
//                }
//                break;
//            case BAR_CONTACT_TAG:
//                if (current_tag != BAR_CONTACT_TAG) {
//
//                    showFragmentMethod(current_tag, BAR_CONTACT_TAG);
//                    current_tag = BAR_CONTACT_TAG;
//                }
//                break;
//            default:
//                break;
//        }
    }

    private void switchFragmentMethod(int index) {

        pager.setCurrentItem(index);
    }

    private void showFragmentMethod(int oldBarIndex, int currentBarIndex) {
        ft = fm.beginTransaction();
        switch (currentBarIndex) {
            case -1:
            case BAR_CONVERSATION_TAG:
                hideFragmentMethod();
                if (conversationFragment.isAdded()) {

                    ft.show(conversationFragment);
                } else {
//                    ft.add(R.id.content, conversationFragment);
                }
                break;
            case BAR_ANNOUNCEMENT_TAG:
                hideFragmentMethod();
                if (announcementFragment.isAdded()) {

                    ft.show(announcementFragment);
                } else {
//                    ft.add(R.id.content, contactFragment);
                }
                break;
            case BAR_FUNCTION_TAG:
                hideFragmentMethod();
                if (functionFragment.isAdded()) {

                    ft.show(functionFragment);
                } else {
//                    ft.add(R.id.content, contactFragment);
                }
                break;
            case BAR_CONTACT_TAG:
                hideFragmentMethod();
                if (contactFragment.isAdded()) {

                    ft.show(contactFragment);
                } else {
//                    ft.add(R.id.content, contactFragment);
                }
                break;
            case BAR_MINE_TAG:
                hideFragmentMethod();
                if (mineFragment.isAdded()) {

                    ft.show(mineFragment);
                } else {
//                    ft.add(R.id.content, contactFragment);
                }
                break;
            default:
                break;
        }

        ft.commit();
    }

    private void hideFragmentMethod() {
        if (conversationFragment.isAdded()) {
            ft.hide(conversationFragment);
        }
        if (contactFragment.isAdded()) {
            ft.hide(contactFragment);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
