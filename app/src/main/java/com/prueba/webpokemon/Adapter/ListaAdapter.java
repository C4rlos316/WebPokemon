
package com.prueba.webpokemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prueba.webpokemon.Models.Pokemon;
import com.prueba.webpokemon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemons;

    private Context context;

    public ListaAdapter(Context context) {

        this.context=context;
        pokemons=new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pokemon p = pokemons.get(position);
        holder.txtNombre.setText(p.getName());

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"
                +p.getNumber()+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgPokemon);



    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void adicionarPokemon(ArrayList<Pokemon> listaPokemon) {


        pokemons.addAll(listaPokemon);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPokemon;
        private TextView txtNombre;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPokemon=itemView.findViewById(R.id.fotoImagenView);
            txtNombre=itemView.findViewById(R.id.nombreTextView);
        }
    }
}
