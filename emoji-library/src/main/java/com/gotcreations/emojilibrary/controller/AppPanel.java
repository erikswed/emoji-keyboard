package com.gotcreations.emojilibrary.controller;

import android.widget.LinearLayout;

import com.gotcreations.emojilibrary.model.layout.AppPanelEventListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatActivity;
import com.gotcreations.emojilibrary.model.layout.EmojiEditText;

/**
 * Created by Andersson G. Acosta on 25/04/17.
 */
public abstract class AppPanel {

    protected EmojiCompatActivity mActivity;
    protected EmojiEditText mInput;
    protected EmojiKeyboard mEmojiKeyboard;
    protected LinearLayout mCurtain;
    protected AppPanelEventListener mListener;
    protected Boolean isEmojiKeyboardVisible = Boolean.FALSE;

    public AppPanel(EmojiCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    protected void init() {
        initBottomPanel();
        setInputConfig();
        setOnBackPressed();
    }

    protected abstract void initBottomPanel();
    protected abstract void setInputConfig();

    public void setAppPanelEventListener(AppPanelEventListener mListener) {
        this.mListener = mListener;
    }

    protected void setOnBackPressed() {
        this.mActivity.setOnBackPressed(new EmojiCompatActivity.OnBackPressedListener() {
            @Override
            public Boolean onBackPressed() {
                if (AppPanel.this.isEmojiKeyboardVisible) {
                    AppPanel.this.isEmojiKeyboardVisible = Boolean.FALSE;
                    AppPanel.this.hideEmojiKeyboard(0);
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }

    public void showEmojiKeyboard(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isEmojiKeyboardVisible = Boolean.TRUE;
        this.mEmojiKeyboard.getEmojiKeyboardLayout().setVisibility(LinearLayout.VISIBLE);
    }

    public void hideEmojiKeyboard(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isEmojiKeyboardVisible = Boolean.FALSE;
        this.mEmojiKeyboard.getEmojiKeyboardLayout().setVisibility(LinearLayout.GONE);
    }

    protected void fireOnMicOnClicked() {
        if (mListener != null) {
            mListener.onMicOnClicked();
        }
    }

    protected void fireOnMicOffClicked() {
        if (mListener != null) {
            mListener.onMicOffClicked();
        }
    }

    protected void fireOnSendClicked() {
        if (mListener != null) {
            mListener.onSendClicked();
        }
    }

    protected void fireOnAttachClicked() {
        if (mListener != null) {
            mListener.onAttachClicked();
        }
    }

    public abstract void showAudioPanel(boolean show);

    public void openCurtain() {
        this.mCurtain.setVisibility(LinearLayout.VISIBLE);
    }

    public void closeCurtain() {
        this.mCurtain.setVisibility(LinearLayout.INVISIBLE);
    }

    // GETTERS AND SETTERS
    public String getText() {
        return this.mInput.getText().toString();
    }

    public void setText(String text) {
        this.mInput.setText(text);
    }
}
