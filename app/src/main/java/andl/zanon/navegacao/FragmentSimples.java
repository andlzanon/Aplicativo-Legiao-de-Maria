package andl.zanon.navegacao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andre on 06/03/2017.
 */

public class FragmentSimples extends Fragment {

    private static final String EXTRA_IDCLASSER = "ID_Classe_R";

    public static FragmentSimples novaInstancia(int inteitroClassR){
        Bundle params = new Bundle();
        params.putInt(EXTRA_IDCLASSER, inteitroClassR);

        FragmentSimples fs = new FragmentSimples();
        fs.setArguments(params);
        return fs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle params = getArguments();
        int telaAtual = params.getInt(EXTRA_IDCLASSER);

        View layout = inflater.inflate(R.layout.fragment_simples, container, false);

        //tria o "lixo" da toolbar
        getActivity().invalidateOptionsMenu();

        TextView textView = (TextView)layout.findViewById(R.id.texto_fragment_simples);

        if(telaAtual == R.string.oracao_frank_duff){
            textView.setText(R.string.oracao_frank_duff);
        }

        else if(telaAtual == R.string.ordem_da_reuniao){
            textView.setText(R.string.ordem_da_reuniao);
        }

        return layout;
    }
}
