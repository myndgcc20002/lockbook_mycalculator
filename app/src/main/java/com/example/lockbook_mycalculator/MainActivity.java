package com.example.lockbook_mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView inputTextView, resultTextView;
    private String operator = "" ;
    private Double result = 0.0 ;
    public String temp = "";

    private StringBuilder currentInput = new StringBuilder();
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTextView = (TextView) findViewById(R.id.txt_input);
        resultTextView = findViewById(R.id.txt_output);
    }

    // Handle when number buttons are pressed
    public void onDigitButtonClick(View view) {
        Button button = (Button) view;
        String digit = button.getText().toString();

        if (isNewInput) {
            currentInput.setLength(0);
            isNewInput = false;
        }
        currentInput.append(digit);
        temp = temp + digit;
        updateInputTextView();
    }

    // Handle when calculation buttons are pressed (+, -, *, /, %)
    public void onOperatorButtonClick(View view) {
        Button button = (Button) view;
        String newOperator = button.getText().toString();

        if (!isNewInput) {
            calculate();
        }

        temp = temp + newOperator;
        updateInputTextView();
        operator = newOperator;
        isNewInput = true;
    }

    // Handle when the "=" button is pressed
    public void onEqualButtonClick(View view) {
        calculate();
        isNewInput = true;
    }
    // Process when "AC" button is pressed to clean all
    public void onClearButtonClick(View view) {
        currentInput.setLength(0);
        operator = "";
        result = 0.0;
        isNewInput = true;
        temp = "";
        updateInputTextView();
        updateResultTextView();
    }

    // Handle when button "." is pressed to add a decimal point
    public void onDecimalButtonClick(View view) {
        if (!currentInput.toString().contains(".")) {
            currentInput.append(".");
            isNewInput = false;
            temp= temp+".";
            updateInputTextView();
        }
    }

    // Handle when the "C" button is pressed to delete the last character
    public void onDeleteButtonClick(View view) {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
        }
        if(temp.length()>0){
            String text = temp.substring(0, temp.length()-1);
            temp = text;
        }
        updateInputTextView();
    }

    // Handle when the "-/+" button changes the direction of the number
    public void onNegativeButtonClick(View view) {
        if (currentInput.length() > 0) {
            double currentValue = Double.parseDouble(currentInput.toString());
            currentValue = -currentValue;
            currentInput.setLength(0);
            currentInput.append(currentValue);
            temp = temp + currentValue;
            updateInputTextView();
        }
    }

    private void calculate() {
        double newOperand = Double.parseDouble(currentInput.toString());

        switch (operator) {
            case "+":
                result += newOperand;
                break;
            case "-":
                result -= newOperand;
                break;
            case "*":
                result *= newOperand;
                break;
            case "/":
                if (newOperand != 0) {
                    result /= newOperand;
                } else {
                    resultTextView.setText("Error");
                    return;
                }
                break;
            case "%":
                result = result * (newOperand / 100);
                break;
            default:
                result = newOperand;
                break;
        }

        currentInput.setLength(0);
        currentInput.append(result);

        updateResultTextView();
    }

    // Update the content displayed on the input TextView
    private void updateInputTextView() {
        inputTextView.setText(temp);
        //inputTextView.setText(currentInput.toString());
    }

    // Update the content displayed on the output TextView
    private void updateResultTextView() {
        resultTextView.setText(Double.toString(result));
    }

}