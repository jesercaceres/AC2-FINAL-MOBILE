package com.example.atividade04;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade04.adapter.AlunoAdapter;
import com.example.atividade04.models.Aluno;
import com.example.atividade04.services.AlunoService;
import com.example.atividade04.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {
    RecyclerView recyclerViewAlunos;
    AlunoAdapter alunoAdapter;
    ArrayList<Aluno> alunos = new ArrayList<>();
    AlunoService alunoService;

    @Override
    protected void onResume() {
        super.onResume();
        buscaAlunos();
    }

    public void configuraRecycler(){
        alunoAdapter = new AlunoAdapter(alunos);
        recyclerViewAlunos.setAdapter(alunoAdapter);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerViewAlunos.setLayoutManager(layoutManager);
        recyclerViewAlunos.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        );
    }

    private void buscaAlunos() {
        retrofit2.Call<List<Aluno>> call = alunoService.listarAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                alunos = (ArrayList<Aluno>) response.body();
                configuraRecycler();
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Log.e("ERRO", "Erro" + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_alunos);

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        alunoService = ApiClient.getAlunoService();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listaAlunos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
