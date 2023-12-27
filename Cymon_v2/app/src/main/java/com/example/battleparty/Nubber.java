package com.example.battleparty;

import android.util.Log;
import android.widget.TextView;

import com.example.cymonbattle.battle.BattleActivity;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;


public class Nubber {

    public PubNub pubnub;

    public TextView txtStatus;

    public Nubber(){
        initPubNub();
    }

    public void initPubNub(){
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey("pub-c-9ba02c9a-c37b-4729-8a08-5e3b0f600413"); // REPLACE with your pub key
        pnConfiguration.setSubscribeKey("sub-c-3e99fc73-3e9b-48e5-95ab-fdf7707dfcf6"); // REPLACE with your sub key
        pnConfiguration.setSecure(true);
        pubnub = new PubNub(pnConfiguration);

        // Listen to messages that arrive on the channel
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pub, PNStatus status) {
            }

            @Override
            public void message(PubNub pub, final PNMessageResult message) {
                // Replace double quotes with a blank space
                final String msg = message.getMessage().toString().replace("\"", "");

                BattleActivity.runOnUiThreadExtension(new Runnable() {
                    @Override
                    public void run() {
                        if(txtStatus!=null){
                            try {
                                txtStatus.setText(msg);
                            }catch (Exception e){
                                Log.d("pokepoke",e.getMessage());
                            }
                        }
                    }
                });


            }

            @Override
            public void presence(PubNub pub, PNPresenceEventResult presence) {
            }
        });

        // Subscribe to the global channel
        pubnub.subscribe()
                .channels(Arrays.asList("global_channel"))
                .execute();
    }

    public void sendMessage(String message){
        pubnub.publish().message(message).channel("global_channel").async(new PNCallback<PNPublishResult>() {
            @Override
            public void onResponse(PNPublishResult result, PNStatus status) {
                // status.isError() to see if error happened and print status code if error
                if(status.isError()) {
                    System.out.println("pub status code: " + status.getStatusCode());
                }
            }
        });
    }

    public void publishMessage(String message){
        pubnub.publish()
                .message(message)
                .channel("global_channel")
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // status.isError() to see if error happened and print status code if error
                        if(status.isError()) {
                            System.out.println("pub status code: " + status.getStatusCode());
                        }
                    }
                });
    }
}
