package ipca.random.computaomovel;

import android.graphics.Bitmap;

public class Obstacle {
    public Bitmap bitmap_base;
    public Bitmap bitmap;

    int screen_width;
    public int x;
    public int y;

    public Obstacle(Bitmap bitmap, int screen_width) {
        // Store obstacle sprite
        this.bitmap_base = bitmap;
        this.screen_width = screen_width;

        // Initialize obstacle position at the top and middle of screen
        y = 0;

    }

    public void Update() {
        // Make obstacle go down every update
        y++;

        // Update scale with current Y position
        SetScale();

        x = (screen_width / 2) - (bitmap.getWidth() / 2);
    }

    // real time dynamic scale rendering
    public void SetScale() {
        int y_t = y / 2;
        if(y_t < 1)
            y_t = 1;
        bitmap = Bitmap.createScaledBitmap(bitmap_base, y_t, y_t, false);
    }
}
