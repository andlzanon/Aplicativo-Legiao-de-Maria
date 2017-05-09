package andl.zanon.navegacao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre on 29/03/2017.
 */

public class EventosSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd_Eventos";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_EVENTOS = "eventos";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LOCAL = "local";
    public static final String COLUNA_HORARIO = "horario";
    public static final String COLUNA_DATA = "data";

    public EventosSQLHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_EVENTOS + " (" +
            COLUNA_ID + " INTEGER PRIMARY KEY NOT NULL," +
            COLUNA_NOME + " TEXT NOT NULL, " +
            COLUNA_LOCAL + " TEXT NOT NULL, " +
            COLUNA_HORARIO + " TEXT NOT NULL, " +
            COLUNA_DATA + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " +TABELA_EVENTOS);
        onCreate(db);
    }
}
