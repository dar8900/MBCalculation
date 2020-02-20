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

import com.google.android.material.snackbar.Snackbar;


public class Calcolo extends AppCompatActivity
{
    static final int MAX_INSTRUMENT = 10;
    static final int MB_CALC = 0;
    static final int CI_CALC = 1;
    static final int CI_NS_CALC = 2;

    private boolean Dbg = true;
    private boolean AddingInstrument = false;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";
    private int CalcoloScelto = 3;
    private String NomeMansione, DescrizioneMansione;
//    private String NomeStrumento, TipoStrumento, ModelloStrumento, MatricolaStrumento;
    private float[][] x_val = new float[3][MAX_INSTRUMENT];
    private float[][] y_val = new float[3][MAX_INSTRUMENT];
    private float[][] z_val = new float[3][MAX_INSTRUMENT];
    private float[] x_avg_val = new float[MAX_INSTRUMENT];
    private float[] y_avg_val = new float[MAX_INSTRUMENT];
    private float[] z_avg_val = new float[MAX_INSTRUMENT];
    private float[] x_avg_dev_std_val = new float[MAX_INSTRUMENT];
    private float[] y_avg_dev_std_val = new float[MAX_INSTRUMENT];
    private float[] z_avg_dev_std_val = new float[MAX_INSTRUMENT];
    private float[] x_max = new float[MAX_INSTRUMENT];
    private float[] y_max = new float[MAX_INSTRUMENT];
    private float[] z_max = new float[MAX_INSTRUMENT];
    private float[] xyz_max_avg_dev_std = new float[MAX_INSTRUMENT];
    private float[] dev_std_max_avg = new float[MAX_INSTRUMENT];
    private float[] EsposizioniSingole = new float[MAX_INSTRUMENT];
    private float EsposizioneTotale;
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
    int N_StrumentiAttuali = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcolo_layout);
        Toolbar toolbar = findViewById(R.id.calcoloToolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MakeSnackBar("Aggiungere uno strumento dal menu per iniziare", Snackbar.LENGTH_LONG);

        Intent fromMansione = getIntent();
        Bundle bundle1 = fromMansione.getBundleExtra("calcolo+mansione");
        CalcoloScelto = bundle1.getInt("calcolo", 3);
        NomeMansione = bundle1.getString("nome_mansione", "N.A.");
        DescrizioneMansione = bundle1.getString("descrizione_mansione", "N.A.");

        tempoCom = findViewById(R.id.durata_complessiva);
        tempoCom.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(N_StrumentiAttuali >= 0)
                {
                    if (!hasFocus)
                    {
                        int numero = 0;
                        int durataTot = 0;
                        try
                        {
                            numero = Integer.parseInt(tempoCom.getText().toString());
                            durata_compl[N_StrumentiAttuali] = numero;
                            for(int cnt = 0; cnt < N_StrumentiAttuali + 1; cnt++)
                                durataTot += durata_compl[cnt];
                            if(durataTot > durata_turno)
                            {
                                MakeToast("Si è superato la durata del turno di lavoro");
                                durata_compl[N_StrumentiAttuali] = 0;
                            }
                            else
                                durata_compl[N_StrumentiAttuali] = numero;
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
                        else if (numero < 1)
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
                else
                {
                    HideKeyboard(v);
                    MakeSnackBar("Aggiungere prima uno strumento dal menu", Snackbar.LENGTH_LONG);
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

            if(N_StrumentiAttuali >= 0)
            {
                GetAllVariables();
                ClearAllData();
            }

            N_StrumentiAttuali++;
            if(N_StrumentiAttuali >= MAX_INSTRUMENT)
            {
                N_StrumentiAttuali = -1;
                MakeToast("Raggiunto il massimo numero di strumenti possibili");
            }
            else
            {
                Nome_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("nome_strumento");
                Modello_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("modello_strumento");
                Tipo_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("tipo_strumento");
                Matricola_strumenti[N_StrumentiAttuali] = fromNewStrumento.getString("matricola_strumento");
                ids_strum[N_StrumentiAttuali] = N_StrumentiAttuali + 1;
                MakeToast("Strumento numero " + Integer.toString(ids_strum[N_StrumentiAttuali]) + " aggiunto");
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
        Intent guarda_exp = new Intent(Calcolo.this, LetturaEsposizione.class);
        Bundle guarda_exp_bundle = new Bundle();
        switch(id)
        {
            case R.id.nuovo_turno:
                Intent new_turn = new Intent(Calcolo.this, InputNuovoTurno.class);
                startActivityForResult(new_turn, NUOVO_TURNO_REQUEST_CODE);
                break;
            case R.id.add_instrument:
                Intent new_instrument = new Intent(Calcolo.this, DescribeInstrument.class);
                startActivityForResult(new_instrument, NUOVO_STRUMENTO_REQUEST_CODE);
//                ClearAllData();
                break;
            case R.id.calcolo_singolo:
                if(N_StrumentiAttuali >= 0)
                {
                    CalcSingleEsposition(CalcoloScelto, N_StrumentiAttuali);
                    guarda_exp_bundle.putInt("tipo_calc", CalcoloScelto);
                    guarda_exp_bundle.putInt("tipo_esp", 0);
                    guarda_exp_bundle.putFloat("valore_esp", EsposizioniSingole[N_StrumentiAttuali]);
                    guarda_exp.putExtra("bundle_esposizione", guarda_exp_bundle);
                    startActivity(guarda_exp);
                }
                else
                    MakeSnackBar("Aggiungere prima uno strumento", Snackbar.LENGTH_SHORT);
                break;
            case R.id.calcolo_totale:
                if(N_StrumentiAttuali >= 0)
                {
                    CalcTotalEsposition();
                    guarda_exp_bundle.putInt("tipo_esp", 1);
                    guarda_exp_bundle.putInt("tipo_calc", CalcoloScelto);
                    guarda_exp_bundle.putFloat("valore_esp", EsposizioneTotale);
                    guarda_exp.putExtra("bundle_esposizione", guarda_exp_bundle);
                    startActivity(guarda_exp);
                }
                else
                    MakeSnackBar("Aggiungere prima uno strumento", Snackbar.LENGTH_SHORT);
                break;
            case R.id.cancella_dati:
                if(N_StrumentiAttuali >= 0)
                {
                    ClearAllData();
                    MakeSnackBar("Dati strumento " + Integer.toString(ids_strum[N_StrumentiAttuali]) + " cancellati", Snackbar.LENGTH_SHORT);
                }
                else
                    MakeSnackBar("Non si è aggiunto nessuno strumento", Snackbar.LENGTH_LONG);
                break;
            case R.id.uscita:
                finish();
                break;
        }
        return ret;
    }

    public void calcStatsData(View v)
    {
        if(N_StrumentiAttuali < 0)
        {
            MakeSnackBar("Aggiungere prima uno strumento dal menu", Snackbar.LENGTH_LONG);
        }
        else
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

    private void GetValoriPerEsposizione(int StrumentoAttuale)
    {
        int cnt = 0;
        GetAllVariables();
        x_avg_val[StrumentoAttuale] = (float)0.0;
        y_avg_val[StrumentoAttuale] = (float)0.0;
        z_avg_val[StrumentoAttuale] = (float)0.0;

        x_avg_dev_std_val[StrumentoAttuale] = (float)0.0;
        y_avg_dev_std_val[StrumentoAttuale] = (float)0.0;
        z_avg_dev_std_val[StrumentoAttuale] = (float)0.0;

        for(cnt = 0; cnt < 3; cnt++)
        {
            x_avg_val[StrumentoAttuale] += x_val[cnt][StrumentoAttuale];
            y_avg_val[StrumentoAttuale] += y_val[cnt][StrumentoAttuale];
            z_avg_val[StrumentoAttuale] += z_val[cnt][StrumentoAttuale];
        }
        x_avg_val[StrumentoAttuale] /= 3;
        y_avg_val[StrumentoAttuale] /= 3;
        z_avg_val[StrumentoAttuale] /= 3;

        float sumQuad = (float)0.0;
        for(cnt = 0; cnt < 3; cnt++)
        {
            sumQuad += Math.pow(x_val[cnt][StrumentoAttuale] - x_avg_val[StrumentoAttuale], 2);
        }
        sumQuad /= 3;
        x_avg_dev_std_val[StrumentoAttuale] = (float)Math.sqrt(sumQuad);

        sumQuad = 0;
        for(cnt = 0; cnt < 3; cnt++)
        {
            sumQuad += Math.pow(y_val[cnt][StrumentoAttuale] - y_avg_val[StrumentoAttuale], 2);
        }
        sumQuad /= 3;
        y_avg_dev_std_val[StrumentoAttuale] = (float)Math.sqrt(sumQuad);

        sumQuad = 0;
        for(cnt = 0; cnt < 3; cnt++)
        {
            sumQuad += Math.pow(z_val[cnt][StrumentoAttuale] - z_avg_val[StrumentoAttuale], 2);
        }
        sumQuad /= 3;
        z_avg_dev_std_val[StrumentoAttuale] = (float)Math.sqrt(sumQuad);

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
            x_cells[cnt].setText("");
            y_cells[cnt].setText("");
            z_cells[cnt].setText("");
        }

        for(cnt = 0; cnt < 5; cnt++)
        {
            maxXYZText[cnt].setText("");
        }
        tempoCom = findViewById(R.id.durata_complessiva);
        tempoCom.setText("");
    }


    private float CalcSingleEsposition(int TipoCalcolo, int StrumentoAttuale)
    {
        float AxyzAvg = (float)0.0;
        float prod_dev_std = (float)1.645;
        GetValoriPerEsposizione(StrumentoAttuale);
        switch (TipoCalcolo)
        {
            case MB_CALC:
                AxyzAvg = (float)Math.sqrt(Math.pow(x_avg_val[StrumentoAttuale], 2) + Math.pow(y_avg_val[StrumentoAttuale], 2) + Math.pow(z_avg_val[StrumentoAttuale], 2));
                break;
            case CI_CALC:
                AxyzAvg = (float)Math.max(1.4 * (x_avg_val[StrumentoAttuale] + (x_avg_dev_std_val[StrumentoAttuale] * prod_dev_std)), 1.4 * (y_avg_val[StrumentoAttuale] + (y_avg_dev_std_val[StrumentoAttuale] * prod_dev_std)));
                AxyzAvg = (float)Math.max(AxyzAvg, z_avg_val[StrumentoAttuale] + (z_avg_dev_std_val[StrumentoAttuale] * prod_dev_std));
                break;
            case CI_NS_CALC:
                AxyzAvg = (float)Math.max(x_avg_val[StrumentoAttuale] + (x_avg_dev_std_val[StrumentoAttuale] * prod_dev_std), y_avg_val[StrumentoAttuale] + (y_avg_dev_std_val[StrumentoAttuale] * prod_dev_std));
                AxyzAvg = (float)Math.max(AxyzAvg, z_avg_val[StrumentoAttuale] + (z_avg_dev_std_val[StrumentoAttuale] * prod_dev_std));
                break;
            default:
                break;
        }
        float frac = (float)Math.sqrt((float)durata_compl[StrumentoAttuale] / (float)durata_turno);
        EsposizioniSingole[StrumentoAttuale] = AxyzAvg * (float)Math.sqrt((float)durata_compl[StrumentoAttuale] / (float)durata_turno);
        return AxyzAvg;
    }


    private void CalcTotalEsposition()
    {
        float SommaParz = 0;
        for(int cnt = 0; cnt < N_StrumentiAttuali + 1; cnt++)
        {
            SommaParz += ((float)Math.pow(CalcSingleEsposition(CalcoloScelto, cnt), 2) * ((float)durata_compl[cnt] / (float)durata_turno));
        }
        EsposizioneTotale = (float)Math.sqrt(SommaParz);
    }

    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void MakeSnackBar(CharSequence msg, int Duration)
    {
        Snackbar.make(findViewById(R.id.calcolo_layout), msg, Duration).show();
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
                if (N_StrumentiAttuali >= 0)
                {
                    if (!hasFocus)
                        HideKeyboard(v);
                }
                else
                {
                    HideKeyboard(v);
                    MakeSnackBar("Aggiungere prima uno strumento dal menu", Snackbar.LENGTH_LONG);
                }
            }
        });
    }

}
