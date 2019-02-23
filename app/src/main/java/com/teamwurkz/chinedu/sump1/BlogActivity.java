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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BlogActivity extends AppCompatActivity
{
    private SwipeRefreshLayout swp;
    private NetClass nClass;
    private String iphost5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity);
        setTitle(getIntent().getStringExtra("title")); //takes title from previous activity in order to name the ToolBar by that

        nClass=new NetClass();
        iphost5=nClass.getIPHost(this);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarBlog);
        setSupportActionBar(toolbar);

        swp=(SwipeRefreshLayout) findViewById(R.id.srl);
        swp.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(nClass.checkNetwork(BlogActivity.this))
                {
                    new BlogAsync().execute(iphost5+"androidblog/"+getIntent().getStringExtra("title")+"/"+getIntent().getStringExtra("table")); //Blogs need only title of blog and table to access blog database
                    Toast.makeText(BlogActivity.this, getIntent().getStringExtra("title"), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    swp.setRefreshing(false);
                    nClass.popNetworkDialog(BlogActivity.this);
                }
            }
        });

        ListView lv=(ListView) findViewById(R.id.bd);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_news_row, getIntent().getStringArrayExtra("blogArray")));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(nClass.checkNetwork(BlogActivity.this))
                {
                    String blog=parent.getItemAtPosition(position).toString();
                    String[] blogSplit=blog.split("\r\n\r\n");
                    Intent intent=new Intent(BlogActivity.this, BlogReaderActivity.class);
                    intent.putExtra("activity_header", getIntent().getStringExtra("title"));
                    intent.putExtra("blog_Title", blogSplit[0]);
                    intent.putExtra("blog_Date", blogSplit[1]);
                    intent.putExtra("blog_Table", getIntent().getStringExtra("table"));
                    startActivity(intent);
                    Toast.makeText(BlogActivity.this, blog, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    nClass.popNetworkDialog(BlogActivity.this);
                }
            }
        });
    }

    private class BlogAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return nClass.blogStream(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            String[] input=s.split("<#>#<]");
            String[] blogarray=input[1].split("<_>_<#");

            switch(input[0])
            {
                case "Writings": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "writing");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Art_History": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "arts");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Health": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "health");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "DIY": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "howtodo");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Entertainment": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "entertainment");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Religion_Politics": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "religionpolitics");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Information": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "information");
                    startActivity(intent);
                    finish();
                    break;
                }
                case "Lifestyle": {
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "flt");
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }
    }
}