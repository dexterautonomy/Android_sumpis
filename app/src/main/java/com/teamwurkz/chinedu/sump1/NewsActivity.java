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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity
{
    private SwipeRefreshLayout swpn;
    private String iphost3;
    private NetClass nClass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_layout);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        nClass=new NetClass();
        iphost3=nClass.getIPHost(this);

        swpn=(SwipeRefreshLayout) findViewById(R.id.srln);
        swpn.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swpn.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(nClass.checkNetwork(NewsActivity.this))
                {
                    new NewsAsync().execute(iphost3+"androidnews");
                    Toast.makeText(NewsActivity.this, "Outside", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    swpn.setRefreshing(false);
                    nClass.popNetworkDialog(NewsActivity.this);
                }
            }
        });

        ListView lv=(ListView) findViewById(R.id.nd);
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.custom_news_row, getIntent().getStringArrayExtra("newsArray") ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(nClass.checkNetwork(NewsActivity.this))
                {
                    String news=parent.getItemAtPosition(position).toString();
                    String[] newsSplit=news.split("\r\n\r\n");

                    Toast.makeText(NewsActivity.this, newsSplit[0], Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(NewsActivity.this, NewsReaderActivity.class);
                    intent.putExtra("news_Title", newsSplit[0]);
                    intent.putExtra("news_Date", newsSplit[1]);
                    startActivity(intent);
                }
                else
                {
                    nClass.popNetworkDialog(NewsActivity.this);
                }
            }
        });
    }

    private class NewsAsync extends AsyncTask<String, Void, String[]>
    {
        @Override
        protected String[] doInBackground(String... params)
        {
            return nClass.newsStream(params[0]);
        }

        @Override
        protected void onPostExecute(String[] s)
        {
            Intent intent=new Intent(NewsActivity.this, NewsActivity.class); //refreshes itself by calling itself
            intent.putExtra("newsArray", s);
            startActivity(intent);
            finish();
        }
    }
}