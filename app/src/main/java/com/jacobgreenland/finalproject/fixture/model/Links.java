
package com.jacobgreenland.finalproject.fixture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class Links extends RealmObject {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("team")
    @Expose
    private FixtureTeam team;

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The team
     */
    public FixtureTeam getTeam() {
        return team;
    }

    /**
     * 
     * @param team
     *     The team
     */
    public void setTeam(FixtureTeam team) {
        this.team = team;
    }

}
