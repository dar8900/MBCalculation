package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        nameInst = findViewById(R.id.nome_strumento);
        modInstr = findViewById(R.id.modello_strumento);
        typeInstr = findViewById(R.id.tipo_strumento);
        matrInstr = findViewById(R.id.matricola_strumento);

        CheckHideKeyboard(nameInst);
        CheckHideKeyboard(modInstr);
        CheckHideKeyboard(typeInstr);
        CheckHideKeyboard(matrInstr);
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


    public void add_strumento(View v)
    {
        if(!IsTextEmpty(nameInst) && !IsTextEmpty(modInstr) && !IsTextEmpty(typeInstr) && !IsTextEmpty(matrInstr))
        {
            NomeStrumento = nameInst.getText().toString();
            ModelloStrumento = modInstr.getText().toString();
            TipoStrumento = typeInstr.getText().toString();
            MatricolaStrumento = matrInstr.getText().toString();

            Intent toCalcolo = new Intent();
            Bundle bundle = new Bundle();

            bundle.putString("nome_strumento", NomeStrumento);
            bundle.putString("modello_strumento", ModelloStrumento);
            bundle.putString("tipo_strumento", TipoStrumento);
            bundle.putString("matricola_strumento", MatricolaStrumento);

            toCalcolo.putExtra("strumento", bundle);
            setResult(DescribeInstrument.RESULT_OK, toCalcolo);
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

    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
