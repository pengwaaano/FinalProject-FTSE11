package com.jacobgreenland.finalproject.league;

import com.jacobgreenland.finalproject.BasePresenter;
import com.jacobgreenland.finalproject.BaseView;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.team.TeamAPI;

import java.util.List;

/**
 * Created by Jacob on 16/06/16.
 */
public interface LeagueContract {

    interface View extends BaseView<Presenter> {
        void setAdapters(LeagueTable leagueTable, boolean fromAPI);
        void setLeagueAdapters();
        void showDialog();
    }

    interface Presenter extends BasePresenter {
        void loadLeagueTable(LeagueAPI _api, final boolean initialLoad, String id);
        void loadTeamsOfLeague(TeamAPI _api, final boolean initialLoad, List<String> id, String s);
        void loadLocalLeagueTable(String leagueTable);
        void loadLocalLeagueTeams(String l);
        //void loadLocalPopSongs();
        //LeagueRepository getRepository();
        //void setRepository(LeagueRepository songRepo);
    }
}
