package andl.zanon.navegacao;

import java.io.Serializable;

/**
 * Created by Andre on 08/03/2017.
 */

public class Trabalhos implements Serializable{

    public int quantidade;
    public String nome;
    public long id;

    public Trabalhos(int quantidade, String nome, long id){
        this.quantidade = quantidade;
        this.nome = nome;
        this.id = id;
    }

    public Trabalhos(int quantidade, String nome){
        this.quantidade = quantidade;
        this.nome = nome;
    }
}
