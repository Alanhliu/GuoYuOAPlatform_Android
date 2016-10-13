package com.guoyu.guoyuoaplatform_android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.guoyu.guoyuoaplatform_android.R;

import java.util.ArrayList;
import java.util.List;

public class CountrySectionActivity extends Activity {

    ExpandableListView listView;
    private List<String> groupList;
    private List<List<String>> childList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_section);

        initdate();

        listView = (ExpandableListView) findViewById(R.id.country_expandableListView);
        final ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(this,groupList,childList);
        listView.setAdapter(adapter);

        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//禁止点击
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;//允许点击
            }
        });

        for(int i = 0; i < adapter.getGroupCount(); i++){
            listView.expandGroup(i);
        }
    }

    private void initdate()
    {
        groupList = new ArrayList<String>();
        childList = new ArrayList<List<String>>();

        addInfo("语言", new String[]{"Oracle","Java","Linux","Jquery"});
        addInfo("男人的需求", new String[]{"金钱","事业","权力","女人","房子","车","球"});
    }

    private void addInfo(String group,String[] child) {

        groupList.add(group);

        List<String> childListInSection = new ArrayList<String>();

        for(int index=0 ;index<child.length ;index++)
        {
            childListInSection.add(child[index]);
        }

        childList.add(childListInSection);
    }

    class ExpandableListViewAdapter extends BaseExpandableListAdapter {
        private List<String> groupList;
        private List<List<String>> childList;
        private LayoutInflater inflater;
        private GroupViewHolder groupViewHolder;
        private ChildViewHolder childViewHolder;

        ExpandableListViewAdapter(Context context, List<String> groupList, List<List<String>> childList) {
            this.groupList = groupList;
            this.childList = childList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list_countrysection_group,parent,false);
                groupViewHolder = new GroupViewHolder(convertView);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }

            String text = (String) getGroup(groupPosition);
            groupViewHolder.item_group_tv.setText(text);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list_countrysection_child,parent,false);
                childViewHolder = new ChildViewHolder(convertView);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }

            String text = (String) getChild(groupPosition,childPosition);
            childViewHolder.item_child_tv.setText(text);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupViewHolder {
            TextView item_group_tv;
            GroupViewHolder (View view) {
                item_group_tv = (TextView) view.findViewById(R.id.country_item_group);
                view.setTag(this);
            }
        }

        class ChildViewHolder {
            TextView item_child_tv;
            ChildViewHolder (View view) {
                item_child_tv = (TextView) view.findViewById(R.id.country_item_child);
                view.setTag(this);
            }
        }
    }
}
