package com.uscapps.srinidhikarthikbs.navigationbartest.bills.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsListViewAdapter;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorsListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class billsActiveFragment extends Fragment implements billsFragment.getDataFromParent{

    ListView listView=null;
    View view = null;
    billsListViewAdapter adapter = null;
    JSONArray data=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bills_active_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        this.listView = (ListView) view.findViewById(R.id.listview);
        this.view = view;
        //if(this.view==null) Toast.makeText(getContext(),"onViewCreated view null",Toast.LENGTH_SHORT).show();
        //if(this.listView==null) Toast.makeText(getContext(),"onViewCreated listView null",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"done with onViewCreated",Toast.LENGTH_SHORT).show();
        getBillsdata(getActivity().getApplicationContext(),this.data);
    }

    @Override
    public void getBillsdata(final Context ctx, JSONArray jsonArray) {
        try{
            int len = jsonArray.length();
            if(len!=50)
                Toast.makeText(ctx,"shit",Toast.LENGTH_SHORT).show();
            this.data = jsonArray;

            final ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
            for(int i=0;i<jsonArray.length();i++)
                jsonObjects.add(jsonArray.getJSONObject(i));
            adapter = new billsListViewAdapter(ctx, R.id.listview, jsonObjects);
            //if(this.view==null) Toast.makeText(ctx,"view null",Toast.LENGTH_SHORT).show();
            if(this.view!=null){
                listView = (ListView) view.findViewById(R.id.listview);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject jsonObject = (JSONObject) listView.getItemAtPosition(position);
                        //Toast.makeText(ctx, jsonObject.getString("bill_id"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ctx, billsViewDetails.class);
                        intent.putExtra("bill", String.valueOf(jsonObject));
                        startActivity(intent);
                    }
                });
            }


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public static Fragment getInstance(){
        return new billsActiveFragment();
    }
}
