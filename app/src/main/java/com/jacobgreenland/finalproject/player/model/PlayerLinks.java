
package com.jacobgreenland.finalproject.player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jacobgreenland.finalproject.team.model.Team;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class PlayerLinks extends RealmObject {

    @SerializedName("self")
    @Expose
    private PlayerSelf self;
    @SerializedName("team")
    @Expose
    private Team team;

    /**
     * 
     * @return
     *     The self
     */
    public PlayerSelf getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(PlayerSelf self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * 
     * @param team
     *     The team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

}
