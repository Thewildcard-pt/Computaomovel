package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
    Bitmap[] spriteList = new Bitmap[5];
    int currentSprite;

    int x;
    int y;

    public Player(Context context){
        spriteList[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_walk1);
        spriteList[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_walk2);
        //spriteList[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_dodgeleft);
        //spriteList[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_dodgeright);
        //spriteList[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_jump);

        // Initialize the walk animator timer
        Timer updateTimer = new Timer("Walk animation");
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(currentSprite == 0)
                    currentSprite = 1;
                else if (currentSprite == 1)
                    currentSprite = 0;
            }
        }, 0, 250);
    }

    public void Update(int canvas_width, int canvas_height){


        x = (canvas_width / 2) - (spriteList[currentSprite].getWidth() / 2);
        y = canvas_height - spriteList[currentSprite].getHeight() - 50;

    }
}
