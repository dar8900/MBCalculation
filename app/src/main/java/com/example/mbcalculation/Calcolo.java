package com.example.mbcalculation;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calcolo extends AppCompatActivity
{
    private boolean Dbg = true;
    private boolean AddingInstrument = false;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";
    private String CalcoloScelto, NomeMansione, DescrizioneMansione;
    private String NomeStrumento, TipoStrumento, ModelloStrumento, MatricolaStrumento;
    private float[] x_val = new float[3];
    private float[] y_val = new float[3];
    private float[] z_val = new float[3];
    private float x_max, y_max, z_max, xyz_max_avg_dev_std, dev_std_max_avg;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcolo_layout);
        Toolbar toolbar = findViewById(R.id.calcoloToolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent fromMansione = getIntent();
        Bundle bundle1 = fromMansione.getBundleExtra("calcolo+mansione");
        CalcoloScelto = bundle1.getString("calcolo", "N.A.");
        NomeMansione = bundle1.getString("nome_mansione", "N.A.");
        DescrizioneMansione = bundle1.getString("descrizione_mansione", "N.A.");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "On Start Calcolo");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(AddingInstrument)
        {
            Intent fromStrumento = getIntent();
            Bundle bundle1 = fromStrumento.getBundleExtra("strumento");
            NomeStrumento = bundle1.getString("nome_strumento", "N.A.");
            ModelloStrumento = bundle1.getString("modello_strumento", "N.A.");
            TipoStrumento = bundle1.getString("tipo_strumento", "N.A.");
            MatricolaStrumento = bundle1.getString("matricola_strumento", "N.A.");
        }

        Log.i(TAG, "On Resume Calcolo");
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Log.i(TAG, "On Pause Calcolo");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Log.i(TAG, "On Destroy DescribeInstrument");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calcolo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        boolean ret = false;
        int id = item.getItemId();
        switch(id)
        {
            case R.id.cancella_dati:
                ClearAllData();
                break;
            case R.id.uscita:
                finish();
                System.exit(0);
                break;
        }
        return ret;
    }

    public void calcStatsData(View v)
    {
        TextView[] maxXYZText = new TextView[5];
        maxXYZText[0] = findViewById(R.id.max_x);
        maxXYZText[1] = findViewById(R.id.max_y);
        maxXYZText[2] = findViewById(R.id.max_z);
        maxXYZText[3] = findViewById(R.id.max_xyz_avg);
        maxXYZText[4] = findViewById(R.id.dev_std);
        GetAllVariables();
        maxXYZText[0].setText(Float.toString(x_max));
        maxXYZText[1].setText(Float.toString(y_max));
        maxXYZText[2].setText(Float.toString(z_max));
        maxXYZText[3].setText(Float.toString(xyz_max_avg_dev_std));
        maxXYZText[4].setText(Float.toString(dev_std_max_avg));
    }

    private boolean IsCellEmpty(EditText Text)
    {
        boolean IsEmpty = false;
        if(Text.getText().toString().isEmpty())
            IsEmpty = true;
        return  IsEmpty;
    }


    private float TextToFloat(EditText TextToVal)
    {
        float FinalFL = (float)0.0;
        String ValueStr = TextToVal.getText().toString();
        try
        {
            FinalFL = Float.parseFloat(ValueStr);
        }
        catch (NumberFormatException e2)
        {
            MakeToast("Non è un numero");
            FinalFL = (float)0.0;
        }
        return FinalFL;
    }

    private void GetAllVariables()
    {
        int cnt = 0;
        float xyzAvg;
        float[] xyzMaxArray = new float[3];
        float sumQuadratic = (float)0.0;

        EditText[] x_cells = new EditText[3];
        EditText[] y_cells = new EditText[3];
        EditText[] z_cells = new EditText[3];

        x_cells[0] = findViewById(R.id.x_1_text);
        x_cells[1] = findViewById(R.id.x_2_text);
        x_cells[2] = findViewById(R.id.x_3_text);

        y_cells[0] = findViewById(R.id.y_1_text);
        y_cells[1] = findViewById(R.id.y_2_text);
        y_cells[2] = findViewById(R.id.y_3_text);

        z_cells[0] = findViewById(R.id.z_1_text);
        z_cells[1] = findViewById(R.id.z_2_text);
        z_cells[2] = findViewById(R.id.z_3_text);

        for(cnt = 0; cnt < 3; cnt++)
        {
            if(!IsCellEmpty(x_cells[cnt]))
            {
                x_val[cnt] = TextToFloat(x_cells[cnt]);
            }
            if(!IsCellEmpty(y_cells[cnt]))
            {
                y_val[cnt] = TextToFloat(y_cells[cnt]);
            }
            if(!IsCellEmpty(z_cells[cnt]))
            {
                z_val[cnt] = TextToFloat(z_cells[cnt]);
            }
        }
        x_max = (float)0.0;
        y_max = (float)0.0;
        z_max = (float)0.0;
        for(cnt = 0; cnt < 3; cnt++)
        {
            if(x_val[cnt] > x_max)
                x_max = x_val[cnt];
            if(y_val[cnt] > y_max)
                y_max = y_val[cnt];
            if(z_val[cnt] > z_max)
                z_max = z_val[cnt];
        }
        xyzAvg = (x_max + y_max + z_max)/3;
        xyzMaxArray[0] = x_max;
        xyzMaxArray[1] = y_max;
        xyzMaxArray[2] = z_max;
        for(cnt = 0; cnt < 3; cnt++)
        {
            sumQuadratic += (float)Math.pow(xyzMaxArray[cnt] - xyzAvg, 2);
        }
        sumQuadratic /= 3;
        dev_std_max_avg = (float)Math.sqrt(sumQuadratic);
        xyz_max_avg_dev_std = xyzAvg + dev_std_max_avg;
    }

    private void ClearAllData()
    {
        int cnt = 0;
        EditText[] x_cells = new EditText[3];
        EditText[] y_cells = new EditText[3];
        EditText[] z_cells = new EditText[3];

        TextView[] maxXYZText = new TextView[5];
        maxXYZText[0] = findViewById(R.id.max_x);
        maxXYZText[1] = findViewById(R.id.max_y);
        maxXYZText[2] = findViewById(R.id.max_z);
        maxXYZText[3] = findViewById(R.id.max_xyz_avg);
        maxXYZText[4] = findViewById(R.id.dev_std);

        x_cells[0] = findViewById(R.id.x_1_text);
        x_cells[1] = findViewById(R.id.x_2_text);
        x_cells[2] = findViewById(R.id.x_3_text);

        y_cells[0] = findViewById(R.id.y_1_text);
        y_cells[1] = findViewById(R.id.y_2_text);
        y_cells[2] = findViewById(R.id.y_3_text);

        z_cells[0] = findViewById(R.id.z_1_text);
        z_cells[1] = findViewById(R.id.z_2_text);
        z_cells[2] = findViewById(R.id.z_3_text);

        for(cnt = 0; cnt < 3; cnt++)
        {
            x_val[cnt] = (float)0.0;
            y_val[cnt] = (float)0.0;
            z_val[cnt] = (float)0.0;
            x_cells[cnt].setText("");
            y_cells[cnt].setText("");
            z_cells[cnt].setText("");
        }
        x_max = (float)0.0;
        y_max = (float)0.0;
        z_max = (float)0.0;
        for(cnt = 0; cnt < 5; cnt++)
        {
            maxXYZText[cnt].setText("");
        }
    }

    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
