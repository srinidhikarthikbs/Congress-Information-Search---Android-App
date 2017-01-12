package com.uscapps.srinidhikarthikbs.navigationbartest.committees.fragments;

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
import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.committees.committeeViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.committees.committeesFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.committees.committeesListViewAdapter;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.fragments.legislatorByStateFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class committeeHouseFragment extends Fragment implements committeesFragment.getDataFromParent{

    ArrayList<JSONObject> data = null;
    ListView listView = null;
    View view = null;
    committeesListViewAdapter adapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.committee_house_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.listView = (ListView) view.findViewById(R.id.listview);
        this.view = view;
        getCommitteesdata(getActivity().getApplicationContext(), this.data);
    }

    @Override
    public void getCommitteesdata(final Context ctx, ArrayList<JSONObject> committees) {
        try{
            int len = committees.size();
            if(len==0)
                Toast.makeText(getParentFragment().getActivity(),"shit",Toast.LENGTH_SHORT).show();
            this.data = committees;
            adapter = new committeesListViewAdapter(ctx, R.id.listview, committees);
            if(view!=null){
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject jsonObject = (JSONObject) listView.getItemAtPosition(position);
                        //Toast.makeText(ctx, jsonObject.getString("committee_id"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ctx, committeeViewDetails.class);
                        intent.putExtra("committee", String.valueOf(jsonObject));
                        startActivity(intent);
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public static Fragment getInstance() {
        //not maintaining any instance here coz the adapter is made to. So no need here.
        return new committeeHouseFragment();
    }
}
