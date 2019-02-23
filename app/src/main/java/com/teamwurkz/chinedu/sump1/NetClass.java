package com.teamwurkz.chinedu.sump1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by DEXTER on 15/05/2018.
 */

public class NetClass   //Utility class
{
    private ConnectivityManager cmng;
    private NetworkInfo netInf;

    private AlertDialog.Builder builder;
    private AlertDialog adiag;

    private Properties prop;
    private InputStream is;

    private SharedPreferences sps;

    public String getIPHost(Context c)
    {
        try
        {
            prop=new Properties();
            is=c.getAssets().open("host.properties");
            prop.load(is);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return prop.getProperty("host");
    }

    public boolean checkNetwork(Context c)
    {
        cmng=(ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInf=cmng.getActiveNetworkInfo();

        boolean check=false;
        if(netInf!=null && netInf.isConnected())
        {
            check=true;
        }
        return check;
    }

    public void popNetworkDialog(Context c)
    {
        builder=new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please turn internet connection on");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        adiag=builder.create();
        adiag.show();
    }

    public String[] newsStream(String link)
    {
        InputStream in;
        String newString;
        String[] resList=null;

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
                newString = IOUtils.toString(in, "UTF-8");
                resList=newString.split("#");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return resList;
    }

    public String blogStream(String link)
    {
        InputStream in;
        String newString="";

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
                newString = IOUtils.toString(in, "UTF-8");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return newString;
    }

    public SharedPreferences sps(Context c)
    {
        return c.getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", c.MODE_PRIVATE);
    }

    public String getUsername(Context c)
    {
        sps=c.getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", c.MODE_PRIVATE);
        return sps.getString("username", "");
    }

    public String getSessionUsername(Context c)
    {
        sps=c.getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", c.MODE_PRIVATE);
        return sps.getString("sessionUsername", "");
    }

    public String getPassword(Context c)
    {
        sps=c.getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", c.MODE_PRIVATE);
        return sps.getString("password", "");
    }

    public String getStatus(Context c)
    {
        sps=c.getSharedPreferences("com.teamwurkz.chinedu.sump1_preferences", c.MODE_PRIVATE);
        return sps.getString("status", "");
    }

    public String responseString(String link)
    {
        InputStream in;
        String response="";

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
                response = IOUtils.toString(in, "UTF-8");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

    public String banCheck(String uNameLink)
    {
        InputStream in;
        String response="";

        try {
            URL url = new URL(uNameLink);
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
                response = IOUtils.toString(in, "UTF-8");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

    public boolean validCheck(String input)
    {
        String[] inputX=input.split("\\s+");
        for(String cx:inputX)
        {
            char[] c=cx.toCharArray();
            for(char ch:c)
            {
                if(!Character.isLetterOrDigit(ch))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String titleCase(String input)
    {
        String title="";
        String[] inputX=input.trim().split("\\s+");
        for(String cx:inputX)
        {
            cx=Character.toUpperCase(cx.charAt(0)) + cx.substring(1).toLowerCase();
            title +=cx + " ";
        }
        return title;
    }

    protected int wordCount(String content)
    {
        List<String> word=new LinkedList<>();
        BreakIterator brk=BreakIterator.getWordInstance();
        brk.setText(content);
        int lastIndex=brk.first();
        while(BreakIterator.DONE != lastIndex)
        {
            int firstIndex=lastIndex;
            lastIndex=brk.next();
            if(lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(content.charAt(firstIndex)))
            {
                word.add(content.substring(firstIndex, lastIndex));
            }
        }
        return word.size();
    }
}