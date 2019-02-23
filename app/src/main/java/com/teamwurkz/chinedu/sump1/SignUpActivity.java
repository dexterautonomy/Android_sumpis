package com.teamwurkz.chinedu.sump1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity_layout);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarsignup);
        setSupportActionBar(toolbar);
    }
}