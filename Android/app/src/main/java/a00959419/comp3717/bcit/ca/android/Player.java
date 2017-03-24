package a00959419.comp3717.bcit.ca.android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Player {
    private static final int HEIGHT = 100;
    private static final int WIDTH = 100;
    // Declare an object of type Bitmap
    Bitmap bitmapBob;
    private ArrayList<Rect> rects;

    // start off not moving.
    MovDirHorizontal movH = MovDirHorizontal.NONE;
    MovDirVertical movV = MovDirVertical.NONE;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float bobXPosition = Resources.getSystem().getDisplayMetrics().widthPixels - WIDTH;
    float bobYPosition = Resources.getSystem().getDisplayMetrics().heightPixels - HEIGHT;

    public Player(ScreenPlay.GameView gameView) {
        // Load Bob from his .png file

        bitmapBob = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dinosaur);
<<<<<<< HEAD
        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, 100, 100, true);

=======
        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, WIDTH, HEIGHT, true);
>>>>>>> e2165d1aaa23e4e9406ec5777cb856bfe66700b6
    }

    public void changeMove(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                // Set isMoving so Bob is moved in the update method
                if (motionEvent.getX() < 200)
                    movH = MovDirHorizontal.LEFT;
                else if (motionEvent.getX() > 800)
                    movH = MovDirHorizontal.RIGHT;


                if (motionEvent.getY() < 200)
                    movV = MovDirVertical.UP;
                else if (motionEvent.getY() > 800)
                    movV = MovDirVertical.DOWN;

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                // Set isMoving so Bob does not move
                movH = MovDirHorizontal.NONE;
                movV = MovDirVertical.NONE;

                break;
        }
    }

    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        switch (movH) {
            case RIGHT:
                float nextXPos = bobXPosition + walkSpeedPerSecond / fps;
                if (!isColliding(nextXPos, bobYPosition, rects)) {
                    bobXPosition = nextXPos;
                }
                break;
            case LEFT:
                nextXPos = bobXPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(nextXPos, bobYPosition, rects)) {
                    bobXPosition = nextXPos;
                }
                break;
            default:
                break;
        }

        switch (movV) {
            case DOWN:
                float nextYPos = bobYPosition + (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, rects)) {
                    bobYPosition = nextYPos;
                }
                break;
            case UP:
                nextYPos = bobYPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, rects)) {
                    bobYPosition = nextYPos;
                }
                break;
            default:
                break;
        }
    }

    private boolean isColliding(float xPos, float yPos, ArrayList<Rect> rects) {
        for (Rect rect: rects) {
            if(rect.intersect((int)xPos, (int)yPos, (int)xPos+WIDTH, (int)yPos+HEIGHT)){
                return true;
            }
        }
        return false;
    }

    public void display(Canvas canvas, Paint paint) {
        // Draw bob at bobXPosition, 200 pixels
        canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }

    public void setRects(ArrayList<Rect> rects) {
        this.rects = rects;
    }
}
