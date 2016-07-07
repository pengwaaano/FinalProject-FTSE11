
package com.jacobgreenland.finalproject.player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jacobgreenland.finalproject.team.model.Team;

import javax.annotation.Generated;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class TeamPlayers extends RealmObject {

    private Team team;
    @PrimaryKey
    private String code;
    @SerializedName("_links")
    @Expose
    private PlayerLinks links;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("players")
    @Expose
    private RealmList<Player> players = new RealmList<Player>();


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team t) {
        this.team = t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s) {
        this.code = s;
    }
    /**
     * 
     * @return
     *     The links
     */
    public PlayerLinks getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The _links
     */
    public void setLinks(PlayerLinks links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The players
     */
    public RealmList<Player> getPlayers() {
        return players;
    }

    /**
     * 
     * @param players
     *     The players
     */
    public void setPlayers(RealmList<Player> players) {
        this.players = players;
    }

}
