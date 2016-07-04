package com.jacobgreenland.finalproject.league;

import com.jacobgreenland.finalproject.team.TeamAPI;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;


/**
 * Created by Jacob on 16/06/16.
 */
public class LeaguePresenter implements LeagueContract.Presenter {

    private final LeagueRepository leagueRepository;

    private final LeagueContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    List<Team> teams;
    int i = 0;

    @Inject
    public LeaguePresenter(LeagueContract.View view, LeagueRepository leagueRepo) {
        mView = view;
        leagueRepository = leagueRepo;

        mView.setPresenter(this);
    }

    @Override
    public void loadLeagueTable(LeagueAPI _api, final boolean initialLoad, String id)
    {
        leagueRepository.getRemoteSource().getLeagueTable(_api, mView, leagueRepository, id);
    }

    @Override
    public void loadTeamsOfLeague(TeamAPI _api, final boolean initialLoad, List<String> id)
    {
        leagueRepository.getRemoteSource().getTeamsOfLeague(_api, initialLoad, mView, leagueRepository, id);
    }

    @Override
    public void loadLocalLeagueTable(String leagueTable) {

        leagueRepository.getLocalSource().getDataFromLocal(leagueTable, mView);
    }
    @Override
    public void loadLocalLeagueTeams() {

        leagueRepository.getLocalSource().getTeamDataFromLocal(mView);
    }

    public LeagueRepository getRepo()
    {
        return leagueRepository;
    }

    @Inject
    @Override
    public void start() { }
}
