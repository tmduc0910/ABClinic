//package com.abclinic.asynctask;
//
//import android.content.Context;
//
//import com.abclinic.dto.GetDataDto;
//import com.abclinic.room.entity.DataEntity;
//import com.abclinic.room.utils.AppDatabase;
//
//import java.util.List;
//
//public class GetDataTask extends CustomAsyncTask<GetDataDto, Void, List<DataEntity>> {
//    private AppDatabase appDatabase;
//
//    public GetDataTask(Context context, AppDatabase appDatabase) {
//        super(context, null);
//        this.appDatabase = appDatabase;
//    }
//
//    @Override
//    protected List<DataEntity> doInBackground(GetDataDto... getDataDtos) {
//        GetDataDto dto = getDataDtos[0];
//        return appDatabase.getDataDao().getDatas(dto.getUserId(), dto.getMonth(), dto.getYear());
//    }
//}
