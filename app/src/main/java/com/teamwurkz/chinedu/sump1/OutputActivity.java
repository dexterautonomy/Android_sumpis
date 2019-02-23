package com.teamwurkz.chinedu.sump1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class OutputActivity extends AppCompatActivity
{
    private DrawerLayout dl;
    private NetClass nClass;
    private SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_activity);

        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        nClass=new NetClass();
        spe=nClass.sps(this).edit();

        EditText edx2=(EditText) findViewById(R.id.textinput2);
        edx2.setText(getIntent().getStringExtra("input"));

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
                        Toast.makeText(OutputActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v2:
                    {
                        Toast.makeText(OutputActivity.this, "Testing2", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v3:
                    {
                        Toast.makeText(OutputActivity.this, "Testing3", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v4:
                    {
                        if(nClass.checkNetwork(OutputActivity.this))
                        {
                            Intent intent=new Intent(OutputActivity.this, InboxActivity.class);
                            intent.putExtra("page", 1);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            nClass.popNetworkDialog(OutputActivity.this);
                        }
                        break;
                    }
                    case R.id.v5:
                    {
                        Toast.makeText(OutputActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v6:
                    {
                        Toast.makeText(OutputActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v7:
                    {
                        Toast.makeText(OutputActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                    case R.id.v8:
                    {
                        Toast.makeText(OutputActivity.this, "Testing1", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(OutputActivity.this, MainActivity.class));
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
}