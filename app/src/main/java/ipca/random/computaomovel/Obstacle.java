package ipca.random.computaomovel;

import android.graphics.Bitmap;

public class Obstacle {
    public Bitmap bitmap_base;
    public Bitmap bitmap;

    int screen_width;
    int screen_height;
    public int x;
    public int y;

    public Obstacle(Bitmap bitmap, int screen_width, int screen_height) {
        // Store obstacle sprite and screen size
        this.bitmap_base = bitmap;
        this.bitmap = bitmap;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        // Initialize obstacle position at the top of the track and in the middle of screen
        x = (screen_width / 2) - (bitmap.getWidth() / 2);
        y = 60;

    }

    public void Update() {
        // Make obstacle go down every update (slightly faster as y increases)
        y = y + 1 + y/20;

        // Set obstacle to the top of the screen if it has reached the bottom
        if (y > screen_height + bitmap.getHeight())
            y = 60;

        // Update scale with current Y position
        SetScale();

        // Re-center the obstacle every update (position changes with the scaling)
        x = (screen_width / 2) - (bitmap.getWidth() / 2);
    }

    // real time dynamic scale rendering
    public void SetScale() {
        int y_t = (int)(y / 1.8f);
        if(y_t < 10)
            y_t = 10;
        bitmap = Bitmap.createScaledBitmap(bitmap_base, y_t, y_t, false);
    }
}
