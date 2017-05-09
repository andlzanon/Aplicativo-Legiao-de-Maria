package andl.zanon.navegacao;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andre on 27/02/2017.
 */

public class AbaFragment extends Fragment{

    ViewPager mViewPager;
    TabLayout mTabLayout;
    //RId é a variável inteira que é unica para o array em que será utilizado para o Pager Adapter
    private static final String EXTRA_ID = "R.ID";

    public static AbaFragment novaInstancia(int id){
        Bundle params = new Bundle();
        params.putInt(EXTRA_ID, id);

        AbaFragment f = new AbaFragment();
        f.setArguments(params);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle params = getArguments();
        int RId = params.getInt(EXTRA_ID);

        View layout = inflater.inflate(R.layout.activity_telas_abas, container, false);

        //tria o "lixo" da toolbar
        getActivity().invalidateOptionsMenu();

        //Passa-se o RId aqui tbm para o Adapter saber qual array utilizar
        // getChildFragment ao invez de getSupportFragmentManager pois estamos trabalhando com um fragment
        // dentro de outro fragment
        AbasPagerAdapter pagerAdapter = new AbasPagerAdapter(this, getChildFragmentManager(), RId);
        mViewPager = (ViewPager)layout.findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout)layout.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        return layout;
    }

}
