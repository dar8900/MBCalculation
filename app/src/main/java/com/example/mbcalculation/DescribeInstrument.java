package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DescribeInstrument extends AppCompatActivity
{
    private boolean Dbg = true;
    private static final String TAG = "MyActivity";
    private static final String DBG = "DebugLog";

    private String NomeStrumento, TipoStrumento, ModelloStrumento, MatricolaStrumento;
    private EditText nameInst, modInstr, typeInstr, matrInstr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_strumento);
//        Intent fromMansione = getIntent();
//        Bundle bundle = fromMansione.getBundleExtra("calcolo+mansione");
//        CalcoloScelto = bundle.getString("calcolo", "MB");
//        NomeMansione = bundle.getString("nome_mansione", "rand");
//        DescrizioneMansione = bundle.getString("descrizione_mansione", "something");

        nameInst = findViewById(R.id.nome_strumento);
        modInstr = findViewById(R.id.modello_strumento);
        typeInstr = findViewById(R.id.tipo_strumento);
        matrInstr = findViewById(R.id.matricola_strumento);



        Log.i(TAG, "On Create DescribeInstrument");

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

//    public void goBack(View v)
//    {
//        Intent toDescriviMansione = new Intent(DescribeInstrument.this, DescribeMansion.class);
//        startActivity(toDescriviMansione);
//        finish();
//    }

    public void goBack(View v)
    {
        if(!IsTextEmpty(nameInst) && !IsTextEmpty(modInstr) && !IsTextEmpty(typeInstr) && !IsTextEmpty(matrInstr))
        {
            NomeStrumento = nameInst.getText().toString();
            ModelloStrumento = modInstr.getText().toString();
            TipoStrumento = typeInstr.getText().toString();
            MatricolaStrumento = matrInstr.getText().toString();

            Intent toCalcolo = new Intent(DescribeInstrument.this, Calcolo.class);
            Bundle bundle = new Bundle();

//            bundle.putString("calcolo", CalcoloScelto);
//            bundle.putString("nome_mansione", NomeMansione);
//            bundle.putString("descrizione_mansione", DescrizioneMansione);

            bundle.putString("nome_strumento", NomeStrumento);
            bundle.putString("modello_strumento", ModelloStrumento);
            bundle.putString("tipo_strumento", TipoStrumento);
            bundle.putString("matricola_strumento", MatricolaStrumento);

            toCalcolo.putExtra("strumento", bundle);
            startActivity(toCalcolo);
            finish();
        }
        else
        {
            MakeToast("Prima inserire tutti i dati");
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
