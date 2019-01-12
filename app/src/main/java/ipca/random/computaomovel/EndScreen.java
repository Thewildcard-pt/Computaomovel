package ipca.random.computaomovel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EndScreen extends AppCompatActivity {

    List<Score> Highscores = new ArrayList<Score>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        Intent intent = getIntent();
        Score pontos = (Score) intent.getSerializableExtra("Score");

        //Receber a tabela de Highscores da firebase;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tabela = database.getReference("Pontuacao");
        for(int i = 0; i<10;i++){
            //variaveis usadas Para criar os spots
            String id;
            int Score;
            DatabaseReference x;
            x = tabela.child(Integer.toString(i));
            id = x.child("id").toString();
            Score = Integer.parseInt(x.child("score").toString());
            Highscores.add(new Score(Score,id));
        }

        Score comp = pontos;
        for(int i=0; i<Highscores.size();i++)
        {
            if(comp.value > Highscores.get(i).value)
            {
                Score temp;
                temp = Highscores.get(i);
                Highscores.get(i).equals(comp);
                comp =  temp;
            }
        }

        DatabaseReference myRef = database.getReference("Pontuacoes");
        DatabaseReference obj;
        for(int i=0; i<Highscores.size();i++)
        {
            obj = myRef.child(Integer.toString(i));
            obj.child("id").setValue(Highscores.get(i).id);
            obj.child("id").setValue(Integer.toString(Highscores.get(i).value));
        }
        
    }
}
