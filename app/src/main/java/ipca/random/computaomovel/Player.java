package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Player {

    // Sprite array and current sprite index int
    Bitmap[] spriteList = new Bitmap[5];
    int currentSprite;

    // Timer used when applying the state change cooldown
    Timer stateChangeDelay = new Timer("Move cooldown");
    Boolean stateChangeOnCooldown = false;

    // Timer used to create the walk animation
    Timer walkTimer = new Timer("Walk animation");

    // Screen sizes
    int screen_width;
    int screen_height;

    // Player position variables
    int x;
    int y;

    public Player(Context context, int screen_width, int screen_height){
        // Store screen size
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        // Store each player sprite
        spriteList[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_walk1);
        spriteList[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_walk2);
        spriteList[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_jump);
        spriteList[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_dodgeright);
        spriteList[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_dodgeleft);

        // Create a walk animation
        walkTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Swaps between the two walk sprites every 250 milliseconds, if one of them is being displayed
                if(currentSprite == 0)
                    currentSprite = 1;
                else if (currentSprite == 1)
                    currentSprite = 0;
            }
        }, 0, 250);
    }

    public void Update(){

        // Set the player sprite positions used by the different sprites
        switch(currentSprite){
            // Walk animation sprite 1
            case 0:
                x = (screen_width / 2) - (spriteList[currentSprite].getWidth() / 2);
                y = screen_height - spriteList[currentSprite].getHeight() - 50;
                break;
            // Walk animation sprite 2
            case 1:
                x = (screen_width / 2) - (spriteList[currentSprite].getWidth() / 2);
                y = screen_height - spriteList[currentSprite].getHeight() - 50;
                break;
            // Jump sprite
            case 2:
                x = (screen_width / 2) - (spriteList[currentSprite].getWidth() / 2);
                y = screen_height / 2 - spriteList[currentSprite].getHeight() - 50;
                break;
            // Dodge right sprite
            case 3:
                x = (3 * screen_width / 4) - (spriteList[currentSprite].getWidth() / 2);
                y = screen_height - spriteList[currentSprite].getHeight() - 50;
                break;
            // Dodge left sprite
            case 4:
                x = (1 * screen_width / 4) - (spriteList[currentSprite].getWidth() / 2);
                y = screen_height - spriteList[currentSprite].getHeight() - 50;
                break;
        }

        // Reset to the walking state if state change cooldown has ended and not already walking
        if(!stateChangeOnCooldown && currentSprite > 1)
            currentSprite = 0;

    }

    public void processInput(int touch_x, int touch_y)
    {
        // Only apply input if not on cooldown
        if(!stateChangeOnCooldown)
        {
            // Set to jump state on tapping the top half of the screen
            if(touch_y < screen_height / 2)
                currentSprite = 2;

            // Set to dodge_right state on tapping the bottom left half of the screen
            else if(touch_x < screen_width / 2)
                currentSprite = 4;

            // Set to dodge_left state on tapping the bottom right half of the screen
            else
                currentSprite = 3;

            // Disable state changing
            stateChangeOnCooldown = true;

            // Set state changing to be enabled in 2 seconds
            stateChangeDelay.schedule(new TimerTask() {
                public void run() {
                    stateChangeOnCooldown = false;
                }}, 500);
        }
    }

    public boolean CheckCollision(List<Obstacle> obstacleList){
        for(Obstacle o:obstacleList) {
            if (o.y + o.bitmap.getHeight() > screen_height - 60) {
                switch (o.type) {
                    case 1:
                        if(currentSprite != 2)
                            return true;
                        break;
                    case 2:
                        if(currentSprite != 3)
                            return true;
                        break;
                    case 3:
                        if(currentSprite != 4)
                            return true;
                        break;
                }
            }
        }

        return false;
    }
}
