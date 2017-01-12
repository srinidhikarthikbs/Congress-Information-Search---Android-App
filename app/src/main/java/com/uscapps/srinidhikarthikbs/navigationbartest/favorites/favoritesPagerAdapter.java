package com.uscapps.srinidhikarthikbs.navigationbartest.favorites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.uscapps.srinidhikarthikbs.navigationbartest.favorites.fragments.favoritesBillsFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.favorites.fragments.favoritesCommitteesFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.favorites.fragments.favoritesLegislatorsFragment;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class favoritesPagerAdapter extends FragmentStatePagerAdapter {

    Fragment legislators=null, bills=null, committees=null;

    public favoritesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:

                Log.d("favoritesPagerAdapter:","bills case");
                /*if(bills!=null) return bills;
                bills = new favoritesBillsFragment();
                return bills;*/
                return new favoritesBillsFragment();
            case 2:
                Log.d("favoritesPagerAdapter:","committees case");
               /* if(committees!=null) return committees;
                committees = new favoritesCommitteesFragment();
                return committees;*/
                return new favoritesCommitteesFragment();
            default:
                Log.d("favoritesPagerAdapter:","legislators case");
                /*if(legislators!=null) return legislators;
                legislators = new favoritesLegislatorsFragment();
                return legislators;*/
                return new favoritesLegislatorsFragment();
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
                return "Bills";
            case 2:
                return "Committees";
            default:
                return "Legislators";
        }
    }
}
