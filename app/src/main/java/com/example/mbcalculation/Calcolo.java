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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Calcolo extends AppCompatActivity
{
    static final int MAX_INSTRUMENT = 10;
    private boolean Dbg = true;
    private boolean AddingInstrument = false;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";
    private String CalcoloScelto, NomeMansione, DescrizioneMansione;
//    private String NomeStrumento, TipoStrumento, ModelloStrumento, MatricolaStrumento;
    private float[][] x_val = new float[3][MAX_INSTRUMENT];
    private float[][] y_val = new float[3][MAX_INSTRUMENT];
    private float[][] z_val = new float[3][MAX_INSTRUMENT];
    private float[] x_max = new float[MAX_INSTRUMENT];
    private float[] y_max = new float[MAX_INSTRUMENT];
    private float[] z_max = new float[MAX_INSTRUMENT];
    private float[] xyz_max_avg_dev_std = new float[MAX_INSTRUMENT];
    private float[] dev_std_max_avg = new float[MAX_INSTRUMENT];
    private int  durata_turno = 480;
    private int[] durata_compl = new int[MAX_INSTRUMENT];

    EditText tempoCom;
    TextView[] maxXYZText = new TextView[5];
    EditText[] x_cells = new EditText[3];
    EditText[] y_cells = new EditText[3];
    EditText[] z_cells = new EditText[3];

    int NUOVO_TURNO_REQUEST_CODE = 1, NUOVO_STRUMENTO_REQUEST_CODE = 2;

    int[] ids_strum = new int[MAX_INSTRUMENT];
    String[] Nome_strumenti = new String[MAX_INSTRUMENT];
    String[] Modello_strumenti = new String[MAX_INSTRUMENT];
    String[] Tipo_strumenti = new String[MAX_INSTRUMENT];
    String[] Matricola_strumenti = new String[MAX_INSTRUMENT];
    int N_StrumentiAttuali = 0;

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

        tempoCom = findViewById(R.id.durata_complessiva);
        tempoCom.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    int numero = 0;
                    try
                    {
                        numero = Integer.parseInt(tempoCom.getText().toString());
                    }
                    catch (NumberFormatException e)
                    {
                        MakeToast("Inserire un numero tra 1 e " + Integer.toString(durata_turno));
                    }
                    if (numero > durata_turno)
                    {
                        MakeToast("La durata supera il turno di lavoro");
                        tempoCom.setText("");
                    }
                    else if(numero < 1)
                    {
                        MakeToast("Inserire un numero maggiore di 0");
                        tempoCom.setText("");
                    }
                    else
                    {
                        durata_compl[N_StrumentiAttuali] = numero;
                    }
                    HideKeyboard(v);
                }
            }
        });

        x_cells[0] = findViewById(R.id.x_1_text);
        x_cells[1] = findViewById(R.id.x_2_text);
        x_cells[2] = findViewById(R.id.x_3_text);

        y_cells[0] = findViewById(R.id.y_1_text);
        y_cells[1] = findViewById(R.id.y_2_text);
        y_cells[2] = findViewById(R.id.y_3_text);

        z_cells[0] = findViewById(R.id.z_1_text);
        z_cells[1] = findViewById(R.id.z_2_text);
        z_cells[2] = findViewById(R.id.z_3_text);

        for(int cnt = 0; cnt < 3; cnt++)
        {
            CheckHideKeyboard(x_cells[cnt]);
            CheckHideKeyboard(y_cells[cnt]);
            CheckHideKeyboard(z_cells[cnt]);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == NUOVO_TURNO_REQUEST_CODE) && (resultCode == Calcolo.RESULT_OK))
        {
            durata_turno = data.getIntExtra("nuovo_turno_inserito", 480);
        }
        else if((requestCode == NUOVO_STRUMENTO_REQUEST_CODE) && (resultCode == Calcolo.RESULT_OK))
        {
            Bundle fromNewStrumento = new Bundle();
            fromNewStrumento = data.getBundleExtra("strumento");

            Nome_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("nome_strumento");
            Modello_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("modello_strumento");
            Tipo_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("tipo_strumento");
            Matricola_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("matricola_strumento");
            ids_strum[N_StrumentiAttuali] = N_StrumentiAttuali + 1;
            MakeToast("Strumento numero " + Integer.toString(ids_strum[N_StrumentiAttuali]) + " aggiunto");
            N_StrumentiAttuali++;
            if(N_StrumentiAttuali >= MAX_INSTRUMENT)
            {
                N_StrumentiAttuali = 0;
                MakeToast("Raggiunto il massimo numero di strumenti possibili");
            }

        }
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
            case R.id.nuovo_turno:
                Intent new_turn = new Intent(Calcolo.this, InputNuovoTurno.class);
                startActivityForResult(new_turn, NUOVO_TURNO_REQUEST_CODE);
                break;
            case R.id.add_instrument:
                Intent new_instrument = new Intent(Calcolo.this, DescribeInstrument.class);
                startActivityForResult(new_instrument, NUOVO_STRUMENTO_REQUEST_CODE);
                break;
            case R.id.cancella_dati:
                ClearAllData();
                break;
            case R.id.uscita:
                finish();
                break;
        }
        return ret;
    }

    public void calcStatsData(View v)
    {
        maxXYZText[0] = findViewById(R.id.max_x);
        maxXYZText[1] = findViewById(R.id.max_y);
        maxXYZText[2] = findViewById(R.id.max_z);
        maxXYZText[3] = findViewById(R.id.max_xyz_avg);
        maxXYZText[4] = findViewById(R.id.dev_std);
        GetAllVariables();
        maxXYZText[0].setText(Float.toString(x_max[N_StrumentiAttuali]));
        maxXYZText[1].setText(Float.toString(y_max[N_StrumentiAttuali]));
        maxXYZText[2].setText(Float.toString(z_max[N_StrumentiAttuali]));
        maxXYZText[3].setText(Float.toString(xyz_max_avg_dev_std[N_StrumentiAttuali]));
        maxXYZText[4].setText(Float.toString(dev_std_max_avg[N_StrumentiAttuali]));
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
                x_val[cnt][N_StrumentiAttuali] = TextToFloat(x_cells[cnt]);
            }
            if(!IsCellEmpty(y_cells[cnt]))
            {
                y_val[cnt][N_StrumentiAttuali] = TextToFloat(y_cells[cnt]);
            }
            if(!IsCellEmpty(z_cells[cnt]))
            {
                z_val[cnt][N_StrumentiAttuali] = TextToFloat(z_cells[cnt]);
            }
        }
        x_max[N_StrumentiAttuali] = (float)0.0;
        y_max[N_StrumentiAttuali] = (float)0.0;
        z_max[N_StrumentiAttuali] = (float)0.0;
        for(cnt = 0; cnt < 3; cnt++)
        {
            if(x_val[cnt][N_StrumentiAttuali]  > x_max[N_StrumentiAttuali])
                x_max[N_StrumentiAttuali] = x_val[cnt][N_StrumentiAttuali] ;
            if(y_val[cnt][N_StrumentiAttuali]  > y_max[N_StrumentiAttuali])
                y_max[N_StrumentiAttuali] = y_val[cnt][N_StrumentiAttuali] ;
            if(z_val[cnt][N_StrumentiAttuali]  > z_max[N_StrumentiAttuali])
                z_max[N_StrumentiAttuali] = z_val[cnt][N_StrumentiAttuali] ;
        }
        xyzAvg = (x_max[N_StrumentiAttuali] + y_max[N_StrumentiAttuali] + z_max[N_StrumentiAttuali])/3;
        xyzMaxArray[0] = x_max[N_StrumentiAttuali];
        xyzMaxArray[1] = y_max[N_StrumentiAttuali];
        xyzMaxArray[2] = z_max[N_StrumentiAttuali];
        for(cnt = 0; cnt < 3; cnt++)
        {
            sumQuadratic += (float)Math.pow(xyzMaxArray[cnt] - xyzAvg, 2);
        }
        sumQuadratic /= 3;
        dev_std_max_avg[N_StrumentiAttuali] = (float)Math.sqrt(sumQuadratic);
        xyz_max_avg_dev_std[N_StrumentiAttuali] = xyzAvg + dev_std_max_avg[N_StrumentiAttuali];
    }

    private void ClearAllData()
    {
        int cnt = 0;


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
            for(int s= 0; s < MAX_INSTRUMENT; s++)
            {
                x_val[cnt][s] = (float) 0.0;
                y_val[cnt][s] = (float) 0.0;
                z_val[cnt][s] = (float) 0.0;
            }
            x_cells[cnt].setText("");
            y_cells[cnt].setText("");
            z_cells[cnt].setText("");
        }
        x_max[N_StrumentiAttuali] = (float)0.0;
        y_max[N_StrumentiAttuali] = (float)0.0;
        z_max[N_StrumentiAttuali] = (float)0.0;
        for(cnt = 0; cnt < 5; cnt++)
        {
            maxXYZText[cnt].setText("");
        }
        tempoCom = findViewById(R.id.durata_complessiva);
        tempoCom.setText("");
    }

    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void HideKeyboard(View view)
    {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Calcolo.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void CheckHideKeyboard(final EditText Text)
    {
        Text.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                    HideKeyboard(v);
            }
        });
    }

}
