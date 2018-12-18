package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Entidades.Estacionamiento;
import com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.R;

import java.util.List;

public class RecyclerViewReimpresionAdapter extends RecyclerView.Adapter<RecyclerViewReimpresionAdapter.
        ReimpresionHolder> implements View.OnClickListener {

    List<Estacionamiento> listaEstacionamiento;
    private View.OnClickListener listener;

    public RecyclerViewReimpresionAdapter(List<Estacionamiento> listaEstacionamiento) {
        this.listaEstacionamiento = listaEstacionamiento;
    }

    @Override
    public RecyclerViewReimpresionAdapter.ReimpresionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_estacionamientos,
                parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(this);
        return new ReimpresionHolder(vista);

    }

    @Override
    public void onBindViewHolder(RecyclerViewReimpresionAdapter.ReimpresionHolder holder, int position) {

        holder.tvplaca.setText(listaEstacionamiento.get(position).getPlaca().toString());
        holder.tvminutoingreso.setText(listaEstacionamiento.get(position).getFecha_ingreso().toString());
        String hor = String.valueOf(listaEstacionamiento.get(position).getHora_ingreso() + ":"+listaEstacionamiento.get(position).getMinuto_ingreso());
        holder.tvhoraingreso.setText(hor);
    }

    @Override
    public int getItemCount() {
        return listaEstacionamiento.size();
    }

    @Override
    public void onClick(View view) {

        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ReimpresionHolder extends RecyclerView.ViewHolder {

        TextView tvplaca, tvhoraingreso, tvminutoingreso;

        public ReimpresionHolder(View itemView) {
            super(itemView);

            //tvminutoingreso = itemView.findViewById(R.id.tvminuto);
            tvplaca = itemView.findViewById(R.id.tvPlaca);
            //tvhoraingreso = itemView.findViewById(R.id.tvHoraIngreso);

        }
    }
}

/*

public class EstacionamientoAdapter extends RecyclerView.Adapter<EstacionamientoAdapter.EstacionamientosHolder>
        implements View.OnClickListener{

    List<Estacionamientos> listaEstacionamientos;

    private View.OnClickListener listener;

    public EstacionamientoAdapter(List<Estacionamientos> listaEstacionamientos) {

        this.listaEstacionamientos = listaEstacionamientos;
    }

    @Override
    public EstacionamientoAdapter.EstacionamientosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_estacionamientos,
                parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(this);
        return new EstacionamientosHolder(vista);
    }

    @Override
    public void onBindViewHolder(EstacionamientoAdapter.EstacionamientosHolder holder, int position) {

        holder.tvplaca.setText(listaEstacionamientos.get(position).getPlaca().toString());
        holder.tvminutoingreso.setText(listaEstacionamientos.get(position).getFecha_Ingreso().toString());
        String hor = String.valueOf(listaEstacionamientos.get(position).getHora_Ingreso() + ":"+listaEstacionamientos.get(position).getMinuto_Ingreso());
        holder.tvhoraingreso.setText(hor);

    }

    @Override
    public int getItemCount() {
        return listaEstacionamientos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        if (listener != null){
            listener.onClick(v);
        }
    }

    public class EstacionamientosHolder extends RecyclerView.ViewHolder {

        TextView tvplaca, tvhoraingreso, tvminutoingreso;

        public EstacionamientosHolder(View itemView) {
            super(itemView);
            tvminutoingreso = itemView.findViewById(R.id.tvminuto);
            tvplaca = itemView.findViewById(R.id.tvPlaca);
            tvhoraingreso = itemView.findViewById(R.id.tvHoraIngreso);
        }
    }
*/

