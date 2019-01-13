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

    private Canvas canvas;
    private Paint paint;

    private int screen_width;
    private int screen_height;
    private SurfaceHolder surfaceHolder;

    private Player player;
    private ObstacleSpawner obstacleSpawner;

    private int score;

    private Boolean inputAllowed = true;

    public GameView(Context context) {

        super(context);

        paint = new Paint();
        surfaceHolder = getHolder();
        screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
        screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Set background image
        this.setBackgroundResource(R.drawable.background_game);

        // Create initial game entities
        obstacleSpawner = new ObstacleSpawner(context, screen_width, screen_height);
        player = new Player(context, screen_width, screen_height);
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

        score = obstacleSpawner.deletedCount * 100;

        player.Update();

        // TODO: THING
        if(player.CheckCollision(obstacleSpawner.obstacleList))
            pause();
            
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.rgb(245, 245, 245));

            paint.setARGB(255, 0, 0,0 );

            paint.setTextSize(100);
            canvas.drawText(String.valueOf(score), 50,110,paint);

            for(Obstacle o: obstacleSpawner.obstacleList){
                canvas.drawBitmap(o.bitmap,o.x,o.y,paint);
            }

            canvas.drawBitmap(player.spriteList[player.currentSprite], player.x, player.y, paint);

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

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                inputAllowed = true;
                break;

            case MotionEvent.ACTION_DOWN:
                if (inputAllowed) {
                    player.processInput((int)event.getX(), (int)event.getY());
                }

                inputAllowed = false;
                break;
        }

        return true;
    }
}