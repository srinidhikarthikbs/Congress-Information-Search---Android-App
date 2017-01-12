package com.uscapps.srinidhikarthikbs.navigationbartest.favorites.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsListViewAdapter;
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.favoritesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class favoritesBillsFragment extends Fragment {

    ListView listView=null;
    View view = null;
    billsListViewAdapter adapter = null;
    JSONArray data=null;
    favoritesManager favMan = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favMan = favoritesManager.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bills_active_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.listView = (ListView) view.findViewById(R.id.listview);
        this.view = view;
    }

    public void getBillsdata(final Context ctx, JSONArray jsonArray) {
        try{
            int len = jsonArray.length();
            /*if(len==0)
                Toast.makeText(ctx,"shit",Toast.LENGTH_SHORT).show();*/
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

    @Override
    public void onResume() {
        super.onResume();
        getBillsdata(getContext(), favMan.getFavoriteEntityJSONArray("bills"));
    }
}
