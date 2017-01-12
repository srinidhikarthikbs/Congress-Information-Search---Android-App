package com.uscapps.srinidhikarthikbs.navigationbartest.legislators;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Srinidhi Karthik B S on 11/20/2016.
 */

public class legislatorsListViewAdapter extends ArrayAdapter<JSONObject> {
    public legislatorsListViewAdapter(Context context, int resource, ArrayList<JSONObject> legislators) {
        super(context, resource, legislators);
    }

    static class ViewHolder {

        private LinearLayout wrapper;
        private ImageView imageView;
        private TextView legislatorName;
        private TextView legislatorCaption;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ViewHolder mViewHolder = null;
        JSONObject legislator = null;

        //if(convertView!=null) return convertView;

        if (convertView == null) {


            mViewHolder = new ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.legislator_row, parent, false);

            mViewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.wrapper);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.legislator_image);
            mViewHolder.legislatorName = (TextView) convertView.findViewById(R.id.legislator_name);
            mViewHolder.legislatorCaption = (TextView) convertView.findViewById(R.id.legislator_caption);

            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        legislator = getItem(position);

        if (legislator != null) {
            try {
                Picasso.with(getContext()).load("https://theunitedstates.io/images/congress/225x275/" + legislator.getString("bioguide_id") + ".jpg").into(mViewHolder.imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String content = (legislator.getString("last_name").length() > 0 ? legislator.getString("last_name") + ", " : "") + (legislator.getString("first_name").length() > 0 ? legislator.getString("first_name") : "");
                mViewHolder.legislatorName.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String district = String.valueOf(legislator.optInt("district",0));
                mViewHolder.legislatorCaption.setText("(" + legislator.getString("party") + ")" + legislator.getString("state_name") + " - District " + district);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
            mViewHolder.wrapper.setBackground(getContext().getResources().getDrawable(R.drawable.list_item_background, null));
            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
        }

        return convertView;
    }
}
