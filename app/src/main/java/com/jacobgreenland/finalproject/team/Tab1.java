package com.jacobgreenland.finalproject.team;

import android.graphics.Bitmap;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.caverock.androidsvg.SVG;
import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.HttpImageRequestTask;
import com.jacobgreenland.finalproject.ImageCacheUtil;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.player.model.Player;
import com.jacobgreenland.finalproject.services.Services;
import com.jacobgreenland.finalproject.team.model.Team;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment implements TeamContract.View{

    Communicator comm;
    @BindView(R.id.clubBadge)
    ImageView badge;
    @BindView(R.id.tTeamName) TextView name;
    @BindView(R.id.tPosition) TextView position;
    @BindView(R.id.tLeague) TextView league;
    @BindView(R.id.favouriteButton) ImageView favourite;

    View v;
    private Unbinder unbinder;

    GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    boolean favouriteBool = false;

    TeamAPI _teamapi;

    TeamTabs ttabs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tab_1,container,false);
        ButterKnife.bind(this,v);

        //rv = (RecyclerView) v.findViewById(R.id.tab1List);
        Log.d("test", "hello this is tab 1");

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        _teamapi = Services._createTeamAPI();

        //Log.d("test", MainActivity.chosenTeamObject.getCrestUrl());

        /*Picasso.with(v.getContext())
                .load(Uri.parse(MainActivity.chosenTeamObject.getCrestUrl()))
                .into(badge);*/
        if (!MainActivity.chosenTeamObject.getCrestUrl().isEmpty()) {
            Log.d("testtesttest", MainActivity.chosenTeamObject.getCrestUrl());

            ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(v.getContext());

            //we somehow get the filename replacing the end point with nothing and the file extension
            String filename = MainActivity.chosenTeamObject.getCrestUrl().replace("https://upload.wikimedia.org/wikipedia/commons/", "")
            .substring(5,MainActivity.chosenTeamObject.getCrestUrl().length()-1)
            .replace(".svg", ".png");

            //first we try getting the image from local saved images
            if (imageCacheUtil.getImageFromFile(badge, filename)) {
                badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                //holder.progressBar.setVisibility(View.GONE);
            } else {
                //if we haven't saved the image previously, we try getting it from our cache system.
                //If we fail, we download it from the URL provided
                Bitmap image = imageCacheUtil.getImage(MainActivity.chosenTeamObject.getCrestUrl());

                if (image != null) {
                    badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    badge.setImageBitmap(image);
                    //holder.progressBar.setVisibility(View.GONE);
                } else {
                    new HttpImageRequestTask(v.getContext(), badge).execute(MainActivity.chosenTeamObject.getCrestUrl());
                }
            }
        } else {
            //sort of placeholder if the item doesn't have a image URL
            badge.setImageResource(R.drawable.pannamatch);
        }

        //

        favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favouriteTeam();
            }
        });

        name.setText(MainActivity.chosenTeamObject.getName());

        position.setText("" + MainActivity.chosenTeamPosition + getDayOfMonthSuffix(MainActivity.chosenTeamPosition));
        league.setText(MainActivity.chosenLeagueObject.getLeagueCaption());

        Log.d("test", "Adapter should have been set by now!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
    @Override
    public void setPresenter(TeamContract.Presenter presenter) {

    }

    @Override
    public void setTeam(Team team, boolean fromAPI) {

    }

    @Override
    public void setFixtureAdapter(List<Fixture> fixtures) {

    }

    @Override
    public void setPlayerAdapter(List<Player> players) {

    }

    @Override
    public void showDialog() {

    }
    String getDayOfMonthSuffix(final int n)
    {
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
    public void favouriteTeam()
    {
        if(!favouriteBool) {
            favourite.setImageResource(R.drawable.favouritechecked);
            favouriteBool = true;
        }
        else {
            favourite.setImageResource(R.drawable.favouriteunchecked);
            favouriteBool = false;
        }
    }
}
class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
