package com.uscapps.srinidhikarthikbs.navigationbartest.bills;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.uscapps.srinidhikarthikbs.navigationbartest.MainActivity;
import com.uscapps.srinidhikarthikbs.navigationbartest.utilities.MySingleton;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class billsFragment extends android.support.v4.app.Fragment {

    ViewPager pager;
    TabLayout tabLayout;
    private static billsFragment myInstance = null;
    public static Context ctx = null;
    billsPagerAdapter adapter = null;

    public static billsFragment getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new billsFragment();
        return myInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new billsPagerAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bills_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //((MainActivity)getActivity()).changeActionBarTitle("Bills");
        ctx = getActivity().getApplicationContext();
        pager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager manager=getChildFragmentManager();

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        //final billsPagerAdapter adapter=new billsPagerAdapter(manager);

        //set Adapter to view pager
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);

        String url_active = "http://srinidhisample.appspot.com/helloworld.php?only_active_bills=true";
        JsonObjectRequest jsObjRequest_active = new JsonObjectRequest
                (Request.Method.GET, url_active, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("active_bills count:",String.valueOf(response.getJSONArray("results").length()));
                            //Toast.makeText(ctx,"active_bills count:"+String.valueOf(response.getJSONArray("results").length()),Toast.LENGTH_SHORT).show();
                            adapter.activeBillsInterface.getBillsdata(ctx, response.getJSONArray("results"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("active_bills error:",error.getMessage());
                        Toast.makeText(ctx,"active_bills error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest_active);

        String url_new = "http://srinidhisample.appspot.com/helloworld.php?only_new_bills=true";
        JsonObjectRequest jsObjRequest_new = new JsonObjectRequest
                (Request.Method.GET, url_new, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("new_bills count:",String.valueOf(response.getJSONArray("results").length()));
                            //Toast.makeText(ctx,"new_bills count:"+String.valueOf(response.getJSONArray("results").length()),Toast.LENGTH_SHORT).show();
                            adapter.newBillsInterface.getBillsdata(ctx, response.getJSONArray("results"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("new_bills error:",error.getMessage());
                        Toast.makeText(ctx,"new_bills error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest_new);
    }

    public interface getDataFromParent{
        public void getBillsdata(Context ctx, JSONArray jsonArray);
    }

}
