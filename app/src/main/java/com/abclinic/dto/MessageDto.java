package com.abclinic.dto;

import com.abclinic.entity.Reply;
import com.abclinic.utils.DateTimeUtils;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.time.LocalDateTime;
import java.util.Date;

public class MessageDto implements IMessage {
    private long id;
    private String content;
    private AuthorDto author;
    private LocalDateTime createdAt;

    public MessageDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.author = new AuthorDto(reply.getUser().getId(), reply.getUser().getName(), reply.getUser().getAvatar());
        this.createdAt = reply.getCreatedAt();
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getText() {
        return content;
    }

    @Override
    public AuthorDto getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return DateTimeUtils.toDate(createdAt);
    }
}
