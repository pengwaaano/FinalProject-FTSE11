package com.jacobgreenland.finalproject;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.LeagueFragment;
import com.jacobgreenland.finalproject.league.model.League;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.team.TeamTabs;
import com.jacobgreenland.finalproject.team.model.FavouriteTeam;
import com.jacobgreenland.finalproject.team.model.Team;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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

    public static FrameLayout mainFrame;
    Snackbar snackbar;

    public static Dialog dialog;
    private CallbackManager callbackManager;
    private TextView infoTextFacebook;
    private LoginButton facebookLoginButton;
    private TwitterLoginButton twitterLoginButton;
    Button twitterTweet;

    public static String fixtureTitle;
    public static String fixtureMessage;
    public DateTime fixtureData;

    public static FavouriteTeam favouriteTeam;
    public static Address stadiumAddress;

    DateTimeFormatter formatter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    RealmConfiguration realmConfig;
    public static Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

        initialiseToolbar();

        ((MyApp) getApplication()).getApiComponent().inject(this);

        mainFrame = (FrameLayout) findViewById(R.id.mainFragment);

        MainFragment lF = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);
        ft.replace(R.id.mainFragment, lF, "tabs");
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

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.socialdialog);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());

        RealmResults<FavouriteTeam> result2 = realm.where(FavouriteTeam.class)
                .findAll();

        if(result2.size() == 0)
            favouriteTeam = new FavouriteTeam();
        else
            favouriteTeam = result2.get(0);
    }

    public void checkCalendarPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CALENDAR)) {
                Log.d("test", "CALENDAR NOT ACTIVATED");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Log.d("test", "CALENDAR ACTIVATED");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("test", "PERMISSION GRANTED");
                    addEventToCalender();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.d("test", "PERMISSION DENIED");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean doesUserHavePermission() {
        int result = this.checkCallingOrSelfPermission(Manifest.permission.WRITE_CALENDAR);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void addEventToCalender() {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.setData(CalendarContract.Events.CONTENT_URI);

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fixtureData.getMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fixtureData.getMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

        intent.putExtra(CalendarContract.Events.TITLE, fixtureTitle);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, stadiumAddress.getFeatureName());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Powered by PannaMatch");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "");
        TimeZone tz = TimeZone.getDefault();
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());

        startActivity(intent);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content)
    {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.pannamatchicon);
        builder.setStyle(new Notification.BigTextStyle());
        return builder.build();
    }

    @Override
    public void setUpNotifications()
    {
        realm.beginTransaction();
        //realm.deleteAll();
        realm.copyToRealmOrUpdate(favouriteTeam);
        realm.commitTransaction();

        Log.d("notifications", "notifications are being set!");
        scheduleNotification(getNotification("30 second delay"), 5000);

        scheduleNotification(getNotification("30 second delay"), 10000);

        scheduleNotification(getNotification("30 second delay"), 30000);

        for(Fixture f : favouriteTeam.getFixtures())
        {
            DateTime fDate = formatter.parseDateTime(f.getDate());

            long futureInMillis = fDate.getMillis() - SystemClock.elapsedRealtime();

            scheduleNotification(getNotification(f.getHomeTeamName() + " v " + f.getAwayTeamName()), (int)futureInMillis);
        }
    }

    public void initialiseToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
    }

    @Override
    public void initialiseNavigationDrawer() {
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

        for (int i = 0; i < leagues.size(); i++) {
            shortLeague = leagues.get(i).getCaption();

            shortLeague = shortLeague.replaceAll("\\d\\d\\d\\d\\S\\d\\d", "");

            final String finalShortLeague = shortLeague;
            //final int finalI1 = i;
            final int finalI1 = i;
            SplashScreen.realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
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
                    ft.setCustomAnimations(R.anim.fragment_slide_right_enter,
                            R.anim.fragment_slide_left_exit,
                            R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_right_exit);
                    ft.replace(R.id.mainFragment, lF, "tabs");
                    ft.addToBackStack(null);
                    ft.commit();

                    return true;
                }
            });
        }
    }

    @Override
    public void initialiseTwitter() {
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.dialogTwitterButton);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                twitterLoginButton.setClickable(false);
                twitterLoginButton.setText("You are signed in.");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        twitterTweet = (Button) dialog.findViewById(R.id.dialogTwitterShare);
        twitterTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int TWEET_COMPOSER_REQUEST_CODE = 100;
                Intent intent = new TweetComposer.Builder(MainActivity.this)
                        .text("I'm going to " + fixtureTitle + " on " + fixtureMessage + ".")
                        .createIntent();
                startActivityForResult(intent, TWEET_COMPOSER_REQUEST_CODE);
            }
        });
    }

    @Override
    public void initialiseFacebook() {
        facebookLoginButton = (LoginButton) findViewById(R.id.dialogFacebookButton);

        callbackManager = CallbackManager.Factory.create();

        final Button shareButton = (Button) dialog.findViewById(R.id.dialogFacebookShare);
        shareButton.setVisibility(View.GONE);

        AccessToken token;
        token = AccessToken.getCurrentAccessToken();

        if (token != null) {
            //Means user is not logged in
            shareButton.setVisibility(View.VISIBLE);
        }

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                shareButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
            }
        });


        final ShareDialog shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new

                FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(MainActivity.fixtureTitle)
                            .setContentDescription(MainActivity.fixtureMessage)
                            .setContentUrl(Uri.parse("http://www.jacobgreenland.co.uk"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
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

    public void switchContent(int id, Fragment fragment) {
        //Switch Fragments method
        Log.d("Test", "Fragment changed!");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);
        if (fragment instanceof TeamTabs) {
            ft.replace(id, fragment, "tabs");
        } else {
            ft.replace(id, fragment, fragment.toString());
        }

        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void loadMoreTabs() {
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
                            @Override
                            public void call(final ConnectivityStatus status) {
                                Log.d(TAG, status.toString());
                                //Snackbar snackbar = new Snackbar(mainFrame, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                                if (status.equals(ConnectivityStatus.OFFLINE)) {
                                    snackbar.show();
                                } else {
                                    //Log.d("")
                                    snackbar.dismiss();
                                }
                            }
                        });

        internetConnectivitySubscription = reactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isConnectedToInternet) {
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jacobgreenland.finalproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jacobgreenland.finalproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
