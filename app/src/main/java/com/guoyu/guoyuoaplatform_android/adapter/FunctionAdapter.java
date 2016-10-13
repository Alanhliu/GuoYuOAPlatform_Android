package com.guoyu.guoyuoaplatform_android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhao on 16/6/27.
 */
public class FunctionAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ViewHolder holder;
    Context context;

    ArrayList<Map<String,String>> list;

    public FunctionAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 12; i++) {
            Map map = new HashMap();
            map.put("text",textDataSource()[i]);
            map.put("image",imageDataSource()[i]);
            list.add(map);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_function,parent,false);
            holder = new ViewHolder(convertView);
        }

        holder = (ViewHolder) convertView.getTag();

        Map<String,String> map = list.get(position);
        String text = map.get("text");
        String imageName = map.get("image");


        Resources res = this.context.getResources();
        int resID = res.getIdentifier(imageName , "mipmap", this.context.getPackageName());
        Drawable drawable = res.getDrawable(resID);

        holder.function_item_tv.setText(text);
        holder.function_item_iv.setImageDrawable(drawable);

        return convertView;
    }


    class ViewHolder {
        ImageView function_item_iv;
        TextView function_item_tv;

        public ViewHolder(View view) {
            function_item_iv = (ImageView) view.findViewById(R.id.function_item_iv);
            function_item_tv = (TextView) view.findViewById(R.id.function_item_tv);
            view.setTag(this);
        }
    }


    static String[] textDataSource() {
        String[] strArray = {"供应商","联系人","商品","采购单","历史记录","统计","报表","审批","订购单","入货单","退货单","结算单"};
        return strArray;
    }

    static String[] imageDataSource() {
        String[] strArray = new String[12];
        for (int i=0 ; i<12; i++) {
            strArray[i] = "function_item_"+i;
        }
        return strArray;
    }
}
