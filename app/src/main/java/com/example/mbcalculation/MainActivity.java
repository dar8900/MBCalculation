package com.example.mbcalculation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MyActivity";
    private Button mbCalc, ciCalc, ciNsCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scelta_calcolo);
        mbCalc = findViewById(R.id.mbsel_butt);
        ciCalc = findViewById(R.id.cisel_butt);
        ciNsCalc = findViewById(R.id.ciNSsel_butt);

        Log.i(TAG, "Creo l'activity MainActivity");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "On Start MainActivity");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.i(TAG, "On Resume MainActivity");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Log.i(TAG, "On Pause MainActivity");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Log.i(TAG, "On Destroy MainActivity");
    }

    public void MbSel(View v)
    {
        Intent startDescMansione = new Intent(MainActivity.this, DescribeMansion.class);
        Bundle bundle = new Bundle();
        bundle.putString("scelta_calcolo", "MB");
        startDescMansione.putExtra("calcolo", bundle);
        startActivity(startDescMansione);
        onStop();
    }

    public void CiSel(View v)
    {
        Intent startDescMansione = new Intent(MainActivity.this, DescribeMansion.class);
        Bundle bundle = new Bundle();
        bundle.putString("scelta_calcolo", "CI");
        startDescMansione.putExtra("calcolo", bundle);
        startActivity(startDescMansione);
        onStop();
    }

    public void CiNsSel(View v)
    {
        Intent startDescMansione = new Intent(MainActivity.this, DescribeMansion.class);
        Bundle bundle = new Bundle();
        bundle.putString("scelta_calcolo", "CI NS");
        startDescMansione.putExtra("calcolo", bundle);
        startActivity(startDescMansione);
        onStop();
    }

}
