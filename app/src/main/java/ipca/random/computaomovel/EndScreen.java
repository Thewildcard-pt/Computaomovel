package ipca.random.computaomovel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EndScreen extends AppCompatActivity {

    List<Score> Highscores = new ArrayList<Score>();
    Button Submit;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Submit = findViewById(R.id.btn_Submit);
        text = findViewById(R.id.Etext_Id);

        Intent intent = getIntent();
        final int sc = intent.getIntExtra("pontos",0);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = Submit.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference tabela = database.getReference("Pontuacao");
                Score pontos = new Score(sc, Name);

                //Receber a Tabela da firebase
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


                //atualizar a tabela
                boolean update = false;
                Score comp = pontos;
                for(int i=0; i<Highscores.size();i++) {
                    if (comp.value > Highscores.get(i).value) {
                        Score temp;
                        temp = Highscores.get(i);
                        Highscores.get(i).equals(comp);
                        comp = temp;
                        update = true;
                    }
                }

                //Enviar a tabela atualizada para O servidor
                if(update)
                    {
                    DatabaseReference myRef = database.getReference("Pontuacoes");
                    DatabaseReference obj;
                        {
                            int z = 0;
                        for (z = 0 ; z < Highscores.size(); z++) {
                            obj = myRef.child(Integer.toString(z));
                            obj.child("id").setValue(Highscores.get(z).id);
                            obj.child("id").setValue(Integer.toString(Highscores.get(z).value));
                            }
                        }
                    }

                Intent intent = new Intent(EndScreen.this,MainActivity.class);

                }


        });





    }
}
