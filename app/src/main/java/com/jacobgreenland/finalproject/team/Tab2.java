package com.jacobgreenland.finalproject.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab2 extends Fragment {

    Communicator comm;
    @BindView(R.id.tab2List)
    RecyclerView rv;
    View v;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_2,container,false);
        unbinder = ButterKnife.bind(this,v);
        comm = (Communicator) getActivity();
        //rv = (RecyclerView) v.findViewById(R.id.tab2List);
        //comm.setRView(rv, 2);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        //comm.afterViewsCreated();
        //comm = (Communicator) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}