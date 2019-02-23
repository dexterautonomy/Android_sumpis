package com.teamwurkz.chinedu.sump1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NewsPostActivity extends AppCompatActivity
{
    private DrawerLayout dl;
    private NetClass nClass;
    private String iphost2;
    private String usx;

    private EditText tit;
    private EditText con;

    private SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspost_activity);

        Toolbar tb=(Toolbar)findViewById(R.id.npTool);
        setSupportActionBar(tb);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        nClass=new NetClass();
        iphost2=nClass.getIPHost(this);

        spe= nClass.sps(this).edit();
        usx=nClass.getSessionUsername(this);

        tit=(EditText) findViewById(R.id.np1);
        con=(EditText) findViewById(R.id.np2);

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
                        Toast.makeText(NewsPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing2", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing3", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v4:
                    {
                        if(nClass.checkNetwork(NewsPostActivity.this))
                        {
                            Intent intent=new Intent(NewsPostActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(NewsPostActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(NewsPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(NewsPostActivity.this, MainActivity.class));
                        finish();
                        break;
                    }
                }
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        switch(mi.getItemId())
        {
            case android.R.id.home:
            {
                dl.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Button button=(Button) findViewById(R.id.npBut);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //EditText tit=(EditText) findViewById(R.id.np1);
                String title=tit.getText().toString();
                //EditText con=(EditText) findViewById(R.id.np2);
                String cont=con.getText().toString();

                if(!title.matches("\\s*"))
                {
                    if(nClass.validCheck(title))
                    {
                        if(!cont.matches("\\s*"))
                        {
                            try
                            {
                                String titlePost=nClass.titleCase(title);
                                String urlTitle=URLEncoder.encode(titlePost, "UTF-8");
                                String urlPost=URLEncoder.encode(cont, "UTF-8");

                                if(nClass.checkNetwork(NewsPostActivity.this))
                                {
                                    new PostNewsAsync().execute(iphost2+"andnewspost?u="+usx+"&t="+urlTitle+"&q="+urlPost);
                                }
                                else
                                {
                                    nClass.popNetworkDialog(NewsPostActivity.this);
                                }
                            }
                            catch (UnsupportedEncodingException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(NewsPostActivity.this, "Content is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(NewsPostActivity.this, "Title must contain only alphanumeric characters", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(NewsPostActivity.this, "Write title of post", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class PostNewsAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            //I will use one of my inputstream methods here, no need creating new ones, it's all about sending to the server right??
            return nClass.responseString(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            if(s.equals("posted"))
            {
                Toast.makeText(NewsPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                tit.setText("");
                con.setText("");
            }
        }
    }
}