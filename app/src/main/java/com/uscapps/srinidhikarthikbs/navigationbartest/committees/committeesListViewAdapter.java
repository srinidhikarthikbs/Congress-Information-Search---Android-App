package com.uscapps.srinidhikarthikbs.navigationbartest.committees;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsListViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Srinidhi Karthik B S on 11/22/2016.
 */

public class committeesListViewAdapter extends ArrayAdapter<JSONObject> {
    public committeesListViewAdapter(Context context, int resource, ArrayList<JSONObject> committees) {
        super(context, resource, committees);
    }

    static class ViewHolder {

        private LinearLayout wrapper;
        private TextView committeeId;
        private TextView committeeName;
        private TextView committeeChamber;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        ViewHolder mViewHolder = null;
        JSONObject committee = null;

        //if(convertView!=null) return convertView;

        if (convertView == null) {


            mViewHolder = new ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.committee_row, parent, false);

            mViewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.wrapper);
            mViewHolder.committeeId = (TextView) convertView.findViewById(R.id.committee_id);
            mViewHolder.committeeName = (TextView) convertView.findViewById(R.id.committee_name);
            mViewHolder.committeeChamber = (TextView) convertView.findViewById(R.id.committee_chamber);

            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        committee = getItem(position);

        if (committee != null) {
            try {
                String content = (committee.getString("committee_id").length() > 0 ? committee.getString("committee_id") : "");
                mViewHolder.committeeId.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String content = (committee.getString("name").length() > 0 ? committee.getString("name") : "");
                mViewHolder.committeeName.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String content = (committee.getString("chamber").length() > 0 ? committee.getString("chamber") : "");
                mViewHolder.committeeChamber.setText(toTitleCase(content));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
            mViewHolder.wrapper.setBackground(getContext().getResources().getDrawable(R.drawable.list_item_background, null));
            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
        }

        return convertView;
    }

    //http://stackoverflow.com/questions/1086123/string-conversion-to-title-case
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
