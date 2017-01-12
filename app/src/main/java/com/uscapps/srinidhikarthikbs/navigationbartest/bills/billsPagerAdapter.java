package com.uscapps.srinidhikarthikbs.navigationbartest.bills;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.uscapps.srinidhikarthikbs.navigationbartest.bills.fragments.billsActiveFragment;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class billsPagerAdapter extends FragmentStatePagerAdapter {
    public Fragment active_bills=null, new_bills=null;
    public billsFragment.getDataFromParent activeBillsInterface  = null, newBillsInterface = null;

    public billsPagerAdapter(FragmentManager fm) {
        super(fm);
        active_bills = billsActiveFragment.getInstance();
        new_bills = billsActiveFragment.getInstance();
        activeBillsInterface = (billsFragment.getDataFromParent) active_bills;
        newBillsInterface = (billsFragment.getDataFromParent) new_bills;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                if(new_bills!=null) return new_bills;
                new_bills = billsActiveFragment.getInstance();
                return new_bills;
            default:
                if(active_bills!=null) return active_bills;
                active_bills = billsActiveFragment.getInstance();
                return active_bills;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1:
                return "new bills";
            default:
                return "active bills";
        }
    }
}
