package com.example.vinaymaneti.androidcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class CalculatorActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorActivity";
    private TextView topTextScreen;
    private String display = "";
    private String currentMathOperator = "";
    //    private boolean hasResult = false;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        topTextScreen = findViewById(R.id.screen);
        topTextScreen.setText(display);

    }

    private void updateScreen(){
        topTextScreen.setText(display);
    }

    public void onClickNumber(View view){
        if(!result.equals("")) {
            clear();
            updateScreen();
        }
        Button button = (Button) view;
        display += button.getText();
        updateScreen();
    }

    public boolean isOperator(char operator){
        switch(operator) {
            case '+':
            case '-':
            case '×':
            case '÷':
            case '%':
                return true;
            default:
                return false;
        }

    }

    public void onClickOperators(View view){
        if(display.equals("")) return;
        Button button = (Button) view;
        if(!result.equals("")) {
            String _display = result;
            clear();
            display = _display;
        }

        if(!currentMathOperator.equals("")) {
            Log.d(TAG, "onClickOperators: " + display.charAt(display.length() - 1));
            if(isOperator(display.charAt(display.length() - 1))) {
//                currentMathOperator = button.getText().toString();
                display = display.replace(display.charAt(display.length() - 1), button.getText().charAt(0));
                updateScreen();
                return;
            } else {
                getResult();
                display = result;
                result = "";
            }
        }

        display += button.getText();
        currentMathOperator = button.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentMathOperator = "";
        result = "";
    }

    public void onClickClear(View view){
        clear();
        updateScreen();
    }

    private boolean getResult(){
        if(currentMathOperator.equals("")) return false;
        String[] operation = display.split(Pattern.quote(currentMathOperator));
        if(operation.length < 2) return false;
        try {
            result = String.valueOf(operate(operation[0], operation[1], currentMathOperator));
        } catch(Exception e) {
            Log.d(TAG, "onClickEquals: " + e.getMessage());
        }
        return true;
    }

    public void onClickEquals(View view){
        if(display.equals("")) return;
        if(!getResult()) return;
        topTextScreen.setText(display + "\n" + result);
    }

    public void onClickDelete(View view){
        if (display != null && display.length() > 0) {
            display = display.substring(0, display.length() - 1);
        }
        topTextScreen.setText(display);
    }

    private double operate(String firstDigits, String secondDigits, String operator){
        result = "";
        switch(operator) {
            case "+":
                return Double.valueOf(firstDigits) + Double.valueOf(secondDigits);
            case "-":
                return Double.valueOf(firstDigits) - Double.valueOf(secondDigits);
            case "×":
                return Double.valueOf(firstDigits) * Double.valueOf(secondDigits);
            case "÷":
                try {
                    return Double.valueOf(firstDigits) / Double.valueOf(secondDigits);
                } catch(Exception ex) {
                    Log.d(TAG, "operate: " + ex.getMessage());
                }
            case "%":
                try {
                    return Double.valueOf(firstDigits) % Double.valueOf(secondDigits);
                } catch(Exception ex) {
                    Log.d(TAG, "operate: " + ex.getMessage());
                }
            default:
                return -1;

        }
    }
}
