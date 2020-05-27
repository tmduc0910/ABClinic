package com.abclinic.state;

import retrofit2.Call;
import retrofit2.Retrofit;

public interface State<T> {
    Call<T> execute(Retrofit retrofit);
}
