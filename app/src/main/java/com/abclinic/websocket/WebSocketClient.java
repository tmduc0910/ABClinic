package com.abclinic.websocket;

import android.util.Log;

import com.abclinic.constant.Constant;
import com.abclinic.websocket.observer.Subject;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class WebSocketClient {
    private static final String TOPIC = "/topic/users/";

    private static WebSocketClient instance;
    private StompClient client;
    private Subject subject;

    public static WebSocketClient getInstance() {
        if (instance == null) {
            instance = new WebSocketClient();
            instance.client = Stomp.over(Stomp.ConnectionProvider.JWS, "http://192.168.1.24:8109/api/ws/websocket");

            instance.client.lifecycle().subscribe(lifecycleEvent -> {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.d(Constant.DEBUG_TAG, "Stomp connection opened");
                        break;
                    case CLOSED:
                        Log.d(Constant.DEBUG_TAG, "Stomp connection closed");
                        break;
                    case ERROR:
                        Log.e(Constant.DEBUG_TAG, "Stomp connection error", lifecycleEvent.getException());
                        break;
                }
            });

            instance.client.topic("/topic/users/5").subscribe(m -> {
                Log.d(Constant.DEBUG_TAG, m.getPayload());
            });
            instance.client.connect();
            instance.client.send("/ws/sendNoti", "fuck").subscribe();
//        Log.d(Constant.DEBUG_TAG, String.valueOf(isSuccess));
        }
        return instance;
    }

    public void subscribe(long id) {
    }

    public void disconnect() {
        client.disconnect();
    }
}
