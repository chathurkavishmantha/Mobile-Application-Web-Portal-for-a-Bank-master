package com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management;

import android.content.Context;
import android.widget.EditText;

public class TMValidator {
    private Context context;

    public TMValidator(Context context) {
        this.context = context;
    }

    public boolean isFieldFilled(EditText EText) {
        String val = EText.getText().toString().trim();
        if(val.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNumericOnly(EditText EText) {
        try {
            Integer.parseInt(EText.getText().toString().trim());
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public  boolean isNineDigit(EditText EText) {
        int var = EText.getText().toString().trim().length();

        if(var == 9)
            return true;
        else
            return false;
    }

    public boolean isDoubleOnly(EditText EText) {
        try {
            Double.parseDouble(EText.getText().toString().trim());
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isPositiveValue(EditText EText) {
        double var = Double.parseDouble(EText.getText().toString().trim());

        if(var <= 0)
            return false;
        else
            return true;
    }

    public boolean isPosByInt(EditText EText) {
        int var = Integer.parseInt(EText.getText().toString().trim());

        if(var > 0)
            return true;
        else
            return false;

    }
}
