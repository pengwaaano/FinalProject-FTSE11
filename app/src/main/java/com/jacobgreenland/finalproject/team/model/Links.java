package com.jacobgreenland.finalproject.team.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacob on 30/06/16.
 */
public class Links {

    @SerializedName("fixtures")
    @Expose
    private Fixtures fixtures;
    @SerializedName("players")
    @Expose
    private Players players;

    /**
     *
     * @return
     * The fixtures
     */
    public Fixtures getFixtures() {
        return fixtures;
    }

    /**
     *
     * @param fixtures
     * The fixtures
     */
    public void setFixtures(Fixtures fixtures) {
        this.fixtures = fixtures;
    }

    /**
     *
     * @return
     * The players
     */
    public Players getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     * The players
     */
    public void setPlayers(Players players) {
        this.players = players;
    }

}
