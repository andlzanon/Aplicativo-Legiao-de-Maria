package andl.zanon.navegacao;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static andl.zanon.navegacao.RecyclerViewFragment.mAdapter;

/**
 * Created by Andre on 08/03/2017.
 */

public class TrabalhosDialogFragment extends DialogFragment implements TextView.OnEditorActionListener{

    private EditText nomeTrabalho;
    private EditText qtadeTrabalho;

    private Trabalhos mTrabalho;

    private static final String EXTRA_TRABALHO = "trabalho";
    private static final String DIALOG_TAG = "dialog";

    public static TrabalhosDialogFragment novaInstancia(Trabalhos trabalhos){
        Bundle params = new Bundle();
        params.putSerializable(EXTRA_TRABALHO, trabalhos);

        TrabalhosDialogFragment dialogFragment = new TrabalhosDialogFragment();
        dialogFragment.setArguments(params);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        mTrabalho = (Trabalhos)getArguments().getSerializable(EXTRA_TRABALHO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View layout = inflater.inflate(R.layout.fragment_dialog_trabalhos, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        nomeTrabalho = (EditText)layout.findViewById(R.id.dialogEditNome);
        nomeTrabalho.requestFocus();
        qtadeTrabalho = (EditText)layout.findViewById(R.id.dialogEditQtade);
        qtadeTrabalho.setOnEditorActionListener(this);

        if(mTrabalho != null){
            nomeTrabalho.setText(mTrabalho.nome);
            qtadeTrabalho.setText(Integer.toString(mTrabalho.quantidade));
        }

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final Button button = (Button)layout.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              salvaEdita();
            }
        });

        final Button buttonCancel = (Button) layout.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return layout;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if(EditorInfo.IME_ACTION_DONE == actionId){
            salvaEdita();
            return true;
        }

        return  false;
    }

    public void abrir(FragmentManager fm){
        if(fm.findFragmentByTag(DIALOG_TAG) == null){
            show(fm, DIALOG_TAG);
        }
    }

    public void salvaEdita(){
        Fragment fragment = getParentFragment();
        if(fragment instanceof AoSavarTrabalho){
            if(mTrabalho == null) {
                try {
                    if(nomeTrabalho.getText().toString().equals(""))
                        Toast.makeText(getContext(), R.string.dialogExecption, Toast.LENGTH_LONG).show();
                    else{
                        mTrabalho = new Trabalhos(Integer.parseInt(qtadeTrabalho.getText().toString()),
                                nomeTrabalho.getText().toString());
                        AoSavarTrabalho adiciona = (AoSavarTrabalho) fragment;
                        adiciona.adicionanoBD(mTrabalho);
                    }

                }catch (java.lang.NullPointerException | java.lang.NumberFormatException e){
                    Toast.makeText(getContext(), R.string.dialogExecption, Toast.LENGTH_LONG).show();
                }
            }

            else{
                try{
                    if(nomeTrabalho.getText().toString().equals(""))
                        Toast.makeText(getContext(), R.string.dialogExecption, Toast.LENGTH_LONG).show();
                    else{
                        mTrabalho.quantidade = Integer.parseInt(qtadeTrabalho.getText().toString());
                        mTrabalho.nome = nomeTrabalho.getText().toString();
                        AoEditarTrabalho edita = (AoEditarTrabalho) fragment;
                        edita.atualizaBD(mTrabalho);

                    }

                }catch (java.lang.NullPointerException | java.lang.NumberFormatException e){
                    Toast.makeText(getContext(),  R.string.dialogExecption, Toast.LENGTH_LONG).show();
                }
            }

            dismiss();
            //refresh a tela de recycler view sempre logo antes de sair de uma mudança,
            //seja de adcionar quanto de modificar
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface AoSavarTrabalho {
        void adicionanoBD(Trabalhos trabalho);
    }

    public interface AoEditarTrabalho{
        void atualizaBD(Trabalhos trabalho);
    }
}
