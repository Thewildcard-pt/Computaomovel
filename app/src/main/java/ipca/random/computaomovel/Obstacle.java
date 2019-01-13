package ipca.random.computaomovel;

import android.graphics.Bitmap;

public class Obstacle {
    public Bitmap bitmap_base;
    public Bitmap bitmap;

    int screen_width;
    int screen_height;
    public int x;
    public int y;

    int type;

    Boolean markForDelete = false;

    public Obstacle(Bitmap bitmap, int type, int screen_width, int screen_height) {
        // Store obstacle sprite and screen size
        this.bitmap_base = bitmap;
        this.bitmap = bitmap;
        this.type = type;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        // Initialize obstacle position at the top of the track
        y = 50;

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
                x = (screen_width / 2);
                break;
            case 2:
                x = (screen_width / 2) - (bitmap.getWidth());
                break;
            case 3:
                x = (screen_width / 2) - (bitmap.getWidth() / 2);
                break;
        }
    }

    // Makes the obstacle sprite bigger as it approaches the bottom of the screen (fake 3D effect)
    public void SetScale() {
        int relativeScale = (int)(y / 1.8f);
        if(relativeScale < 10)
            relativeScale = 10;

        bitmap = Bitmap.createScaledBitmap(bitmap_base, relativeScale, relativeScale, false);

    }
}
