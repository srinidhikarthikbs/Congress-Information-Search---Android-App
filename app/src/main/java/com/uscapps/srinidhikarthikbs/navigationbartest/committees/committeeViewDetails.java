package com.uscapps.srinidhikarthikbs.navigationbartest.committees;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import org.w3c.dom.Text;

import java.util.Objects;

/**
 * Created by Srinidhi Karthik B S on 11/22/2016.
 */

public class committeeViewDetails extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    JSONObject jsonObject = null;
    favoritesManager favMan = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.committee_view_details);
        favMan = favoritesManager.getInstance(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("committee"));
            String committee_id = jsonObject.getString("committee_id");
            //Toast.makeText(getApplicationContext(), "inside view details: "+committee_id, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.committee_view_details);

        final ImageView favorites_star = (ImageView) findViewById(R.id.favorites_star);
        try {
            if(favMan.isFavorited("committee", jsonObject.getString("committee_id"))){
                favorites_star.setImageResource(R.mipmap.yellow_filled_star);
            }
            else {
                favorites_star.setImageResource(R.mipmap.big_empty_star);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "something went wrong with setting favorite star", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        favorites_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(favMan.isFavorited("committee", jsonObject.getString("committee_id"))){
                        favMan.removeFromFavorites("committee", jsonObject.getString("committee_id"));
                        favorites_star.setImageResource(R.mipmap.big_empty_star);
                    }
                    else {
                        favMan.addToFavorites("committee", jsonObject.getString("committee_id"), jsonObject)   ;
                        favorites_star.setImageResource(R.mipmap.yellow_filled_star);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "something went wrong with setting favorite star", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        LinearLayout committee_id_ll = (LinearLayout) findViewById(R.id.committee_id);
        TextView committee_id_key = (TextView) committee_id_ll.findViewById(R.id.key);
        committee_id_key.setText("Committee ID:");
        TextView committee_id_value = (TextView) committee_id_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("committee_id"), "null"))
                committee_id_value.setText(jsonObject.getString("committee_id").toUpperCase());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "error setting committee_id", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        LinearLayout committee_name_ll = (LinearLayout) findViewById(R.id.committee_name);
        TextView committee_name_key = (TextView) committee_name_ll.findViewById(R.id.key);
        committee_name_key.setText("Name: ");
        TextView committee_name_value = (TextView) committee_name_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("name"), "null"))
                committee_name_value.setText(jsonObject.getString("name"));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "error setting committee_name", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        TextView committee_chamber_value = (TextView) findViewById(R.id.committee_chamber_value);
        ImageView committee_chamber_image = (ImageView) findViewById(R.id.committee_chamber_image);
        try {
            if(!Objects.equals(jsonObject.getString("chamber"), "null")){
                committee_chamber_value.setText(toTitleCase(jsonObject.getString("chamber")));
                switch (jsonObject.getString("chamber")){
                    case "house":committee_chamber_image.setImageResource(R.mipmap.house);break;
                    case "senate":committee_chamber_image.setImageResource(R.mipmap.senate);break;
                    default:
                        //Toast.makeText(getApplicationContext(), "error while setting committee_chamber_image", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout committee_parent_committee_ll = (LinearLayout) findViewById(R.id.committee_parent_committee);
        TextView committee_parent_committee_key = (TextView) committee_parent_committee_ll.findViewById(R.id.key);
        committee_parent_committee_key.setText("Parent Committee: ");
        TextView committee_parent_committee_value = (TextView) committee_parent_committee_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("parent_committee_id"), "null"))
                committee_parent_committee_value.setText(jsonObject.getString("parent_committee_id").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout committee_contact_ll = (LinearLayout) findViewById(R.id.committee_contact);
        TextView committee_contact_key = (TextView) committee_contact_ll.findViewById(R.id.key);
        committee_contact_key.setText("Contact: ");
        TextView committee_contact_value = (TextView) committee_contact_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("phone"), "null"))
            committee_contact_value.setText(jsonObject.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout committee_office_ll = (LinearLayout) findViewById(R.id.committee_office);
        TextView committee_office_key = (TextView) committee_office_ll.findViewById(R.id.key);
        committee_office_key.setText("Office: ");
        TextView committee_office_value = (TextView) committee_office_ll.findViewById(R.id.value);
        try {
            if(!Objects.equals(jsonObject.getString("office"), "null"))
                committee_office_value.setText(jsonObject.getString("office"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //http://stackoverflow.com/questions/1086123/string-conversion-to-title-case
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
                .setName("committeeViewDetails Page") // TODO: Define a title for the content shown.
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
