package com.example.atividade04.services;

import com.example.atividade04.models.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlunoService {

    @POST("alunos")
    Call<Aluno> adicionarAlunos(@Body Aluno aluno);

    @GET("alunos")
    Call<List<Aluno>> listarAlunos();
}
