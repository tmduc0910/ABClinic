package com.abclinic.constant;

public enum NotificationType {
    INQUIRY(0),
    MED_ADVICE(1),
    REPLY(2),
    ASSIGN(3),
    ACCEPT_ASSIGN(4),
    DENY_ASSIGN(5),
    REMOVE_ASSIGN(6),
    SCHEDULE(7),
    SEND_INDEX(8),
    SCHEDULE_REMINDER(9),
    DEACTIVATED(10),
    DIET_ADVICE(11);

    private int value;

    NotificationType(int value) {
        this.value = value;
    }

    public static NotificationType getType(int value) {
        for (NotificationType type : values()) {
            if (type.getValue() == value)
                return type;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
