package ipca.random.computaomovel;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        Drawable background = getResources().getDrawable(R.drawable.background_game, null);
        gameView.setBackground(background);
        setContentView(gameView);
        Intent change = new Intent(GameActivity.this,EndScreen.class);
        change.putExtra("pontos",gameView.score);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
