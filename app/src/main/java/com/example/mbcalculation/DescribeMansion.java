package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.format.TextStyle;

public class DescribeMansion extends AppCompatActivity
{
    private  boolean Dbg = true;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";
    private EditText nomeMans;
    private EditText descrMans;
    private int CalcoloScelto = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_mansione);
        nomeMans = (EditText)findViewById(R.id.nome_mansione);
        descrMans = (EditText)findViewById(R.id.descrizione_mansione);
        Intent fromSceltaCalcolo = getIntent();
        Bundle bundle = fromSceltaCalcolo.getBundleExtra("calcolo");
        CalcoloScelto = bundle.getInt("scelta_calcolo", 3);
        Log.i(TAG, "Creazione DescribeMansion");
        if(Dbg)
        {
            Log.i(DBG, "Calcolo: " + CalcoloScelto);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "On Start DescribeMansion");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.i(TAG, "On Resume DescribeMansion");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Log.i(TAG, "On Pause DescribeMansion");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Log.i(TAG, "On Destroy DescribeMansion");
    }

    public void goBack(View v)
    {
//        Intent toSceltaCalcolo = new Intent(DescribeMansion.this, MainActivity.class);
//        startActivity(toSceltaCalcolo);
        finish();
    }

    public void goForward(View v)
    {
        if(!IsTextEmpty(nomeMans) && !IsTextEmpty(descrMans) && !Dbg)
        {
            Intent toCalcolo = new Intent(DescribeMansion.this, Calcolo.class);
            Bundle bundle = new Bundle();
            bundle.putInt("calcolo", CalcoloScelto);
            bundle.putString("nome_mansione", nomeMans.getText().toString());
            bundle.putString("descrizione_mansione", descrMans.getText().toString());
            toCalcolo.putExtra("calcolo+mansione", bundle);
            startActivity(toCalcolo);
            onStop();
        }
        else if(Dbg)
        {
            Intent toCalcolo = new Intent(DescribeMansion.this, Calcolo.class);
            Bundle bundle = new Bundle();
            bundle.putInt("calcolo", CalcoloScelto);
            bundle.putString("nome_mansione", "N.A.");
            bundle.putString("descrizione_mansione", "N.A.");
            toCalcolo.putExtra("calcolo+mansione", bundle);
            startActivity(toCalcolo);
            onStop();
        }
        else
        {
            MakeToast("Inserire il nome e la descrizione prima");
        }
    }


    private boolean IsTextEmpty(EditText Text)
    {
        if(TextUtils.isEmpty(Text.getText()))
            return true;
        else
            return false;
    }

    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
