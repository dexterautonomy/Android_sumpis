package com.teamwurkz.chinedu.sump1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BlogPostActivity extends AppCompatActivity
{
    private DrawerLayout dl;
    private EditText tit;
    private EditText con;

    private NetClass nClass;
    private String iphost2;
    private String usx;

    private SharedPreferences sps;
    private SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogpost_activity);

        Toolbar tb=(Toolbar) findViewById(R.id.npToolx);
        setSupportActionBar(tb);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        nClass=new NetClass();
        iphost2=nClass.getIPHost(this);
        usx=nClass.getSessionUsername(this);

        sps=nClass.sps(this);
        spe=sps.edit();

        Spinner spn=(Spinner) findViewById(R.id.spn);
        spn.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getIntent().getStringArrayExtra("niche")));

        tit=(EditText) findViewById(R.id.npx1);
        con=(EditText) findViewById(R.id.npx2);

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
                        startActivity(new Intent(BlogPostActivity.this, RegisteredUsers.class));
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(BlogPostActivity.this, "Go Premium", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        if(nClass.checkNetwork(BlogPostActivity.this))
                        {
                            new BlogPostActivity.FriendAsync().execute(iphost2+"andfriend/"+sps.getString("sessionUsername", ""));
                        }
                        else
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(BlogPostActivity.this);
                            builder.setTitle("No Internet Connection");
                            builder.setMessage("Colleague list will not be updated");
                            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(BlogPostActivity.this, ComposeActivity.class));
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
                        if(nClass.checkNetwork(BlogPostActivity.this))
                        {
                            Intent intent=new Intent(BlogPostActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(BlogPostActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(BlogPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(BlogPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(BlogPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(BlogPostActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(BlogPostActivity.this, MainActivity.class));
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
        Button button=(Button) findViewById(R.id.npButx);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Spinner spn=(Spinner)findViewById(R.id.spn);

                String category=String.valueOf(spn.getSelectedItem());
                String title=tit.getText().toString();
                String cont=con.getText().toString();
                int titleCount=nClass.wordCount(title);

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
                                String urlPost= URLEncoder.encode(cont, "UTF-8");

                                if(nClass.checkNetwork(BlogPostActivity.this))
                                {
                                    if(!category.equals("--Select--"))
                                    {
                                        if(titleCount<20)
                                        {
                                            new PostBlogAsync().execute(iphost2 + "andblogpost?c=" + category.toLowerCase() + "&u=" + usx + "&t=" + urlTitle + "&q=" + urlPost);
                                        }
                                        else
                                        {
                                            Toast.makeText(BlogPostActivity.this, "Title is too lengthy", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(BlogPostActivity.this, "Please select category of article", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    nClass.popNetworkDialog(BlogPostActivity.this);
                                }
                            }
                            catch (UnsupportedEncodingException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(BlogPostActivity.this, "Content is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(BlogPostActivity.this, "Title must contain only alphanumeric characters", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(BlogPostActivity.this, "Write title of article", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class PostBlogAsync extends AsyncTask<String, Void, String>
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
                Toast.makeText(BlogPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                tit.setText("");
                con.setText("");
            }
            else if(s.equals("banned"))
            {
                Toast.makeText(BlogPostActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("blocked"))
            {
                Toast.makeText(BlogPostActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
            }
        }
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
            Intent intent=new Intent(BlogPostActivity.this, ComposeActivity.class);
            intent.putExtra("friends", s.split(", "));
            startActivity(intent);
            finish();
        }
    }
}