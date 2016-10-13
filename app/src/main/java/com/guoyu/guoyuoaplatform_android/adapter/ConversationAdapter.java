package com.guoyu.guoyuoaplatform_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;
import com.guoyu.guoyuoaplatform_android.constant.ConversationConstant;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by liuhao on 16/6/2.
 */
public class ConversationAdapter extends BaseAdapter {

    private ArrayList<Map<String,String>> list;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public ConversationAdapter(ArrayList<Map<String,String>> list, Context context) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_conversation, parent,
                    false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String,String> map = this.list.get(position);

        holder.name_tv.setText(map.get(ConversationConstant.KEY_NAME));
        holder.message_tv.setText(map.get(ConversationConstant.KEY_MESSAGE));
        holder.time_tv.setText(map.get(ConversationConstant.KEY_TIME));

        return convertView;
    }

    class ViewHolder {
        TextView name_tv;
        TextView message_tv;
        TextView time_tv;

        public ViewHolder (View view) {
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            message_tv = (TextView) view.findViewById(R.id.last_message_tv);
            time_tv = (TextView) view.findViewById(R.id.time_tv);

            view.setTag(this);
        }
    }
}
