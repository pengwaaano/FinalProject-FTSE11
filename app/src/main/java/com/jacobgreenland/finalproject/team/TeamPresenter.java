package com.jacobgreenland.finalproject.team;

import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.LeagueContract;
import com.jacobgreenland.finalproject.league.LeagueRepository;
import com.jacobgreenland.finalproject.league.model.League;

import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;


/**
 * Created by Jacob on 16/06/16.
 */
public class TeamPresenter implements TeamContract.Presenter {

    private final TeamRepository teamRepository;

    private final TeamContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    List<League> results;
    int i = 0;

    @Inject
    public TeamPresenter(TeamContract.View view, TeamRepository teamRepo) {
        mView = view;
        teamRepository = teamRepo;

        mView.setPresenter(this);
    }

    @Override
    public void loadTeam(TeamAPI _api, final boolean initialLoad, String id)
    {
        teamRepository.getRemoteSource().getTeam(_api, initialLoad, mView, teamRepository, id);
    }

    @Inject
    @Override
    public void start() { }
}
