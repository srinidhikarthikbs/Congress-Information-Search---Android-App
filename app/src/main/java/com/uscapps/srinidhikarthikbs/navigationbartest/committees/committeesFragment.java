package com.uscapps.srinidhikarthikbs.navigationbartest.committees;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

public class committeesFragment extends Fragment {

    public static committeesFragment myInstance = null;
    ViewPager pager;
    TabLayout tabLayout;
    committeesPagerAdapter adapter = null;

    public static committeesFragment getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new committeesFragment();
        return myInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new committeesPagerAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.committees_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //((MainActivity)getActivity()).changeActionBarTitle("Committees");
        pager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager manager=getChildFragmentManager();

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        //final committeesPagerAdapter adapter=new committeesPagerAdapter(manager);

        //set Adapter to view pager
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);
        Log.d("committees request:","entering now");
        String url = "http://srinidhisample.appspot.com/hw9.php?only_committees_name=true";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("committees count:",String.valueOf(response.getJSONArray("results").length()));
                            //Toast.makeText(getActivity(),"committees count:"+String.valueOf(response.getJSONArray("results").length()),Toast.LENGTH_SHORT).show();

                            ArrayList<JSONObject> jsonObjectsHouse = new ArrayList<JSONObject>(),
                                    jsonObjectsSenate = new ArrayList<JSONObject>(),
                                    jsonObjectsJoint = new ArrayList<JSONObject>();
                            for(int i=0;i<response.getJSONArray("results").length();i++) {
                                if (response.getJSONArray("results").getJSONObject(i).getString("chamber").equals("house")) {
                                    jsonObjectsHouse.add(response.getJSONArray("results").getJSONObject(i));
                                }
                                if (response.getJSONArray("results").getJSONObject(i).getString("chamber").equals("senate")) {
                                    jsonObjectsSenate.add(response.getJSONArray("results").getJSONObject(i));
                                }
                                if (response.getJSONArray("results").getJSONObject(i).getString("chamber").equals("joint")) {
                                    jsonObjectsJoint.add(response.getJSONArray("results").getJSONObject(i));
                                }
                            }

                            adapter.houseCommitteeInterface.getCommitteesdata(getActivity().getApplicationContext(), jsonObjectsHouse);
                            adapter.senateCommitteeInterface.getCommitteesdata(getActivity().getApplicationContext(), jsonObjectsSenate);
                            adapter.jointCommitteeInterface.getCommitteesdata(getActivity().getApplicationContext(), jsonObjectsJoint);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("committees error:",error.getMessage());
                        Toast.makeText(getActivity(),"committees error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

        Log.d("committees request:","added to queue");
    }

    public interface getDataFromParent{
        public void getCommitteesdata(Context ctx, ArrayList<JSONObject> committees);
    }
}
