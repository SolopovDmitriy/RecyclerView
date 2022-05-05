package com.example.notepad.tools;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.EditText;

public class TextStyle {
    public static void style(EditText editText, int typeFace){
        SpannableString sp = new SpannableString(editText.getText());
        StyleSpan styleSpan = new StyleSpan(typeFace);
        sp.setSpan(
                styleSpan,
                editText.getSelectionStart(), editText.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        editText.setText(sp);
        System.out.println("==========================");
        System.out.println();
        StyleSpan[] styleSpans = sp.getSpans(0, editText.getText().length(), StyleSpan.class);
        for (StyleSpan span: styleSpans) {
            System.out.println(span);
        }
        System.out.println("==========================");
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





}