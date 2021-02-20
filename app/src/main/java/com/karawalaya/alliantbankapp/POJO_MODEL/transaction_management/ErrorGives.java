package com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.karawalaya.alliantbankapp.R;


public class ErrorGives {
    private Context context;
    private ForegroundColorSpan foreGroundColorSpan;

    public ErrorGives(Context context) {
        this.context = context;
        foreGroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary));
    }

    public SpannableStringBuilder getFieldError(String error) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(error);
        spannableStringBuilder.setSpan(foreGroundColorSpan, 0, error.length(), 0);

        return spannableStringBuilder;
    }
}
