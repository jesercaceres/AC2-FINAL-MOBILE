package com.example.atividade04.services;

import com.example.atividade04.models.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {

    @GET("{cep}/json/")
    Call<Endereco> buscarCep(@Path("cep") String cep);
}
