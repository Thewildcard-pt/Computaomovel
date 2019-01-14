package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ObstacleSpawner {

    List<Obstacle> obstacleList = new ArrayList<>();
    List<Bitmap> wallSpriteList = new ArrayList<>();

    Bitmap obstacleSprite_base;

    int smoothCount;

    int screen_width;
    int screen_height;

    Timer spawnTimer = new Timer("Obstacle spawner cycle");
    Boolean shouldSpawnObstacle = true;
    int spawnFrequency;

    public int deletedCount = 0;

    Random random = new Random();

    public ObstacleSpawner(Context context, int screen_width, int screen_height) {

        // Set starting spawner frequency
        spawnFrequency = 3000;

        // Bigger smoothCount value means more interpolated positions are created for each sprite (better accuracy)
        smoothCount = 50;

        // Load the obstacle sprite
        obstacleSprite_base = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);

        this.screen_width = screen_width;
        this.screen_height = screen_height;

        PreloadSprites();
    }

    public void Update() {

        // Obstacle spawner loop
        if (shouldSpawnObstacle) {
            // Spawn an obstacle
            SpawnObstacle();

            // Disable obstacle spawning on Update()
            shouldSpawnObstacle = false;

            // Set a timer to re-enable obstacle spawning
            spawnTimer.schedule(new TimerTask() {
                public void run() {
                    shouldSpawnObstacle = true;

                    // Lower delay every obstacle spawn down to a set minimum
                    if (spawnFrequency > 800)   // Minimum should be bigger than the player's input cooldown
                        spawnFrequency = (int) (spawnFrequency * 0.95f);
                }
            }, spawnFrequency);
        }

        int deleteIndex = -1;
        // Update all existing obstacles
        for (Obstacle o : obstacleList) {
            o.Update();

            // If the obstacle has finished its course
            if (o.markForDelete)
                deleteIndex = obstacleList.indexOf(o); // Store object index for deletion after done iterating
        }

        // If an obstacle was marked for deletion
        if (deleteIndex != -1) {
            // Delete the obstacle
            obstacleList.remove(deleteIndex);
            deletedCount++;
        }
    }

    public void SpawnObstacle() {
        obstacleList.add(new Obstacle(random.nextInt(3) + 1, wallSpriteList, smoothCount, screen_width, screen_height));
    }

    public void PreloadSprites() {

        float y_increment = (screen_height) / smoothCount;

        float current_y = 0;

        // Create the different low wall sprites
        for(int i = 0; i < smoothCount; i++) {

            int relativeScale = (int) (current_y * 0.92f);

            if (relativeScale < 10)
                relativeScale = 10;

            int scale_x = (int)(relativeScale * 0.6f);
            int scale_y = (int)(relativeScale * 0.4f);

            wallSpriteList.add(Bitmap.createScaledBitmap(obstacleSprite_base, scale_x, scale_y, false));

            current_y += y_increment;
        }

        current_y = 0;

        // Create the different tall wall sprites
        for(int i = 0; i < smoothCount; i++) {
            int relativeScale = (int) (current_y * 0.9f);

            if (relativeScale < 10)
                relativeScale = 10;

            int scale_x = (int)(relativeScale * 0.5f);
            int scale_y = (int)(relativeScale * 1.5f);

            wallSpriteList.add(Bitmap.createScaledBitmap(obstacleSprite_base, scale_x, scale_y, false));

            current_y += y_increment;
        }
    }
}
