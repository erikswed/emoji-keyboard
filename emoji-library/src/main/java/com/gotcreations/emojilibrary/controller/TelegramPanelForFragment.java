package com.gotcreations.emojilibrary.controller;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gotcreations.emojilibrary.R;
import com.gotcreations.emojilibrary.model.layout.AppPanelEventListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatFragment;
import com.gotcreations.emojilibrary.model.layout.EmojiEditText;
import com.gotcreations.emojilibrary.model.layout.TelegramPanelView;
import com.gotcreations.emojilibrary.util.AbstractAnimatorListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;


public class TelegramPanelForFragment extends AppPanelForFragment{

    private static final String TAG = "TelegramPanelForFragmen";
    private static final int EMPTY_MESSAGE = 0;
    private static final int EMPTY_MESSAGE_EMOJI_KEYBOARD = 1;
    private static final int EMPTY_MESSAGE_KEYBOARD = 2;
    private static final int PREPARED_MESSAGE = 3;
    private static final int PREPARED_MESSAGE_EMOJI_KEYBOARD = 4;
    private static final int PREPARED_MESSAGE_KEYBOARD = 5;
    private static final int AUDIO = 6;

    private Toolbar mBottomPanel;
    private TextView audioTime;
    private TelegramPanelView panelView;
    private int state;

    // CONSTRUCTOR
    public TelegramPanelForFragment(EmojiCompatFragment fragment, AppPanelEventListener listener) {
        super(fragment);
        this.mFragment = fragment;
        init();
        this.mEmojiKeyboard = new EmojiKeyboardForFragment(mFragment, this.mInput);
        this.mListener = listener;
    }

    public TelegramPanelForFragment(EmojiCompatFragment activity) {
        this(activity, null);
    }

    // INITIALIZATION
    @Override
    protected void initBottomPanel() {
        this.audioTime = (TextView) this.mFragment.getView().findViewById(R.id.audio_time);
        this.mBottomPanel = (Toolbar) this.mFragment.getView().findViewById(R.id.panel);
        this.panelView = (TelegramPanelView) this.mFragment.getView().findViewById(R.id.panel_container).getParent();
        this.mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
        this.mBottomPanel.inflateMenu(R.menu.telegram_menu);

        this.mBottomPanel.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == AUDIO) {
                    fireOnMicOffClicked();
                    showAudioPanel(false);
                } else if (TelegramPanelForFragment.this.isEmojiKeyboardVisible) {
                    TelegramPanelForFragment.this.closeCurtain();
                    if (TelegramPanelForFragment.this.mInput.isSoftKeyboardVisible()) {
                        TelegramPanelForFragment.this.mBottomPanel.setNavigationIcon(R.drawable.ic_keyboard);
                        TelegramPanelForFragment.this.mInput.hideSoftKeyboard();
                    } else {
                        TelegramPanelForFragment.this.mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
                        TelegramPanelForFragment.this.mInput.showSoftKeyboard();
                    }
                } else {
                    TelegramPanelForFragment.this.mBottomPanel.setNavigationIcon(R.drawable.ic_keyboard);
                    TelegramPanelForFragment.this.closeCurtain();
                    TelegramPanelForFragment.this.showEmojiKeyboard(0);
                }
            }
        });

        this.mBottomPanel.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_attach) {
                    fireOnAttachClicked();
                } else if (item.getItemId() == R.id.action_mic) {
                    switch (state) {
                        case AUDIO:
                            fireOnSendClicked();
                            showAudioPanel(false);
                            break;
                        default:
                            if (TelegramPanelForFragment.this.mInput.getText().toString().equals("")) {
                                showAudioPanel(true);
                            } else {
                                fireOnSendClicked();
                            }
                    }

                }
                return Boolean.TRUE;
            }
        });

        this.mCurtain = (LinearLayout) this.mFragment.getView().findViewById(R.id.curtain);
        this.state = EMPTY_MESSAGE;
    }

    @Override
    protected void setInputConfig() {
        this.mInput = (EmojiEditText) this.mBottomPanel.findViewById(R.id.input);
        mInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        this.mInput.addOnSoftKeyboardListener(new EmojiEditText.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardDisplay() {
                if (!TelegramPanelForFragment.this.isEmojiKeyboardVisible) {
                    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                    scheduler.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Handler mainHandler = new Handler(TelegramPanelForFragment.this.mFragment.getActivity().getMainLooper());
                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    TelegramPanelForFragment.this.openCurtain();
                                    TelegramPanelForFragment.this.showEmojiKeyboard(0);
                                }
                            };
                            mainHandler.post(myRunnable);
                        }
                    }, 150, TimeUnit.MILLISECONDS);
                }
            }

            @Override
            public void onSoftKeyboardHidden() {
                if (TelegramPanelForFragment.this.isEmojiKeyboardVisible) {
                    TelegramPanelForFragment.this.closeCurtain();
                    TelegramPanelForFragment.this.hideEmojiKeyboard(200);
                }
            }
        });

        this.mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showSendOptions(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        audioTime.setTextColor(panelView.getAudioTextColor());
        mInput.setTextColor(panelView.getTextColor());
        mInput.setHint(panelView.getHintText());
        mInput.setHintTextColor(panelView.getTextColorHint());

        setIcon(R.id.action_attach, panelView.getAttachIconColor(), R.drawable.ic_attachment);
        setIcon(R.id.action_mic, panelView.getAudioIconColor(), R.drawable.ic_mic);
    }

    private void setIcon(int itemId, int color, @DrawableRes int drawableId) {
        Drawable icon = mFragment.getResources().getDrawable(drawableId);
        icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        setIcon(itemId, icon);
    }

    private void setIcon(int itemId, Drawable icon) {
        Menu menu = this.mBottomPanel.getMenu();
        MenuItem mi = menu.findItem(itemId);
        mi.setIcon(icon);
    }

    @Override
    public void showAudioPanel(final boolean show) {

        if (show) {
            state = AUDIO;
            hideEmojiKeyboard(0);
            this.mInput.hideSoftKeyboard();
            this.mInput.animate().alpha(0).setDuration(75).setListener(new AbstractAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "Hide mInput");
                    mInput.setVisibility(GONE);
                }
            }).start();

            this.audioTime.animate().alpha(1).setDuration(75).setListener(new AbstractAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "show audioTime");
                    audioTime.setVisibility(View.VISIBLE);
                }
            }).start();
            TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_attach).animate().scaleX(0).scaleY(0).setDuration(150).start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        setIcon(R.id.action_mic, panelView.getSendIconColor(), R.drawable.ic_send);
                        TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(1).scaleY(1).setDuration(75).start();
                    }
                }).start();
            }
            Drawable icCircle = mFragment.getResources().getDrawable(R.drawable.ic_circle);
            icCircle.setColorFilter(panelView.getAudioIconColor(), PorterDuff.Mode.SRC_ATOP);
            this.mBottomPanel.setNavigationIcon(icCircle);
            fireOnMicOnClicked();
        } else {
            this.audioTime.animate().alpha(0).setDuration(75).setListener(new AbstractAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "Hide audioInput");
                    audioTime.setVisibility(GONE);
                }
            }).start();
            this.mInput.animate().alpha(1).setDuration(75).setListener(new AbstractAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "Show mInput");
                    mInput.setVisibility(View.VISIBLE);
                }
            }).start();
            showSendOptions(true);
        }

    }

    private void showSendOptions(boolean show) {

        final MenuItem micButton = TelegramPanelForFragment.this.mBottomPanel.getMenu().findItem(R.id.action_mic);

        if (isEmojiKeyboardVisible) {
            this.mBottomPanel.setNavigationIcon(R.drawable.ic_keyboard);
        } else {
            this.mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
        }

        if (!this.mInput.getText().toString().equals("") && show) {

            if (state != PREPARED_MESSAGE && state != PREPARED_MESSAGE_EMOJI_KEYBOARD && state != PREPARED_MESSAGE_KEYBOARD) {
                TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_attach).animate().scaleX(0).scaleY(0).setDuration(150).start();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            setIcon(R.id.action_mic, panelView.getSendIconColor(), R.drawable.ic_send);
                            TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(1).scaleY(1).setDuration(75).start();
                        }
                    }).start();
                }
            }

            state = PREPARED_MESSAGE;
            if (mInput.isSoftKeyboardVisible()) {
                state = PREPARED_MESSAGE_KEYBOARD;
            } else if (isEmojiKeyboardVisible) {
                state = PREPARED_MESSAGE_EMOJI_KEYBOARD;
            }


        } else {
            state = EMPTY_MESSAGE;
            if (mInput.isSoftKeyboardVisible()) {
                state = EMPTY_MESSAGE_KEYBOARD;
            } else if (isEmojiKeyboardVisible) {
                state = EMPTY_MESSAGE_EMOJI_KEYBOARD;
            }

            TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_attach).animate().scaleX(1).scaleY(1).setDuration(150).start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        setIcon(R.id.action_mic, panelView.getAudioIconColor(), R.drawable.ic_mic);
                        TelegramPanelForFragment.this.mBottomPanel.findViewById(R.id.action_mic).animate().scaleX(1).scaleY(1).setDuration(75).start();
                    }
                }).start();
            }
        }


    }

    public int getState() {
        return state;
    }

    private boolean isInState(int state) {
        return this.state == state;
    }

    private boolean isInAudioState() {
        return isInState(AUDIO);
    }

    public boolean isInMessageState() {
        return !isInAudioState();
    }

    @Override
    public void hideEmojiKeyboard(int delay) {
        super.hideEmojiKeyboard(delay);
        this.mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
    }

    public void setAudioTime(CharSequence time) {
        this.audioTime.setText(time);
    }
}
