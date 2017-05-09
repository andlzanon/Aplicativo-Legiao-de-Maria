package andl.zanon.navegacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.StringBuilderPrinter;

import java.util.ArrayList;

/**
 * Created by Andre on 29/03/2017.
 */

public class EventosRepositorio {

    private EventosSQLHelper helper;

    public EventosRepositorio(Context context){
        helper = new EventosSQLHelper(context);
    }

    public long inserir(Evento evento){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EventosSQLHelper.COLUNA_NOME, evento.nome);
        cv.put(EventosSQLHelper.COLUNA_LOCAL, evento.local);
        cv.put(EventosSQLHelper.COLUNA_HORARIO, evento.horario);
        cv.put(EventosSQLHelper.COLUNA_DATA, evento.data);

        long id = bd.insert(EventosSQLHelper.TABELA_EVENTOS, null, cv);
        bd.close();

        return id;
    }

    public int excluir(Evento evento){
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasModificas = db.delete(
                EventosSQLHelper.TABELA_EVENTOS,
                EventosSQLHelper.COLUNA_ID + " = ?",
                new String[]{ String.valueOf(evento._id)}
        );

        db.close();
        return linhasModificas;
    }

    public int atualizar(Evento evento){
        SQLiteDatabase bd = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EventosSQLHelper.COLUNA_NOME, evento.nome);
        cv.put(EventosSQLHelper.COLUNA_LOCAL, evento.local);
        cv.put(EventosSQLHelper.COLUNA_HORARIO, evento.horario);
        cv.put(EventosSQLHelper.COLUNA_DATA, evento.data);

        int linhasModificadas = bd.update(EventosSQLHelper.TABELA_EVENTOS,
                cv,
                EventosSQLHelper.COLUNA_ID + " = ?",
                new String[]{String.valueOf(evento._id)});

        return linhasModificadas;
    }

    public void listaEventos(ArrayList<Evento> arrayList){
        SQLiteDatabase bd = helper.getReadableDatabase();

        arrayList.clear();

        try{
            String sql = "SELECT * FROM " + EventosSQLHelper.TABELA_EVENTOS;
            sql += " ORDER BY " + EventosSQLHelper.COLUNA_DATA;

            Cursor cursor = bd.rawQuery(sql, null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    long id = cursor.getLong(cursor.getColumnIndex(EventosSQLHelper.COLUNA_ID));
                    String nome = cursor.getString(cursor.getColumnIndex(EventosSQLHelper.COLUNA_NOME));
                    String local = cursor.getString(cursor.getColumnIndex(EventosSQLHelper.COLUNA_LOCAL));
                    String horario = cursor.getString(cursor.getColumnIndex(EventosSQLHelper.COLUNA_HORARIO));
                    String data = cursor.getString(cursor.getColumnIndex(EventosSQLHelper.COLUNA_DATA));

                    Evento novoEvento = new Evento(nome, local, horario, data, id);
                    arrayList.add(novoEvento);

                }while (cursor.moveToNext());
            }

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        bd.close();
    }
}
