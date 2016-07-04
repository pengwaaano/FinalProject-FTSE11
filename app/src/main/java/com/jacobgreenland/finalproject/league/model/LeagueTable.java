package com.jacobgreenland.finalproject.league.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class LeagueTable extends RealmObject {

    @SerializedName("leagueCaption")
    @PrimaryKey
    @Expose
    private String leagueCaption;
    @SerializedName("matchday")
    @Expose
    private Integer matchday;
    @SerializedName("standing")
    @Expose
    private RealmList<Standing> standing = new RealmList<Standing>();

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
    public RealmList<Standing> getStanding() {
        return standing;
    }

    /**
     *
     * @param standing
     * The standing
     */
    public void setStanding(RealmList<Standing> standing) {
        this.standing = standing;
    }

}