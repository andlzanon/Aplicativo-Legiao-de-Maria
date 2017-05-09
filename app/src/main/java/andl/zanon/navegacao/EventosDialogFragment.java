package andl.zanon.navegacao;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

import static andl.zanon.navegacao.EventosRecyclerViewFragment.mAdapterEvento;
import static andl.zanon.navegacao.RecyclerViewFragment.mAdapter;

/**
 * Created by Andre on 29/03/2017.
 */

public class EventosDialogFragment extends DialogFragment {

    private static final String EXTRA_EVENTO = "evento";
    private static final String DIALOG_TAG_EVENTO = "dialog";
    private Evento mEvento;

    EditText nome;
    EditText local;
    EditText data;
    EditText horario;
    DecimalFormat mFormato = new DecimalFormat("00");

    public static EventosDialogFragment novaInstancia(Evento evento){
        Bundle params = new Bundle();
        params.putSerializable(EXTRA_EVENTO, evento);

        EventosDialogFragment eventosDialogFragment = new EventosDialogFragment();
        eventosDialogFragment.setArguments(params);
        return eventosDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvento = (Evento) getArguments().getSerializable(EXTRA_EVENTO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.activity_novo_evento, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        nome = (EditText) layout.findViewById(R.id.edtTxtNomeEvento);
        local = (EditText) layout.findViewById(R.id.edtTxtLocalEvento);
        data = (EditText) layout.findViewById(R.id.edtDataLocalEvento);
        horario = (EditText) layout.findViewById(R.id.edtHorarioLocalEvento);

        if (mEvento != null) {
            nome.setText(mEvento.nome);
            local.setText(mEvento.local);
            data.setText(mEvento.data);
            horario.setText(mEvento.horario);
        }

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar tempoAtual = Calendar.getInstance();
                int hora = tempoAtual.get(Calendar.HOUR_OF_DAY);
                int minuto = tempoAtual.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String tempo = mFormato.format(hourOfDay) + ":" + mFormato.format(minute);
                        horario.setText(tempo);
                    }
                }, hora, minuto, true);
                timePickerDialog.show();
            }
        });


        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dataAtual = Calendar.getInstance();
                int dia = dataAtual.get(Calendar.DAY_OF_MONTH);
                int mes = dataAtual.get(Calendar.MONTH);
                int ano = dataAtual.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        String date = mFormato.format(monthOfYear) + "/" + mFormato.format(dayOfMonth) + "/" + year;
                        data.setText(date);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        final Button button = (Button) layout.findViewById(R.id.button_save_evento);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaEdita();
            }
        });

        final Button buttonCancel = (Button) layout.findViewById(R.id.button_cancel_evento);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return layout;
    }

    public void abrir(FragmentManager fm){
        if(fm.findFragmentByTag(DIALOG_TAG_EVENTO) == null){
            show(fm, DIALOG_TAG_EVENTO);
        }
    }

    public void salvaEdita(){
        Fragment fragment = getParentFragment();
        if (mEvento == null) {
            if(nome.getText().toString().equals("") || local.getText().toString().equals("")
                    || data.getText().toString().equals("") || horario.getText().toString().equals("")){
                Toast.makeText(getContext(), getResources().getString(R.string.dialogExecption), Toast.LENGTH_LONG).show();
            }
            else {
                mEvento = new Evento(nome.getText().toString(), local.getText().toString(),
                        horario.getText().toString(), data.getText().toString());
                AoSalvarEvento aoSalvarEvento = (AoSalvarEvento) fragment;
                aoSalvarEvento.adicionanoBD(mEvento);
            }
        }

        else{
            if(nome.getText().toString().equals("") || local.getText().toString().equals("")
                    || data.getText().toString().equals("") || horario.getText().toString().equals("")){
                Toast.makeText(getContext(), getResources().getString(R.string.dialogExecption), Toast.LENGTH_LONG).show();
            }
            else {
                mEvento.nome = nome.getText().toString();
                mEvento.local = local.getText().toString();
                mEvento.horario = horario.getText().toString();
                mEvento.data = data.getText().toString();
                AoEditarEvento aoEditarEvento = (AoEditarEvento) fragment;
                aoEditarEvento.editaEvento(mEvento);
            }
        }

        dismiss();
        //refresh a tela de recycler view sempre logo antes de sair de uma mudança,
        //seja de adcionar quanto de modificar
        mAdapterEvento.notifyDataSetChanged();
    }

    public interface AoSalvarEvento{
        void adicionanoBD(Evento evento);
    }

    public interface AoEditarEvento{
        void editaEvento(Evento evento);
    }

}
