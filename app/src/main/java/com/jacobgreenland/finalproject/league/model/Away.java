package com.jacobgreenland.finalproject.league.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacob on 29/06/16.
 */

public class Away {

    @SerializedName("goals")
    @Expose
    private Integer goals;
    @SerializedName("goalsAgainst")
    @Expose
    private Integer goalsAgainst;
    @SerializedName("wins")
    @Expose
    private Integer wins;
    @SerializedName("draws")
    @Expose
    private Integer draws;
    @SerializedName("losses")
    @Expose
    private Integer losses;

    /**
     *
     * @return
     * The goals
     */
    public Integer getGoals() {
        return goals;
    }

    /**
     *
     * @param goals
     * The goals
     */
    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    /**
     *
     * @return
     * The goalsAgainst
     */
    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    /**
     *
     * @param goalsAgainst
     * The goalsAgainst
     */
    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    /**
     *
     * @return
     * The wins
     */
    public Integer getWins() {
        return wins;
    }

    /**
     *
     * @param wins
     * The wins
     */
    public void setWins(Integer wins) {
        this.wins = wins;
    }

    /**
     *
     * @return
     * The draws
     */
    public Integer getDraws() {
        return draws;
    }

    /**
     *
     * @param draws
     * The draws
     */
    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    /**
     *
     * @return
     * The losses
     */
    public Integer getLosses() {
        return losses;
    }

    /**
     *
     * @param losses
     * The losses
     */
    public void setLosses(Integer losses) {
        this.losses = losses;
    }

}
