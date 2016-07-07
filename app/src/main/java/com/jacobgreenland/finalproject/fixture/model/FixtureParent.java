
package com.jacobgreenland.finalproject.fixture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jacobgreenland.finalproject.team.model.Team;

import javax.annotation.Generated;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class FixtureParent extends RealmObject {

    private Team team;
    @PrimaryKey
    private String code;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("fixtures")
    @Expose
    private RealmList<Fixture> fixtures = new RealmList<Fixture>();


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
     *     The fixtures
     */
    public RealmList<Fixture> getFixtures() {
        return fixtures;
    }

    /**
     * 
     * @param fixtures
     *     The fixtures
     */
    public void setFixtures(RealmList<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

}
