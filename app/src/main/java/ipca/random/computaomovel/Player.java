package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Timer;

public class Player {

    Bitmap bitmap;

    int x;
    int y;

    int currentState;
    Timer stateLength;

    public Player(Context context){
        currentState = 0;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public void Update(int canvas_width, int canvas_height){
        x = (canvas_width / 2) - (bitmap.getWidth() / 2);
        y = canvas_height - bitmap.getHeight();
    }

}
