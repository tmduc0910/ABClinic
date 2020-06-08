package com.abclinic.dto;

import com.abclinic.entity.HealthIndexSchedule;

import java.io.Serializable;
import java.util.List;

public class ScheduleListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<HealthIndexSchedule> list;

    public ScheduleListDto(List<HealthIndexSchedule> list) {
        this.list = list;
    }

    public List<HealthIndexSchedule> getList() {
        return list;
    }

    public void setList(List<HealthIndexSchedule> list) {
        this.list = list;
    }
}
