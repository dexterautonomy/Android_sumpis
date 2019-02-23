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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ComposeActivity extends AppCompatActivity
{
    private DrawerLayout dl;
    private String[] friendList;
    private  ArrayAdapter<String> friendAdapter;

    private NetClass nClass;
    private String iphost2;
    private SharedPreferences sps;
    private SharedPreferences.Editor spe;

    private FrameLayout frm;
    private LinearLayoutCompat.LayoutParams paramx;
    private ProgressBar pb;

    private String usx;

    private MultiAutoCompleteTextView mactv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);

        Toolbar tb=(Toolbar) findViewById(R.id.toolbarC);
        setSupportActionBar(tb);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        dl=(DrawerLayout) findViewById(R.id.drawer);

        frm=(FrameLayout) findViewById(R.id.compfrm);
        paramx=new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        pb=new ProgressBar(this);
        pb.setLayoutParams(paramx);

        nClass=new NetClass();
        iphost2=nClass.getIPHost(this);
        sps=nClass.sps(this);
        spe=sps.edit();

        usx=nClass.getSessionUsername(this);


        friendList=getIntent().getStringArrayExtra("friends");
        mactv=(MultiAutoCompleteTextView) findViewById(R.id.mactv);

        if(friendList!=null)
        {
            friendAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendList);
            MultiAutoCompleteTextView mactv=(MultiAutoCompleteTextView) findViewById(R.id.mactv);
            mactv.setThreshold(1);
            mactv.setAdapter(friendAdapter);
            mactv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        }


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
                        Toast.makeText(ComposeActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing2", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing3", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v4:
                    {
                        if(nClass.checkNetwork(ComposeActivity.this))
                        {
                            Intent intent=new Intent(ComposeActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(ComposeActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(ComposeActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(ComposeActivity.this, MainActivity.class));
                        finish();
                        break;
                    }
                }
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });

        /*BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.regnav);
        switch (nClass.getStatus(this))
        {
            case "first_client":
            {
                navigation.inflateMenu(R.menu.navigationx1);
            }
            break;

            case "second_client":
            {
                navigation.inflateMenu(R.menu.navigationx2);
            }
            break;

            case "third_client":
            {
                navigation.inflateMenu(R.menu.navigationx1);
            }
            break;

            case "fourth_client":
            {
                navigation.inflateMenu(R.menu.navigationx2);
            }
            break;

        }
        navigation.setOnNavigationItemSelectedListener(new NavigationBottom());*/
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

    public void sendMessage(View view)
    {
        if(nClass.checkNetwork(this))
        {
            EditText subj=(EditText) findViewById(R.id.comptitle);
            String subject=subj.getText().toString();

            EditText comp=(EditText) findViewById(R.id.comptext);
            String compose1=comp.getText().toString();

            String mactvx=mactv.getText().toString();

            //Instead of using URLEncoder
            String mactv1;
            if(mactvx.contains(" "))
            {
                mactv1=mactvx.replaceAll("\\s+", "");
            }
            else
            {
                mactv1=mactvx;
            }

            if(!subject.matches("\\s*"))
            {
                if(!compose1.matches("\\s*"))
                {
                    if(!mactv1.matches("\\s*"))
                    {
                        //new SendAsync().execute(iphost2+"andsend?username="+usx+"&recipient="+mactv1+"&msg="+compose1+"&subject="+subject);
                        try
                        {
                            new SendAsync().execute(iphost2+"andsend?username="+usx+"&recipient="+mactv1+"&msg="+
                                    URLEncoder.encode(compose1, "UTF-8")+"&subject="+URLEncoder.encode(subject, "UTF-8"));
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Add at least a recipient", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Cannot send an empty content", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(!compose1.matches("\\s*"))
                {
                    if(!mactv1.matches("\\s*"))
                    {
                        //new SendAsync().execute(iphost2+"andsend?username="+usx+"&recipient="+mactv1+"&msg="+compose1+"&subject=No_Subject");
                        try
                        {
                            new SendAsync().execute(iphost2+"andsend?username="+usx+"&recipient="+mactv1+"&msg="+
                                    URLEncoder.encode(compose1, "UTF-8")+"&subject="+URLEncoder.encode("No subject", "UTF-8"));
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Add at least a recipient", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Cannot send an empty content", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            nClass.popNetworkDialog(this);
        }
    }

    public void addRecipient(View v) //I used xml method to add functionality to the button instead of anonymous inner class (it's called variety)
    {
        if(nClass.checkNetwork(this))
        {
            MultiAutoCompleteTextView mactv=(MultiAutoCompleteTextView) findViewById(R.id.mactv);
            String m=mactv.getText().toString();

            Intent intent=new Intent(this, ColleagueActivity.class);
            intent.putExtra("written", m);

            if(friendList==null)
            {
                friendList=new String[]{"admin"};
            }
            intent.putExtra("list", friendList);
            startActivityForResult(intent, 10);
        }
        else
        {
            nClass.popNetworkDialog(this);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==10)
        {
            if(resultCode==RESULT_OK)
            {
                mactv.setText(data.getStringExtra("selected"));
            }
        }
    }

    private String sendMessage(String link)
    {
        InputStream in;
        String sent="";
        try
        {
            URL url=new URL(link);
            URLConnection urlConnection=url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection))
            {
                throw new IOException("Not an Http Connection");
            }

            HttpURLConnection httpURLConnection=(HttpURLConnection) urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                in = httpURLConnection.getInputStream();
                sent = IOUtils.toString(in, "UTF-8");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sent;
    }

    private class SendAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            frm.removeView(pb);
            frm.addView(pb);
        }

        @Override
        protected String doInBackground(String... params)
        {
            return sendMessage(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            if(!s.equals(""))
            {
                Toast.makeText(ComposeActivity.this, "Message delivered to " + s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ComposeActivity.this, "Message was not delivered", Toast.LENGTH_SHORT).show();
            }
            pb.setVisibility(View.GONE);
        }
    }
}