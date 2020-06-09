package com.example.abclinic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.abclinic.constant.NotificationType;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CustomEventDay extends EventDay {
    private boolean isEnabled;
    private List<IconType> icons;

    public CustomEventDay(Calendar day, Drawable drawable, List<IconType> icons) {
        super(day, drawable);
        this.isEnabled = true;
        this.icons = icons;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Date getDate() {
        return getCalendar().getTime();
    }

    public List<IconType> getIcons() {
        return icons;
    }

    public boolean containsIcon(IconType type) {
        return icons.contains(type);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof CustomEventDay) {
            return this.getDate().equals(((CustomEventDay) obj).getDate());
        }
        return false;
    }

    public enum IconType {
        INQUIRY(R.drawable.ic_dot_green, 0, "Yêu cầu xin tư vấn", NotificationType.INQUIRY.getValue()),
        MED_RECORD(R.drawable.ic_dot_blue, 1, "Tư vấn khám bệnh của bác sĩ", NotificationType.MED_ADVICE.getValue()),
        DIET_RECORD(R.drawable.ic_dot_blue, 2, "Tư vấn dinh dưỡng của bác sĩ", NotificationType.DIET_ADVICE.getValue()),
        REPLY(R.drawable.ic_dot_purple, 3, "Trả lời từ bác sĩ", NotificationType.REPLY.getValue()),
        SCHEDULE(R.drawable.ic_schedule_red, 4, "Lịch nhắc nhở", NotificationType.SCHEDULE_REMINDER.getValue());

        private int resourceId;
        private int value;
        private String meaning;
        private int type;

        IconType(int resourceId, int value, String meaning, int type) {
            this.resourceId = resourceId;
            this.value = value;
            this.meaning = meaning;
            this.type = type;
        }

        public static IconType getType(int value) {
            return Stream.of(values()).filter(v -> v.getValue() == value).findFirst().get();
        }

        public int getResourceId() {
            return resourceId;
        }

        public int getValue() {
            return value;
        }

        public String getMeaning() {
            return meaning;
        }

        public int getType() {
            return type;
        }
    }

    public static class Builder implements Comparable<Builder> {
        private List<Boolean> list;
        private Context context;
        private Calendar calendar;
        private Date date;

        public Builder(Context context) {
            this.context = context;
            list = new ArrayList<>(Collections.nCopies(IconType.values().length, false));
        }

        public Builder display(IconType type) {
            this.list.set(type.getValue(), Boolean.TRUE);
            return this;
        }

        public Builder setCalendar(Calendar calendar) {
            this.calendar = calendar;
            this.date = calendar.getTime();
            return this;
        }

        public CustomEventDay build() {
            int[] values = IntStream.range(0, list.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> i, list::get))
                    .entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .mapToInt(Map.Entry::getKey)
                    .toArray();

            int count = values.length;
            switch (count) {
                case 0:
                    throw new IllegalStateException();
                case 1:
                    IconType iconType = IconType.getType(values[0]);
                    Drawable drawable = context.getDrawable(iconType.resourceId);
                    return new CustomEventDay(calendar,
                            drawable,
                            Collections.singletonList(iconType));
                default:
                    IconType[] iconTypes = Arrays.stream(values)
                            .mapToObj(IconType::getType)
                            .collect(Collectors.toSet())
                            .toArray(new IconType[0]);
                    Drawable[] drawables = Arrays.stream(iconTypes)
                            .map(t -> context.getResources().getDrawable(t.getResourceId(), null))
                            .toArray(Drawable[]::new);
                    LayerDrawable layerDrawable = new LayerDrawable(drawables);
                    for (int i = 0; i < count; i++) {
                        layerDrawable.setLayerSize(i, 100, 100);
                    }
                    if (count == 2) {
                        layerDrawable.setLayerGravity(0, Gravity.START);
                        layerDrawable.setLayerGravity(1, Gravity.END);
                        layerDrawable.setLayerInsetLeft(1, 40);
                    } else if (count == 3) {
                        layerDrawable.setLayerGravity(0, Gravity.START);
                        layerDrawable.setLayerGravity(1, Gravity.CENTER);
                        layerDrawable.setLayerGravity(2, Gravity.END);
                        layerDrawable.setLayerInsetLeft(2, 80);
                    }

                    return new CustomEventDay(calendar,
                            layerDrawable,
                            Arrays.asList(iconTypes));
            }
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof CustomEventDay.Builder)
                return this.date.equals(((Builder) obj).date);
            return false;
        }

        @Override
        public int compareTo(Builder o) {
            return this.date.compareTo(o.date);
        }
    }
}
