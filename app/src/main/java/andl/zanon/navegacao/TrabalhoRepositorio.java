package andl.zanon.navegacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andre on 14/03/2017.
 */

public class TrabalhoRepositorio {

    private TrabalhosSQLHelper helper;

    public TrabalhoRepositorio(Context context){
        helper = new TrabalhosSQLHelper(context);
    }

    public long inserir(Trabalhos trabalho){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TrabalhosSQLHelper.COLUNA_NOME, trabalho.nome);
        cv.put(TrabalhosSQLHelper.COLUNA_QTD, trabalho.quantidade);

        long id = db.insert(TrabalhosSQLHelper.TABELA_TRABALHOS, null, cv);
        Log.d("insere", "id = " + id);

        db.close();
        return id;
    }

    public int atualizar(Trabalhos trabalho){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TrabalhosSQLHelper.COLUNA_NOME, trabalho.nome);
        cv.put(TrabalhosSQLHelper.COLUNA_QTD, trabalho.quantidade);

        int linhasModificadas = db.update(
                TrabalhosSQLHelper.TABELA_TRABALHOS,
                cv,
                TrabalhosSQLHelper.COLUNA_ID + " = ?",
                new String[]{ String.valueOf(trabalho.id)}
        );

        db.close();
        return linhasModificadas;
    }


    public int excluir(Trabalhos trabalho){
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasModificas = db.delete(
                TrabalhosSQLHelper.TABELA_TRABALHOS,
                TrabalhosSQLHelper.COLUNA_ID + " = ?",
                new String[]{ String.valueOf(trabalho.id)}
        );

        db.close();
        return linhasModificas;
    }

    public void listaTrabalhos(ArrayList<Trabalhos> arrayList){
        SQLiteDatabase db = helper.getReadableDatabase();

        arrayList.clear();

        try{
            String sql = "SELECT * FROM " + TrabalhosSQLHelper.TABELA_TRABALHOS;
            sql += " ORDER BY " + TrabalhosSQLHelper.COLUNA_NOME;

            Cursor cursor = db.rawQuery(sql, null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    long id = cursor.getLong(cursor.getColumnIndex(TrabalhosSQLHelper.COLUNA_ID));
                    String nome = cursor.getString(cursor.getColumnIndex(TrabalhosSQLHelper.COLUNA_NOME));
                    int quantidade = cursor.getInt(cursor.getColumnIndex(TrabalhosSQLHelper.COLUNA_QTD));

                    Trabalhos novoTrabalho = new Trabalhos(quantidade, nome, id);
                    arrayList.add(novoTrabalho);
                }while(cursor.moveToNext());

                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        db.close();
    }
}
