package com.example.abclinic.adapter;

import com.abclinic.dto.MessageDto;
import com.abclinic.entity.Reply;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class ReplyListAdapter extends MessagesListAdapter<com.abclinic.dto.MessageDto> {

    public ReplyListAdapter(String senderId, ImageLoader imageLoader) {
        super(senderId, imageLoader);
    }

    public void addReply(Reply reply) {
        this.addToStart(new MessageDto(reply), true);
    }

}
