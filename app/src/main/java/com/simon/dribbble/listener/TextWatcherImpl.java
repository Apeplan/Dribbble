package com.simon.dribbble.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/9/11 18:20
 */

public abstract class TextWatcherImpl implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
