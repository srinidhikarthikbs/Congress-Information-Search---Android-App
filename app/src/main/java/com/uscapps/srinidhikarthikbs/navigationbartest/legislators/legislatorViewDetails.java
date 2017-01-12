package com.uscapps.srinidhikarthikbs.navigationbartest.legislators;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;
import com.uscapps.srinidhikarthikbs.navigationbartest.favoritesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by srinidhikarthikbs on 11/20/16.
 */

public class legislatorViewDetails extends AppCompatActivity {
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
        setContentView(R.layout.legislator_view_details);
        favMan = favoritesManager.getInstance(getApplicationContext());

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (Exception e){
            e.printStackTrace();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("legislator"));
            //Toast.makeText(getApplicationContext(), "inside view details: "+jsonObject.getString("bioguide_id"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ImageView favorites_star = (ImageView) findViewById(R.id.favorites_star);
        try {
            if(favMan.isFavorited("legislator", jsonObject.getString("bioguide_id")))
                favorites_star.setImageResource(R.mipmap.yellow_filled_star);
            else
                favorites_star.setImageResource(R.mipmap.big_empty_star);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        favorites_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(favMan.isFavorited("legislator", jsonObject.getString("bioguide_id"))){
                        favMan.removeFromFavorites("legislator", jsonObject.getString("bioguide_id"));
                        favorites_star.setImageResource(R.mipmap.big_empty_star);
                    }
                    else {
                        favMan.addToFavorites("legislator", jsonObject.getString("bioguide_id"), jsonObject)   ;
                        favorites_star.setImageResource(R.mipmap.yellow_filled_star);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageView facebook = (ImageView) findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!Objects.equals(jsonObject.getString("facebook_id"), "null")){
                        //taken from http://stackoverflow.com/questions/29351183/open-url-with-an-browser
                        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com/"+jsonObject.getString("facebook_id")));
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Facebook Not available for the Legislator", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        ImageView twitter = (ImageView) findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!Objects.equals(jsonObject.getString("twitter_id"), "null")){
                        //taken from http://stackoverflow.com/questions/29351183/open-url-with-an-browser
                        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.twitter.com/"+jsonObject.getString("twitter_id")));
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Twitter Not available for the Legislator", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        ImageView website = (ImageView) findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!jsonObject.getString("website").equals("null")){
                        //taken from http://stackoverflow.com/questions/29351183/open-url-with-an-browser
                        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(jsonObject.getString("website")));
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(), "Website Not available for the Legislator", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Website Not available for the Legislator", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        ImageView legislatorImage = (ImageView) findViewById(R.id.legislator_image);
        try {
            Picasso.with(getApplicationContext()).load("https://theunitedstates.io/images/congress/225x275/" + String.valueOf(jsonObject.getString("bioguide_id")) + ".jpg").resize(450, 550).into(legislatorImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView legislatorPartyImage = (ImageView) findViewById(R.id.legislator_party_image);
        try {
            if(!Objects.equals(jsonObject.getString("party"), "null")){
                switch (jsonObject.getString("party")){
                    case "R":legislatorPartyImage.setImageResource(R.mipmap.r);break;
                    case "D":legislatorPartyImage.setImageResource(R.mipmap.d);break;
                    case "I":legislatorPartyImage.setImageResource(R.mipmap.i);break;
                    default:
                        Toast.makeText(getApplicationContext(), "error happened while setting Party Image", Toast.LENGTH_SHORT).show();

                }
            }
            else
                Toast.makeText(getApplicationContext(), "invalid party param in legislator", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView legislatorParty = (TextView) findViewById(R.id.legislator_party);
        try {
            if(!Objects.equals(jsonObject.getString("party"), "null")){
                switch (jsonObject.getString("party")){
                    case "R":legislatorParty.setText("Republican");break;
                    case "D":legislatorParty.setText("Democratic");break;
                    case "I":legislatorParty.setText("Independent");break;
                    default:
                        Toast.makeText(getApplicationContext(), "shit happened while setting Party Image", Toast.LENGTH_SHORT).show();

                }
            }
            else
                Toast.makeText(getApplicationContext(), "shit happened while setting party name", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //http://stackoverflow.com/questions/33527598/android-progressbar-style for progress bar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        int progress = 0;
        try {
            long x = getDateDiff(getParsedDate(jsonObject.getString("term_start")), new Date(),TimeUnit.DAYS);
            long y = getDateDiff(getParsedDate(jsonObject.getString("term_start")), getParsedDate(jsonObject.getString("term_end")), TimeUnit.DAYS);
            //Toast.makeText(getApplicationContext(), String.valueOf(x), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), String.valueOf(y), Toast.LENGTH_SHORT).show();
            progress = Math.round((float)((int)x*100/(int)y));
            //Toast.makeText(getApplicationContext(), String.valueOf(progress), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setProgress(progress);
        TextView progressBarText = (TextView) findViewById(R.id.progress_bar_text);
        progressBarText.setText(String.valueOf(progress)+"%");

        LinearLayout first_6_textviews = (LinearLayout) findViewById(R.id.first_6_textviews);
        LinearLayout last_4_textviews = (LinearLayout) findViewById(R.id.last_4_textviews);

        //Sometimes params only dont exist. Then it throws. So, those dummy null params if they dont already exist
        String title= null,
                first_name= null,
                last_name= null,
                email = null,
                chamber = null,
                phone = null,
                term_start = null,
                term_end = null,
                office = null,
                state = null,
                fax = null,
                birthday = null;
        try {
            title = jsonObject.getString("title");
            fax = jsonObject.getString("fax");
            state = jsonObject.getString("state_name");
            office = jsonObject.getString("office");
            term_end = jsonObject.getString("term_end");
            term_start = jsonObject.getString("term_start");
            phone = jsonObject.getString("phone");
            chamber = jsonObject.getString("chamber");
            email = jsonObject.getString("oc_email");
            last_name = jsonObject.getString("last_name");
            first_name = jsonObject.getString("first_name");
            birthday = jsonObject.getString("birthday");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "soemthing went wrong in getting params from legislator", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        LinearLayout legislator_name = (LinearLayout) findViewById(R.id.legislator_name);

        String name = (!Objects.equals(title, "null") ? title + ". " : "") + (!Objects.equals(last_name, "null") ? last_name + ", " : "") + (!Objects.equals(first_name, "null") ? first_name : "");
        TextView tvNameKey = (TextView) legislator_name.findViewById(R.id.key);
        tvNameKey.setText("Name: ");
        TextView tvName = (TextView) legislator_name.findViewById(R.id.value);
        tvName.setText(name);
        //first_6_textviews.addView(legislator_name);

        LinearLayout legislator_email = (LinearLayout) findViewById(R.id.legislator_email);

        String forEmail = email;
        TextView tvEmailKey = (TextView) legislator_email.findViewById(R.id.key);
        tvEmailKey.setText("Email: ");
        TextView tvEmail = (TextView) legislator_email.findViewById(R.id.value);
        if(!Objects.equals(forEmail, "null") & !(forEmail.equals("null"))){
            //Toast.makeText(getApplicationContext(), "forEmail: "+forEmail, Toast.LENGTH_SHORT).show();
            tvEmail.setText(forEmail);
            tvEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        //first_6_textviews.addView(legislator_email);

        LinearLayout legislator_chamber = (LinearLayout) findViewById(R.id.legislator_chamber);

        String forChamber = chamber;
        TextView tvChamberKey = (TextView) legislator_chamber.findViewById(R.id.key);
        tvChamberKey.setText("Chamber: ");
        TextView tvChamber = (TextView) legislator_chamber.findViewById(R.id.value);
        if(!Objects.equals(forChamber, "null"))
            tvChamber.setText(toTitleCase(forChamber));
        //first_6_textviews.addView(legislator_chamber);

        LinearLayout legislator_contact = (LinearLayout) findViewById(R.id.legislator_contact);

        String forContact = phone;
        TextView tvContactKey = (TextView) legislator_contact.findViewById(R.id.key);
        tvContactKey.setText("Contact: ");
        TextView tvContact = (TextView) legislator_contact.findViewById(R.id.value);
        if(!Objects.equals(forContact, "null"))
            tvContact.setText(forContact);
        //first_6_textviews.addView(legislator_contact);

        LinearLayout legislator_start_term = (LinearLayout) findViewById(R.id.legislator_start_term);

        String forStartTerm = getDate(term_start);
        TextView tvstartTermKey = (TextView) legislator_start_term.findViewById(R.id.key);
        tvstartTermKey.setText("Start Term: ");
        TextView tvStartTerm = (TextView) legislator_start_term.findViewById(R.id.value);
        tvStartTerm.setText(forStartTerm);
        //first_6_textviews.addView(legislator_start_term);

        LinearLayout legislator_end_term = (LinearLayout) findViewById(R.id.legislator_end_term);

        String forEndTerm = getDate(term_end);
        TextView tvEndTermKey = (TextView) legislator_end_term.findViewById(R.id.key);
        tvEndTermKey.setText("End Term: ");
        TextView tvEndTerm = (TextView) legislator_end_term.findViewById(R.id.value);
        tvEndTerm.setText(forEndTerm);
        //first_6_textviews.addView(legislator_end_term);

        //office,state,fax,birthday in last_4_textviews
        LinearLayout legislator_office = (LinearLayout) findViewById(R.id.legislator_office);
        TextView tvOfficeKey = (TextView) legislator_office.findViewById(R.id.key);
        tvOfficeKey.setText("Office: ");
        TextView tvOfficeValue = (TextView) legislator_office.findViewById(R.id.value);
        if(!Objects.equals(office, "null"))
            tvOfficeValue.setText(office);
        //last_4_textviews.addView(legislator_office);;

        LinearLayout legislator_state = (LinearLayout) findViewById(R.id.legislator_state);
        TextView tvStateKey = (TextView) legislator_state.findViewById(R.id.key);
        tvStateKey.setText("State: ");
        TextView tvStateValue = (TextView) legislator_state.findViewById(R.id.value);
        if(!Objects.equals(state, "null"))
            tvStateValue.setText(state);
        //last_4_textviews.addView(legislator_state);;

        LinearLayout legislator_fax = (LinearLayout) findViewById(R.id.legislator_fax);
        TextView tvFaxKey = (TextView) legislator_fax.findViewById(R.id.key);
        tvFaxKey.setText("Fax: ");
        TextView tvFaxValue = (TextView) legislator_fax.findViewById(R.id.value);
        if(!Objects.equals(fax, "null"))
            tvFaxValue.setText(fax);
        //last_4_textviews.addView(legislator_fax);

        LinearLayout legislator_birthday = (LinearLayout) findViewById(R.id.legislator_birthday);
        TextView tvbirthdayKey = (TextView) legislator_birthday.findViewById(R.id.key);
        tvbirthdayKey.setText("Birthday: ");
        TextView tvbirthdayValue = (TextView) legislator_birthday.findViewById(R.id.value);
        if(!Objects.equals(birthday, "null"))
            tvbirthdayValue.setText(getDate(birthday));
        //last_4_textviews.addView(legislator_birthday);
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

    //http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public Date getParsedDate(String given_date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        java.util.Date date = null;
        try
        {
            date = form.parse(given_date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return new Date();
        }
        return date;
    }

    //http://stackoverflow.com/questions/6896635/date-format-conversion-android
    public String getDate(String given_date){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        java.util.Date date = null;
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("legislatorViewDetails Page") // TODO: Define a title for the content shown.
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
