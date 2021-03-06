package com.abclinic.dto;

import androidx.annotation.Nullable;

import com.abclinic.entity.Notification;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NotificationListDto implements Serializable {
    private List<Notification> list = new LinkedList<>();

    public NotificationListDto() {
    }

    public NotificationListDto(List<Notification> list) {
        this.list = list;
    }

    public boolean addItems(boolean toFirst, Collection<Notification> c) {
        boolean isNew = false;
        for (Notification n : c) {
            if (!list.contains(n)) {
                isNew = true;
                if (toFirst)
                    list.add(0, n);
                else list.add(n);
            } else break;
        }
        return isNew;
    }

    public List<Notification> getList() {
        return list;
    }

    public Notification get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof NotificationListDto)
            return this.list.equals(((NotificationListDto) obj).list);
        return false;
    }
}
