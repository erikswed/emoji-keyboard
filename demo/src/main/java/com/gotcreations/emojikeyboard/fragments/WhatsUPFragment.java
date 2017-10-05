package com.gotcreations.emojikeyboard.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gotcreations.emojikeyboard.R;
import com.gotcreations.emojikeyboard.adapter.MessageAdapter;
import com.gotcreations.emojikeyboard.controller.LauncherActivity;
import com.gotcreations.emojikeyboard.model.Message;
import com.gotcreations.emojikeyboard.model.MessageType;
import com.gotcreations.emojikeyboard.util.TimestampUtil;
import com.gotcreations.emojilibrary.controller.TelegramPanelForFragment;
import com.gotcreations.emojilibrary.controller.WhatsAppPanelForFragment;
import com.gotcreations.emojilibrary.model.layout.AppPanelEventListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatFragment;

import java.util.ArrayList;

public class WhatsUPFragment extends EmojiCompatFragment implements AppPanelEventListener {

    public static final String TAG = "WhatsUPFragment";

    private WhatsAppPanelForFragment mBottomPanel;
    private RecyclerView mMessages;
    private MessageAdapter mAdapter;

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whatsup, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.initMessageList();
        this.mBottomPanel = new WhatsAppPanelForFragment(this, this, R.color.colorPrimaryWhatsApp);
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initMessageList() {
        this.mMessages = (RecyclerView) this.getView().findViewById(R.id.messages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(Boolean.TRUE);
        this.mMessages.setLayoutManager(linearLayoutManager);
        this.mAdapter = new MessageAdapter(new ArrayList<Message>());
        this.mMessages.setAdapter(mAdapter);
    }

    // TELEGRAM PANEL INTERFACE
    @Override
    public void onAttachClicked() {
        Toast.makeText(getActivity(), "Attach was clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMicOnClicked() {
        Log.i(TAG, "Mic on was clicked");
        Toast.makeText(getActivity(), "Mic on was clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMicOffClicked() {
        Log.i(TAG, "Mic off was clicked");
        Toast.makeText(getActivity(), "Mic off was clicked!", Toast.LENGTH_SHORT).show();
        this.mBottomPanel.showAudioPanel(false);
    }

    @Override
    public void onSendClicked() {
        Log.i(TAG, "message: " + this.mBottomPanel.getText());
        Message message = new Message();
        message.setType(MessageType.OUTGOING);
        message.setTimestamp(TimestampUtil.getCurrentTimestamp());
        message.setContent(this.mBottomPanel.getText());
        this.mBottomPanel.setText("");
        this.updateMessageList(message);
        this.echoMessage(message);
    }

    private void echoMessage(final Message income) {
        new AsyncTask<Void, Void, Message>() {
            @Override
            protected void onPostExecute(Message message) {
                updateMessageList(message);
            }

            @Override
            protected Message doInBackground(Void... params) {
                try {
                    Thread.sleep(1200);
                    Message outgoing = new Message();
                    outgoing.setType(MessageType.INCOME);
                    outgoing.setTimestamp(TimestampUtil.getCurrentTimestamp());
                    outgoing.setContent(income.getContent());
                    return outgoing;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void updateMessageList(Message message) {
        this.mAdapter.getDataset().add(message);
        this.mAdapter.notifyDataSetChanged();
        this.mMessages.scrollToPosition(this.mAdapter.getItemCount() - 1);
    }
}
