package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.HttpStatus;
import com.abclinic.dto.InquiryDto;
import com.abclinic.dto.RequestCreateInquiryDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.exception.BadRequestException;
import com.abclinic.exception.ForbiddenException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.InquiryMapper;
import com.example.abclinic.R;

import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadInquiryTask extends CustomAsyncTask<InquiryDto, Void, Inquiry> {

    public UploadInquiryTask(Context context, String storageKey, AsyncResponse<Inquiry> delegate) {
        super(context, storageKey, delegate);
    }

    @Override
    protected Inquiry doInBackground(InquiryDto... inquiryDtos) {
        try {
            InquiryDto inquiry = inquiryDtos[0];
            RequestCreateInquiryDto requestCreateInquiryDto = new RequestCreateInquiryDto(
                    inquiry.getType(),
                    inquiry.getAlbumId(),
                    inquiry.getContent(),
                    inquiry.getDate() + " " + inquiry.getTime() + ":00"
            );

            String uid = storageService.getUid();
            Retrofit retrofit = RetrofitClient.getClient(uid);
            Response<Inquiry> response = retrofit.create(InquiryMapper.class).createInquiry(requestCreateInquiryDto).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                switch (response.code()) {
                    case HttpStatus.BAD_REQUEST:
                        this.exception = new BadRequestException(getStringResource(R.string.inq_failed_wrong_type_err));
                        break;
                    case HttpStatus.FORBIDDEN:
                        this.exception = new ForbiddenException(getStringResource(R.string.inq_failed_queue_err));
                        break;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
