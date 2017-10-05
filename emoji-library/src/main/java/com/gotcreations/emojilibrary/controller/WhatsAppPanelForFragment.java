package com.gotcreations.emojilibrary.controller;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gotcreations.emojilibrary.R;
import com.gotcreations.emojilibrary.model.layout.AppPanelEventListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatFragment;
import com.gotcreations.emojilibrary.model.layout.EmojiEditText;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WhatsAppPanelForFragment extends AppPanelForFragment {

    private static final String TAG = "WhatsAppPanelForFragment";

    private ImageView mEmojiButton;
    private int mButtonColor;

    // CONSTRUCTOR
    public WhatsAppPanelForFragment(EmojiCompatFragment fragment, AppPanelEventListener listener, int color) {
        super(fragment);
        this.mFragment = fragment;
        this.mButtonColor = color;
        init();
        this.mEmojiKeyboard = new EmojiKeyboardForFragment(this.mFragment, this.mInput);
        this.mListener = listener;
    }

    public WhatsAppPanelForFragment(EmojiCompatFragment activity, int color) {
        this(activity, null, color);
    }

    // INITIALIZATION
    @Override
    protected void initBottomPanel() {
        this.mEmojiButton = (ImageView) mFragment.getView().findViewById(R.id.emojiButton);
        mFragment.getView().findViewById(R.id.emojiButtonWrapper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WhatsAppPanelForFragment.this.isEmojiKeyboardVisible) {
                    WhatsAppPanelForFragment.this.closeCurtain();
                    if (WhatsAppPanelForFragment.this.mInput.isSoftKeyboardVisible()) {
                        WhatsAppPanelForFragment.this.mEmojiButton.setImageResource(R.drawable.ic_keyboard_grey600_24dp);
                        WhatsAppPanelForFragment.this.mInput.hideSoftKeyboard();
                    } else {
                        WhatsAppPanelForFragment.this.mEmojiButton.setImageResource(R.drawable.input_emoji);
                        WhatsAppPanelForFragment.this.mInput.showSoftKeyboard();
                    }
                } else {
                    WhatsAppPanelForFragment.this.mEmojiButton.setImageResource(R.drawable.ic_keyboard_grey600_24dp);
                    WhatsAppPanelForFragment.this.closeCurtain();
                    WhatsAppPanelForFragment.this.showEmojiKeyboard(0);
                }
            }
        });
        FloatingActionButton mSend = (FloatingActionButton) mFragment.getView().findViewById(R.id.send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireOnSendClicked();
            }
        });
        mSend.setBackgroundTintList(ColorStateList.valueOf(mFragment.getResources().getColor(mButtonColor)));
        this.mCurtain = (LinearLayout) mFragment.getView().findViewById(R.id.curtain);
    }

    @Override
    protected void setInputConfig() {
        this.mInput = (EmojiEditText) mFragment.getView().findViewById(R.id.input);
        this.mInput.addOnSoftKeyboardListener(new EmojiEditText.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardDisplay() {
                if (!WhatsAppPanelForFragment.this.isEmojiKeyboardVisible) {
                    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                    scheduler.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Handler mainHandler = new Handler(WhatsAppPanelForFragment.this.mFragment.getActivity().getMainLooper());
                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    WhatsAppPanelForFragment.this.openCurtain();
                                    WhatsAppPanelForFragment.this.showEmojiKeyboard(0);
                                }
                            };
                            mainHandler.post(myRunnable);
                        }
                    }, 150, TimeUnit.MILLISECONDS);
                }
            }

            @Override
            public void onSoftKeyboardHidden() {
                if (WhatsAppPanelForFragment.this.isEmojiKeyboardVisible) {
                    WhatsAppPanelForFragment.this.closeCurtain();
                    WhatsAppPanelForFragment.this.hideEmojiKeyboard(200);
                }
            }
        });
    }

    @Override
    public void hideEmojiKeyboard(int delay) {
        super.hideEmojiKeyboard(delay);
        WhatsAppPanelForFragment.this.mEmojiButton.setImageResource(R.drawable.input_emoji);
    }

    @Override
    public void showAudioPanel(boolean show) {

    }
}
