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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.favoritesManager;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorViewDetails;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorsListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class favoritesLegislatorsFragment extends Fragment implements View.OnClickListener {

    ListView listView=null;
    Map<String, Integer> mapIndex = null;
    LinearLayout indexLayout = null;
    View view = null;
    legislatorsListViewAdapter adapter = null;
    favoritesManager favMan = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favMan = favoritesManager.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.legislator_by_state_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.listView = (ListView) view.findViewById(R.id.listview);
        this.indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
        this.view = view;
        //setFavoriteLegislators(getContext(), favMan.getFavoriteEntityArrayList("legislators"));
    }

    public void setFavoriteLegislators(final Context ctx, ArrayList<JSONObject> legislators) {
        //Toast.makeText(getContext(),"from byState fragment: "+String.valueOf(jsonArray.length()),Toast.LENGTH_SHORT).show();
        try{
            int len = legislators.size();
            /*if(len==0)
                Toast.makeText(ctx,"shit",Toast.LENGTH_SHORT).show();*/
            adapter = new legislatorsListViewAdapter(ctx, R.id.listview, legislators);
            if(view!=null){
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject jsonObject = (JSONObject) listView.getItemAtPosition(position);
                        //Toast.makeText(ctx, jsonObject.getString("bioguide_id"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ctx, legislatorViewDetails.class);
                        intent.putExtra("legislator", String.valueOf(jsonObject));
                        startActivity(intent);
                    }
                });
                getIndexList(legislators, "last_name");
                displayIndex();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void getIndexList(ArrayList<JSONObject> legislators, String sortParam) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legislators.size(); i++) {
            JSONObject legislator = legislators.get(i);
            String index = null;
            try {
                index = (String.valueOf(legislator.getString(sortParam)).substring(0, 1)).toUpperCase();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        indexLayout.removeAllViews();
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index.toUpperCase());
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        TextView selectedIndex = (TextView) v;
        listView.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    @Override
    public void onResume() {
        super.onResume();
        setFavoriteLegislators(getContext(), favMan.getFavoriteEntityArrayList("legislators"));
        //Toast.makeText(getContext().getApplicationContext(), "onresume from favoritesLegislatorsFragment", Toast.LENGTH_SHORT).show();
    }

}
