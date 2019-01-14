package ipca.random.computaomovel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EndScreen extends AppCompatActivity {
//codigo inutil
    List<Score> Highscores = new ArrayList<Score>();
    Button Submit;
    EditText text;
    TextView pont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Submit = findViewById(R.id.btn_Submit);
        text = findViewById(R.id.Etext_Id);
        pont = findViewById(R.id.txt_score);

        Intent intent = getIntent();
        final int sc = intent.getIntExtra("pontos",0);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = text.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference tabela = database.getReference("Pontuacao");
                DatabaseReference posicao ;
                Score pontos = new Score(sc, Name);
                JSONObject obj = new JSONObject();


                try {
                    obj.put("id" , pontos.id);
                    obj.put("score" , Integer.toString(pontos.value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jj = new JSONArray();
                jj.put(obj);

                tabela.push().setValue(jj);


                Intent intent = new Intent(EndScreen.this,MainActivity.class);

                }


        });
    }
}
