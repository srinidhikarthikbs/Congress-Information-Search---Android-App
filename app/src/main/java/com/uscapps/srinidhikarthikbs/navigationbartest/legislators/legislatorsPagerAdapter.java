package com.uscapps.srinidhikarthikbs.navigationbartest.legislators;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.fragments.legislatorByStateFragment;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class legislatorsPagerAdapter extends FragmentStatePagerAdapter {
    //public Fragment byState=null, house=null, senate=null;
    public Fragment byState=null, house=null, senate=null;
    public legislatorsFragment.getDataFromParent byStateInterface = null,
            houseInterface = null,
            senateInterface = null;

    public legislatorsPagerAdapter(FragmentManager fm) {
        super(fm);
        byState = legislatorByStateFragment.getInstance();
        house = legislatorByStateFragment.getInstance();
        senate = legislatorByStateFragment.getInstance();
        byStateInterface = (legislatorsFragment.getDataFromParent) byState;
        houseInterface = (legislatorsFragment.getDataFromParent) house;
        senateInterface = (legislatorsFragment.getDataFromParent) senate;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                Log.d("legislatorsPagerAdapter:","house case");
                if(house!=null) return house;
                house = legislatorByStateFragment.getInstance();
                return house;
                //return legislatorHouseFragment.getInstance();
            case 2:
                Log.d("legislatorsPagerAdapter:","senate case");
                if(senate!=null) return senate;
                senate = legislatorByStateFragment.getInstance();
                return senate;
                //return legislatorSenateFragment.getInstance();
            default:
                Log.d("legislatorsPagerAdapter:","default case");
                if(byState!=null) return byState;
                byState = legislatorByStateFragment.getInstance();
                return byState;
                //return legislatorByStateFragment.getinstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1:
                return "House";
            case 2:
                return "Senate";
            default:
                return "By State";
        }
    }
}
