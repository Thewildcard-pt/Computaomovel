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

    Bitmap obstacleSprite;

    List<Obstacle> obstacleList = new ArrayList<>();
    int screen_width;
    int screen_height;

    Timer spawnTimer = new Timer("Obstacle spawner cycle");
    Boolean shouldSpawnObstacle = true;
    int spawnFrequency;

    Random random = new Random();

    public ObstacleSpawner(Context context, int screen_width, int screen_height) {

        // Set starting spawner frequency
        spawnFrequency = 3000;

        // Load obstacle sprites
        obstacleSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);

        this.screen_width = screen_width;
        this.screen_height = screen_height;

    }

    public void Update(){

        // Obstacle spawner loop
        if(shouldSpawnObstacle) {
            // Spawn an obstacle
            SpawnObstacle();

            // Disable obstacle spawning on Update()
            shouldSpawnObstacle = false;

            // TO DO: FIX MYSTERIOUS MEMORY LEAK ?
            /*
            // Set a timer to re-enable obstacle spawning
            spawnTimer.schedule(new TimerTask() {
                public void run() {
                    shouldSpawnObstacle = true;

                    // Lower delay every obstacle spawn down to a set minimum
                    if (spawnFrequency > 1000)
                        spawnFrequency = (int)(spawnFrequency * 0.95f);
                }
            }, spawnFrequency);
            */
        }

        int deleteIndex = -1;

        // Update all existing obstacles
        for (Obstacle o: obstacleList)
        {
            o.Update();

            // If the obstacle has finished its course
            if(o.markForDelete)
                deleteIndex = obstacleList.indexOf(o); // Store object index for deletion after done iterating
        }

        // If an obstacle was marked for deletion
        if(deleteIndex != -1)
            // Delete the obstacle
            obstacleList.remove(deleteIndex);

    }

    public void SpawnObstacle(){
        obstacleList.add(new Obstacle(obstacleSprite, random.nextInt(3) + 1, screen_width, screen_height));
    }
}
