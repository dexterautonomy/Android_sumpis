/*
This Class/Fragment is no longer in use, but MUST be revisited for important codes


package com.teamwurkz.chinedu.sump1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SumSubHomeFragment extends Fragment
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.sum_sub_home_fragment, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        Button btn=(Button) getActivity().findViewById(R.id.actionbutton);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Spinner spinx=(Spinner) getActivity().findViewById(R.id.optionspinner);
                String options=String.valueOf(spinx.getSelectedItem());

                switch (options)
                {
                    case "PARAPHRASE":
                    {
                        EditText edx1=(EditText) getActivity().findViewById(R.id.textinput1);
                        String input=String.valueOf(edx1.getText());
                        Intent intent=new Intent(getActivity(), OutputActivity.class);
                        intent.putExtra("input", input);
                        startActivity(intent);
                        //ftx=fmx.beginTransaction();
                        //ftx.replace(R.id.content, of);
                        //ftx.addToBackStack(null);
                        //ftx.commit();
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
                    case "--Select Action--":
                    {
                        break;
                    }
                }
            }
        });

        //DECISION TO MAKE
        Spinner spinx=(Spinner) getActivity().findViewById(R.id.optionspinner);
        spinx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item=parent.getItemAtPosition(position).toString();
                switch (item)
                {
                    case "PARAPHRASE":
                    {
                        EditText edx1=(EditText) getActivity().findViewById(R.id.textinput1);
                        String input=String.valueOf(edx1.getText());
                        Intent intent=new Intent(getActivity(), OutputActivity.class);
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
                    case "--Select Action--":
                    {
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}*/