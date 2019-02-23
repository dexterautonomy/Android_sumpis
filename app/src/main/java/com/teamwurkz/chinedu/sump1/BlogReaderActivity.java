package com.teamwurkz.chinedu.sump1;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class BlogReaderActivity extends AppCompatActivity
{
    private String usx;

    private NetClass nClass;
    private String iphost6;

    private SwipeRefreshLayout swipeBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_reader_activity);
        setTitle(getIntent().getStringExtra("activity_header"));

        nClass=new NetClass();
        iphost6=nClass.getIPHost(this);

        //usx=sps.getString("sessionUsername", "");
        usx=nClass.getSessionUsername(this);

        final WebView webViewBlog=(WebView) findViewById(R.id.wbvblog);
        webViewBlog.setWebViewClient(new WebViewClient());
        WebSettings wbs=webViewBlog.getSettings();
        wbs.setJavaScriptEnabled(true);
        wbs.setBuiltInZoomControls(true);
        wbs.setDisplayZoomControls(false);


        webViewBlog.loadUrl(iphost6+"andblog?blog="+getIntent().getStringExtra("blog_Title")
        +"&date="+getIntent().getStringExtra("blog_Date")+"&id=1&pgn=1&username="+usx+"&table="+getIntent().getStringExtra("blog_Table"));

        swipeBlog=(SwipeRefreshLayout) findViewById(R.id.swipeblog);
        swipeBlog.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swipeBlog.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(nClass.checkNetwork(BlogReaderActivity.this))
                {
                    Intent intent=new Intent(BlogReaderActivity.this, BlogReaderActivity.class);
                    intent.putExtra("blog_Title", getIntent().getStringExtra("blog_Title"));
                    intent.putExtra("blog_Date", getIntent().getStringExtra("blog_Date"));
                    intent.putExtra("blog_Table", getIntent().getStringExtra("blog_Table"));
                    startActivity(intent);
                    finish();
                }
                else
                {
                    swipeBlog.setRefreshing(false);
                    nClass.popNetworkDialog(BlogReaderActivity.this);
                }
            }
        });
    }
}