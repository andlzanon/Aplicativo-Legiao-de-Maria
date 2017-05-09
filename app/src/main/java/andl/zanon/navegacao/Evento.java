package andl.zanon.navegacao;

import java.io.Serializable;

/**
 * Created by Andre on 22/03/2017.
 */

public class Evento implements Serializable{
    public long _id;
    public String nome;
    public String local;
    public String horario;
    public String data;

    Evento(String nome, String local, String horario, String data){
        this.nome = nome;
        this.local = local;
        this.horario = horario;
        this.data = data;
    };

    Evento(String nome, String local, String horario, String data, long _id){
        this.nome = nome;
        this.local = local;
        this.horario = horario;
        this.data = data;
        this._id = _id;
    };
}
