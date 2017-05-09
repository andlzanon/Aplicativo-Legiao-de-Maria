package andl.zanon.navegacao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by André on 15/10/2016.
 */

public class AbasPagerAdapter extends FragmentPagerAdapter{

    private String[] titulosAbas;
    private int ClassRID;

    public AbasPagerAdapter(Fragment ctx, FragmentManager fm, int id) {
        super(fm);
        //id é recebido com sucesso e consegue-se acessar o id é da classe R.
        titulosAbas = ctx.getResources().getStringArray(id);
        ClassRID = id;
    }

    @Override
    public Fragment getItem(int position) {
        // os parametros são: posiçao: para se saber qual texto deverá ser colocado no caso das orações
        // classRID: para saber qual é o array que estou usando se é o do terço ou oração
        return ContentAbasFragment.novaInstancia(position, ClassRID);
    }

    @Override
    public int getCount() {
        //tamanho do vetor do array
        return titulosAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        return titulosAbas[position].toUpperCase(l);
    }
}
