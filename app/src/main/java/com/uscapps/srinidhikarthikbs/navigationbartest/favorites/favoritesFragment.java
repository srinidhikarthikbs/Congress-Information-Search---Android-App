package com.uscapps.srinidhikarthikbs.navigationbartest.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uscapps.srinidhikarthikbs.navigationbartest.MainActivity;
import com.uscapps.srinidhikarthikbs.navigationbartest.R;

/**
 * Created by srinidhikarthikbs on 11/18/16.
 */

public class favoritesFragment extends Fragment {

    public static favoritesFragment myInstance = null;
    ViewPager pager;
    TabLayout tabLayout;

    public static favoritesFragment getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new favoritesFragment();
        return myInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //((MainActivity)getActivity()).changeActionBarTitle("Favorites");
        pager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager manager=getChildFragmentManager();

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
       favoritesPagerAdapter adapter=new favoritesPagerAdapter(manager);

        //set Adapter to view pager
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);

    }
}
