package com.chat.inomma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chat.inomma.services.impl.CustomMessagingServiceImpl;
import com.chat.inomma.utils.SharedPrefUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vahe on 12/26/2017.
 */

public class ConversationActivity extends AppCompatActivity {
    private String TAG = ConversationActivity.class.getCanonicalName();
    private String from;
    private String friendName;

    ScrollView scrollView;
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;

    private CustomMessagingServiceImpl messagingService;

    private BroadcastReceiver messageRecivedBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity);
        String to = getIntent().getStringExtra(AppConstants.USER_UID_KEY);
        friendName = getIntent().getStringExtra(AppConstants.USER_NAME_KEY);
        from = SharedPrefUtils.loadText(this, AppConstants.REGISTERED_USER_UID);

        layout = (LinearLayout) findViewById(R.id.layout1);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);

        String senderStorageLink = AppConstants.DATABASE_LINK + AppConstants.CONVERSATIONS_TABLE + "/" + from + "_" + to;
        String getterStorageLink = AppConstants.DATABASE_LINK + AppConstants.CONVERSATIONS_TABLE + "/" + to + "_" + from;

        messagingService = new CustomMessagingServiceImpl(this, senderStorageLink, getterStorageLink);
        messagingService.initListeners();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", from);
                    messageArea.setText("");
                    messagingService.sendMessage(map);
                }
            }
        });

        initReceivers();
        registerReceivers();


    }

    private void initReceivers(){
        messageRecivedBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String userName = intent.getStringExtra(AppConstants.MESSAGE_SENDER);
                String message = intent.getStringExtra(AppConstants.MESSAGE_TEXT);

                if (userName.equals(from)) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(friendName + ":-\n" + message, 2);
                }
            }
        };
    }

    private void registerReceivers(){
        registerReceiver(messageRecivedBroadcast, new IntentFilter(AppConstants.MESSAGE_RECEIVED));
    }

    private void unregisterReceivers(){
        unregisterReceiver(messageRecivedBroadcast);
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ConversationActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_in);

        } else {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_out);

        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }
}
