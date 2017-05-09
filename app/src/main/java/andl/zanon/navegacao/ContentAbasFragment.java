package andl.zanon.navegacao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andre on 01/03/2017.
 */

public class ContentAbasFragment extends Fragment {

    private static final String EXTRA_POSICAO = "posicaoAtual";
    private static final String EXTRA_CLASSER = "inteiroClasseR";

    public static ContentAbasFragment novaInstancia(int posicao, int inteiroR){
        Bundle params = new Bundle();
        params.putInt(EXTRA_POSICAO, posicao);
        params.putInt(EXTRA_CLASSER, inteiroR);

        ContentAbasFragment caf = new ContentAbasFragment();
        caf.setArguments(params);
        return caf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        Bundle params = getArguments();
        int abaAtual = params.getInt(EXTRA_POSICAO);
        int identificadorClasseR = params.getInt(EXTRA_CLASSER);

        View layout;
        if(identificadorClasseR == R.array.tercos){
            layout = inflater.inflate(R.layout.aba_terco_fragment_layout, container, false);
            textoDoMisterio(layout, abaAtual);
        }
        else{
            layout = inflater.inflate(R.layout.aba_oracoes_fragment_layout, container, false);
            textoDaOracao(layout, abaAtual);
        }

        return layout;
    }

    //método que coloca corretamente a string do dia e de cada mistério. Dessa maneira, se esta na aba Gozoso
    // seus mistérioso sao colcados corretamente a partir do id do layout
    private void textoDoMisterio(View layout, int abaAtual){
        //definição de variáveis
        String[] misterios;

        TextView diasMisterio = (TextView)layout.findViewById(R.id.diasMisterio);
        TextView priMisterio = (TextView)layout.findViewById(R.id.priMisterio);
        TextView segMisterio = (TextView)layout.findViewById(R.id.segMisterio);
        TextView terMisterio = (TextView)layout.findViewById(R.id.terMisterio);
        TextView quaMisterio = (TextView)layout.findViewById(R.id.quaMisterio);
        TextView quiMisterio = (TextView)layout.findViewById(R.id.quiMisterio);

        //verifica em qual aba está e a partir da aba seta o vetor com as strings do mistério respectivo
        switch (abaAtual){
            case 0:
                misterios = layout.getResources().getStringArray(R.array.Gozoso);
                break;

            case 1:
                misterios = layout.getResources().getStringArray(R.array.Glorioso);
                break;

            case 2:
                misterios = layout.getResources().getStringArray(R.array.Luminoso);
                break;

            case 3:
                misterios = layout.getResources().getStringArray(R.array.Doloroso);
                break;

            //teoricamente nunca ocorre, vetor vai de 0 a 3
            default:
                misterios = layout.getResources().getStringArray(R.array.Gozoso);
                break;
        }

        //seta texto a partir do array
        diasMisterio.setText(misterios[0]);
        priMisterio.setText(misterios[1]);
        segMisterio.setText(misterios[2]);
        terMisterio.setText(misterios[3]);
        quaMisterio.setText(misterios[4]);
        quiMisterio.setText(misterios[5]);
    }

    //metodo coloca na aba correta a oração da téssera respectiva
    private void textoDaOracao(View layout, int abaAtual){
        //definição de variáveis
        TextView oracaoTessera = (TextView)layout.findViewById(R.id.oracao);

        // seta oração a partir da aba
        switch (abaAtual){
            //aba 0, logo, orações inciais.
            case 0:
               oracaoTessera.setText(layout.getResources().getString(R.string.oracoes_iniciais));
                break;

            case 1:
                oracaoTessera.setText(layout.getResources().getString(R.string.catena));
                break;

            case 2:
                oracaoTessera.setText(layout.getResources().getString(R.string.oracoes_finais));
        }
    }
}
