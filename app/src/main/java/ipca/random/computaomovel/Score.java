package ipca.random.computaomovel;

import java.io.Serializable;

public class Score implements Serializable {

    int value;
    String id;

    public Score(int score, String nome)
    {
        id = nome;
        value  = score;
    }

    public String GetID(){
        return id;
    }

    public int GetValue(){
        return  value;
    }
}
