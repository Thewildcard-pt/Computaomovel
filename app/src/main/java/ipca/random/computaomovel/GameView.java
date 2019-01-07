package ipca.random.computaomovel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable{


    volatile boolean playing;

    private Thread gameThread =  null;

    private Paint paint;
    private Canvas canvas;
    private int canvas_width;
    private int canvas_height;

    private SurfaceHolder surfaceHolder;

    private Player player;


    public GameView(Context context, int width, int height) {
        super(context);

        // Set background image
        this.setBackgroundResource(R.drawable.background);

        surfaceHolder = getHolder();
        paint =  new Paint();

        player = new Player(context);
    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }

    }

    private void update() {
        player.Update(canvas_width, canvas_height);

    }

    private void draw() {

        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();
canvas_width = canvas.getWidth();
canvas_height = canvas.getHeight();

            canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(player.bitmap, player.x, player.y, paint);

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

    public  void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause(){
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
