package com.jacobgreenland.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.LeagueFragment;
import com.jacobgreenland.finalproject.league.model.League;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.team.TeamTabs;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Communicator {

    Toolbar toolbar;

    public static List<League> leagues;
    @Inject
    public static LeagueAPI _api;


    public static String chosenLeague;
    public static String chosenLeagueID;
    public static String chosenTeam;
    public static String chosenFixtures;
    public static int chosenTeamPosition;
    public static Team chosenTeamObject;
    public static LeagueTable chosenLeagueObject;
    public static List<Team> loadedLeagueTeams = new ArrayList<Team>();

    private TextView tvConnectivityStatus;
    private TextView tvInternetStatus;
    private ReactiveNetwork reactiveNetwork;
    private Subscription networkConnectivitySubscription;
    private Subscription internetConnectivitySubscription;
    private static final String TAG = "ReactiveNetwork";

    FrameLayout mainFrame;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseToolbar();

        ((MyApp) getApplication()).getApiComponent().inject(this);

        mainFrame = (FrameLayout) findViewById(R.id.mainFragment);

        MainFragment lF = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.mainFragment, lF, "tabs");
        ft.commit();

        //initialiseNavigationDrawer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.INVISIBLE);

        snackbar = Snackbar
            .make(mainFrame, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
    }

    public void initialiseToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }
    @Override
    public void initialiseNavigationDrawer()
    {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //List<League> leagues = leagueRepository.getRemoteSource().getLeagueList();
        String shortLeague = "";

        for(int i = 0; i < leagues.size(); i++)
        {
            shortLeague = leagues.get(i).getCaption();

            shortLeague = shortLeague.replaceAll("\\d\\d\\d\\d\\S\\d\\d", "");

            final String finalShortLeague = shortLeague;
            //final int finalI1 = i;
            final int finalI1 = i;
            SplashScreen.realm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    leagues.get(finalI1).setCaption(finalShortLeague);
                }
            });

            Log.d("test", "looping");

            final int finalI = i;
            navigationView.getMenu().add(shortLeague).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d("looptest", finalI + " i hate final");
                    Log.d("looptest", leagues.get(finalI).getCaption());
                    chosenLeagueID = leagues.get(finalI).getId().toString();
                    chosenLeague = leagues.get(finalI).getCaption();
                    drawer.closeDrawer(GravityCompat.START);

                    LeagueFragment lF = new LeagueFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.mainFragment, lF, "tabs");
                    ft.commit();

                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchContent(int id, Fragment fragment, View view) {
        //Switch Fragments method
        Log.d("Test", "Fragment changed!");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(fragment instanceof TeamTabs)
        {
            ft.replace(id, fragment, "tabs");
        }
        else {
            ft.replace(id, fragment, fragment.toString());
        }
        ft.addToBackStack(null);
        ft.commit();
    }
    @Override
    public void loadMoreTabs()
    {
        Log.d("test", "main load more tabs :(");
    }

    @Override
    protected void onResume() {
        super.onResume();
        reactiveNetwork = new ReactiveNetwork();

        networkConnectivitySubscription =
                reactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ConnectivityStatus>() {
                            @Override public void call(final ConnectivityStatus status) {
                                Log.d(TAG, status.toString());
                                //Snackbar snackbar = new Snackbar(mainFrame, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                                if(status.equals(ConnectivityStatus.OFFLINE)) {

                                    snackbar.show();
                                }
                                else
                                {
                                    //Log.d("")
                                    snackbar.dismiss();
                                }
                            }
                        });

        internetConnectivitySubscription = reactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {
                        /*Snackbar snackbar = Snackbar
                                .make(mainFrame, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                        if(isConnectedToInternet)
                        {
                            snackbar.show();
                        }
                        else
                        {
                            snackbar.dismiss();
                        }*/

                        //tvInternetStatus.setText(isConnectedToInternet.toString());
                    }
                });


    }

    @Override
    protected void onPause() {
        super.onPause();
        safelyUnsubscribe(networkConnectivitySubscription, internetConnectivitySubscription);
    }

    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
