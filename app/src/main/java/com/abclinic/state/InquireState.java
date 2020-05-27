package com.abclinic.state;

import com.abclinic.entity.Inquiry;
import com.abclinic.retrofit.api.InquiryMapper;

import retrofit2.Call;
import retrofit2.Retrofit;

public class InquireState extends StateImpl<Inquiry> {
    public InquireState(int type, long id) {
        super(type, id);
    }

    @Override
    public Call<Inquiry> execute(Retrofit retrofit) {
        return retrofit.create(InquiryMapper.class).getInquiry(getId());
    }
}
