package com.jacobgreenland.finalproject.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jacob on 11/06/16.
 */
public class TeamTabs extends Fragment
{
    @BindView(R.id.pager)
    ViewPager pager;
    ViewPagerAdapter adapter;
    @BindView(R.id.tabs) SlidingTabLayout tabs;
    CharSequence Titles[]={"Info","Players","Fixtures"};
    int Numboftabs =3;
    Communicator comm;
    View v;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tab_changer,container,false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //getActivity().getFragmentManager();
        comm = (Communicator) getActivity();

        //set adapters and pagers for the Tab Screen
        adapter =  new ViewPagerAdapter(getChildFragmentManager(),Titles,Numboftabs);
        //pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        //tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


        Log.d("test", "Adapter should have been set by now!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
