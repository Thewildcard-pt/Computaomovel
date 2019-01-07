package ipca.random.computaomovel;

import android.content.Context;
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
    private SurfaceHolder surfaceHolder;

    private Player player;


    public GameView(Context context, int width, int height) {
        super(context);


        surfaceHolder = getHolder();
        paint =  new Paint();


    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }

    }


    int boomupdate = 0;

    private void update() {

    }

    private void draw() {

        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);


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
