package com.gotcreations.emojilibrary.model.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;


public class EmojiCompatFragment extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private OnBackPressedListener mOnBackPressedListener;


    public boolean onBackPress() {
        if (this.mOnBackPressedListener != null) {
            if (!this.mOnBackPressedListener.onBackPressed()) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public void setOnBackPressed(OnBackPressedListener backListener) {
        this.mOnBackPressedListener = backListener;
    }

    public interface OnBackPressedListener {
        Boolean onBackPressed();
    }
}
