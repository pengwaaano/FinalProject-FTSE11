package com.jacobgreenland.finalproject.team.model;

import com.jacobgreenland.finalproject.fixture.model.Fixture;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jacob on 13/07/16.
 */
public class FavouriteTeam extends RealmObject {

    @PrimaryKey
    private int id;
    private Team favourite;
    private RealmList<Fixture> fixtures;

    public FavouriteTeam()
    {
        id = 1;
        favourite = null;
        fixtures = null;
    }

    public Team getFavourite() {
        return favourite;
    }
    public void setFavourite(Team favourite) {
        this.favourite = favourite;
    }

    public List<Fixture> getFixtures() {return fixtures;}
    public void setFixures(RealmList<Fixture> fixtures) {this.fixtures = fixtures; }
}
