package com.example.atividade04.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String BASE_URL_MOCKAPI = "https://673d0e124db5a341d833e196.mockapi.io/";
    private static String BASE_URL_VIACEP = "https://viacep.com.br/ws/";

    private static Retrofit retrofitMockApi, retrofitViaCep;

    // pega o retorfit do mock:
    public static Retrofit getRetrofitMockApi() {
        if (retrofitMockApi == null) {
            retrofitMockApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL_MOCKAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitMockApi;
    }

    // pega o retrofit do viacep:
    public static Retrofit getRetrofitViaCep() {
        if (retrofitViaCep == null) {
            retrofitViaCep = new Retrofit.Builder()
                    .baseUrl(BASE_URL_VIACEP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitViaCep;
    }

    // retornando os serviços:
    public static AlunoService getAlunoService() {
        return getRetrofitMockApi().create(AlunoService.class);
    }

    public static ViaCepService getViaCepService() {
        return getRetrofitViaCep().create(ViaCepService.class);
    }
}
