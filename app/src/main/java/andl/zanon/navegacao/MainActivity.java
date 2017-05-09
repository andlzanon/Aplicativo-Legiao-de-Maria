package andl.zanon.navegacao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mOpcaoSelecionada;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tiraa "lixo" da parte de cima da toolbar
        invalidateOptionsMenu();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selecionarOpcaoMenu(menuItem);
                return true;
            }
        });

        //Define os valores defaut na criação do sharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        //chama metodo para escrever o nome do perfil existente em sharedPreferences, mesmo que for o default
        escreveNomePerfil(mNavigationView);
        //chama metodo para escrever o nome do presidium existente em sharedPreferences, mesmo que for o default
        escrevePresidiumPerfil(mNavigationView);
        //chama metodo que desenha imagem caso exista uma em sharedPreferences
        desenhaFotoPerfil(mNavigationView);

        if(savedInstanceState == null)
            mOpcaoSelecionada = R.id.action_terco;

        else
            mOpcaoSelecionada = savedInstanceState.getInt("menuItem");

        selecionarOpcaoMenu(mNavigationView.getMenu().findItem(mOpcaoSelecionada));

        setToolbarTitle(getResources().getString(R.string.app_name));
    }

    public void escreveNomePerfil(NavigationView mNavigationView){
        TextView nomeUsuario;
        SharedPreferences sharedPreferences;
        // acessa o sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Acha o Header
        View header = mNavigationView.getHeaderView(0);
        //acha o TextView no Header
        nomeUsuario = (TextView)header.findViewById(R.id.nomeHeader);
        //seta o Text
        nomeUsuario.setText(sharedPreferences.getString(getString(R.string.key_nome_perfil), ""));

    }

    public void escrevePresidiumPerfil(NavigationView mNavigationView){
        TextView nomePresidium;
        SharedPreferences sharedPreferences;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        View header = mNavigationView.getHeaderView(0);
        nomePresidium = (TextView)header.findViewById(R.id.presidiumHeader);
        nomePresidium.setText(sharedPreferences.getString(getString(R.string.key_nome_presidium), ""));
    }

    public void desenhaFotoPerfil(NavigationView mNavigationView){
        SharedPreferences sharedPreferences;
        View header = mNavigationView.getHeaderView(0);
        ImageView imagemPerfil = (ImageView)header.findViewById(R.id.imgFotoPerfil);;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean bool = sharedPreferences.getBoolean(getString(R.string.key_foto_modificada), false);
        if(bool) {
            String imgPhoto = sharedPreferences.getString(getString(R.string.key_foto_perfil), "");
            byte[] decodedImage = Base64.decode(imgPhoto, 0);
            Bitmap imageDecodificada = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            Bitmap squareImage = Bitmap.createScaledBitmap(imageDecodificada, 1000, 1000, true);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), squareImage);
            roundedBitmapDrawable.setCornerRadius(Math.max(squareImage.getWidth(), squareImage.getHeight()));
            imagemPerfil.setImageDrawable(roundedBitmapDrawable);
        }

        else{
            Bitmap imgDefault = BitmapFactory.decodeResource(getResources(), R.drawable.ic_face_white_48dp);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), imgDefault);
            roundedBitmapDrawable.setCornerRadius(Math.max(imgDefault.getWidth(), imgDefault.getHeight()));
            imagemPerfil.setImageDrawable(roundedBitmapDrawable);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuItem", mOpcaoSelecionada);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selecionarOpcaoMenu(MenuItem menuItem) {

        ///opcao possivel tambem em caso de diferentes fragmentos
        mOpcaoSelecionada = menuItem.getItemId();
        AbaFragment abaFragment;
        FragmentSimples fragmentoSimples;
        RecyclerViewFragment recyclerViewFragment;
        EventosRecyclerViewFragment eventosRecyclerViewFragment;

        switch (mOpcaoSelecionada) {
            case R.id.action_terco:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_terco));

                //abaFragment passa um int como parametro para determinar qual é o aray do PagerAdapter
                abaFragment = AbaFragment.novaInstancia(R.array.tercos);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, abaFragment)
                        .commit();

                break;

            case R.id.action_oracoes:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_oracoes));


                //abaFragment passa um int como parametro para determinar qual é o aray do PagerAdapter
                abaFragment = AbaFragment.novaInstancia(R.array.oracoes);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, abaFragment)
                        .commit();

                break;

            case R.id.action_ordemReuniao:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_ordemReuniao));

                fragmentoSimples = FragmentSimples.novaInstancia(R.string.ordem_da_reuniao);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, fragmentoSimples)
                        .commit();
                break;

            case R.id.action_oracaoFrank:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_oracaoFrank));

                fragmentoSimples = FragmentSimples.novaInstancia(R.string.oracao_frank_duff);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, fragmentoSimples)
                        .commit();
                break;

            case R.id.action_settings:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                Intent it = new Intent(this, SettingsActivity.class);
                startActivity(it);

                break;

            case R.id.action_trabalhos:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_trabalhos));

                recyclerViewFragment = RecyclerViewFragment.novaInstancia();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, recyclerViewFragment)
                        .commit();
                break;

            case R.id.action_eventos:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setToolbarTitle(getResources().getString(R.string.opcao_eventos));

                eventosRecyclerViewFragment = EventosRecyclerViewFragment.novaInstancia();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteudo, eventosRecyclerViewFragment)
                        .commit();

                break;
        }
    }

    public void setToolbarTitle(String name){
        toolbar.setTitle(name);
    }
}
