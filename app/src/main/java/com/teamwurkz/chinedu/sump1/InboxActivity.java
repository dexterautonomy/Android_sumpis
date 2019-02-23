package com.teamwurkz.chinedu.sump1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class InboxActivity extends AppCompatActivity
{
    private DrawerLayout dl;

    private NetClass nClass;
    private SharedPreferences.Editor spe;
    private String iphost2;

    private WebView wbv;
    private WebSettings wbs;
    private SwipeRefreshLayout swp;

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        Toolbar tb=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        nClass=new NetClass();
        iphost2=nClass.getIPHost(this);
        spe=nClass.sps(this).edit();

        wbv=(WebView) findViewById(R.id.wbv);
        wbv.setWebViewClient(new WebViewClient());
        wbs=wbv.getSettings();
        wbs.setJavaScriptEnabled(true);
        wbs.setBuiltInZoomControls(true);
        wbs.setDisplayZoomControls(false);

        wbs.setAppCacheEnabled(false);
        wbs.setCacheMode(WebSettings.LOAD_NO_CACHE);

        wbv.loadUrl(iphost2+"andinbox/"+nClass.getSessionUsername(this)+"/"+ getIntent().getIntExtra("page", 1));

        swp=(SwipeRefreshLayout) findViewById(R.id.swp);
        swp.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(nClass.checkNetwork(InboxActivity.this))
                {
                    //pp
                    startActivity(new Intent(InboxActivity.this, InboxActivity.class));
                    finish();
                }
                else
                {
                    swp.setRefreshing(false);
                    nClass.popNetworkDialog(InboxActivity.this);
                }
            }
        });


        dl=(DrawerLayout) findViewById(R.id.drawer);
        NavigationView nv=(NavigationView) findViewById(R.id.navview);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.v1:
                    {
                        startActivity(new Intent(InboxActivity.this, RegisteredUsers.class));
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(InboxActivity.this, "Go Premium", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        if(nClass.checkNetwork(InboxActivity.this))
                        {
                            new FriendAsync().execute(iphost2+"andfriend/"+nClass.getSessionUsername(InboxActivity.this));
                        }
                        else
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(InboxActivity.this);
                            builder.setTitle("No Internet Connection");
                            builder.setMessage("Colleague list will not be updated");
                            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(InboxActivity.this, ComposeActivity.class));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog adiag=builder.create();
                            adiag.show();
                        }
                    }
                    break;
                    case R.id.v4:
                    {
                        if(nClass.checkNetwork(InboxActivity.this))
                        {
                            Intent intent=new Intent(InboxActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(InboxActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(InboxActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(InboxActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(InboxActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(InboxActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v9:
                    {
                        spe.remove("username");
                        spe.remove("password");
                        spe.remove("sessionUsername");
                        spe.remove("status");
                        spe.commit();
                        startActivity(new Intent(InboxActivity.this, MainActivity.class));
                        finish();
                        break;
                    }
                }
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });

        Button btn1=(Button) findViewById(R.id.next);
        btn1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int pg=getIntent().getIntExtra("page", 1);
                Intent intent=new Intent(InboxActivity.this, InboxActivity.class);
                intent.putExtra("page", ++pg);
                startActivity(intent);
                finish();
            }
        });

        Button btn2=(Button) findViewById(R.id.prev);
        btn2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int pg=getIntent().getIntExtra("page", 1);
                if(pg==1)
                {
                    Toast.makeText(InboxActivity.this, "End", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(InboxActivity.this, InboxActivity.class);
                    intent.putExtra("page", --pg);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                dl.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return false;
    }

    private class FriendAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return nClass.blogStream(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            Intent intent=new Intent(InboxActivity.this, ComposeActivity.class);
            intent.putExtra("friends", s.split(", "));
            startActivity(intent);
            finish();
        }
    }
}