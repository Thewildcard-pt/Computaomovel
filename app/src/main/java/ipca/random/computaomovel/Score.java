package ipca.random.computaomovel;

public class Score {

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
