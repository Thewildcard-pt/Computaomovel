package ipca.random.computaomovel;

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
    }
}
