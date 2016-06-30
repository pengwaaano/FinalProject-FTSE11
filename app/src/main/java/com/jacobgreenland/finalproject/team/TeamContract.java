package com.jacobgreenland.finalproject.team;

import com.jacobgreenland.finalproject.BasePresenter;
import com.jacobgreenland.finalproject.BaseView;
import com.jacobgreenland.finalproject.team.model.Team;

/**
 * Created by Jacob on 16/06/16.
 */
public interface TeamContract {

    interface View extends BaseView<Presenter> {
        void setAdapters(Team team, boolean fromAPI);
        void showDialog();
    }

    interface Presenter extends BasePresenter {
        void loadTeam(TeamAPI _api, final boolean initialLoad, String id);
        //void loadLocalPopSongs();
        //LeagueRepository getRepository();
        //void setRepository(LeagueRepository songRepo);
    }
}
