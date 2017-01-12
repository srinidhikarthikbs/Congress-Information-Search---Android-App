package com.uscapps.srinidhikarthikbs.navigationbartest.bills;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorsListViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Srinidhi Karthik B S on 11/22/2016.
 */

public class billsListViewAdapter extends ArrayAdapter<JSONObject> {
    public billsListViewAdapter(Context context, int resource, ArrayList<JSONObject> bills) {
        super(context, resource, bills);
    }

    static class ViewHolder {

        private LinearLayout wrapper;
        private TextView billId;
        private TextView billTitle;
        private TextView billDate;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ViewHolder mViewHolder = null;
        JSONObject bill = null;

        //if(convertView!=null) return convertView;

        if (convertView == null) {


            mViewHolder = new ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.bill_row, parent, false);

            mViewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.wrapper);
            mViewHolder.billId = (TextView) convertView.findViewById(R.id.bill_id);
            mViewHolder.billTitle = (TextView) convertView.findViewById(R.id.bill_title);
            mViewHolder.billDate = (TextView) convertView.findViewById(R.id.bill_date);

            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        bill = getItem(position);

        if (bill != null) {
            try {
                String content = (bill.getString("bill_id").length() > 0 ? bill.getString("bill_id") : "");
                mViewHolder.billId.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String content = (bill.getString("official_title").length() > 0 ? bill.getString("official_title") : "");
                mViewHolder.billTitle.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                String content = (bill.getString("introduced_on").length() > 0 ? bill.getString("introduced_on") : "");
                mViewHolder.billDate.setText(getDate(String.valueOf(content)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
            mViewHolder.wrapper.setBackground(getContext().getResources().getDrawable(R.drawable.list_item_background, null));
            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
        }

        return convertView;
    }

    //http://stackoverflow.com/questions/6896635/date-format-conversion-android
    public String getDate(String given_date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        java.util.Date date = null;
        try
        {
            date = form.parse(given_date);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
            return "";
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String newDateStr = postFormater.format(date);
        return String.valueOf(newDateStr);
    }
}
