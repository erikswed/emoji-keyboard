package com.gotcreations.emojilibrary.model.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gotcreations.emojilibrary.R;

/**
 * Created by Andersson G. Acosta on 5/05/17.
 */

public class AudioRecordView extends LinearLayout {

    public interface OnButtonRecordListener {
        void onStopRecordClick(View view);
        void onSendRecordClick(View view);
    }

    private View stopRecord;
    private View sendRecord;
    private TextView timeView;

    private OnButtonRecordListener onButtonRecordListener;

    public AudioRecordView(Context context) {
        super(context);
        init(context);
    }

    public AudioRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AudioRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.audio_record_panel, this, true);

        stopRecord = findViewById(R.id.stop_record);
        sendRecord = findViewById(R.id.send_record);
        timeView = (TextView) findViewById(R.id.audio_time);

        stopRecord.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonRecordListener != null) {
                    onButtonRecordListener.onStopRecordClick(v);
                }
            }
        });

        sendRecord.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonRecordListener != null) {
                    onButtonRecordListener.onSendRecordClick(v);
                }
            }
        });
    }

    public void updateTime(CharSequence time) {
        timeView.setText(time);
    }

    public void setOnButtonRecordListener(OnButtonRecordListener onButtonRecordListener) {
        this.onButtonRecordListener = onButtonRecordListener;
    }
}
