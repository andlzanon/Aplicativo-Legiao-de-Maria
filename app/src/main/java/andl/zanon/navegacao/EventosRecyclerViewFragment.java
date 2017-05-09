package andl.zanon.navegacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static andl.zanon.navegacao.EventosRecyclerAdapter.EXTRA_EVENTO;

/**
 * Created by Andre on 23/03/2017.
 */

public class EventosRecyclerViewFragment extends Fragment implements EventosDialogFragment.AoEditarEvento, EventosDialogFragment.AoSalvarEvento{

    public static ArrayList<Evento> mEventos = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapterEvento;
    private RecyclerView.LayoutManager mLayoutManager;

    public static EventosRecyclerViewFragment novaInstancia() {
        EventosRecyclerViewFragment mRcvf = new EventosRecyclerViewFragment();
        return  mRcvf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.eventos_recycler_view, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.eventosRecyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fragmentManager = getChildFragmentManager();

        mAdapterEvento = new EventosRecyclerAdapter(getContext(), mEventos, fragmentManager);
        mRecyclerView.setAdapter(mAdapterEvento);

        FloatingActionButton fab = (FloatingActionButton)layout.findViewById(R.id.addEventos);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                EventosDialogFragment eventosDialogFragment = EventosDialogFragment.novaInstancia(null);
                eventosDialogFragment.abrir(getChildFragmentManager());
            }
        });

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                excluidoBD(mEventos.get(viewHolder.getAdapterPosition()));
                mAdapterEvento.notifyDataSetChanged();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                Collections.swap(mEventos, fromPos, toPos);
                mAdapterEvento.notifyItemMoved(fromPos, toPos);
                return true;
            }
        });mIth.attachToRecyclerView(mRecyclerView);

        return layout;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_eventos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_eventos, menu);

        menu.getItem(0).setEnabled(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.buttonDelete){
            Toast.makeText(getContext(), getResources().getString(R.string.mensagem_lixo_evento), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void excluidoBD(Evento evento){
        EventosRepositorio er = new EventosRepositorio(getContext());
        er.excluir(evento);
        mEventos.remove(evento);

        ArrayList<Evento> teste = new ArrayList<>();
        er.listaEventos(teste);
        for(int i = 0; i < teste.size(); i++){
            Log.d("TAG", "E = " + teste.get(i).nome);
        }
    }

    public void adicionanoBD(Evento evento){
        EventosRepositorio er = new EventosRepositorio(getContext());
        er.inserir(evento);
        mEventos.add(evento);
    }

    public void editaEvento(Evento evento){
        EventosRepositorio er = new EventosRepositorio(getContext());
        er.atualizar(evento);
        mAdapterEvento.notifyDataSetChanged();
    }
}
