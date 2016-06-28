package com.jacobgreenland.finalproject.league;

import com.jacobgreenland.finalproject.BasePresenter;
import com.jacobgreenland.finalproject.BaseView;

import java.util.List;

/**
 * Created by Jacob on 16/06/16.
 */
public interface LeagueContract {

    interface View extends BaseView<Presenter> {
        void setAdapters(List<League> results, boolean fromAPI);
        void showDialog();
    }

    interface Presenter extends BasePresenter {
        void loadLeagues(LeagueAPI _api, final boolean initialLoad, String season);
        //void loadLocalPopSongs();
        //LeagueRepository getRepository();
        //void setRepository(LeagueRepository songRepo);
    }
}
