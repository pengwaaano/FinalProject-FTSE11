package com.jacobgreenland.finalproject.league;

/**
 * Created by Jacob on 09/06/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacobgreenland.finalproject.ItemClickListener;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.league.model.Standing;
import com.jacobgreenland.finalproject.team.TeamTabs;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;

import rx.subscriptions.CompositeSubscription;


public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder>{

    private List<Standing> Standings;
    private int rowLayout;
    private Context mContext;
    private boolean landscape;
    LeagueRepository leagueR;
    TeamTabs mFragment;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;


    public LeagueAdapter(List<Standing> r, int rowLayout, Context context, boolean landscape, LeagueRepository leagueRepo) {

        this.Standings = r;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.landscape = landscape;
        this.leagueR = leagueRepo;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Standing standing = Standings.get(i);

        //Log.d("test", ""+ Standings.size());

        viewHolder.TeamPosition.setText(standing.getPosition().toString() + ".");
        viewHolder.TeamGamesPlayed.setText(standing.getPlayedGames().toString());
        viewHolder.TeamPoints.setText(standing.getPoints().toString());
        if(landscape) {
            viewHolder.TeamGoalDifference.setText(standing.getGoalDifference().toString());
            viewHolder.TeamGoalsFor.setText(standing.getGoals().toString());
            viewHolder.TeamGoalsAgainst.setText(standing.getGoalsAgainst().toString());
            viewHolder.TeamWins.setText(standing.getWins().toString());
            viewHolder.TeamDraws.setText(standing.getDraws().toString());
            viewHolder.TeamLosses.setText(standing.getLosses().toString());
        }

        viewHolder.TeamName.setText(standing.getTeamName());
        /*Picasso.with(mContext)
                .load(Result.getArtworkUrl100())
                .into( viewHolder.ResultArtwork);*/
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //MainActivity.chosenSong = Result;
                String team = standing.getLinks().getTeamLink().getHref();
                MainActivity.chosenTeam = team.substring(32,team.length());

                //Team chosen = new Team();
                Log.d("test", MainActivity.loadedLeagueTeams.size() + "");
                for(Team t : MainActivity.loadedLeagueTeams)
                {
                    if(t.getName() != null) {
                        //Log.d("test", t.getName());
                        Log.d("test", t.getName() + " " + standing.getTeamName());
                        if (t.getName().equals(standing.getTeamName())) {
                            MainActivity.chosenTeamObject = t;
                            MainActivity.chosenTeamPosition = standing.getPosition();
                            Log.d("test", MainActivity.chosenTeam);
                            fragmentJump(view);
                        }
                    }
                }
                //MainActivity.chosenTeamObject = MainActivity.loadedLeagueTeams.get(i);
                    //((MyApp) mContext.getApplicationContext()).getApiComponent().inject((MainActivity) mContext);
                    //Toast.makeText(mContext, "#" + position + " - " + Result.getTrackName(), Toast.LENGTH_SHORT).show();
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
        mainActivity.switchContent(id, frag);
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
        return Standings == null ? 0 : Standings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        //@BindView(R.id.teamName)
        TextView TeamName;
        TextView TeamPosition;
        TextView TeamGamesPlayed;
        TextView TeamPoints;
        TextView TeamWins;
        TextView TeamDraws;
        TextView TeamLosses;
        TextView TeamGoalDifference;
        TextView TeamGoalsFor;
        TextView TeamGoalsAgainst;

        //@BindView(R.id.songArtwork) ImageView ResultArtwork;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            TeamName = (TextView) itemView.findViewById(R.id.teamName);
            TeamPosition = (TextView) itemView.findViewById(R.id.teamPosition);
            TeamGamesPlayed = (TextView) itemView.findViewById(R.id.teamGamesPlayed);
            TeamPoints = (TextView) itemView.findViewById(R.id.teamPoints);
            TeamWins = (TextView) itemView.findViewById(R.id.teamWins);
            TeamDraws = (TextView) itemView.findViewById(R.id.teamDraws);
            TeamLosses = (TextView) itemView.findViewById(R.id.teamLosses);
            TeamGoalDifference = (TextView) itemView.findViewById(R.id.teamGoalDifference);
            TeamGoalsFor = (TextView) itemView.findViewById(R.id.teamGoalsFor);
            TeamGoalsAgainst = (TextView) itemView.findViewById(R.id.teamGoalsAgainst);

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
