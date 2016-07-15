package com.jacobgreenland.finalproject.fixture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jacobgreenland.finalproject.CustomScaleAnimation;
import com.jacobgreenland.finalproject.HttpImageRequestTask;
import com.jacobgreenland.finalproject.ImageCacheUtil;
import com.jacobgreenland.finalproject.ItemClickListener;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.MapsActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.services.Services;
import com.jacobgreenland.finalproject.team.TeamAPI;
import com.jacobgreenland.finalproject.team.TeamTabs;
import com.jacobgreenland.finalproject.team.model.Team;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 01/07/16.
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder> {

    private List<Fixture> Fixtures;
    private int rowLayout;
    private Context mContext;
    private boolean landscape;
    Dialog dialog;
    TeamTabs mFragment;
    MapsActivity mapFrag;
    RelativeLayout fInfo;
    RelativeLayout fStadium;
    private boolean open = false;
    TeamAPI _api;
    Team homeTeam;
    Team awayTeam;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;

    CardView cardView;
    int minHeight = 20;

    DateTimeFormatter formatter;
    DateTime fixtureData;

    ViewHolder currentlyOpenView;

    public FixtureAdapter(List<Fixture> r, int rowLayout, Context context, boolean landscape, final Dialog dialog) {

        this.Fixtures = r;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.landscape = landscape;
        this.dialog = dialog;
        this.open = false;

        currentlyOpenView = null;
        notifyDataSetChanged();

        //dialog = new Dialog(mContext);

        _api = Services._createTeamAPI();

        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        cardView = (CardView) v.findViewById(R.id.fixtureCard);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Fixture fixture = Fixtures.get(i);
        //Log.d("test", ""+ Standings.size());

        viewHolder.HomeTeam.setText(fixture.getHomeTeamName().replace("AFC", "").replace("FC", ""));
        viewHolder.AwayTeam.setText(fixture.getAwayTeamName().replace("AFC", "").replace("FC", ""));

        //MainActivity.fixtureTitle = fixture.getHomeTeamName() + " v " + fixture.getAwayTeamName();


        viewHolder.HomeTeam.setTypeface(Typeface.DEFAULT);
        viewHolder.AwayTeam.setTypeface(Typeface.DEFAULT);

        if (fixture.getHomeTeamName().equals(MainActivity.chosenTeamObject.getName()))
            viewHolder.HomeTeam.setTypeface(Typeface.DEFAULT_BOLD);
        else
            viewHolder.AwayTeam.setTypeface(Typeface.DEFAULT_BOLD);

        if (fixture.getStatus().equals("SCHEDULED") || fixture.getStatus().equals("TIMED"))
            viewHolder.Vs.setText("  V  ");
        else
            viewHolder.Vs.setText(fixture.getResult().getGoalsHomeTeam() + " V " + fixture.getResult().getGoalsAwayTeam());

        viewHolder.fixtureInfo.setVisibility(View.GONE);
        viewHolder.fixtureSocial.setVisibility(View.GONE);

        /*Picasso.with(mContext)
                .load(Result.getArtworkUrl100())
                .into( viewHolder.ResultArtwork);*/
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                    //viewHolder.fixtureInfo.setVisibility(View.VISIBLE);
                    //viewHolder.fixtureStadium.setVisibility(View.VISIBLE);
                    //viewHolder.fixtureSocial.setVisibility(View.VISIBLE);

                    if(currentlyOpenView != null)
                    {
                        if(currentlyOpenView.HomeTeam.getText().equals(viewHolder.HomeTeam.getText()) &&
                                currentlyOpenView.AwayTeam.getText().equals(viewHolder.AwayTeam.getText()))
                        {
                            viewHolder.fixtureInfo.startAnimation(new CustomScaleAnimation(viewHolder.fixtureInfo, false));
                            viewHolder.fixtureSocial.startAnimation(new CustomScaleAnimation(viewHolder.fixtureSocial, false));
                            currentlyOpenView = null;
                        }
                        else {
                            currentlyOpenView.fixtureInfo.startAnimation(new CustomScaleAnimation(currentlyOpenView.fixtureInfo, false));
                            currentlyOpenView.fixtureSocial.startAnimation(new CustomScaleAnimation(currentlyOpenView.fixtureSocial, false));
                            currentlyOpenView = viewHolder;
                        }
                    }
                    else
                    {
                        currentlyOpenView = viewHolder;
                    }

                    fixtureData = formatter.parseDateTime(fixture.getDate());

                    String time = "";
                    if (fixtureData.getMinuteOfHour() == 0) {
                        time = fixtureData.getHourOfDay() + ":" + fixtureData.getMinuteOfHour() + "0";
                    } else {
                        time = fixtureData.getHourOfDay() + ":" + fixtureData.getMinuteOfHour();
                    }

                    String date = fixtureData.getDayOfMonth() + "/" + fixtureData.getMonthOfYear() + "/" + fixtureData.getYear();

                    MainActivity.fixtureTitle = fixture.getHomeTeamName() + " v " + fixture.getAwayTeamName();

                    MainActivity.fixtureMessage = time + " - " + date;

                    viewHolder.time.setText(time);
                    viewHolder.date.setText(date);

                    String homeT = fixture.getLinks().getHomeTeam().getHref().substring(32, fixture.getLinks().getHomeTeam().getHref().length());
                    String awayT = fixture.getLinks().getAwayTeam().getHref().substring(32, fixture.getLinks().getAwayTeam().getHref().length());


                    if(viewHolder.homeTeamBadge.getDrawable() == null)
                        loadTeam(homeT, true, viewHolder.homeTeamBadge);
                    if(viewHolder.awayTeamBadge.getDrawable() == null)
                        loadTeam(awayT, false, viewHolder.awayTeamBadge);

                    MainActivity.stadiumAddress = getStadium(fixture);

                    open = true;

                if(currentlyOpenView != null) {
                    viewHolder.fixtureInfo.startAnimation(new CustomScaleAnimation(viewHolder.fixtureInfo, true));
                    viewHolder.fixtureSocial.startAnimation(new CustomScaleAnimation(viewHolder.fixtureSocial, true));
                }
            }
        });

        viewHolder.stadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(v, true);
            }
        });

        viewHolder.socialSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        viewHolder.addToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addEventToCalender(mContext, fixture);
                new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle("Add to calendar")
                        .setMessage("Are you sure you want to add this to your calendar?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity main = (MainActivity)mContext;
                                main.fixtureData = fixtureData;

                                if(main.doesUserHavePermission())
                                    main.addEventToCalender();
                                else
                                    main.checkCalendarPermissions();


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public void loadTeam(String id, final boolean home, final ImageView teamBadge)
    {
        _subscriptions.add(_api.getTeam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<Team>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "Error");
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                        /*if(initialLoad) {
                            leagueRepository.getLocalSource().addData(leagues);
                        }
                        else {*/
                        //Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                        //mView.setTeam(team, true);
                        //mView.showDialog();
                        if(home)
                            loadLogo(teamBadge, homeTeam.getCrestUrl());
                        else
                            loadLogo(teamBadge, awayTeam.getCrestUrl());
                        //}
                    }
                    @Override
                    public void onNext(Team team2) {
                        Log.i("Retrofit", "onNext");

                        if(home)
                            homeTeam = team2;
                        else
                            awayTeam = team2;
                    }
                }));
    }

    public void loadLogo(ImageView badge, String url)
    {
        if (!url.isEmpty()) {
            Log.d("logotest", url);
            if(url.contains(".png") || url.contains(".gif"))
            {
                Log.d("logotest", "contains png");
                    Picasso.with(mContext)
                            .load(Uri.parse(url))
                            .into(badge);
            }
            else
            {
                ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(mContext);

                //we somehow get the filename replacing the end point with nothing and the file extension
                String filename = null;
                try {
                    filename = URLEncoder.encode(url,"UTF-8").replace(".svg", ".png");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //first we try getting the image from local saved images
                if (imageCacheUtil.getImageFromFile(badge, filename)) {
                    badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    //holder.progressBar.setVisibility(View.GONE);
                } else {
                    //if we haven't saved the image previously, we try getting it from our cache system.
                    //If we fail, we download it from the URL provided
                    Bitmap image = imageCacheUtil.getImage(url);

                    if (image != null) {
                        badge.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        badge.setImageBitmap(image);
                        //holder.progressBar.setVisibility(View.GONE);
                    } else {
                        new HttpImageRequestTask(mContext, badge).execute(url);
                    }
                }
            }
        } else {
            //sort of placeholder if the item doesn't have a image URL
            badge.setImageResource(R.drawable.nologo);
        }
    }

    public Address getStadium(Fixture fixture)
    {
        String stadium = "stadium: " + fixture.getHomeTeamName().replace("FC","");

        Log.d("map test", stadium);

        Geocoder gc = new Geocoder(mContext, Locale.ENGLISH);
        List<Address> geoResults = new ArrayList<Address>();

        try
        {
            geoResults = gc.getFromLocationName(stadium, 1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if(geoResults.size() > 0)
            return geoResults.get(0);
        else
            return null;
    }

    private void fragmentJump(View view, boolean map) {
        //call switch content to proceed with changing fragment
            if(map) {
                mapFrag = new MapsActivity();
                switchContent(R.id.mainFragment, mapFrag);
            }
    }

    public void switchContent(int id, MapsActivity mapFrag) {
        if (mContext == null) {
            Log.d("test", "this isn't good");
            return;
        }
        // jump to main activity to switch fragment
        Log.d("test", "this is better");
        MainActivity mainActivity = (MainActivity) mContext;
        MapsActivity frag = mapFrag;
        mainActivity.switchContent(id, frag);
        //}
    }

    private void hidePDialog()
    {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public int getItemCount() {
        return Fixtures == null ? 0 : Fixtures.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //@BindView(R.id.teamName)

        CardView cardView;

        TextView HomeTeam;
        TextView AwayTeam;
        TextView Vs;

        RelativeLayout fixtureInfo;
        RelativeLayout fixtureSocial;

        ImageView homeTeamBadge;
        ImageView awayTeamBadge;
        ImageView addToCalender;
        TextView time;
        TextView date;
        ImageView stadium;
        ImageView socialSignin;

        //@BindView(R.id.songArtwork) ImageView ResultArtwork;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            HomeTeam = (TextView) itemView.findViewById(R.id.fHomeTeam);
            AwayTeam = (TextView) itemView.findViewById(R.id.fAwayTeam);

            HomeTeam.setTypeface(Typeface.DEFAULT);
            AwayTeam.setTypeface(Typeface.DEFAULT);

            Vs = (TextView) itemView.findViewById(R.id.vs);

            fixtureInfo = (RelativeLayout) itemView.findViewById(R.id.fixtureInfo);
            fixtureSocial = (RelativeLayout) itemView.findViewById(R.id.socialSignIn);

            homeTeamBadge = (ImageView) itemView.findViewById(R.id.fHomeBadge);
            awayTeamBadge = (ImageView) itemView.findViewById(R.id.fAwayBadge);
            addToCalender = (ImageView) itemView.findViewById(R.id.fAddToCalendar);

            time = (TextView) itemView.findViewById(R.id.fTime);
            date = (TextView) itemView.findViewById(R.id.fDate);
            stadium = (ImageView) itemView.findViewById(R.id.fStadium);
            socialSignin = (ImageView) itemView.findViewById(R.id.socialButton);
            //ButterKnife.bind(this, itemView);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition());
            return true;
        }
    }
}

