
package com.jacobgreenland.finalproject.fixture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class Fixture extends RealmObject {

    @SerializedName("_links")
    @Expose
    private Links_ links;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("matchday")
    @Expose
    private Integer matchday;
    @SerializedName("homeTeamName")
    @Expose
    private String homeTeamName;
    @SerializedName("awayTeamName")
    @Expose
    private String awayTeamName;
    @SerializedName("result")
    @Expose
    private Result result;

    /**
     *
     * @return
     *     The links
     */
    public Links_ getLinks() {
        return links;
    }

    /**
     *
     * @param links
     *     The _links
     */
    public void setLinks(Links_ links) {
        this.links = links;
    }

    /**
     *
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The matchday
     */
    public Integer getMatchday() {
        return matchday;
    }

    /**
     *
     * @param matchday
     *     The matchday
     */
    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    /**
     *
     * @return
     *     The homeTeamName
     */
    public String getHomeTeamName() {
        return homeTeamName;
    }

    /**
     *
     * @param homeTeamName
     *     The homeTeamName
     */
    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    /**
     *
     * @return
     *     The awayTeamName
     */
    public String getAwayTeamName() {
        return awayTeamName;
    }

    /**
     *
     * @param awayTeamName
     *     The awayTeamName
     */
    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    /**
     *
     * @return
     *     The result
     */
    public Result getResult() {
        return result;
    }

    /**
     *
     * @param result
     *     The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

}
