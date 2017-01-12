package com.uscapps.srinidhikarthikbs.navigationbartest.committees;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.uscapps.srinidhikarthikbs.navigationbartest.committees.fragments.committeeHouseFragment;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class committeesPagerAdapter extends FragmentStatePagerAdapter {

    public Fragment house_committee=null, senate_committee=null, joint_committee=null;
    public committeesFragment.getDataFromParent houseCommitteeInterface=null, senateCommitteeInterface=null, jointCommitteeInterface=null;

    public committeesPagerAdapter(FragmentManager fm) {
        super(fm);
        house_committee = committeeHouseFragment.getInstance();
        senate_committee = committeeHouseFragment.getInstance();
        joint_committee = committeeHouseFragment.getInstance();
        houseCommitteeInterface = (committeesFragment.getDataFromParent) house_committee;
        senateCommitteeInterface = (committeesFragment.getDataFromParent) senate_committee;
        jointCommitteeInterface = (committeesFragment.getDataFromParent) joint_committee;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                if(senate_committee!=null) return senate_committee;
                senate_committee = committeeHouseFragment.getInstance();
                return senate_committee;
            case 2:
                if(joint_committee!=null) return joint_committee;
                joint_committee = committeeHouseFragment.getInstance();
                return joint_committee;
            default:
                if(house_committee!=null) return house_committee;
                house_committee = committeeHouseFragment.getInstance();
                return house_committee;
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
                return "Senate";
            case 2:
                return "Joint";
            default:
                return "House";
        }
    }
}
