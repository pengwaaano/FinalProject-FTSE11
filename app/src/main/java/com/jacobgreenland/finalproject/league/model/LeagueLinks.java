package com.jacobgreenland.finalproject.league.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Jacob on 30/06/16.
 */

public class LeagueLinks extends RealmObject {

    @SerializedName("team")
    @Expose
    private TeamLink teamLink;

    /**
     *
     * @return
     * The teamLink
     */
    public TeamLink getTeamLink() {
        return teamLink;
    }

    /**
     *
     * @param teamLink
     * The teamLink
     */
    public void setTeamLink(TeamLink teamLink) {
        this.teamLink = teamLink;
    }

}
