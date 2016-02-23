package com.orderfood;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orderfood.model.Calculation;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    Button btn;

    Button btnEur;
    Button btnBgn;
    Button btnUsd;

    TextView txtSouphQuant;
    TextView txtDishQuant;
    TextView txtDesertQuant;
    TextView txtDrinkQuant;

    TextView txtSouphPrice;
    TextView txtDishPrice;
    TextView txtDesertPrice;
    TextView txtDrinkPrice;

    Button btnSoupAdd;
    Button btnSoupRemove;
    Button btnDishAdd;
    Button btnDishRemove;
    Button btnDesertAdd;
    Button btnDesertRemove;

    SeekBar sliderDrink;

    CheckBox chbDelivery;

    Button btnCalc;
    Button btnClear;

    TextView txtTotalPrice;
    TextView txtCurrecy;

    String curerncy;
    Double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher_food);
        }

//      Starting default currency
        curerncy = getResources().getString(R.string.currency_eur);
        TextView txtDrinkMeasure = (TextView) findViewById(R.id.txt_Drink_Currency);
        String measure = getString(R.string.volume_measure_liter);
        txtDrinkMeasure.append("/" + measure);

        txtCurrecy = (TextView) findViewById(R.id.txt_Currency);

        btnEur = (Button) findViewById(R.id.btn_EUR);
        btnEur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurency(v);
            }
        });

        btnBgn = (Button) findViewById(R.id.btn_BGN);
        btnBgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurency(v);
            }
        });

        btnUsd = (Button) findViewById(R.id.btn_USD);
        btnUsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurency(v);
            }
        });

        txtSouphQuant = (TextView) findViewById(R.id.txt_Soup_Quantity);
        txtDishQuant = (TextView) findViewById(R.id.txt_Dish_Quantity);
        txtDesertQuant = (TextView) findViewById(R.id.txt_Desert_Quantity);
        txtDrinkQuant = (TextView) findViewById(R.id.txt_Drink_Quantity);

        txtSouphPrice = (TextView) findViewById(R.id.txt_Soup_Price);
        txtDishPrice = (TextView) findViewById(R.id.txt_Dish_Price);
        txtDesertPrice = (TextView) findViewById(R.id.txt_Desert_Price);
        txtDrinkPrice = (TextView) findViewById(R.id.txt_Drink_Price);

        txtSouphPrice.setText(String.valueOf(Calculation.PRICE_SOUP));
        txtDishPrice.setText(String.valueOf(Calculation.PRICE_DISH));
        txtDesertPrice.setText(String.valueOf(Calculation.PRICE_DESERT));
        txtDrinkPrice.setText(String.valueOf(Calculation.PRICE_DRINK));

        btnSoupAdd = (Button) findViewById(R.id.btn_Add_Suop);
        addQuant(btnSoupAdd, txtSouphQuant);
        btnSoupRemove = (Button) findViewById(R.id.btn_Remove_Soup);
        removeQuant(btnSoupRemove,txtSouphQuant);

        btnDishAdd = (Button) findViewById(R.id.btn_Add_Dish);
        addQuant(btnDishAdd,txtDishQuant);
        btnDishRemove = (Button) findViewById(R.id.btn_Remove_Dish);
        removeQuant(btnDishRemove,txtDishQuant);

        btnDesertAdd = (Button) findViewById(R.id.btn_Add_Desert);
        addQuant(btnDesertAdd, txtDesertQuant);
        btnDesertRemove = (Button) findViewById(R.id.btn_Remove_Desert);
        removeQuant(btnDesertRemove,txtDesertQuant);

        sliderDrink = (SeekBar) findViewById(R.id.slider_Drink);
        sliderDrink.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            double progresValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progresValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                DecimalFormat df = new DecimalFormat();
                df.setDecimalSeparatorAlwaysShown(false);
                txtDrinkQuant.setText(df.format(progresValue));
            }
        });

        chbDelivery = (CheckBox) findViewById(R.id.checkBox_Home_Delivery);
        chbDelivery.setText("Home Delivery\n" + Calculation.PRICE_DELIVERY + " EUR");

        txtTotalPrice = (TextView) findViewById(R.id.txt_Total_Price);

        btnCalc = (Button) findViewById(R.id.btn_Calc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soupQunat = Integer.parseInt(txtSouphQuant.getText().toString());
                int dishQuant = Integer.parseInt(txtDishQuant.getText().toString());
                int desertQuant = Integer.parseInt(txtDesertQuant.getText().toString());
                double dringQuant = Integer.parseInt(txtDrinkQuant.getText().toString());
                boolean isForHome = chbDelivery.isChecked();

                total = Calculation.calculateTotal(soupQunat, dishQuant, desertQuant,dringQuant, isForHome);
                double convertedCost = Calculation.converter(curerncy,total);

                txtTotalPrice.setText(String.valueOf(formatMoney(convertedCost)));
            }
        });

        btnClear = (Button) findViewById(R.id.btn_Clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderDrink.setProgress(0);
                chbDelivery.setChecked(false);
                recreate();
            }
        });
    }

    private void addQuant(Button btn , final TextView txtV){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevQuant = Integer.parseInt(txtV.getText().toString());
                int newQuant = Calculation.addProduct(prevQuant);
                txtV.setText(String.valueOf(newQuant));
//                maximum is 10 items, no need to parse
                if (txtV.getText().length()>1){
//                    can't use getColor() because of compatibility issues(Requires API 23)
                    txtV.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent));
                }
            }
        });
    }

    private void removeQuant(Button btn , final TextView txtV){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtV.getCurrentTextColor() == ContextCompat.getColor(MainActivity.this,R.color.colorAccent)){
                    txtV.setTextColor(Color.WHITE);
                }
                int prevQuant = Integer.parseInt(txtV.getText().toString());
                int newQuant = Calculation.removeProduct(prevQuant);
                txtV.setText(String.valueOf(newQuant));
            }
        });
    }

    private void setCurency(View v){
        curerncy = ((Button) v).getText().toString();
        double convertedCost = Calculation.converter(curerncy,total);
        txtTotalPrice.setText(String.valueOf(formatMoney(convertedCost)));
        txtCurrecy.setText(curerncy);
    }

    private String formatMoney(double money){
        DecimalFormat df = new DecimalFormat();
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        return String.valueOf(df.format(money));
    }
}
