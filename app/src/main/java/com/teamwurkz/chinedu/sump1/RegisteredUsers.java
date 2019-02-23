package com.teamwurkz.chinedu.sump1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RegisteredUsers extends AppCompatActivity
{
    private FrameLayout el1;
    private LinearLayout clx;
    private LinearLayoutCompat.LayoutParams paramx;
    private ProgressBar pb;

    private DrawerLayout dl;
    private NetClass nClass;
    private String iphost2;

    private SharedPreferences sps;
    private SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_users_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        el1=(FrameLayout) findViewById(R.id.content);

        nClass=new NetClass();
        iphost2=nClass.getIPHost(this);

        sps=getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", MODE_PRIVATE);
        spe=sps.edit();

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
                        startActivity(new Intent(RegisteredUsers.this, RegisteredUsers.class));
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(RegisteredUsers.this, "Testing2", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.v3:
                    {
                        if(nClass.checkNetwork(RegisteredUsers.this))
                        {
                            new FriendAsync().execute(iphost2+"andfriend/"+sps.getString("sessionUsername", ""));
                        }
                        else
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(RegisteredUsers.this);
                            builder.setTitle("No Internet Connection");
                            builder.setMessage("Colleague list will not be updated");
                            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(RegisteredUsers.this, ComposeActivity.class));
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
                        if(nClass.checkNetwork(RegisteredUsers.this))
                        {
                            Intent intent=new Intent(RegisteredUsers.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                        }
                        else
                        {
                            nClass.popNetworkDialog(RegisteredUsers.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(RegisteredUsers.this, "Testing1", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(RegisteredUsers.this, "Testing1", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(RegisteredUsers.this, "Testing1", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(RegisteredUsers.this, "Testing1", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.v9:
                    {
                        spe.remove("username");
                        spe.remove("password");
                        spe.remove("sessionUsername");
                        spe.remove("status");
                        spe.commit();
                        startActivity(new Intent(RegisteredUsers.this, MainActivity.class));
                        finish();
                        break;
                    }
                }
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });

        clx=(LinearLayout) findViewById(R.id.consum);
        //clx.setBackgroundColor(Color.rgb(242, 250, 242));

        paramx=new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.regnav);
        switch (sps.getString("status", ""))
        {
            case "first_client":
            {
                navigation.inflateMenu(R.menu.navigation1);
            }
            break;

            case "second_client":
            {
                navigation.inflateMenu(R.menu.navigation2);
            }
            break;

            case "third_client":
            {
                navigation.inflateMenu(R.menu.navigation1);
            }
            break;

            case "fourth_client":
            {
                navigation.inflateMenu(R.menu.navigation2);
            }
            break;

        }
        navigation.setOnNavigationItemSelectedListener(new NavigationBottom());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Button btn=(Button) findViewById(R.id.actionbutton);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Spinner spinx=(Spinner) findViewById(R.id.optionspinner);
                String options=String.valueOf(spinx.getSelectedItem());

                switch (options)
                {
                    case "PARAPHRASE":
                    {
                        EditText edx1=(EditText) findViewById(R.id.textinput1);
                        String input=String.valueOf(edx1.getText());
                        Intent intent=new Intent(RegisteredUsers.this, OutputActivity.class);
                        intent.putExtra("input", input);
                        startActivity(intent);
                        break;
                    }
                    case "STREAM-SUMMARY":
                    {
                        break;
                    }
                    case "SENTENCE COUNT":
                    {
                        break;
                    }
                    case "WORD COUNT":
                    {
                        break;
                    }
                    case "PARA-SUMMARY":
                    {
                        break;
                    }
                    case "REARRANGE":
                    {
                        break;
                    }
                    case "--select--":
                    {
                        Toast.makeText(RegisteredUsers.this, "Click --select-- to pick action", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m)
    {
        getMenuInflater().inflate(R.menu.menu_sumsub, m);
        return true;
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

            case R.id.x1:
            {
                if(ContextCompat.checkSelfPermission(RegisteredUsers.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(RegisteredUsers.this, new String[]{Manifest.permission.INTERNET}, 47);
                }
                else
                {
                    new NewsAsyncReg().execute(iphost2+"androidnews");
                    Toast.makeText(this, "Registered user wants to read news", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            case R.id.x2:
            {
                View v=findViewById(R.id.x2);
                PopupMenu pm=new PopupMenu(this, v);
                getMenuInflater().inflate(R.menu.popup, pm.getMenu());
                pm.show();
                pm.setOnMenuItemClickListener(new BlogItemClickReg());
                return true;
            }
            /*
            case R.id.x3:
            {
                return true;
            }
            case R.id.x4:
            {
                return true;
            }
            case R.id.x5:
            {
                return true;
            }
            case R.id.x6:
            {
                return true;
            }
            case R.id.x7:
            {
                return true;
            }
            case R.id.x8:
            {
                //SharedPreferences spf=getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", MODE_PRIVATE);
                //SharedPreferences.Editor spe=spf.edit();

                spe.remove("username");
                spe.remove("password");
                spe.remove("sessionUsername");
                spe.remove("status");

                spe.commit();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }*/
        }
        return false;
    }

    private class NavigationBottom implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(RegisteredUsers.this, RegisteredUsers.class));
                    finish();
                    return true;
                case R.id.compose:
                {
                    if(nClass.checkNetwork(RegisteredUsers.this))
                    {
                        new FriendAsync().execute(iphost2+"andfriend/"+sps.getString("sessionUsername", ""));
                        return true;
                    }
                    else
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(RegisteredUsers.this);
                        builder.setTitle("No Internet Connection");
                        builder.setMessage("Colleague list will not be updated");
                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(new Intent(RegisteredUsers.this, ComposeActivity.class));
                                dialog.dismiss();
                            }
                        });
                        AlertDialog adiag=builder.create();
                        adiag.show();
                        return true;
                    }
                }
                case R.id.postnews:
                {
                    if(nClass.checkNetwork(RegisteredUsers.this))
                    {
                        new BanAsync().execute(iphost2+"banCheck/"+sps.getString("sessionUsername", ""));
                    }
                    else
                    {
                        nClass.popNetworkDialog(RegisteredUsers.this);
                    }
                    return true;
                }
                case R.id.sumblog:
                    if(nClass.checkNetwork(RegisteredUsers.this))
                    {
                        new SumblogAsync().execute(iphost2+"sumblogCheck/"+sps.getString("sessionUsername", ""));
                    }
                    else
                    {
                        nClass.popNetworkDialog(RegisteredUsers.this);
                    }
                    return true;
            }
            return false;
        }
    }

    /*public void doDismiss()
    {
        startActivity(new Intent(RegisteredUsers.this, ComposeActivity.class));
    }*/

    //Private Inner Class for handling popupmenu item click, I didnt want to use anonymous inner class so I created a private inner class
    private class BlogItemClickReg implements PopupMenu.OnMenuItemClickListener
    {
        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.axx:
                {
                    Toast.makeText(RegisteredUsers.this, "Creative Writing", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Writings/writing");
                    return true;
                }
                case R.id.bxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Art and History", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Art_History/arts");
                    return true;
                }
                case R.id.cxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Health tips", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Health/health");
                    return true;
                }
                case R.id.dxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Do It Yourself Tip", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/DIY/howtodo");
                    return true;
                }
                case R.id.exx:
                {
                    Toast.makeText(RegisteredUsers.this, "Let's Entertain You", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Entertainment/entertainment");
                    return true;
                }
                case R.id.fxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Religion and Politics", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Religion_Politics/religionpolitics");
                    return true;
                }
                case R.id.gxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Information is Power", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Information/information");
                    return true;
                }
                case R.id.hxx:
                {
                    Toast.makeText(RegisteredUsers.this, "Lifestyles and Trends", Toast.LENGTH_SHORT).show();
                    new BlogAsyncReg().execute(iphost2+"androidblog/Lifestyle/flt");
                    return true;
                }
            }
            return false;
        }
    }

    //Getting news list for Registered users
    private class NewsAsyncReg extends AsyncTask<String, Void, String[]>
    {
        @Override
        protected void onPreExecute()
        {
            pb=new ProgressBar(RegisteredUsers.this);
            pb.setLayoutParams(paramx);
            el1.addView(pb);
        }

        @Override
        protected String[] doInBackground(String... params)
        {
            return nClass.newsStream(params[0]);
        }

        @Override
        protected void onPostExecute(String[] s)
        {
            Intent intent=new Intent(RegisteredUsers.this, NewsActivity.class); //call NewsActivity here
            intent.putExtra("newsArray", s);
            startActivity(intent);
            pb.setVisibility(View.GONE);
        }
    }

    //Getting blog list for Registered users
    private class BlogAsyncReg extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            pb=new ProgressBar(RegisteredUsers.this);
            pb.setLayoutParams(paramx);
            el1.addView(pb);
        }

        @Override
        protected String doInBackground(String... params)
        {
            return nClass.blogStream(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            String[] input=s.split("<");
            String[] blogarray=input[1].split("#");

            switch(input[0])
            {
                case "Writings":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "writing");
                    startActivity(intent);
                    break;
                }
                case "Art_History":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "arts");
                    startActivity(intent);
                    break;
                }
                case "Health":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "health");
                    startActivity(intent);
                    break;
                }
                case "DIY":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "howtodo");
                    startActivity(intent);
                    break;
                }
                case "Entertainment":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "entertainment");
                    startActivity(intent);
                    break;
                }
                case "Religion_Politics":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "religionpolitics");
                    startActivity(intent);
                    break;
                }
                case "Information":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "information");
                    startActivity(intent);
                    break;
                }
                case "Lifestyle":
                {
                    Intent intent=new Intent(RegisteredUsers.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "flt");
                    startActivity(intent);
                    break;
                }
            }
            pb.setVisibility(View.GONE);
        }
    }

    /*private String friendStream(String link) //same as blogstream @NetClass, reconsider reducing your codes
    {
        InputStream in;
        String newString="";
        try
        {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            if (!(urlConnection instanceof HttpURLConnection))
            {
                throw new IOException("Not an Http Connection");
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                in = httpURLConnection.getInputStream();
                newString = IOUtils.toString(in, "UTF-8");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return newString;
    }*/

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
            Intent intent=new Intent(RegisteredUsers.this, ComposeActivity.class);
            intent.putExtra("friends", s.split(", "));
            startActivity(intent);
            Toast.makeText(RegisteredUsers.this, "from FriendAsync", Toast.LENGTH_SHORT).show();
        }
    }

    private class BanAsync extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... params)
        {
            return nClass.banCheck(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            switch (s)
            {
                case "clean":
                {
                    startActivity(new Intent(RegisteredUsers.this, NewsPostActivity.class));
                }
                break;

                case "banned":
                {
                    Toast.makeText(RegisteredUsers.this, "Please try again later", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private class SumblogAsync extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... params)
        {
            return nClass.banCheck(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            if(!s.equals(""))
            {
                String[] niche=s.trim().split("\\s+");
                Intent intent=new Intent(RegisteredUsers.this, BlogPostActivity.class);
                intent.putExtra("niche", niche);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(RegisteredUsers.this, "Category unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}