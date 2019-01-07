package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Timer;

public class Player {

    Bitmap bitmap;

    int currentState;
    Timer stateLength;

    public Player(Context context){
        currentState = 0;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public void Update(){

    }

}
