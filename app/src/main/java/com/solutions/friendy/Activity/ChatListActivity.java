package com.solutions.friendy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.model.ModelLoaderRegistry;
import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.CustomMessage;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.LoginRegisterPojo;
import com.solutions.friendy.Models.MessageWrapper;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.StringContract;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";

    private String receiverID;
    private String receiverType = CometChatConstants.RECEIVER_TYPE_USER;
    private MessagesListAdapter<IMessage> adapter;

    public static void start(Context context,String id){
        Intent starter = new Intent(context,ChatListActivity.class);
        starter.putExtra(StringContract.IntentStrings.UID,id);
        context.startActivity(starter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Intent intent=getIntent();
        if (intent!=null){
            receiverID=intent.getStringExtra(StringContract.IntentStrings.UID);
        }

        initView();
        addListener();
        FetchPreviousMessage();

    }

    private void FetchPreviousMessage() {
        MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setUID(receiverID).build();
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> baseMessages) {
                addMessages(baseMessages);
            }

            @Override
            public void onError(CometChatException e) {

            }
        });
    }

    private void addMessages(List<BaseMessage> baseMessages) {
        List<IMessage> list=new ArrayList<>();
        for (BaseMessage message:baseMessages){
            if (message instanceof TextMessage){

                list.add(new MessageWrapper((TextMessage) message));
            }
        }
        adapter.addToEnd(list,true);
    }

    private void addListener() {
       String listenerID = "UNIQUE_LISTENER_ID";

        CometChat.addMessageListener(listenerID, new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
                addMessage(textMessage);
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
            @Override
            public void onCustomMessageReceived(CustomMessage customMessage) {
                Log.d(TAG, "Custom message received successfully: " +customMessage.toString());
            }
        });
    }

    private void initView() {
        MessageInput inputView=findViewById(R.id.input);
        MessagesList messagesList =findViewById(R.id.messagesList);
        inputView.setInputListener(input -> {
            sendMessage(input.toString());
            return true;
        });

        String senderId=CometChat.getLoggedInUser().getUid();
        ImageLoader imageLoader = (imageView, url, payload) -> Picasso.with(ChatListActivity.this).load(url).into(imageView);

        adapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesList.setAdapter(adapter);
    }

    private void sendMessage(String message) {

        TextMessage textMessage = new TextMessage(receiverID, message, receiverType);

        CometChat.sendMessage(textMessage, new CometChat.CallbackListener <TextMessage> () {
            @Override
            public void onSuccess(TextMessage textMessage) {
                addMessage(textMessage);

            }
            @Override
            public void onError(CometChatException e) {
            }
        });
    }

    private void addMessage(TextMessage textMessage) {

        adapter.addToStart(new MessageWrapper(textMessage),true);
    }
}
