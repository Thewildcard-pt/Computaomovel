package ipca.random.computaomovel;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btn_start;
    Button btn_quit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Nintent = new Intent(MainActivity.this,GameActivity.class);
                DatabaseReference myRef = database.getReference("Pontuacoes");
                DatabaseReference obj = myRef.child("1");
                DatabaseReference name = obj.child("id");
                String nome = name.toString();
                nome = "some random";
                name.setValue(nome);

                startActivity(Nintent);

            }
        });


        btn_quit = findViewById(R.id.btn_quit);

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
