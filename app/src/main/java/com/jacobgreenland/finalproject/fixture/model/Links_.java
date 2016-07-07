
package com.jacobgreenland.finalproject.fixture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class Links_ extends RealmObject {

    @SerializedName("self")
    @Expose
    private Self_ self;
    @SerializedName("competition")
    @Expose
    private Competition competition;
    @SerializedName("homeTeam")
    @Expose
    private HomeTeam homeTeam;
    @SerializedName("awayTeam")
    @Expose
    private AwayTeam awayTeam;

    /**
     * 
     * @return
     *     The self
     */
    public Self_ getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self_ self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The competition
     */
    public Competition getCompetition() {
        return competition;
    }

    /**
     * 
     * @param competition
     *     The competition
     */
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    /**
     * 
     * @return
     *     The homeTeam
     */
    public HomeTeam getHomeTeam() {
        return homeTeam;
    }

    /**
     * 
     * @param homeTeam
     *     The homeTeam
     */
    public void setHomeTeam(HomeTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * 
     * @return
     *     The awayTeam
     */
    public AwayTeam getAwayTeam() {
        return awayTeam;
    }

    /**
     * 
     * @param awayTeam
     *     The awayTeam
     */
    public void setAwayTeam(AwayTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

}
