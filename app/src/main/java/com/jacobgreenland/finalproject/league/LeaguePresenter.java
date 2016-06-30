package com.jacobgreenland.finalproject.league;

import com.jacobgreenland.finalproject.league.model.League;

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

    List<League> results;
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
        leagueRepository.getRemoteSource().getLeagueTable(_api, initialLoad, mView, leagueRepository, id);
    }

    @Inject
    @Override
    public void start() { }
}
