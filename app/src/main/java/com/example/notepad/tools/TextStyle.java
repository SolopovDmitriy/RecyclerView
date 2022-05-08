package com.example.notepad.tools;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

import androidx.annotation.ColorInt;

public class TextStyle {



    // refactor both methods to one method

    public static void style(EditText editText, int typeFace){
        SpannableString sp = new SpannableString(editText.getText());
        sp.setSpan(
                new StyleSpan(typeFace),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }


    public static void styleUnderline(EditText editText){
        SpannableString sp = new SpannableString(editText.getText());
        sp.setSpan(
                new UnderlineSpan(),
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }


    public static void clearAll(EditText editText){
        SpannableString sp = new SpannableString(editText.getText());
        Object[] spans = sp.getSpans(0, sp.length(), Object.class);
        for(Object s: spans) sp.removeSpan(s);
        editText.setText(sp);
    }


//    public static void styleClear(EditText editText){
//        SpannableString sp = new SpannableString(editText.getText());
//        StyleSpan[] spans = sp.getSpans(
//                editText.getSelectionStart(),
//                editText.getSelectionEnd(),
//                StyleSpan.class
//        );
//        for(Object s: spans) sp.removeSpan(s);
//        editText.setText(sp);
//    }


// https://stackoverflow.com/questions/17921314/removing-style-from-selected-text-in-edittext
        public static void styleClear(EditText editText){
            int startSelection=editText.getSelectionStart();
            int endSelection=editText.getSelectionEnd();
            Spannable spannable = editText.getText();

            StyleSpanRemover spanRemover = new StyleSpanRemover();
            // to remove all styles
            spanRemover.RemoveAll(spannable,startSelection,endSelection);
        }

    public static void setColor(EditText editText, @ColorInt int color){
        SpannableString sp = new SpannableString(editText.getText());
        ForegroundColorSpan styleSpan = new ForegroundColorSpan(color);
        sp.setSpan(
                styleSpan,
                editText.getSelectionStart(),
                editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }

    public static void setBackColor(EditText editText, @ColorInt int color){
        SpannableString sp = new SpannableString(editText.getText());
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        sp.setSpan(
                colorSpan,
                editText.getSelectionStart(),
                editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
    }

}