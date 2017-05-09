package andl.zanon.navegacao;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andre on 08/03/2017.
 */

public class TrabalhosRecyclerAdapter extends RecyclerView.Adapter<TrabalhosRecyclerAdapter.TrabalhosViewHolder>{

    public static class TrabalhosViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView nome;
        TextView qtade;

        TrabalhosViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardview);
            nome = (TextView)itemView.findViewById(R.id.nomeTrabalho);
            qtade = (TextView)itemView.findViewById(R.id.quantidadeTrabalho);
        }
    }

    private ArrayList<Trabalhos> trabalhos;
    FragmentManager fragmentManager;

    TrabalhosRecyclerAdapter(Context context, ArrayList<Trabalhos> trabalhos, FragmentManager fragmentManager){
        this.trabalhos = trabalhos;
        this.fragmentManager = fragmentManager;

        //mudei
        TrabalhoRepositorio repo = new TrabalhoRepositorio(context);
        repo.listaTrabalhos(trabalhos);
    }

    @Override
    public TrabalhosRecyclerAdapter.TrabalhosViewHolder onCreateViewHolder(ViewGroup parent, int i){
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trabalho, parent, false);
        TrabalhosViewHolder tvh = new TrabalhosViewHolder(layout);
        return tvh;
    }

    @Override
    public void onBindViewHolder(final TrabalhosViewHolder trabalhoViewHolder, final int i) {
        trabalhoViewHolder.nome.setText(trabalhos.get(i).nome);
        trabalhoViewHolder.qtade.setText(Integer.toString(trabalhos.get(i).quantidade));

        trabalhoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrabalhosDialogFragment trabalhosDialogFragment =
                        TrabalhosDialogFragment.novaInstancia(trabalhos.get(trabalhoViewHolder.getAdapterPosition()));
                trabalhosDialogFragment.abrir(fragmentManager);
                notifyItemChanged(trabalhoViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount(){
        return trabalhos.size();
    }
}
