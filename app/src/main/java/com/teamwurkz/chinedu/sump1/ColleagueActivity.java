package com.teamwurkz.chinedu.sump1;

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
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColleagueActivity extends AppCompatActivity
{
    private DrawerLayout dl;
    private String[] friendList;
    private ArrayAdapter<String> ad;
    private String written;

    private NetClass nClass;

    private String ipHostx;
    private String usx;
    private SharedPreferences sps;
    private SharedPreferences.Editor spe;

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colleague_activity);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarCo);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        nClass=new NetClass();
        ipHostx=nClass.getIPHost(this);

        sps=nClass.sps(this);
        spe=sps.edit();

        usx=nClass.getSessionUsername(this);

        written=getIntent().getStringExtra("written");
        friendList=getIntent().getStringArrayExtra("list");
        ad=new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, friendList);

        lv=(ListView) findViewById(R.id.collg);
        lv.setAdapter(ad);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.regnavx);
        navigation.inflateMenu(R.menu.colleaguemenu);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case (R.id.k1x):
                    {
                        addColl();
                        return true;
                    }

                    case (R.id.k2x):
                    {
                        removeColl();
                        return true;
                    }
                }
                return false;
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
                        Toast.makeText(ColleagueActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing2", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing3", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v4:
                    {
                        if(nClass.checkNetwork(ColleagueActivity.this))
                        {
                            Intent intent=new Intent(ColleagueActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(ColleagueActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(ColleagueActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(ColleagueActivity.this, MainActivity.class));
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

    public void addColl()
    {
        int len=lv.getCount();
        SparseBooleanArray sba=lv.getCheckedItemPositions();
        for(int i=0; i<len; i++)
        {
            if(sba.get(i))
            {
                String checked=(String)lv.getAdapter().getItem(i);

                if(written.equals(""))
                {
                    written +=checked+", ";
                }
                else if(!written.contains(checked))
                {
                    if(written.endsWith(", "))
                    {
                        written +=checked+", ";
                    }
                    else
                    {
                        written +=", "+checked+", ";
                    }
                }
            }
        }
        Intent intent=new Intent();
        intent.putExtra("selected", written);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void removeColl()
    {
        String colleague="";
        int len=lv.getCount();
        SparseBooleanArray sba=lv.getCheckedItemPositions();
        for(int i=0; i<len; i++)
        {
            if(sba.get(i))
            {
                String checked=(String)lv.getAdapter().getItem(i);
                colleague +=checked+ ",";
            }
        }
        new RemoveAsync().execute(ipHostx+"removeColleague?username="+usx+"&colleague="+colleague);
    }

    private class RemoveAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return nClass.responseString(params[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            if(s.equals(""))
            {
                String[] frd=s.split(" ");

                List<String> list= Arrays.asList(friendList);
                ArrayList<String> al=new ArrayList<>(list);

                for(int x=0; x<frd.length; x++)
                {
                    al.remove(frd[x]);
                }

                String[] newList=al.toArray(new String[al.size()]);
                Intent intent=new Intent(ColleagueActivity.this, ColleagueActivity.class);
                intent.putExtra("list", newList);
                startActivity(intent);
                finish();
                Toast.makeText(ColleagueActivity.this, "Successfully removed "+s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ColleagueActivity.this, "No colleague(s) selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}