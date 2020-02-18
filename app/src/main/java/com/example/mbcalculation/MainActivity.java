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
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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
        onPause();
    }

    public void CiSel(View v)
    {
        Intent startDescMansione = new Intent(MainActivity.this, DescribeMansion.class);
        Bundle bundle = new Bundle();
        bundle.putString("scelta_calcolo", "CI");
        startDescMansione.putExtra("calcolo", bundle);
        startActivity(startDescMansione);
        onPause();
    }

    public void CiNsSel(View v)
    {
        Intent startDescMansione = new Intent(MainActivity.this, DescribeMansion.class);
        Bundle bundle = new Bundle();
        bundle.putString("scelta_calcolo", "CI NS");
        startDescMansione.putExtra("calcolo", bundle);
        startActivity(startDescMansione);
        onPause();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_calcolo, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}