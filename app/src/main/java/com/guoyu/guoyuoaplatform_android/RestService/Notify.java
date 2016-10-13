package com.guoyu.guoyuoaplatform_android.RestService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.guoyu.guoyuoaplatform_android.R;
import com.guoyu.guoyuoaplatform_android.activity.CountryActivity;
import com.guoyu.guoyuoaplatform_android.activity.MainActivity;

/**
 * Created by siasun on 16/9/29.
 */

public class Notify {
    public static void simpleNotify(Context context){
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //Ticker是状态栏显示的提示
        builder.setTicker("简单Notification");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题");
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.function_item_0));
        Intent intent = new Intent(context,CountryActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,1,intent,0);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,notification);
    }

    public static void progressNotify(Context context) {
        int progress = 10;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.function_item_3));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //取消右上角的时间显示
        builder.setShowWhen(false);
        builder.setContentTitle("下载中..."+progress+"%");
        builder.setProgress(100,progress,false);
        //builder.setContentInfo(progress+"%");
        builder.setOngoing(true);
        builder.setShowWhen(false);
        Intent intent = new Intent(context,CountryActivity.class);
        intent.putExtra("command",1);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0,notification);
    }

    //command是自定义用来区分各种点击事件的
    public static void sendCustomerNotification(Context context,int command){
        int CommandNext = -1;
        int StatusStop = 0;
        int CommandPlay = 1;
        int CommandClose = 11;

        int playerStatus = 0;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Notification");
        builder.setContentText("自定义通知栏示例");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.push));
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setShowWhen(false);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification_template_customer);
        remoteViews.setTextViewText(R.id.title,"Notification");
        remoteViews.setTextViewText(R.id.text,"song");
        if(command==CommandNext){
            remoteViews.setImageViewResource(R.id.btn1,R.mipmap.function_item_5);
        }else if(command==CommandPlay){
            if(playerStatus==StatusStop){
                remoteViews.setImageViewResource(R.id.btn1,R.mipmap.function_item_6);
            }else{
                remoteViews.setImageViewResource(R.id.btn1,R.mipmap.function_item_7);
            }
        }
        Intent Intent1 = new Intent(context,CountryActivity.class);
        Intent1.putExtra("command",CommandPlay);
        //getService(Context context, int requestCode, @NonNull Intent intent, @Flags int flags)
        //不同控件的requestCode需要区分开 getActivity broadcoast同理
        PendingIntent PIntent1 =  PendingIntent.getService(context,5,Intent1,0);
        remoteViews.setOnClickPendingIntent(R.id.btn1,PIntent1);

        Intent Intent2 = new Intent(context,CountryActivity.class);
        Intent2.putExtra("command",CommandNext);
        PendingIntent PIntent2 =  PendingIntent.getService(context,6,Intent2,0);
        remoteViews.setOnClickPendingIntent(R.id.btn2,PIntent2);

        Intent Intent3 = new Intent(context,CountryActivity.class);
        Intent3.putExtra("command",CommandClose);
        PendingIntent PIntent3 =  PendingIntent.getService(context,7,Intent3,0);
        remoteViews.setOnClickPendingIntent(R.id.btn3,PIntent3);

        builder.setContent(remoteViews);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0,notification);
    }
}
