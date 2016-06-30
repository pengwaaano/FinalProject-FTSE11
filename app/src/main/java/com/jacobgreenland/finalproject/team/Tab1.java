package com.jacobgreenland.finalproject.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment {

    Communicator comm;
    @BindView(R.id.tab1List)
    RecyclerView rv;
    View v;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tab_1,container,false);
        ButterKnife.bind(this,v);
        comm = (Communicator) getActivity();
        //rv = (RecyclerView) v.findViewById(R.id.tab1List);

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        Log.d("test", "Adapter should have been set by now!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
}
