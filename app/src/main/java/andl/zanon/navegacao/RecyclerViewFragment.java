package andl.zanon.navegacao;

import android.os.Bundle;
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

/**
 * Created by Andre on 08/03/2017.
 */

public class RecyclerViewFragment extends Fragment implements TrabalhosDialogFragment.AoSavarTrabalho,
        TrabalhosDialogFragment.AoEditarTrabalho{

    private ArrayList<Trabalhos> mTrabalhos = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static RecyclerViewFragment novaInstancia(){
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        return recyclerViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.recycler_view_layout, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FragmentManager fm = getChildFragmentManager();

        mAdapter = new TrabalhosRecyclerAdapter(getContext(), mTrabalhos, fm);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)layout.findViewById(R.id.addTrabalhos);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                TrabalhosDialogFragment trabalhosDialogFragment = TrabalhosDialogFragment.novaInstancia(null);
                trabalhosDialogFragment.abrir(getChildFragmentManager());
            }
        });

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //mudei
                excluinoBD(mTrabalhos.get(viewHolder.getAdapterPosition()));
                Log.d("ExcluindoTrabalho", "Excluido " +viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                Collections.swap(mTrabalhos, fromPos, toPos);
                mAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }
        });mIth.attachToRecyclerView(mRecyclerView);

        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_eventos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.buttonDelete){
            Toast.makeText(getContext(), getResources().getString(R.string.mensagem_lixo_trabalho), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //mudei
    public void adicionanoBD(Trabalhos trabalho){
        TrabalhoRepositorio repo = new TrabalhoRepositorio(getContext());
        mTrabalhos.add(trabalho);
        repo.inserir(trabalho);
    }

    //mudei
    public void excluinoBD(Trabalhos trabalho){
        TrabalhoRepositorio repo = new TrabalhoRepositorio(getContext());
        mTrabalhos.remove(trabalho);
        repo.excluir(trabalho);

        ArrayList<Trabalhos> teste = new ArrayList<>();
        repo.listaTrabalhos(teste);
        for(int i = 0; i < teste.size(); i++){
            Log.d("TAG", "T = " + teste.get(i).id);
        }
    }

    public void atualizaBD(Trabalhos trabalho){
        TrabalhoRepositorio repo = new TrabalhoRepositorio(getContext());
        repo.atualizar(trabalho);
    }
}
