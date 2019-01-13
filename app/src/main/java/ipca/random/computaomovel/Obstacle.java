package ipca.random.computaomovel;

import android.graphics.Bitmap;
import android.support.v4.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Obstacle {
    public Bitmap bitmap;

    List<Bitmap> wallSpriteList;

    int screen_width;
    int screen_height;
    public int x;
    public int y;

    int type;

    Boolean markForDelete = false;

    public Obstacle(int type, List wallSpriteList, int screen_width, int screen_height) {
        // Store obstacle sprite and screen size
        this.type = type;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.wallSpriteList = wallSpriteList;

        // Initialize obstacle position at the top of the track
        y = 20;

        // Update scale with the current Y position
        Update();
    }

    public void Update() {

        // Make obstacle go down every update (slightly faster as y increases)
        y = y + 1 + y / 20;

        // Update scale with the current Y position
        SetScale();

        // Mark obstacle for deletion if it has disappeared past the bottom
        if (y > screen_height - bitmap.getHeight() / 2) {
            markForDelete = true;
            return;
        }

        switch(type)
        {
            case 1:
                x = (screen_width / 2) - (bitmap.getWidth() / 2);
                break;
            case 2:
                x = (screen_width / 2) - (bitmap.getWidth());
                break;
            case 3:
                x = (screen_width / 2);
                break;
        }
    }

    // Makes the obstacle sprite bigger as it approaches the bottom of the screen (fake 3D effect)
    public void SetScale() {
        int i = (y*300)/screen_height;

        if(type > 1)
            i += 300;

        i = MathUtils.clamp(i, 0, 2* 300);

        bitmap = wallSpriteList.get(i);
    }
}
