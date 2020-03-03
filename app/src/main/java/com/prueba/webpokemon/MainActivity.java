package com.prueba.webpokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.prueba.webpokemon.Adapter.ListaAdapter;
import com.prueba.webpokemon.Models.Pokemon;
import com.prueba.webpokemon.Models.PokemonRespuesta;
import com.prueba.webpokemon.PokeApi.PokeApiServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private RecyclerView recyclerView;

    private ListaAdapter adapter;

    private int offset;

    private boolean aptoCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);

        adapter=new ListaAdapter(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy>0){

                    int visibleItemCount= layoutManager.getChildCount();
                    int totalItemCount= layoutManager.getItemCount();
                    int pastVisibleItem= layoutManager.findFirstVisibleItemPosition();

                    if (aptoCargar){

                        if (visibleItemCount+pastVisibleItem>=totalItemCount){

                            aptoCargar=false;
                            offset +=20;
                            obtenerDatos(offset);
                        }
                    }


                }
            }
        });



        retrofit=new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoCargar=true;
        offset = 0;


        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset) {


        PokeApiServices services=retrofit.create(PokeApiServices.class);
        Call<PokemonRespuesta> pokemonRespuestaCall=services.obtenerListaPokemon(20,offset);

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {

                aptoCargar=true;
                if (response.isSuccessful()){

                    PokemonRespuesta pokemonRespuesta= response.body();

                    ArrayList<Pokemon> listaPokemon=pokemonRespuesta.getResults();


                    adapter.adicionarPokemon(listaPokemon);


                }
                else {

                    Log.e("","onResponse"+response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                aptoCargar=true;

                Log.e("",t.getMessage());

            }
        });


    }
}
