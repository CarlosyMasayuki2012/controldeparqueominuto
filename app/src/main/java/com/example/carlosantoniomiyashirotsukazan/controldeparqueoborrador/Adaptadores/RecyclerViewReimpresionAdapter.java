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

        holder.tvplaca.setText(listaEstacionamiento.get(position).getPlaca());
        holder.tvminutoingreso.setText(listaEstacionamiento.get(position).getFecha_ingreso());
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

            tvminutoingreso = itemView.findViewById(R.id.tvminuto);
            tvplaca = itemView.findViewById(R.id.tvPlaca);
            tvhoraingreso = itemView.findViewById(R.id.tvHoraIngreso);
        }
    }
}


