package andl.zanon.navegacao;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andre on 22/03/2017.
 */

public class EventosRecyclerAdapter extends RecyclerView.Adapter<EventosRecyclerAdapter.EventosViewHolder> {

    public static class EventosViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView nomeDoEvento;
        TextView localDoEvento;
        TextView horaDoEvento;
        TextView dataDoEvento;

        public EventosViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.evento_cardview);
            nomeDoEvento = (TextView) itemView.findViewById(R.id.nomeEvento);
            localDoEvento = (TextView) itemView.findViewById(R.id.localEvento);
            horaDoEvento = (TextView) itemView.findViewById(R.id.horaEvento);
            dataDoEvento = (TextView) itemView.findViewById(R.id.dataEvento);
        }
    }

    public static ArrayList<Evento> eventos;
    FragmentManager fragmentManager;
    Context context;
    public static final String EXTRA_EVENTO = "evento";

    EventosRecyclerAdapter(Context context, ArrayList<Evento> eventos, FragmentManager fragmentManager){
        this.eventos = eventos;
        this.fragmentManager = fragmentManager;
        this.context = context;

        EventosRepositorio er = new EventosRepositorio(context);
        er.listaEventos(eventos);
    }

    @Override
    public EventosRecyclerAdapter.EventosViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evento, parent, false);
        EventosViewHolder evh = new EventosViewHolder(layout);
        return evh;
    }

    @Override
    public void onBindViewHolder(final EventosRecyclerAdapter.EventosViewHolder eventosViewHolder, int i) {
        eventosViewHolder.nomeDoEvento.setText(eventos.get(i).nome);
        eventosViewHolder.localDoEvento.setText(eventos.get(i).local);
        eventosViewHolder.horaDoEvento.setText(eventos.get(i).horario);
        eventosViewHolder.dataDoEvento.setText(eventos.get(i).data);

        eventosViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EventosDialogFragment eventosDialogFragment = EventosDialogFragment.novaInstancia(eventos.get(eventosViewHolder.getAdapterPosition()));
                eventosDialogFragment.abrir(fragmentManager);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
