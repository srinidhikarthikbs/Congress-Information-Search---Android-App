package com.uscapps.srinidhikarthikbs.navigationbartest.bills;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.favoritesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Srinidhi Karthik B S on 11/22/2016.
 */

public class billsViewDetails extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    JSONObject jsonObject = null;
    favoritesManager favMan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_view_details);
        favMan = favoritesManager.getInstance(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("bill"));
            //Toast.makeText(getApplicationContext(), "inside view details: "+jsonObject.getString("bill_id"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bill_view_details);
        final ImageView favorites_star = (ImageView) findViewById(R.id.favorites_star);
        try {
            if(favMan.isFavorited("bill", jsonObject.getString("bill_id"))){
                favorites_star.setImageResource(R.mipmap.yellow_filled_star);
            }
            else {
                favorites_star.setImageResource(R.mipmap.big_empty_star);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        favorites_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(favMan.isFavorited("bill", jsonObject.getString("bill_id"))){
                        favMan.removeFromFavorites("bill", jsonObject.getString("bill_id"));
                        favorites_star.setImageResource(R.mipmap.big_empty_star);
                    }
                    else {
                        favMan.addToFavorites("bill", jsonObject.getString("bill_id"), jsonObject)   ;
                        favorites_star.setImageResource(R.mipmap.yellow_filled_star);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        LinearLayout bill_id_ll = (LinearLayout) findViewById(R.id.bill_id);
        TextView bill_id_key = (TextView) bill_id_ll.findViewById(R.id.key);
        bill_id_key.setText("Bill ID: ");
        TextView bill_id_value = (TextView) bill_id_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("bill_id"), "null"))
                bill_id_value.setText(jsonObject.getString("bill_id").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_title_ll = (LinearLayout) findViewById(R.id.bill_title);
        TextView bill_title_key = (TextView) bill_title_ll.findViewById(R.id.key);
        bill_title_key.setText("Bill title: ");
        TextView bill_title_value = (TextView) bill_title_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("official_title"), "null"))
                bill_title_value.setText(jsonObject.getString("official_title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_type_ll = (LinearLayout) findViewById(R.id.bill_type);
        TextView bill_type_key = (TextView) bill_type_ll.findViewById(R.id.key);
        bill_type_key.setText("Bill Type: ");
        TextView bill_type_value = (TextView) bill_type_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("bill_type"), "null"))
                bill_type_value.setText(jsonObject.getString("bill_type").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_sponsor_ll = (LinearLayout) findViewById(R.id.bill_sponsor);
        TextView bill_sponsor_key = (TextView) bill_sponsor_ll.findViewById(R.id.key);
        bill_sponsor_key.setText("Sponsor: ");
        TextView bill_sponsor_value = (TextView) bill_sponsor_ll.findViewById(R.id.value);
        try {
            if(jsonObject.getJSONObject("sponsor")!=null)
                bill_sponsor_value.setText(jsonObject.getJSONObject("sponsor").getString("title")+". "+jsonObject.getJSONObject("sponsor").getString("last_name")+", "+jsonObject.getJSONObject("sponsor").getString("first_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_chamber_ll = (LinearLayout) findViewById(R.id.bill_chamber);
        TextView bill_chamber_key = (TextView) bill_chamber_ll.findViewById(R.id.key);
        bill_chamber_key.setText("Chamber: ");
        TextView bill_chamber_value = (TextView) bill_chamber_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("chamber"), "null"))
                bill_chamber_value.setText(toTitleCase(jsonObject.getString("chamber")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_status_ll = (LinearLayout) findViewById(R.id.bill_status);
        TextView bill_status_key = (TextView) bill_status_ll.findViewById(R.id.key);
        bill_status_key.setText("Status: ");
        TextView bill_status_value = (TextView) bill_status_ll.findViewById(R.id.value);
        try {
            if(jsonObject.getJSONObject("history")!=null)
                bill_status_value.setText(jsonObject.getJSONObject("history").getBoolean("active") ? "Active" : "New");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_introduced_on_ll = (LinearLayout) findViewById(R.id.bill_introduced_on);
        TextView bill_introduced_on_key = (TextView) bill_introduced_on_ll.findViewById(R.id.key);
        bill_introduced_on_key.setText("Introduced On: ");
        TextView bill_introduced_on_value = (TextView) bill_introduced_on_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("introduced_on"), "null"))
                bill_introduced_on_value.setText(getDate(jsonObject.getString("introduced_on")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_congress_url_ll = (LinearLayout) findViewById(R.id.bill_congress_url);
        TextView bill_congress_url_key = (TextView) bill_congress_url_ll.findViewById(R.id.key);
        bill_congress_url_key.setText("Congress URL: ");
        TextView bill_congress_url_value = (TextView) bill_congress_url_ll.findViewById(R.id.value);
        try {
            if(jsonObject.getJSONObject("urls")!=null && !Objects.equals(jsonObject.getJSONObject("urls").getString("congress"), "null"))
                bill_congress_url_value.setText(jsonObject.getJSONObject("urls").getString("congress"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_version_status_ll = (LinearLayout) findViewById(R.id.bill_version_status);
        TextView bill_version_status_key = (TextView) bill_version_status_ll.findViewById(R.id.key);
        bill_version_status_key.setText("Version Status: ");
        TextView bill_version_status_value = (TextView) bill_version_status_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getJSONObject("last_version").getString("version_name"), "null"))
                bill_version_status_value.setText(jsonObject.getJSONObject("last_version").getString("version_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bill_url_ll = (LinearLayout) findViewById(R.id.bill_url);
        TextView bill_url_key = (TextView) bill_url_ll.findViewById(R.id.key);
        bill_url_key.setText("Bill URL: ");
        TextView bill_url_value = (TextView) bill_url_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"), "null"))
                bill_url_value.setText(jsonObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //http://stackoverflow.com/questions/6896635/date-format-conversion-android
    public String getDate(String given_date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        java.util.Date date;
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

    //http://stackoverflow.com/questions/1086123/string-conversion-to-title-case
    @NonNull
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("billsViewDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
