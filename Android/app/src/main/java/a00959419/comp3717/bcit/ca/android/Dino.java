package a00959419.comp3717.bcit.ca.android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Andrew on 4/6/2017.
 */

public class Dino {
    public ScreenPlay screen;

    protected static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    protected static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;

    protected static final int MY_HEIGHT = SCREEN_WIDTH/23;
    protected static final int MY_WIDTH = SCREEN_WIDTH/23;

    float walkSpeedPerSecond = SCREEN_WIDTH/10;

    public float xPosition;
    public float yPosition;

    // Declare an object of type Bitmap
    Bitmap bitmapBob;
    protected ArrayList<Rect> buildings;
    protected ArrayList<Rect> trees;

    // start off not moving.
    Dino.MovDirHorizontal movH = Dino.MovDirHorizontal.NONE;
    Dino.MovDirVertical movV = Dino.MovDirVertical.NONE;

    public Dino(ScreenPlay.GameView gameView, Map map, int imgId, ScreenPlay screen, float spawnXPos,
                float spawnYPos) {
        this.screen = screen;

        bitmapBob = BitmapFactory.decodeResource(gameView.getResources(), imgId);

        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, MY_WIDTH, MY_HEIGHT, true);

        xPosition = spawnXPos;
        yPosition = spawnYPos;

        setBuildings(map.getBuildings());
        setTrees(map.getTrees());
    }

    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        switch (movH) {
            case RIGHT:
                float nextXPos = xPosition + walkSpeedPerSecond / fps;
                if (!isColliding(nextXPos, yPosition, buildings) && !isAtEdge(nextXPos, yPosition)) {
                    xPosition = nextXPos;
                }
                break;
            case LEFT:
                nextXPos = xPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(nextXPos, yPosition, buildings) && !isAtEdge(nextXPos, yPosition)) {
                    xPosition = nextXPos;
                }
                break;
            default:
                break;
        }

        switch (movV) {
            case DOWN:
                float nextYPos = yPosition + (walkSpeedPerSecond / fps);
                if (!isColliding(xPosition, nextYPos, buildings) && !isAtEdge(xPosition, nextYPos)) {
                    yPosition = nextYPos;
                }
                break;
            case UP:
                nextYPos = yPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(xPosition, nextYPos, buildings) && !isAtEdge(xPosition, nextYPos)) {
                    yPosition = nextYPos;
                }
                break;
            default:
                break;
        }
    }

    protected boolean isAtEdge(float xPos, float yPos) {
        if (xPos < 0 || yPos < 0 || xPos > (SCREEN_WIDTH - MY_WIDTH) || yPos > (SCREEN_HEIGHT -
                MY_HEIGHT *2)) {
            return true;
        }
        return false;
    }

    protected boolean isColliding(float xPos, float yPos, ArrayList<Rect> rects) {
        for (Rect rect : rects) {
            if (Rect.intersects(rect,
                    new Rect((int) xPos, (int) yPos, (int) xPos + MY_WIDTH, (int) yPos +
                            MY_HEIGHT))) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<Rect> collisions(float xPos, float yPos, ArrayList<Rect> rects) {
        ArrayList<Rect> output = new ArrayList<>();

        for (Rect rect : rects) {
            if (Rect.intersects(rect,
                    new Rect((int) xPos, (int) yPos, (int) xPos + MY_WIDTH, (int) yPos +
                            MY_HEIGHT))) {
                output.add(rect);
            }
        }
        return output;
    }

    public void display(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmapBob, xPosition, yPosition, paint);
        paint.setColor(Color.BLACK);
    }

    public void setTrees(ArrayList<Rect> trees) {
        this.trees = trees;
    }

    public void setBuildings(ArrayList<Rect> buildings) {
        this.buildings = buildings;
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }
}
