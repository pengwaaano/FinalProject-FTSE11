package com.jacobgreenland.finalproject.team.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jacob on 30/06/16.
 */
public class Team extends RealmObject {

    @SerializedName("_links")
    @Expose
    private TeamLinks links;
    @SerializedName("name")
    @PrimaryKey
    @Expose
    private String name;

    private String league;

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("squadMarketValue")
    @Expose
    private String squadMarketValue;
    @SerializedName("crestUrl")
    @Expose
    private String crestUrl;

    /**
     *
     * @return
     * The links
     */
    public TeamLinks getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The _links
     */
    public void setLinks(TeamLinks links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }
    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *
     * @param shortName
     * The shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     *
     * @return
     * The squadMarketValue
     */
    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    /**
     *
     * @param squadMarketValue
     * The squadMarketValue
     */
    public void setSquadMarketValue(String squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
    }

    /**
     *
     * @return
     * The crestUrl
     */
    public String getCrestUrl() {
        return crestUrl;
    }

    /**
     *
     * @param crestUrl
     * The crestUrl
     */
    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

}
