package andl.zanon.navegacao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre on 14/03/2017.
 */

public class TrabalhosSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd_Trabalhos";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_TRABALHOS = "trabalhos";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_QTD = "quantidade";

    public TrabalhosSQLHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_TRABALHOS + " (" +
        COLUNA_ID + " INTEGER PRIMARY KEY NOT NULL," +
        COLUNA_NOME + " TEXT NOT NULL, " +
        COLUNA_QTD + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " +TABELA_TRABALHOS);
        onCreate(db);
    }
}
