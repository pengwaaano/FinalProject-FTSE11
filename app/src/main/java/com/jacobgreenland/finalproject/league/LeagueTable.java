package com.jacobgreenland.finalproject.league;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jacobgreenland.finalproject.league.model.Standing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LeagueTable {

    @SerializedName("leagueCaption")
    @Expose
    private String leagueCaption;
    @SerializedName("matchday")
    @Expose
    private Integer matchday;
    @SerializedName("standing")
    @Expose
    private List<Standing> standing = new ArrayList<Standing>();

    /**
     *
     * @return
     * The leagueCaption
     */
    public String getLeagueCaption() {
        return leagueCaption;
    }

    /**
     *
     * @param leagueCaption
     * The leagueCaption
     */
    public void setLeagueCaption(String leagueCaption) {
        this.leagueCaption = leagueCaption;
    }

    /**
     *
     * @return
     * The matchday
     */
    public Integer getMatchday() {
        return matchday;
    }

    /**
     *
     * @param matchday
     * The matchday
     */
    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    /**
     *
     * @return
     * The standing
     */
    public List<Standing> getStanding() {
        return standing;
    }

    /**
     *
     * @param standing
     * The standing
     */
    public void setStanding(List<Standing> standing) {
        this.standing = standing;
    }

}