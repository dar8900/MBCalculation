package com.example.mbcalculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LetturaEsposizione extends AppCompatActivity
{
    private TextView mostraEsposizione;
    private float Esposizione;
    private int TipoEsp = 2;
    private int TipoCalcolo = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lettura_esposizione_layout);
        mostraEsposizione = findViewById(R.id.mostra_esposizione);

        Intent fromCalcolo = getIntent();
        Bundle bundle = fromCalcolo.getBundleExtra("bundle_esposizione");
        TipoEsp = bundle.getInt("tipo_esp", 2);
        Esposizione = bundle.getFloat("valore_esp", (float)0.0);
        TipoCalcolo = bundle.getInt("tipo_calc", 3);

        TextView TitoloEsp = findViewById(R.id.titolo_mostra_esposizione);
        TextView TestoEsp = findViewById(R.id.mostra_esposizione);
        switch(TipoEsp)
        {
            case 0:
                TitoloEsp.setText("ESPOSIZIONE SINGOLA");
                TestoEsp.setText(FormatFlToStr(Esposizione, 2) + " m/s^2");
                if(TipoCalcolo == 0)
                {
                    if (Esposizione < 2.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 2.5 && Esposizione <= 5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                else if(TipoCalcolo == 1)
                {
                    if (Esposizione < 0.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 0.5 && Esposizione <= 1)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                else
                {
                    if (Esposizione < 0.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 0.5 && Esposizione <= 1)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                break;
            case 1:
                TitoloEsp.setText("ESPOSIZIONE TOTALE");
                TestoEsp.setText(FormatFlToStr(Esposizione, 2) + " m/s^2");
                if(TipoCalcolo == 0)
                {
                    if (Esposizione < 2.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 2.5 && Esposizione <= 5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                else if(TipoCalcolo == 1)
                {
                    if (Esposizione < 0.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 0.5 && Esposizione <= 1)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                else
                {
                    if (Esposizione < 0.5)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if (Esposizione >= 0.5 && Esposizione <= 1)
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else
                    {
                        TestoEsp.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                break;
            default:
                TitoloEsp.setText("NO DATA");
                TestoEsp.setText("");
                break;
        }
    }

    private String FormatFlToStr(float number, int howManyDigit)
    {
        String numStr = "", format = "";
        format = "%." + howManyDigit + "f";
        numStr = String.format(format, number);
        return numStr;
    }

    public void closeMostraEsp(View v)
    {
        finish();
    }
}
