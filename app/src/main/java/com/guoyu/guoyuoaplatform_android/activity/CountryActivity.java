package com.guoyu.guoyuoaplatform_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.bean.Country;
import com.guoyu.guoyuoaplatform_android.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends Activity {

    public static final int TAG = 101;

    private ListView listView;
    private List<Country> countryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        listView = (ListView) findViewById(R.id.country_listview);

        AssetManager asset = getAssets();
        try {//null
            InputStream input = asset.open("Country_dial.xml");
            countryList = countryList(input);

        } catch (Throwable e) {
            e.printStackTrace();
        }

        final CountryAdapter adapter = new CountryAdapter(countryList,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country country = countryList.get(position);
                String text = country.getName()+":"+country.getDialCode();
//                Toast.makeText(getApplicationContext(), text,
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("result", text);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，
                 * 这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(TAG, intent);

                countryList.remove(position);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

                //结束当前这个Activity对象的生命
//                finish();
            }
        });
    }

    private List<Country> countryList(InputStream inStream) throws Throwable {

        List<Country> countries = null;
        Country country = null;

        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullFactory.newPullParser();

        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                     countries = new ArrayList<Country>();
                     break;

                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if ("dict".equals(name)) {
                        country = new Country();
                    }

                    if (country != null) {

                        if ("string".equals(name)) {
                            // 获取解析器当前指向元素的下一个文本节点的值
                            String value = parser.nextText();
                            if (country.getName() == null) {
                                country.setName(value);
                                break;
                            }
                            if (country.getDialCode() == null) {
                                country.setDialCode(value);
                                break;
                            }
                            if (country.getPinyin() == null) {
                                country.setPinyin(value);
                                break;
                            }
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                     if ("dict".equals(parser.getName())) {
                         countries.add(country);
                         country = null;
                     }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return countries;
    }

    private class CountryAdapter extends BaseAdapter {

        private List<Country> countries;
        private LayoutInflater inflater;
        private ViewHolder holder;

        CountryAdapter(List<Country> countries, Context context) {
            this.countries = countries;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return countries.size();
        }

        @Override
        public Object getItem(int position) {
            return countries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list_country,parent,false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = (Country) getItem(position);
            String text = country.getName()+"("+country.getPinyin()+"):"+country.getDialCode();
            holder.country_name_tv.setText(text);

            return convertView;
        }

        private class ViewHolder {
            TextView country_name_tv;
            ViewHolder(View view) {
                country_name_tv = (TextView) view.findViewById(R.id.country_name_tv);
                view.setTag(this);
            }
        }
    }
}
