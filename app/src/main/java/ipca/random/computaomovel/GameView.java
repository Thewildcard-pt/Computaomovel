package ipca.random.computaomovel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    private Paint paint;

    private Canvas canvas;

    private int screen_width;
    private int screen_height;
    int timer;
    private SurfaceHolder surfaceHolder;

    private Player player;
    private ObstacleSpawner obstacleSpawner;

    public GameView(Context context, int width, int height) {

        super(context);

        screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
        screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Set background image
        this.setBackgroundResource(R.drawable.background);

        surfaceHolder = getHolder();
        paint = new Paint();

        obstacleSpawner = new ObstacleSpawner(context, screen_width, screen_height);
        player = new Player(context);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }

    }

    private void update() {
        obstacleSpawner.Update();

        timer++;

        player.Update(screen_width, screen_height);
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(player.bitmap, player.x, player.y, paint);

            for(Obstacle o: obstacleSpawner.obstacleList){
                canvas.drawBitmap(o.bitmap,o.x,o.y,paint);
            }

            paint.setColor(Color.CYAN);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return true;
    }
}