package com.gotcreations.emojilibrary.model.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by edgar on 18/02/2016.
 */
public class EmojiCompatActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private OnBackPressedListener mOnBackPressedListener;
    public OnLauncherListener mOnLauncherListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (this.mOnBackPressedListener != null) {
            if (!this.mOnBackPressedListener.onBackPressed()) {
                mOnLauncherListener.launch();
                super.onBackPressed();
            }
        } else {
            mOnLauncherListener.launch();
            super.onBackPressed();
        }
    }

    public void setOnBackPressed(OnBackPressedListener backListener) {
        this.mOnBackPressedListener = backListener;
    }

    public interface OnBackPressedListener {
        Boolean onBackPressed();
    }

    public interface OnLauncherListener {
        void launch();
    }
}
