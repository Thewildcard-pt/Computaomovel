package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class ObstacleSpawner {

    Bitmap obstacleSprite;

    List<Obstacle> obstacleList = new ArrayList<>();
    int screen_width;
    int screen_height;

    public ObstacleSpawner(Context context, int screen_width, int screen_height) {

        obstacleSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);

        this.screen_width = screen_width;
        this.screen_height = screen_height;

        SpawnObstacle();

    }

    public void Update(){

        // Update all existing obstacles
        for (Obstacle o: obstacleList)
        {
            o.Update();
        }
    }


    public void SpawnObstacle(){
        obstacleList.add(new Obstacle(obstacleSprite, screen_width, screen_height));
    }
}
