package com.example.abclinic.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.NotificationType;
import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.MessageDto;
import com.abclinic.dto.RequestCreateReplyDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.Reply;
import com.abclinic.entity.UserInfo;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.retrofit.api.ReplyMapper;
import com.abclinic.utils.services.JsonService;
import com.abclinic.utils.services.MyFirebaseService;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ReplyListAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class ReplyActivity extends CustomActivity {

    MessagesList messagesList;
    MessageInput messageInput;
    private long inquiryId;
    private UserInfo userInfo;
    private ReplyListAdapter replyListAdapter;
    private List<Reply> replies;
    private String temp;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_INQUIRY;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        messagesList = findViewById(R.id.messagesList);
        messageInput = findViewById(R.id.input);

        userInfo = storageService.getUserInfo();
        replyListAdapter = new ReplyListAdapter(String.valueOf(userInfo.getId()), null);
        messagesList.setAdapter(replyListAdapter);

        if (observer == null) {
            observer = notificationDto -> {
                if (notificationDto.getType() == NotificationType.REPLY.getValue()) {
                    Call<Inquiry> inquiryCall = retrofit.create(InquiryMapper.class)
                            .getInquiry(inquiryId);
                    inquiryCall.enqueue(new CustomCallback<Inquiry>(ReplyActivity.this) {
                        @Override
                        protected void processResponse(Response<Inquiry> response) {
                            List<Reply> rep = response.body().getReplies();
                            if (rep.size() != replies.size()) {
                                Reply newReply = rep.get(rep.size() - 1);
                                replyListAdapter.addToStart(new MessageDto(newReply), true);
                                replies.add(newReply);
                            }
                        }

                        @Override
                        protected boolean useDialog() {
                            return false;
                        }
                    });
                }
            };
            MyFirebaseService.subject.attach(observer);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String json = getIntent().getExtras().getString(Constant.REPLIES);
        inquiryId = getIntent().getExtras().getLong(Constant.INQUIRY_ID);
        replies = JsonService.toList(json, new TypeReference<List<Reply>>() {
        });
        if (replies != null)
            replyListAdapter.addToEnd(replies.stream().map(MessageDto::new).collect(Collectors.toList()), true);

        messageInput.setInputListener((input) -> {
            if (!input.toString().trim().equals("")) {
                RequestCreateReplyDto requestCreateReplyDto = new RequestCreateReplyDto(inquiryId, input.toString());
                Call<Reply> call = retrofit.create(ReplyMapper.class).createReply(requestCreateReplyDto);
                call.enqueue(new CustomCallback<Reply>(this) {
                    @Override
                    protected void processResponse(Response<Reply> response) {
                        replyListAdapter.addToStart(new MessageDto(response.body()), true);
                    }
                }.handle(HttpStatus.BAD_REQUEST, R.string.reply_bad_request)
                        .handle(HttpStatus.FORBIDDEN, R.string.reply_forbidden));
//                storageService.saveCache(StorageConstant.KEY_REPLY, "");
                storageService.getSharedPreferences().edit().remove(StorageConstant.KEY_REPLY).apply();
                temp = null;
            }
            return true;
        });

        temp = storageService.getSharedPreferences().getString(StorageConstant.KEY_REPLY, "");
        messageInput.getInputEditText().setText(temp);
        messageInput.setTypingListener(new MessageInput.TypingListener() {
            @Override
            public void onStartTyping() {

            }

            @Override
            public void onStopTyping() {
                temp = messageInput.getInputEditText().getText().toString();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        storageService.saveCache(StorageConstant.KEY_REPLY, temp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        storageService.saveCache(StorageConstant.KEY_REPLY, temp);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
