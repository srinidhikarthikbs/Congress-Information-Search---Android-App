package com.uscapps.srinidhikarthikbs.navigationbartest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by srinidhikarthikbs on 11/28/16.
 */

public class favoritesManager {
    private static favoritesManager myInstance = null;
    private SharedPreferences prefs = null;
    private static Context context = null;

    public static favoritesManager getInstance(Context ctx){
        if(context==null) context=ctx;
        if(myInstance!=null) return myInstance;
        myInstance = new favoritesManager();
        return myInstance;
    }

    public boolean isFavorited(String entity, String id){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);

        switch (entity) {

            case "legislator":
                String legislators_favorite = prefs.getString("legislators_favorite", null);
                JSONArray legislators_favorite_jsonarray = null;
                if(legislators_favorite!=null){
                    try {
                        legislators_favorite_jsonarray = new JSONArray(legislators_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(legislators_favorite_jsonarray!=null && legislators_favorite_jsonarray.length()>0){
                    for (int i = 0; i < legislators_favorite_jsonarray.length(); i++) {
                        try {
                            if (legislators_favorite_jsonarray.getJSONObject(i).getString("bioguide_id").equals(id)){
                                //Toast.makeText(context, "legislator "+id+" is favorited already", Toast.LENGTH_SHORT).show();
                                return true;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            break;

            case "bill":
                String bills_favorite = prefs.getString("bills_favorite", null);
                JSONArray bills_favorite_jsonarray = null;
                if(bills_favorite!=null){
                    try {
                        bills_favorite_jsonarray = new JSONArray(bills_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(bills_favorite_jsonarray!=null && bills_favorite_jsonarray.length()>0){
                    for (int i = 0; i < bills_favorite_jsonarray.length(); i++) {
                        try {
                            if (bills_favorite_jsonarray.getJSONObject(i).getString("bill_id").equals(id)){
                                //Toast.makeText(context, "bill "+id+" is favorited already", Toast.LENGTH_SHORT).show();
                                return true;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            break;

            case "committee":
                String committees_favorite = prefs.getString("committees_favorite", null);
                JSONArray committees_favorite_jsonarray = null;
                if(committees_favorite!=null){
                    try {
                        committees_favorite_jsonarray = new JSONArray(committees_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(committees_favorite_jsonarray!=null && committees_favorite_jsonarray.length()>0){
                    for (int i = 0; i < committees_favorite_jsonarray.length(); i++) {
                        try {
                            if (committees_favorite_jsonarray.getJSONObject(i).getString("committee_id").equals(id)){
                                //Toast.makeText(context, "committee "+id+" is favorited already", Toast.LENGTH_SHORT).show();
                                return true;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            break;

            default:
                Toast.makeText(context, "shit happened in isFavorited", Toast.LENGTH_SHORT).show();
                return false;
        }

        return false;
    }

    public void removeFromFavorites(String entity, String id){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if(!isFavorited(entity, id)) return;

        switch (entity) {

            case "legislator":
                String legislators_favorite = prefs.getString("legislators_favorite", null);
                JSONArray legislators_favorite_jsonarray = null,
                        new_legislators_favorite_jsonarray = new JSONArray();
                if(legislators_favorite!=null){
                    try {
                        legislators_favorite_jsonarray = new JSONArray(legislators_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(legislators_favorite_jsonarray!=null && legislators_favorite_jsonarray.length()>0){
                    for (int i = 0; i < legislators_favorite_jsonarray.length(); i++) {
                        try {
                            if (!(legislators_favorite_jsonarray.getJSONObject(i).getString("bioguide_id").equals(id)))
                                new_legislators_favorite_jsonarray.put(legislators_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("legislators_favorite", String.valueOf(new_legislators_favorite_jsonarray));
                //Toast.makeText(context, "legislator "+id+" is removed from favorites", Toast.LENGTH_SHORT).show();
                break;

            case "bill":
                String bills_favorite = prefs.getString("bills_favorite", null);
                JSONArray bills_favorite_jsonarray = null,
                        new_bills_favorite_jsonarray = new JSONArray();
                if(bills_favorite!=null){
                    try {
                        bills_favorite_jsonarray = new JSONArray(bills_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(bills_favorite_jsonarray!=null && bills_favorite_jsonarray.length()>0){
                    for (int i = 0; i < bills_favorite_jsonarray.length(); i++) {
                        try {
                            if (!(bills_favorite_jsonarray.getJSONObject(i).getString("bill_id").equals(id)))
                                new_bills_favorite_jsonarray.put(bills_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("bills_favorite", String.valueOf(new_bills_favorite_jsonarray));
                //Toast.makeText(context, "bill "+id+" is removed from favorites", Toast.LENGTH_SHORT).show();
                break;

            case "committee":
                String committees_favorite = prefs.getString("committees_favorite", null);
                JSONArray committees_favorite_jsonarray = null,
                        new_committees_favorite_jsonarray = new JSONArray();
                if(committees_favorite!=null){
                    try {
                        committees_favorite_jsonarray = new JSONArray(committees_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(committees_favorite_jsonarray!=null && committees_favorite_jsonarray.length()>0){
                    for (int i = 0; i < committees_favorite_jsonarray.length(); i++) {
                        try {
                            if (!(committees_favorite_jsonarray.getJSONObject(i).getString("committee_id").equals(id)))
                                new_committees_favorite_jsonarray.put(committees_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("committees_favorite", String.valueOf(new_committees_favorite_jsonarray));
                //Toast.makeText(context, "committee "+id+" is removed from favorites", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(context, "error happened in removeFromFavorites", Toast.LENGTH_SHORT).show();
                return;
        }

        editor.apply();
    }

    public void addToFavorites(String entity, String id, JSONObject object){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if(isFavorited(entity, id)) return;
        switch (entity) {

            case "legislator":
                String legislators_favorite = prefs.getString("legislators_favorite", null);
                JSONArray legislators_favorite_jsonarray = new JSONArray();
                if(legislators_favorite!=null){
                    try {
                        legislators_favorite_jsonarray = new JSONArray(legislators_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                legislators_favorite_jsonarray.put(object);
                editor.putString("legislators_favorite", String.valueOf(legislators_favorite_jsonarray));
                //Toast.makeText(context, "legislator "+id+" is added to favorites", Toast.LENGTH_SHORT).show();
                break;

            case "bill":
                String bills_favorite = prefs.getString("bills_favorite", null);
                JSONArray bills_favorite_jsonarray = new JSONArray();
                if(bills_favorite!=null){
                    try {
                        bills_favorite_jsonarray = new JSONArray(bills_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                bills_favorite_jsonarray.put(object);
                editor.putString("bills_favorite", String.valueOf(bills_favorite_jsonarray));
                //Toast.makeText(context, "bill "+id+" is added to favorites", Toast.LENGTH_SHORT).show();
                break;

            case "committee":
                String committees_favorite = prefs.getString("committees_favorite", null);
                JSONArray committees_favorite_jsonarray = new JSONArray();
                if(committees_favorite!=null){
                    try {
                        committees_favorite_jsonarray = new JSONArray(committees_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                committees_favorite_jsonarray.put(object);
                editor.putString("committees_favorite", String.valueOf(committees_favorite_jsonarray));
                //Toast.makeText(context, "committee "+id+" is added to favorites", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(context, "error happened in addToFavorites", Toast.LENGTH_SHORT).show();
                return;
        }
        editor.apply();
    }

    public ArrayList<JSONObject> getFavoriteEntityArrayList(String entity){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);

        switch (entity) {

            case "legislators":
                //Toast.makeText(context, "getting legislator", Toast.LENGTH_SHORT).show();
                String legislators_favorite = prefs.getString("legislators_favorite", null);
                JSONArray legislators_favorite_jsonarray = null;
                ArrayList<JSONObject> jsonObjectsLeg = new ArrayList<JSONObject>();
                if(legislators_favorite!=null){
                    try {
                        legislators_favorite_jsonarray = new JSONArray(legislators_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(legislators_favorite_jsonarray!=null && legislators_favorite_jsonarray.length()>0){

                    for (int i = 0; i < legislators_favorite_jsonarray.length(); i++) {
                        try {
                            jsonObjectsLeg.add(legislators_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < jsonObjectsLeg.size() - 1; i++) {
                        for (int j = i + 1; j < jsonObjectsLeg.size(); j++) {
                            try {
                                if (jsonObjectsLeg.get(i).getString("last_name").compareToIgnoreCase(jsonObjectsLeg.get(j).getString("last_name")) > 0) {
                                    JSONObject temp = jsonObjectsLeg.get(i);
                                    jsonObjectsLeg.set(i, jsonObjectsLeg.get(j));
                                    jsonObjectsLeg.set(j, temp);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return jsonObjectsLeg;

            case "bills":
                //Toast.makeText(context, "getting bill", Toast.LENGTH_SHORT).show();
                String bills_favorite = prefs.getString("bills_favorite", null);
                JSONArray bills_favorite_jsonarray = null;
                ArrayList<JSONObject> jsonObjectsBil = new ArrayList<JSONObject>();
                if(bills_favorite!=null){
                    try {
                        bills_favorite_jsonarray = new JSONArray(bills_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(bills_favorite_jsonarray!=null && bills_favorite_jsonarray.length()>0){
                    for (int i = 0; i < bills_favorite_jsonarray.length(); i++) {
                        try {
                            jsonObjectsBil.add(bills_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return jsonObjectsBil;

            case "committees":
                //Toast.makeText(context, "getting committee", Toast.LENGTH_SHORT).show();
                String committees_favorite = prefs.getString("committees_favorite", null);
                JSONArray committees_favorite_jsonarray = null;
                ArrayList<JSONObject> jsonObjectsCom = new ArrayList<JSONObject>();
                if(committees_favorite!=null){
                    try {
                        committees_favorite_jsonarray = new JSONArray(committees_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(committees_favorite_jsonarray!=null && committees_favorite_jsonarray.length()>0){
                    for (int i = 0; i < committees_favorite_jsonarray.length(); i++) {
                        try {
                            jsonObjectsCom.add(committees_favorite_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return jsonObjectsCom;

            default:
                Toast.makeText(context, "error happened in getEntity", Toast.LENGTH_SHORT).show();
                return new ArrayList<JSONObject>();
        }
    }

    public JSONArray getFavoriteEntityJSONArray(String entity){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);

        switch (entity) {

            case "legislators":
                //Toast.makeText(context, "getting legislator", Toast.LENGTH_SHORT).show();
                String legislators_favorite = prefs.getString("legislators_favorite", null);
                JSONArray legislators_favorite_jsonarray = new JSONArray();
                if(legislators_favorite!=null){
                    try {
                        legislators_favorite_jsonarray = new JSONArray(legislators_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return  legislators_favorite_jsonarray;

            case "bills":
                //Toast.makeText(context, "getting bill", Toast.LENGTH_SHORT).show();
                String bills_favorite = prefs.getString("bills_favorite", null);
                JSONArray bills_favorite_jsonarray = new JSONArray();
                if(bills_favorite!=null){
                    try {
                        bills_favorite_jsonarray = new JSONArray(bills_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return bills_favorite_jsonarray;

            case "committees":
                //Toast.makeText(context, "getting committee", Toast.LENGTH_SHORT).show();
                String committees_favorite = prefs.getString("committees_favorite", null);
                JSONArray committees_favorite_jsonarray = new JSONArray();
                if(committees_favorite!=null){
                    try {
                        committees_favorite_jsonarray = new JSONArray(committees_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return committees_favorite_jsonarray;

            default:
                Toast.makeText(context, "shit happened in getEntity", Toast.LENGTH_SHORT).show();
                return new JSONArray();
        }
    }
}
