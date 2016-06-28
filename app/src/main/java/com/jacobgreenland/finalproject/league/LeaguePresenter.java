package com.jacobgreenland.finalproject.league;

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

    /*@Override
    public LeagueRepository getRepository()
    {
        return songRepository;
    }

    @Override
    public void setRepository(LeagueRepository songRepo)
    {
        //this.songRepository = songRepo;
    }

    @Override
    public void loadUsers() {
        i++;
        s = "Users Loaded " + i;
        mView.showUsers(s);
    }*/
    @Override
    public void loadLeagues(LeagueAPI _api, final boolean initialLoad, String season)
    {
        leagueRepository.getRemoteSource().getLeagues(_api, initialLoad, mView, leagueRepository, season);
    }
    /*
    @Override
    public void loadLocalPopSongs()
    {
        mView.setAdapters(songRepository.getLocalSource().getDataFromLocal("Pop"),false);
    }*/

    @Inject
    @Override
    public void start() { }
}
