package com.smartgateapps.spanifootball.spani;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.smartgateapps.spanifootball.R;
import com.smartgateapps.spanifootball.model.Legue;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Raafat on 04/11/2015.
 */
public class MyApplication extends Application {


    public static AlarmManager alarmManager;
    public static SharedPreferences pref;
    public static DbHelper dbHelper;
    public static SQLiteDatabase dbw, dbr;

    public static int pageSize = 15;
    public static Typeface font;

    public static final String BASE_URL = "http://m.kooora.com/";
    public static final String SPANI_EXT_HOME = "?n=0&o=nces&pg=";
    public static final String FIRST_LEAGUE_EXT = "?c=7714";
    public static final String KING_CUP_EXT = "?c=11339";
    public static final String FIRST_LEAGUE_NEWS_EXT = "?n=0&o=n7714&pg=";
    public static final String KING_CUP_NEWS_EXT = "?n=0&o=n11339&pg=";
    public static final String TEAM_NEWS_EXT = "?n=0&o=n1000000";
    public static final String TEAM_MATCHES_EXT = "?region=-6&team=";

    public static final String TEAMS_CM = "&cm=t";
    public static final String POSES_CM = "&cm=i";
    public static final String MATCHES_CM = "&cm=m";
    public static final String SCORERS_CM = "&scorers=true";

    public static Context APP_CTX;
    public static final String LIVE_CAST_APP_PACKAGE_NAME = "com.smartgateapps.livesports";

    public static Picasso picasso;
    public static WebView webView;

    public static final int HEADER_TYPE_GOALERS = 0;

    public static String[] PLAYERS_POS = new String[]{"", "مدرب", "حارس", "دفاع", "وسط", "هجوم", "مساعد مدرب", " مدرب حراس", "مدرب بدني", "طبيب الفريق"};

    public static HashMap<String, Integer> monthOfTheYear = new HashMap<>(12);

    public static MyApplication instance;

    public static HashMap<Integer, Integer> teamsLogos = new HashMap<>();

    public static String ACTION_ACTIVATION = "ACTION_ACTIVATION_SPANI";
    public static String UPATE_MATCH = "UPDATE_MATCH_SPANI";
    public static String DO_AT_2_AM = "DO_AT_2_AM_SPANI";

    public static NotificationManager notificationManager;

    public static SimpleDateFormat sourceTimeFormate = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat destTimeFormate = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat sourceDateFormat = new SimpleDateFormat("E d MMMM yyy", new Locale("ar"));
    public static SimpleDateFormat destDateFormat = new SimpleDateFormat("E d MMMM yyy", new Locale("ar"));

    public static TimeZone currentTimeZone;

    public static InterstitialAd mInterstitialAd;


    public static Long parseDateTime(String date, String time) {

        Long dateL = 0L;
        Long timeL = MyApplication.getCurretnDateTime();
        try {
            dateL = sourceDateFormat.parse(date).getTime();
            timeL = sourceTimeFormate.parse(time).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateL + timeL - getCurrentOffset();
    }

    public static String[] formatDateTime(Long dateTime) {
        dateTime += getCurrentOffset();
        String date = sourceDateFormat.format(dateTime);
        String time = sourceTimeFormate.format(dateTime);

        return new String[]{date, time};
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        currentTimeZone = TimeZone.getDefault();
        sourceTimeFormate.setTimeZone(TimeZone.getTimeZone("UTC"));
        destTimeFormate.setTimeZone(currentTimeZone);
        sourceDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        destDateFormat.setTimeZone(currentTimeZone);

        teamsLogos.put(11122, R.mipmap.t11122 );
        teamsLogos.put(11126, R.mipmap.t11126 );
        teamsLogos.put(1234, R.mipmap.t1234 );
        teamsLogos.put(1235, R.mipmap.t1235 );
        teamsLogos.put(12377, R.mipmap.t12377 );
        teamsLogos.put(1238, R.mipmap.t1238 );
        teamsLogos.put(1239, R.mipmap.t1239 );
        teamsLogos.put(1240, R.mipmap.t1240 );
        teamsLogos.put(1241, R.mipmap.t1241 );
        teamsLogos.put(1242, R.mipmap.t1242 );
        teamsLogos.put(1244, R.mipmap.t1244 );
        teamsLogos.put(1246, R.mipmap.t1246 );
        teamsLogos.put(13356, R.mipmap.t13356 );
        teamsLogos.put(14716, R.mipmap.t14716 );
        teamsLogos.put(1572, R.mipmap.t1572 );
        teamsLogos.put(16546, R.mipmap.t16546 );
        teamsLogos.put(16550, R.mipmap.t16550 );
        teamsLogos.put(16658, R.mipmap.t16658 );
        teamsLogos.put(18367, R.mipmap.t18367 );
        teamsLogos.put(18369, R.mipmap.t18369 );
        teamsLogos.put(18370, R.mipmap.t18370 );
        teamsLogos.put(19003, R.mipmap.t19003 );
        teamsLogos.put(23957, R.mipmap.t23957 );
        teamsLogos.put(24589, R.mipmap.t24589 );
        teamsLogos.put(24591, R.mipmap.t24591 );
        teamsLogos.put(27834, R.mipmap.t27834 );
        teamsLogos.put(27979, R.mipmap.t27979 );
        teamsLogos.put(28004, R.mipmap.t28004 );
        teamsLogos.put(28006, R.mipmap.t28006 );
        teamsLogos.put(28007, R.mipmap.t28007 );
        teamsLogos.put(3277, R.mipmap.t3277 );
        teamsLogos.put(3316, R.mipmap.t3316 );
        teamsLogos.put(3318, R.mipmap.t3318 );
        teamsLogos.put(3648, R.mipmap.t3648 );
        teamsLogos.put(5802, R.mipmap.t5802 );
        teamsLogos.put(60, R.mipmap.t60 );
        teamsLogos.put(61, R.mipmap.t61 );
        teamsLogos.put(62, R.mipmap.t62 );
        teamsLogos.put(63, R.mipmap.t63 );
        teamsLogos.put(64, R.mipmap.t64 );
        teamsLogos.put(65, R.mipmap.t65 );
        teamsLogos.put(66, R.mipmap.t66 );
        teamsLogos.put(67, R.mipmap.t67 );
        teamsLogos.put(68, R.mipmap.t68 );
        teamsLogos.put(69, R.mipmap.t69 );
        teamsLogos.put(70, R.mipmap.t70 );
        teamsLogos.put(71, R.mipmap.t71 );
        teamsLogos.put(72, R.mipmap.t72 );
        teamsLogos.put(73, R.mipmap.t73 );
        teamsLogos.put(74, R.mipmap.t74 );
        teamsLogos.put(75, R.mipmap.t75 );
        teamsLogos.put(7503, R.mipmap.t7503 );
        teamsLogos.put(7508, R.mipmap.t7508 );
        teamsLogos.put(76, R.mipmap.t76 );
        teamsLogos.put(77, R.mipmap.t77 );
        teamsLogos.put(78, R.mipmap.t78 );
        teamsLogos.put(79, R.mipmap.t79 );
        teamsLogos.put(923, R.mipmap.t923 );
        teamsLogos.put(924, R.mipmap.t924 );
        teamsLogos.put(930, R.mipmap.t930 );
        teamsLogos.put(932, R.mipmap.t932 );
        teamsLogos.put(933, R.mipmap.t933 );
        teamsLogos.put(9343, R.mipmap.t9343 );
        teamsLogos.put(935, R.mipmap.t935 );
        teamsLogos.put(936, R.mipmap.t936 );
        teamsLogos.put(938, R.mipmap.t938 );
        teamsLogos.put(939, R.mipmap.t939 );
        teamsLogos.put(940, R.mipmap.t940 );
        teamsLogos.put(942, R.mipmap.t942 );
        teamsLogos.put(943, R.mipmap.t943 );
        teamsLogos.put(944, R.mipmap.t944 );
        teamsLogos.put(947, R.mipmap.t947 );
        teamsLogos.put(9513, R.mipmap.t9513 );
        teamsLogos.put(9516,R.mipmap.t9516 );

        APP_CTX = getApplicationContext();
        font = Typeface.createFromAsset(APP_CTX.getAssets(), "fonts/jf_flat_regular.ttf");
        dbHelper = new DbHelper(APP_CTX);
        dbw = dbHelper.getWritableDatabase();
        dbr = dbHelper.getReadableDatabase();

        picasso = Picasso.with(this);

        Legue spani = new Legue(0L, "اخبار إسبانية","?y=es", SPANI_EXT_HOME);
        Legue firstLeague = new Legue(1L, "الدوري الإسباني الدرجة الأولى", FIRST_LEAGUE_EXT, FIRST_LEAGUE_NEWS_EXT);
        Legue kingCup = new Legue(2L, "كأس ملك إسبانيا", KING_CUP_EXT, KING_CUP_NEWS_EXT);

        spani.save();
        firstLeague.save();
        kingCup.save();

        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.APP_CTX);
        boolean b = pref.getBoolean(getString(R.string.first_league_notificatin_pref_key),true);
        pref.edit().putBoolean(getString(R.string.first_league_notificatin_pref_key),b).apply();
        notificationManager = (NotificationManager) APP_CTX.getSystemService(NOTIFICATION_SERVICE);

        alarmManager = (AlarmManager) APP_CTX.getSystemService(ALARM_SERVICE);


        monthOfTheYear.put("يناير", 1);
        monthOfTheYear.put("فبراير", 2);
        monthOfTheYear.put("مارس", 3);
        monthOfTheYear.put("أبريل", 4);
        monthOfTheYear.put("مايو", 5);
        monthOfTheYear.put("يونيو", 6);
        monthOfTheYear.put("يوليو", 7);
        monthOfTheYear.put("أغسطس", 8);
        monthOfTheYear.put("سبتمبر", 9);
        monthOfTheYear.put("أكتوبر", 10);
        monthOfTheYear.put("نوفمبر", 11);
        monthOfTheYear.put("ديسمبر", 12);

        Parse.initialize(this, "d0nQzjSHzrWqmfyEecqkIQ5zlA75sC44fhf97gfc", "zInKbjuZVrj9Zpy5ZTYR3VIJ49fMYP9s2G320k8g");
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public static void openPlayStor(String appPackageName) {
        try {
            APP_CTX.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(appPackageName)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException anfe) {
            APP_CTX.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    public static void changeTabsFont(TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(MyApplication.font);
                }
            }
        }
    }

    public static Long getCurretnDateTime(){
        Calendar rightNow = Calendar.getInstance();
        return (rightNow.getTimeInMillis());
    }

    public static Long getCurrentOffset(){
        Calendar rightNow = Calendar.getInstance();
        long offset = rightNow.get(Calendar.ZONE_OFFSET) +
                rightNow.get(Calendar.DST_OFFSET);

        return offset;
    }
}
