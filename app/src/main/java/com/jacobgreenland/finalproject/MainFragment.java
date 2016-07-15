package com.jacobgreenland.finalproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jacob on 29/06/16.
 */
public class MainFragment extends Fragment {
    View v;
    Communicator comm;
    Button donateButton;

    ImageView favouriteTeamBadge;
    TextView favouriteTeamName;
    LinearLayout favouriteTeamFixtureLayout;
    TextView favouriteFixtureHome;
    TextView favouriteFixtureAway;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v =inflater.inflate(R.layout.mainfragment,container,false);
        setRetainInstance(true);
        comm = (Communicator) getActivity();

        favouriteTeamBadge = (ImageView) v.findViewById(R.id.favouriteTeamBadge);
        favouriteTeamName = (TextView) v.findViewById(R.id.favouriteTeamName);
        favouriteTeamFixtureLayout = (LinearLayout) v.findViewById(R.id.fUpcomingFixturesLayout);
        favouriteFixtureHome = (TextView) v.findViewById(R.id.favHomeTeam);
        favouriteFixtureAway = (TextView) v.findViewById(R.id.favAwayTeam);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //header.attachTo(rv);

        comm.initialiseNavigationDrawer();
        comm.initialiseTwitter();
        comm.initialiseFacebook();

        if(MainActivity.favouriteTeam.getFavourite() != null) {
            favouriteTeamBadge.setVisibility(View.VISIBLE);
            favouriteTeamFixtureLayout.setVisibility(View.VISIBLE);
            setPhoto(favouriteTeamBadge);
            favouriteTeamName.setText(MainActivity.favouriteTeam.getFavourite().getName());

            if(MainActivity.favouriteTeam.getFixtures().size() > 0)
            {
                favouriteFixtureHome.setText(MainActivity.favouriteTeam.getFixtures().get(0).getHomeTeamName());
                favouriteFixtureAway.setText(MainActivity.favouriteTeam.getFixtures().get(0).getAwayTeamName());
            }
            else
            {
                favouriteTeamFixtureLayout.setVisibility(View.GONE);
            }
        }
        else
        {
            favouriteTeamBadge.setVisibility(View.GONE);
            favouriteTeamFixtureLayout.setVisibility(View.GONE);
            favouriteTeamName.setText("No Favourite Team Chosen.");
        }
        //fPresenter = new LeaguePresenter(this, MainActivity.leagueRepository);
        //fPresenter.loadLeagues(MainActivity._api, false, Constants.CURRENT_SEASON);
    }

    public void setPhoto(ImageView badge)
    {
        if (!MainActivity.favouriteTeam.getFavourite().getCrestUrl().isEmpty())
        {
            Log.d("testtesttest", MainActivity.favouriteTeam.getFavourite().getCrestUrl());

            ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(v.getContext());

            String filename = "";

            //we somehow get the filename replacing the end point with nothing and the file extension
            try
            {
                filename = URLEncoder.encode(MainActivity.favouriteTeam.getFavourite().getCrestUrl(),"UTF-8").replace(".svg", ".png");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            //first we try getting the image from local saved images
            if (imageCacheUtil.getImageFromFile(badge, filename))
            {
                badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                //holder.progressBar.setVisibility(View.GONE);
            } else
            {
                //if we haven't saved the image previously, we try getting it from our cache system.
                //If we fail, we download it from the URL provided
                Bitmap image = imageCacheUtil.getImage(MainActivity.favouriteTeam.getFavourite().getCrestUrl());

                if (image != null) {
                    badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    badge.setImageBitmap(image);
                    //holder.progressBar.setVisibility(View.GONE);
                } else {
                    new HttpImageRequestTask(v.getContext(), badge).execute(MainActivity.favouriteTeam.getFavourite().getCrestUrl());
                }
            }
        }
        else {
            //sort of placeholder if the item doesn't have a image URL
            badge.setImageResource(R.drawable.pannamatch);
        }
    }
}
