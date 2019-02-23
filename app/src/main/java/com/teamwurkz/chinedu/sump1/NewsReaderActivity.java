package com.teamwurkz.chinedu.sump1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class NewsReaderActivity extends AppCompatActivity
{
    private SharedPreferences sps;
    private String usx;
    private NetClass nClass;
    private String iphost4;

    private SwipeRefreshLayout swipeWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_reader_activity);

        nClass=new NetClass();
        iphost4=nClass.getIPHost(this);

        sps=getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", MODE_PRIVATE);
        usx=sps.getString("sessionUsername", "");

        final WebView webView=(WebView) findViewById(R.id.wbv);
        webView.setWebViewClient(new WebViewClient());
        WebSettings wbs=webView.getSettings();
        wbs.setJavaScriptEnabled(true);
        wbs.setBuiltInZoomControls(true);
        wbs.setDisplayZoomControls(false);

        //wbs.setTextZoom(150);
        //wbs.setAppCacheEnabled(false);
        //wbs.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //URLEncoder not needed here because this acts as a webpage loading a url, so all spaces and others are being taken care of.
        webView.loadUrl(iphost4+"andwx?news="+getIntent().getStringExtra("news_Title")
        +"&date="+getIntent().getStringExtra("news_Date")+"&id=1&pgn=1&username="+usx);

        swipeWeb=(SwipeRefreshLayout) findViewById(R.id.wbvswipe);
        swipeWeb.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeWeb.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(nClass.checkNetwork(NewsReaderActivity.this))
                {
                    Intent intent=new Intent(NewsReaderActivity.this, NewsReaderActivity.class);
                    intent.putExtra("news_Title", getIntent().getStringExtra("news_Title"));
                    intent.putExtra("news_Date", getIntent().getStringExtra("news_Date"));
                    startActivity(intent);
                    finish();
                }
                else
                {
                    swipeWeb.setRefreshing(false);
                    nClass.popNetworkDialog(NewsReaderActivity.this);
                }
            }
        });
    }
}