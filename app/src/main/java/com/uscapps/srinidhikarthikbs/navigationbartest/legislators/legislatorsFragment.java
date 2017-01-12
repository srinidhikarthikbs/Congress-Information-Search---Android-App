package com.uscapps.srinidhikarthikbs.navigationbartest.legislators;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class legislatorsFragment extends android.support.v4.app.Fragment {

    private static legislatorsFragment myInstance = null;
    ViewPager pager;
    TabLayout tabLayout;
    legislatorsPagerAdapter adapter=null;

    private static Context ctx;

    public static legislatorsFragment getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new legislatorsFragment();
        return myInstance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new legislatorsPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.legislators_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //((MainActivity)getActivity()).changeActionBarTitle("Legislators");
        ctx = getActivity().getApplicationContext();
        pager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager manager=getChildFragmentManager();

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        //final legislatorsPagerAdapter adapter=new legislatorsPagerAdapter(manager);

        //set Adapter to view pager
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);

        String url = "http://srinidhisample.appspot.com/helloworld.php?only_legislators=true";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("legislators count:",String.valueOf(response.getJSONArray("results").length()));
                            //Toast.makeText(ctx,"legislators count:"+String.valueOf(response.getJSONArray("results").length()),Toast.LENGTH_SHORT).show();

                            ArrayList<JSONObject> jsonObjectsHouse = new ArrayList<JSONObject>(),
                                    jsonObjectsSenate = new ArrayList<JSONObject>();
                            for(int i=0;i<response.getJSONArray("results").length();i++) {
                                if (response.getJSONArray("results").getJSONObject(i).getString("chamber").equals("house")) {
                                    jsonObjectsHouse.add(response.getJSONArray("results").getJSONObject(i));
                                }
                                if (response.getJSONArray("results").getJSONObject(i).getString("chamber").equals("senate")) {
                                    jsonObjectsSenate.add(response.getJSONArray("results").getJSONObject(i));
                                }
                            }

                            for (int i = 0; i < jsonObjectsHouse.size() - 1; i++) {
                                for (int j = i + 1; j < jsonObjectsHouse.size(); j++) {
                                    if (jsonObjectsHouse.get(i).getString("last_name").compareToIgnoreCase(jsonObjectsHouse.get(j).getString("last_name")) > 0) {
                                        JSONObject temp = jsonObjectsHouse.get(i);
                                        jsonObjectsHouse.set(i, jsonObjectsHouse.get(j));
                                        jsonObjectsHouse.set(j, temp);

                                    }
                                }
                            }

                            for (int i = 0; i < jsonObjectsSenate.size() - 1; i++) {
                                for (int j = i + 1; j < jsonObjectsSenate.size(); j++) {
                                    if (jsonObjectsSenate.get(i).getString("last_name").compareToIgnoreCase(jsonObjectsSenate.get(j).getString("last_name")) > 0) {
                                        JSONObject temp = jsonObjectsSenate.get(i);
                                        jsonObjectsSenate.set(i, jsonObjectsSenate.get(j));
                                        jsonObjectsSenate.set(j, temp);

                                    }
                                }
                            }

                            adapter.byStateInterface.getLegislatorsdata(ctx, response.getJSONArray("results"));//correct
                            adapter.houseInterface.getLegislatorsdata(ctx, jsonObjectsHouse);//sort by lastname and filter house
                            adapter.senateInterface.getLegislatorsdata(ctx, jsonObjectsSenate);//sort by lastname and filter senate
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("legislator error:",error.getMessage());
                        Toast.makeText(ctx,"legislator error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }

    public interface getDataFromParent{
        public void getLegislatorsdata(Context ctx, JSONArray jsonArray);
        public void getLegislatorsdata(Context ctx, ArrayList<JSONObject> legislators);
    }
}
