package com.jacobgreenland.finalproject.fixture;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jacobgreenland.finalproject.HttpImageRequestTask;
import com.jacobgreenland.finalproject.ImageCacheUtil;
import com.jacobgreenland.finalproject.ItemClickListener;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.MapsActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.team.TeamTabs;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 01/07/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder> {

    private List<Fixture> Fixtures;
    private int rowLayout;
    private Context mContext;
    private boolean landscape;
    TeamTabs mFragment;
    MapsActivity mapFrag;
    RelativeLayout fInfo;
    RelativeLayout fStadium;
    boolean open = false;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;

    CardView cardView;
    int minHeight = 20;

    public FixtureAdapter(List<Fixture> r, int rowLayout, Context context, boolean landscape) {

        this.Fixtures = r;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.landscape = landscape;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        cardView = (CardView) v.findViewById(R.id.fixtureCard);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Fixture fixture = Fixtures.get(i);
        //Log.d("test", ""+ Standings.size());

        viewHolder.HomeTeam.setText(fixture.getHomeTeamName());
        viewHolder.AwayTeam.setText(fixture.getAwayTeamName());
        viewHolder.Vs.setText("V");

        if (!MainActivity.chosenTeamObject.getCrestUrl().isEmpty()) {

            ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(mContext);

            //we somehow get the filename replacing the end point with nothing and the file extension
            String filename = MainActivity.chosenTeamObject.getCrestUrl().replace("https://upload.wikimedia.org/wikipedia/commons/", "");
                    filename.substring(5,filename.length()-1).replace(".svg", ".png");

            //first we try getting the image from local saved images
            if (imageCacheUtil.getImageFromFile(viewHolder.homeTeamBadge, filename)) {
                viewHolder.homeTeamBadge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                //holder.progressBar.setVisibility(View.GONE);
            } else {
                //if we haven't saved the image previously, we try getting it from our cache system.
                //If we fail, we download it from the URL provided
                Bitmap image = imageCacheUtil.getImage(MainActivity.chosenTeamObject.getCrestUrl());

                if (image != null) {
                    viewHolder.homeTeamBadge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    viewHolder.homeTeamBadge.setImageBitmap(image);
                    //holder.progressBar.setVisibility(View.GONE);
                } else {
                    new HttpImageRequestTask(mContext, viewHolder.homeTeamBadge).execute(MainActivity.chosenTeamObject.getCrestUrl());
                }
            }
        } else {
            //sort of placeholder if the item doesn't have a image URL
            viewHolder.homeTeamBadge.setImageResource(R.drawable.norwich);
        }

        viewHolder.fixtureInfo.setVisibility(View.GONE);
        viewHolder.fixtureStadium.setVisibility(View.GONE);

        /*Picasso.with(mContext)
                .load(Result.getArtworkUrl100())
                .into( viewHolder.ResultArtwork);*/
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                if(!open)
                {
                    viewHolder.fixtureInfo.setVisibility(View.VISIBLE);
                    viewHolder.fixtureStadium.setVisibility(View.VISIBLE);

                    loadLogo(viewHolder.homeTeamBadge, MainActivity.chosenTeamObject.getCrestUrl());
                    loadLogo(viewHolder.awayTeamBadge, MainActivity.chosenTeamObject.getCrestUrl());


                    open = true;
                }
                else
                {
                    viewHolder.fixtureInfo.setVisibility(View.GONE);
                    viewHolder.fixtureStadium.setVisibility(View.GONE);

                    open = false;
                }
            }
        });

        viewHolder.stadium.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fragmentJump(v);
            }
        });
    }

    public void loadLogo(ImageView badge, String url)
    {
        if (!MainActivity.chosenTeamObject.getCrestUrl().isEmpty()) {

            ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(mContext);

            //we somehow get the filename replacing the end point with nothing and the file extension
            String filename = MainActivity.chosenTeamObject.getCrestUrl().replace("https://upload.wikimedia.org/wikipedia/commons/", "");
            filename.substring(5,filename.length()-1).replace(".svg", ".png");

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
                    new HttpImageRequestTask(mContext, badge).execute(MainActivity.chosenTeamObject.getCrestUrl());
                }
            }
        } else {
            //sort of placeholder if the item doesn't have a image URL
            badge.setImageResource(R.drawable.norwich);
        }
    }

    private void fragmentJump(View view) {
        //call switch content to proceed with changing fragment

            mapFrag = new MapsActivity();
            switchContent(R.id.mainFragment, mapFrag, view);
    }

    public void switchContent(int id, MapsActivity mapFrag, View view) {
        if (mContext == null) {
            Log.d("test", "this isn't good");
            return;
        }
        // jump to main activity to switch fragment
        Log.d("test", "this is better");
        MainActivity mainActivity = (MainActivity) mContext;
        MapsActivity frag = mapFrag;
        mainActivity.switchContent(id, frag, view);
        //}
    }

    private void hidePDialog()
    {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public int getItemCount() {
        return Fixtures == null ? 0 : Fixtures.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //@BindView(R.id.teamName)

        CardView cardView;

        TextView HomeTeam;
        TextView AwayTeam;
        TextView Vs;

        RelativeLayout fixtureInfo;
        RelativeLayout fixtureStadium;

        ImageView homeTeamBadge;
        ImageView awayTeamBadge;
        TextView time;
        TextView date;
        TextView stadium;

        //@BindView(R.id.songArtwork) ImageView ResultArtwork;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            HomeTeam = (TextView) itemView.findViewById(R.id.fHomeTeam);
            AwayTeam = (TextView) itemView.findViewById(R.id.fAwayTeam);
            Vs = (TextView) itemView.findViewById(R.id.vs);

            fixtureInfo = (RelativeLayout) itemView.findViewById(R.id.fixtureInfo);
            fixtureStadium = (RelativeLayout) itemView.findViewById(R.id.fixtureStadium);

            homeTeamBadge = (ImageView) itemView.findViewById(R.id.fHomeBadge);
            awayTeamBadge = (ImageView) itemView.findViewById(R.id.fAwayBadge);

            time = (TextView) itemView.findViewById(R.id.fTime);
            date = (TextView) itemView.findViewById(R.id.fDate);
            stadium = (TextView) itemView.findViewById(R.id.fStadium);
            //ButterKnife.bind(this, itemView);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition());
            return true;
        }
    }
}

