package com.jacobgreenland.finalproject.team;

import com.jacobgreenland.finalproject.BasePresenter;
import com.jacobgreenland.finalproject.BaseView;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.player.model.Player;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;

/**
 * Created by Jacob on 16/06/16.
 */
public interface TeamContract {

    interface View extends BaseView<Presenter> {
        void setTeam(Team team, boolean fromAPI);
        void setFixtureAdapter(List<Fixture> fixtures);
        void setPlayerAdapter(List<Player> players);
        void showDialog();
    }

    interface Presenter extends BasePresenter {
        void loadTeam(TeamAPI _api, final boolean initialLoad, String id);
        void loadPlayers(TeamAPI _api, final boolean initialLoad, String id);
        void loadFixtures(TeamAPI _api, final boolean initialLoad, String id);
        TeamRepository getRepo();
    }
}
