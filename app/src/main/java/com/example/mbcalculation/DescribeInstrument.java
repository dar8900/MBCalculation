package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DescribeInstrument extends AppCompatActivity
{
    private boolean Dbg = true;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";
    private String CalcoloScelto, NomeMansione, DescrizioneMansione;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_strumento);
        Intent fromMansione = getIntent();
        Bundle bundle = fromMansione.getBundleExtra("calcolo+mansione");
        CalcoloScelto = bundle.getString("calcolo", "MB");
        NomeMansione = bundle.getString("nome_mansione", "rand");
        DescrizioneMansione = bundle.getString("descrizione_mansione", "something");
        Log.i(TAG, "On Create DescribeInstrument");
        if(Dbg)
        {
            Log.i(DBG, "Calcolo scelto: " + CalcoloScelto);
            Log.i(DBG, "Nome mansione: " + NomeMansione);
            Log.i(DBG, "Descrizione mansione: " + DescrizioneMansione);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "On Start DescribeInstrument");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.i(TAG, "On Resume DescribeInstrument");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Log.i(TAG, "On Pause DescribeInstrument");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Log.i(TAG, "On Destroy DescribeInstrument");
    }

    public void goBack(View v)
    {
        Intent toDescriviMansione = new Intent(DescribeInstrument.this, DescribeMansion.class);
        startActivity(toDescriviMansione);
        finish();
    }

    public void goForward(View v)
    {

    }
}
