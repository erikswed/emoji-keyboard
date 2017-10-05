package com.gotcreations.emojilibrary.controller;

import android.widget.LinearLayout;

import com.gotcreations.emojilibrary.model.layout.AppPanelEventListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatFragment;
import com.gotcreations.emojilibrary.model.layout.EmojiEditText;

public abstract class AppPanelForFragment {

    EmojiCompatFragment mFragment;
    EmojiEditText mInput;
    EmojiKeyboardForFragment mEmojiKeyboard;
    LinearLayout mCurtain;
    AppPanelEventListener mListener;

    Boolean isEmojiKeyboardVisible = Boolean.FALSE;

    AppPanelForFragment(EmojiCompatFragment fragment) {
        this.mFragment = fragment;
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

    void setOnBackPressed() {
        this.mFragment.setOnBackPressed(new EmojiCompatFragment.OnBackPressedListener() {
            @Override
            public Boolean onBackPressed() {
                if (AppPanelForFragment.this.isEmojiKeyboardVisible) {
                    AppPanelForFragment.this.isEmojiKeyboardVisible = Boolean.FALSE;
                    AppPanelForFragment.this.hideEmojiKeyboard(0);
                    return Boolean.TRUE;
                }

                return Boolean.FALSE;
            }
        });
    }

    void showEmojiKeyboard(int delay) {
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

    void fireOnMicOnClicked() {
        if (mListener != null) {
            mListener.onMicOnClicked();
        }
    }

    void fireOnMicOffClicked() {
        if (mListener != null) {
            mListener.onMicOffClicked();
        }
    }

    void fireOnSendClicked() {
        if (mListener != null) {
            mListener.onSendClicked();
        }
    }

    void fireOnAttachClicked() {
        if (mListener != null) {
            mListener.onAttachClicked();
        }
    }

    public abstract void showAudioPanel(boolean show);

    void openCurtain() {
        this.mCurtain.setVisibility(LinearLayout.VISIBLE);
    }

    void closeCurtain() {
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
