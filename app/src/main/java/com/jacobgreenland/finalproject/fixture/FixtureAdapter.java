package com.jacobgreenland.finalproject.fixture;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jacobgreenland.finalproject.ItemClickListener;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.team.TeamTabs;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 01/07/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder>{

    private List<Fixture> Fixtures;
    private int rowLayout;
    private Context mContext;
    private boolean landscape;
    TeamTabs mFragment;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;


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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Fixture fixture = Fixtures.get(i);

        //Log.d("test", ""+ Standings.size());

        viewHolder.HomeTeam.setText(fixture.getHomeTeamName());
        viewHolder.AwayTeam.setText(fixture.getAwayTeamName());
        viewHolder.Vs.setText("V");

        /*Picasso.with(mContext)
                .load(Result.getArtworkUrl100())
                .into( viewHolder.ResultArtwork);*/
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //MainActivity.chosenSong = Result;
                /*String team = standing.getLinks().getTeamLink().getHref();
                MainActivity.chosenTeam = team.substring(32,team.length());
                MainActivity.chosenTeamPosition = standing.getPosition();
                Log.d("test", MainActivity.chosenTeam);
                fragmentJump(view);*/
                //((MyApp) mContext.getApplicationContext()).getApiComponent().inject((MainActivity) mContext);
                Toast.makeText(mContext, "#" + position + " - " + fixture.getDate(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fragmentJump(View view) {
        //call switch content to proceed with changing fragment
        mFragment = new TeamTabs();
        switchContent(R.id.mainFragment, mFragment, view);
    }

    public void switchContent(int id, TeamTabs fragment, View view) {
        if (mContext == null) {
            Log.d("test", "this isn't good");
            return;
        }
        // jump to main activity to switch fragment
        Log.d("test", "this is better");
        MainActivity mainActivity = (MainActivity) mContext;
        TeamTabs frag = fragment;
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
        TextView HomeTeam;
        TextView AwayTeam;
        TextView Vs;

        //@BindView(R.id.songArtwork) ImageView ResultArtwork;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            HomeTeam = (TextView) itemView.findViewById(R.id.fHomeTeam);
            AwayTeam = (TextView) itemView.findViewById(R.id.fAwayTeam);
            Vs = (TextView) itemView.findViewById(R.id.vs);

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

