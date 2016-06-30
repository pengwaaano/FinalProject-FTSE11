package com.jacobgreenland.finalproject.league.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacob on 30/06/16.
 */

public class Links_ {

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
