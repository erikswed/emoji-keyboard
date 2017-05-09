package com.gotcreations.emojilibrary.model.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.gotcreations.emojilibrary.R;

/**
 * Created by edgar on 18/02/2016.
 */
public class TelegramPanelView extends LinearLayout {

    private static final String TAG = "EmojiKeyboardLayout";

    // CONSTRUCTORS
    public TelegramPanelView(Context context) {
        super(context);
        init(context);
    }

    public TelegramPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TelegramPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TelegramPanelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    // INITIALIZATIONS
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rsc_telegram_panel, this, true);
    }

}
