package com.oceanmanaus.lab.oceancoffee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class SummaryActivity extends AppCompatActivity {


    public Integer turnWithMoreSales(int[] array)
    {
        Integer larger = 0;

        for(int i = 0; i < array.length; i++)
        {
            if(array[i] > array[larger])
            {
                larger = i;
            }
        }
        return larger;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView text = (TextView) findViewById(R.id.textViewSummary);

        Intent intent = getIntent();

        int total_coffes = intent.getExtras().getInt("total");
        int total_coffes_cream = intent.getExtras().getInt("total_cream");
        int total_coffes_cream_choco = intent.getExtras().getInt("total_cream_choco");
        int total_gain = intent.getExtras().getInt("total_gain");

        int[] salesPerTurn = intent.getExtras().getIntArray("sales");

        Log.d("SalesPerTurn","" + salesPerTurn);
        Integer IndexTurnWithMoreSales = turnWithMoreSales(salesPerTurn);

        String turnoMaisVendido;

        if(IndexTurnWithMoreSales == 0)
        {
            turnoMaisVendido = "manhã";
        }else if(IndexTurnWithMoreSales == 1)
        {
            turnoMaisVendido = "tarde";
        }else
        {
            turnoMaisVendido = "noite";
        }

        if(salesPerTurn[0] == salesPerTurn[1] && salesPerTurn[1] == salesPerTurn[2])
        {
            turnoMaisVendido = "nenhum";
        }

        String textViewString = "* Total de cafés vendidos: " + total_coffes + '\n' +
                                "* Total de cafés com creme vendidos: " + total_coffes_cream + '\n' +
                                "* Total de cafés com creme e chocolate vendidos: " + total_coffes_cream_choco + '\n' +
                                "* Valor bruto vendido: " + total_gain + '\n' +
                                "* Perído com mais venda de café: " + turnoMaisVendido + '\n';

        text.setText(textViewString);


    }


}
