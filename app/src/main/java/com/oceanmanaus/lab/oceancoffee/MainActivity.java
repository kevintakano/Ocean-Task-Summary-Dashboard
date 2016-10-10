package com.oceanmanaus.lab.oceancoffee;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    private int total_coffes = 0;
    private int total_coffes_with_cream = 0;
    private int total_coffes_with_cream_choco = 0;
    private int total_gain = 0;
    private int[] SalePerTurn =  new int[]{0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view){

        EditText nameView = (EditText) findViewById(R.id.name);
        String name = nameView.getText().toString();

        Log.d("Debug", "Name : " + name);

        CheckBox whippedCreamView = (CheckBox) findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whippedCreamView.isChecked();

        CheckBox chocolateView = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateView.isChecked();

        Log.d("Debug", "Add hasWhippedCream? " + hasWhippedCream);
        Log.d("Debug", "Add hasChocolate? " + hasChocolate);

        int price = calculatePrice(quantity, 5, hasWhippedCream, hasChocolate);
        String message = createOrderSummary(quantity, price, hasWhippedCream, hasChocolate, name);

        Log.d("Debug", "Price : " + price);

        Log.i("Info", message);

        Intent intent = new Intent(this,SummaryActivity.class);
        intent.putExtra("summary",message);

        TextView summaryView = (TextView) findViewById(R.id.summaryOrder);
        summaryView.setText(message);

        total_coffes += quantity;

        if(hasWhippedCream && hasChocolate)
        {
            total_coffes_with_cream_choco += quantity;
        }
        else if(hasWhippedCream)
        {
            total_coffes_with_cream += quantity;
        }

        total_gain += price;

        int hours = new Time(System.currentTimeMillis()).getHours();

        Log.d("Horas","" + hours);

        if(hours >= 0  && hours < 12)
        {
            SalePerTurn[0]++;

        }else if(hours < 18)
        {
            SalePerTurn[1]++;
        }else
        {
            SalePerTurn[2]++;
        }

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("malito:"));// Only email
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java " + name);
//        intent.putExtra(Intent.EXTRA_TEXT, message);

//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

    }
    public void increment(View view){
        if(quantity >= 100){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity++;
        displayQuantity();

    }
    public void decrement(View view){
        if(quantity <= 1){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity--;
        displayQuantity();
    }

    private void displayMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayPrice(){

    }

    private void displayQuantity(){
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        quantityView.setText(quantity + "");
    }

    private String createOrderSummary(int quantity, int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String message = getString(R.string.name) + name;
        message += "\n" + getString(R.string.hasWhippedCream) + hasWhippedCream;
        message += "\n" + getString(R.string.hasChocolate) + hasChocolate;
        message += "\n" + getString(R.string.quantity) + quantity; // Message = message + ""
        message += "\n" + getString(R.string.price) + price;
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    private int calculatePrice(int quantity, int priceByCup, boolean hasWhippedCream, boolean hasChocolate){

        if(hasWhippedCream){
            priceByCup += 1; // priceByCup = priceByCup + 1
        }

        if(hasChocolate){
            priceByCup += 2;
        }

        return quantity * priceByCup;
    }

    public void DadosClick(View view)
    {
            Intent intent = new Intent(this,SummaryActivity.class);
            intent.putExtra("total",total_coffes);
            intent.putExtra("total_cream",total_coffes_with_cream);
            intent.putExtra("total_cream_choco",total_coffes_with_cream_choco);
            intent.putExtra("total_gain",total_gain);

            Log.d("Valor de Sales","" + SalePerTurn);

            intent.putExtra("sales",SalePerTurn);

            startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
