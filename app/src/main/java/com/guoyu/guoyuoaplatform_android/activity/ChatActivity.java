package com.guoyu.guoyuoaplatform_android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends Activity {

    private Button sendButton;
    private TextView textView;
    private List<String> messages;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messages = new ArrayList<String>();

        sendButton = (Button) findViewById(R.id.send);
        textView = (TextView) findViewById(R.id.input_text);
        listView = (ListView) findViewById(R.id.chat_listview);


        final ChatAdapter adapter = new ChatAdapter(this);

        listView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messages.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                listView.smoothScrollToPosition(0);
            }
        });
    }

    private class ChatAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private ViewHolder holder;

        ChatAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list_chat,parent,false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String text = messages.get(position);

            holder.contentView.setText(text);

            return convertView;
        }

        private class ViewHolder {
            TextView contentView;
            ViewHolder(View view) {
                contentView = (TextView) view.findViewById(R.id.textView);
                view.setTag(this);
            }
        }
    }
}
