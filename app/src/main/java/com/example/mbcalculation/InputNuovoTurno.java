package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class InputNuovoTurno extends AppCompatActivity
{
    EditText insert_turno;
    int nuovo_turno;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_nuovo_turno_layout);
        insert_turno = findViewById(R.id.nuovo_turno_insert);
        CheckHideKeyboard(insert_turno);
    }

    public void newTurnInsert(View v)
    {
        Intent intent = new Intent();
        intent.putExtra("nuovo_turno_inserito", nuovo_turno);
        setResult(InputNuovoTurno.RESULT_OK, intent);
        finish();
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
                {
                    int numero = 0;
                    try
                    {
                        numero = Integer.parseInt(Text.getText().toString());
                    }
                    catch (NumberFormatException e)
                    {
                        MakeToast("Inserire un numero tra 1 e " + Integer.toString(480));
                    }
                    if (numero > 480)
                    {
                        MakeToast("La durata inserita supera il turno di lavoro massimo di 480 min (8h)");
                        Text.setText("");
                    }
                    else if(numero < 1)
                    {
                        MakeToast("Inserire un numero maggiore di 0");
                        Text.setText("");
                    }
                    else
                    {
                        nuovo_turno = numero;
                    }
                    HideKeyboard(v);
                }
            }
        });
    }
    private void MakeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
