package andl.zanon.navegacao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by André on 07/01/2017.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    EditTextPreference mNome;
    EditTextPreference mPresidium;
    CheckBoxPreference mBool;
    Preference mFoto;
    PreferenceCategory mPerfil;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        addPreferencesFromResource(R.xml.settings);

        mPerfil = (PreferenceCategory)findPreference("pref_category_perfil");
        mNome = (EditTextPreference)findPreference(getString(R.string.key_nome_perfil));
        mPresidium = (EditTextPreference)findPreference(getString(R.string.key_nome_presidium));
        mFoto = (Preference)findPreference(getString(R.string.key_foto_perfil));
        mBool = (CheckBoxPreference)findPreference(getString(R.string.key_foto_modificada));

        mPerfil.removePreference(mBool);
        preencherSumario(mNome);
        preencherSumario(mPresidium);
        preencherSumario(mPerfil);
    }

    private void preencherSumario(Preference preference){
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Object value = pref.getString(preference.getKey(), "");
        onPreferenceChange(preference, value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if(preference.equals(mNome)) {
            mNome.setSummary(stringValue);
        }
        else if(preference.equals(mPresidium)){
            mPresidium.setSummary(stringValue);
        }else if(preference.equals(mFoto)){
            mFoto.setSummary("Foto Definida está no perfil");
        }

        return true;
    }

}
