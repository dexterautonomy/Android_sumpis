package com.teamwurkz.chinedu.sump1;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity
{
    private String usx;
    private String psx;

    private EditText ed1;
    private EditText ed2;
    private CheckBox chbx;
    private boolean cbx;

    private SharedPreferences.Editor spe;

    private FrameLayout el1;
    private LinearLayoutCompat.LayoutParams paramx;
    private ProgressBar pb;

    private String iphost;
    private NetClass nClass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nClass=new NetClass();
        iphost=nClass.getIPHost(this);

        el1=(FrameLayout) findViewById(R.id.conpx);
        paramx=new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        pb=new ProgressBar(this);
        pb.setLayoutParams(paramx);

        //sps=getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", MODE_PRIVATE);
        //spe=sps.edit();
        spe=nClass.sps(this).edit();


        //usx=sps.getString("username", "");
        //psx=sps.getString("password", "");

        usx=nClass.getUsername(this);
        psx=nClass.getPassword(this);

        if(nClass.checkNetwork(this))
        {
            if(!usx.matches("\\s*") && !psx.matches("\\s*"))
            {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 77);
                }
                else
                {
                    new AutomaticLoginAsync().execute(iphost+"extlogin/"+usx+"/"+psx);
                }
            }
        }
        else
        {
            nClass.popNetworkDialog(this);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        TextView tv=(TextView) findViewById(R.id.signuplab);
        tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        Button login=(Button) findViewById(R.id.loginBut);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ed1=(EditText) findViewById(R.id.username);
                usx=ed1.getText().toString().trim();
                ed2=(EditText) findViewById(R.id.password);
                psx=ed2.getText().toString();
                chbx=(CheckBox) findViewById(R.id.checkbox1);
                cbx=chbx.isChecked();

                if(!usx.matches("\\s*"))
                {
                    if(!psx.matches("\\s*"))
                    {
                        if(nClass.checkNetwork(MainActivity.this))
                        {
                            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 123);
                            }
                            else
                            {
                                new ManualLoginAsync().execute(iphost+"extlogin/"+usx+"/"+psx);
                            }
                        }
                        else
                        {
                            nClass.popNetworkDialog(MainActivity.this);
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String loginStream(String link)
    {
        InputStream in;
        String res="";

        try {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            if (!(urlConnection instanceof HttpURLConnection)) {
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
                res = IOUtils.toString(in, Charsets.toCharset("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m)
    {
        getMenuInflater().inflate(R.menu.menu_main, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        switch(mi.getItemId())
        {
            case R.id.k1:
            {
                if(nClass.checkNetwork(this))
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 47);
                    }
                    else
                    {
                        new NewsAsync().execute(iphost + "androidnews");
                        Toast.makeText(this, "Outside", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    nClass.popNetworkDialog(this);
                }
                return true;
            }
            case R.id.k2:
            {
                View v=findViewById(R.id.k2);
                PopupMenu pm=new PopupMenu(this, v);
                getMenuInflater().inflate(R.menu.popup, pm.getMenu());
                pm.show();
                pm.setOnMenuItemClickListener(new BlogItemClick());
                return true;
            }
            case R.id.k3:
            {
                return true;
            }
            case R.id.k4:
            {
                return true;
            }
        }
        return false;
    }

    //Private Inner Class for handling popupmenu item click, I didnt want to use anonymous inner class so I created a private inner class
    private class BlogItemClick implements PopupMenu.OnMenuItemClickListener
    {
        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            if(nClass.checkNetwork(MainActivity.this))
            {
                switch (item.getItemId())
                {
                    case R.id.axx:
                    {
                        Toast.makeText(MainActivity.this, "Creative Writing", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Writings/writing");
                    }
                    break;
                    case R.id.bxx:
                    {
                        Toast.makeText(MainActivity.this, "Art and History", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Art_History/arts");
                    }
                    break;
                    case R.id.cxx:
                    {
                        Toast.makeText(MainActivity.this, "Health tips", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Health/health");
                    }
                    break;
                    case R.id.dxx:
                    {
                        Toast.makeText(MainActivity.this, "Do It Yourself Tip", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/DIY/howtodo");
                    }
                    break;
                    case R.id.exx:
                    {
                        Toast.makeText(MainActivity.this, "Let's Entertain You", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Entertainment/entertainment");
                    }
                    break;
                    case R.id.fxx:
                    {
                        Toast.makeText(MainActivity.this, "Religion and Politics", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Religion_Politics/religionpolitics");
                    }
                    break;
                    case R.id.gxx:
                    {
                        Toast.makeText(MainActivity.this, "Information is Power", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Information/information");
                    }
                    break;
                    case R.id.hxx:
                    {
                        Toast.makeText(MainActivity.this, "Lifestyles and Trends", Toast.LENGTH_SHORT).show();
                        new BlogAsync().execute(iphost+"androidblog/Lifestyle/flt");
                    }
                    break;
                }
            }
            else
            {
                nClass.popNetworkDialog(MainActivity.this);
            }
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 77:
            {
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    new AutomaticLoginAsync().execute(iphost+"extlogin/"+usx+"/"+psx);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 123:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    new ManualLoginAsync().execute(iphost+"extlogin/"+usx+"/"+psx);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Manual login
    private class ManualLoginAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            el1.removeView(pb);
            el1.addView(pb);
        }

        @Override
        protected String doInBackground(String... params)
        {
            return loginStream(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            switch(s)
            {
                case "uname_err":
                {
                    Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "pswd_err":
                {
                    Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "login_err":
                {
                    Toast.makeText(MainActivity.this, "You have been suspended", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "s_s": //Sumblogger + Subscription
                {
                    if(cbx)
                    {
                        spe.putString("username", usx);
                        spe.putString("password", psx);
                    }
                    spe.putString("sessionUsername", usx);
                    spe.putString("status", "first_client");
                    spe.commit();
                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, "sumblogger and subscription is alive", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "n_s": //Not sumblogger + Subscription
                {
                    if(cbx)
                    {
                        spe.putString("username", usx);
                        spe.putString("password", psx);
                    }
                    spe.putString("sessionUsername", usx);
                    spe.putString("status", "second_client");
                    spe.commit();

                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, "not a sumblogger and subscription alive", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "s_n": //Sumblogger + No subsription
                {
                    if(cbx)
                    {
                        spe.putString("username", usx);
                        spe.putString("password", psx);
                    }
                    spe.putString("sessionUsername", usx);
                    spe.putString("status", "third_client");
                    spe.commit();

                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, "sumblogger and subscription has expired", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "n_n": //Nothing nothing
                {
                    if(cbx)
                    {
                        spe.putString("username", usx);
                        spe.putString("password", psx);
                    }
                    spe.putString("sessionUsername", usx);
                    spe.putString("status", "fourth_client");
                    spe.commit();

                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, "ordinary registered user: NOT sumblogger, NO subscription ", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            pb.setVisibility(View.GONE);
        }
    }

    //For SharePreferences/Automatic login
    private class AutomaticLoginAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            el1.removeView(pb);
            el1.addView(pb);
        }

        @Override
        protected String doInBackground(String... params)
        {
            return loginStream(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            switch(s)
            {
                case "uname_err":
                {
                    Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                    spe.remove("username");
                    spe.remove("password");
                    spe.remove("status");
                    spe.remove("sessionUsername");
                    spe.commit();
                    break;
                }

                case "pswd_err":
                {
                    Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    spe.remove("username");
                    spe.remove("password");
                    spe.remove("status");
                    spe.remove("sessionUsername");
                    spe.commit();
                    break;
                }

                case "login_err":
                {
                    Toast.makeText(MainActivity.this, "You have been suspended", Toast.LENGTH_SHORT).show();
                    spe.remove("username");
                    spe.remove("password");
                    spe.remove("status");
                    spe.remove("sessionUsername");
                    spe.commit();
                    break;
                }

                case "s_s":
                {
                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, usx+" sumblogger and subscription is alive", Toast.LENGTH_LONG).show();
                    break;
                }

                case "n_s":
                {
                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, usx+" not a sumblogger and subscription alive", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "s_n":
                {
                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, usx+" sumblogger and subscription has expired", Toast.LENGTH_SHORT).show();
                    break;
                }

                case "n_n":
                {
                    startActivity(new Intent(MainActivity.this, RegisteredUsers.class));
                    finish();
                    Toast.makeText(MainActivity.this, usx+" ordinary registered user: NOT sumblogger, NO subscription ", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            pb.setVisibility(View.GONE);
        }
    }

    //Getting news list
    private class NewsAsync extends AsyncTask<String, Void, String[]>
    {
        @Override
        protected void onPreExecute()
        {
            el1.removeView(pb);
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
            Intent intent=new Intent(MainActivity.this, NewsActivity.class); //call NewsActivity here
            intent.putExtra("newsArray", s);
            startActivity(intent);
            pb.setVisibility(View.GONE);
        }
    }

    //Getting blog list
    private class BlogAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            el1.removeView(pb);
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
            String[] input=s.split("<");  //splits in order to get blog title, eg, writing, DIY etc. It is at array index[0]
            String[] blogarray=input[1].split("#"); //gets the blog and date

            switch(input[0])
            {
                case "Writings":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "writing");
                    startActivity(intent);
                    break;
                }
                case "Art_History":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "arts");
                    startActivity(intent);
                    break;
                }
                case "Health":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "health");
                    startActivity(intent);
                    break;
                }
                case "DIY":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "howtodo");
                    startActivity(intent);
                    break;
                }
                case "Entertainment":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "entertainment");
                    startActivity(intent);
                    break;
                }
                case "Religion_Politics":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "religionpolitics");
                    startActivity(intent);
                    break;
                }
                case "Information":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
                    intent.putExtra("blogArray", blogarray);
                    intent.putExtra("title", input[0]);
                    intent.putExtra("table", "information");
                    startActivity(intent);
                    break;
                }
                case "Lifestyle":
                {
                    Intent intent=new Intent(MainActivity.this, BlogActivity.class); //call NewsActivity here
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
}