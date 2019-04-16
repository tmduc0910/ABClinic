package com.example.abclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abclinic.R;

import java.util.ArrayList;
import java.util.List;

public class ChatBox extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText input_msg;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        //chat box
        ChatBubbles = new ArrayList<>();

        ChatBubble msg1 = new ChatBubble("Xin chao", true);
        ChatBubble msg2 = new ChatBubble("Chao ban", false);
        ChatBubble msg3 = new ChatBubble("Ban co cau hoi gi the", false);
        ChatBubble msg4 = new ChatBubble("Chung toi luon ho tro nhiet tinh", false);
        ChatBubbles.add(msg1);
        ChatBubbles.add(msg2);
        ChatBubbles.add(msg3);
        ChatBubbles.add(msg4);

        listView = (ListView) findViewById(R.id.conversation);
        btnSend = findViewById(R.id.send_message);
        input_msg = (EditText) findViewById(R.id.input_message);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.right_chat, ChatBubbles);
        listView.setAdapter(adapter);

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_msg.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatBox.this, "Vui lòng nhập văn bản...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    ChatBubble ChatBubble = new ChatBubble(input_msg.getText().toString(), true);
                    ChatBubbles.add(ChatBubble);
                    adapter.notifyDataSetChanged();
                    input_msg.setText("");
                }
            }
        });

    }
}
