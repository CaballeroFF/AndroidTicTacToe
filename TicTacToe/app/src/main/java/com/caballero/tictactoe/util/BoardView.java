package com.caballero.tictactoe.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class BoardView extends View {
    private static final String TAG = "BoardView";
    public static final String X = "x";
    public static final String O = "o";
    public static final String EMPTY = "com.caballero.tictactoe.util.empty";
    public static final String TOP_LEFT_DIAGONAL = "com.caballero.tictactoe.top.left.diagonal";
    public static final String TOP_RIGHT_DIAGONAL = "com.caballero.tictactoe.top.right.diagonal";
    public static final String MIDDLE_COLUMN = "com.caballero.tictactoe.middle.column";
    public static final String LEFT_COLUMN = "com.caballero.tictactoe.left.column";
    public static final String RIGHT_COLUMN = "com.caballero.tictactoe.right.column";
    public static final String MIDDLE_ROW = "com.caballero.tictactoe.middle.row";
    public static final String TOP_ROW = "com.caballero.tictactoe.top.row";
    public static final String BOTTOM_ROW = "com.caballero.tictactoe.bottom.row";

    private Paint brush = new Paint();
    private PointF pointClicked = new PointF();
    private OnFinishedDrawingListener listener;

    private int tiles = 3;
    private int strokeColor = Color.BLACK;
    private int fillColor = Color.WHITE;

    private float strokeWidth = 20f;
    private float pieceOffset = 2 * strokeWidth;
    private float winLineOffset = 3 * strokeWidth;
    private float lineStartX = 0f;
    private float lineStartY = 0f;
    private float lineEndX = 0f;
    private float lineEndY = 0f;
    private float lineStopX = 0f;
    private float lineStopY = 0f;
    private float step = 25f;
    private float xStep = lineStopX / step;
    private float yStep = lineStopY / step;
    float oneThirdWidth = 0f;
    float twoThirdWidth = 0f;
    float oneThirdHeight = 0f;
    float twoThirdHeight = 0f;

    private boolean canDrawWinLine;
    private boolean canDrawPiece;
    private boolean isClearing;

    private Map<Point, String> tilePieces = new HashMap<>();
    private String lineType = EMPTY;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        oneThirdWidth = getWidth() / 3f;
        oneThirdHeight = getHeight() / 3f;
        twoThirdWidth = 2f * getWidth() / 3f;
        twoThirdHeight = 2f * getHeight() / 3f;

        drawTileBackground(canvas);

        drawLines(canvas, true);
        drawLines(canvas, false);

        if (!isClearing) {
            if (canDrawPiece) {
                for (Point p : tilePieces.keySet()) {
                    drawPiece(p, canvas, tilePieces.get(p));
                }
            }
            if (canDrawWinLine) {
                canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, brush);
                updateWinLine(lineType);
            }
        }

        super.onDraw(canvas);
    }

    private void drawTileBackground(Canvas canvas) {
        brush.setColor(fillColor);
        brush.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), brush);
    }

    private void drawLines(Canvas canvas, boolean isHorizontal) {
        brush.setColor(strokeColor);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeWidth(strokeWidth);
        for (float line = 1; line < tiles; line++) {
            if (isHorizontal) {
                float xPosition = (line / tiles) * getWidth();
                canvas.drawLine(xPosition, 0, xPosition, getHeight(), brush);
            } else {
                float yPosition = (line / tiles) * getHeight();
                canvas.drawLine(0, yPosition, getWidth(), yPosition, brush);
            }
        }
    }

    private void drawPiece(Point point, Canvas canvas, String piece) {
//        oneThirdWidth = getWidth() / 3f;
//        oneThirdHeight = getHeight() / 3f;
//        twoThirdWidth = 2f * getWidth() / 3f;
//        twoThirdHeight = 2f * getHeight() / 3f;

        float radius = (getWidth() < getHeight()) ? (oneThirdWidth / 2f) - pieceOffset : (oneThirdHeight / 2f) - pieceOffset;
        float xCenter = getXCenter(point);
        float yCenter = getYCenter(point);

        brush.setColor(strokeColor);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeWidth(strokeWidth);

        PointF start1 = getPointInCircle(xCenter, yCenter, radius, 240);
        PointF end1 = getPointInCircle(xCenter, yCenter, radius, 60);
        PointF start2 = getPointInCircle(xCenter, yCenter, radius, 300);
        PointF end2 = getPointInCircle(xCenter, yCenter, radius, 120);

        if (X.equals(piece)) {
            canvas.drawLine(start1.x, start1.y, end1.x, end1.y, brush);
            canvas.drawLine(start2.x, start2.y, end2.x, end2.y, brush);
        } else if (O.equals(piece)) {
            canvas.drawCircle(xCenter, yCenter, radius, brush);
        }
    }

    private PointF getPointInCircle(float xc, float yc, float radius, double degree) {
        float x = (float) (radius * Math.cos(Math.toRadians(degree))) + xc;
        float y = (float) (radius * Math.sin(Math.toRadians(degree))) + yc;
        return new PointF(x, y);
    }

    private float getXCenter(Point point) {
        if (point.y == 0) {
            return oneThirdWidth / 2f;
        } else if (point.y == 1) {
            return (oneThirdWidth + twoThirdWidth) / 2f;
        } else if (point.y == 2) {
            return (twoThirdWidth + getWidth()) / 2f;
        } else {
            return -1;
        }
    }

    private float getYCenter(Point point) {
        if (point.x == 0) {
            return oneThirdHeight / 2f;
        } else if (point.x == 1) {
            return (oneThirdHeight + twoThirdHeight) / 2f;
        } else if (point.x == 2) {
            return (twoThirdHeight + getHeight()) / 2f;
        } else {
            return -1;
        }
    }

    private void updateWinLine(String type) {
        if (TOP_LEFT_DIAGONAL.equals(type)) {
            if (lineEndX <= lineStopX && lineEndY <= lineStopY) {
                lineEndX += xStep;
                lineEndY += yStep;
                invalidate();
                requestLayout();
            } else {
                canDrawWinLine = false;
                if (listener != null) {
                    listener.finishedDrawing();
                }
            }
        } else if (TOP_RIGHT_DIAGONAL.equals(type)) {
            if (lineEndX >= lineStopX && lineEndY <= lineStopY) {
                lineEndX -= xStep;
                lineEndY += yStep;
                invalidate();
                requestLayout();
            } else {
                canDrawWinLine = false;
                if (listener != null) {
                    listener.finishedDrawing();
                }
            }
        } else if (MIDDLE_COLUMN.equals(type) || LEFT_COLUMN.equals(type) || RIGHT_COLUMN.equals(type)) {
            if (lineEndY <= lineStopY) {
                lineEndY += yStep;
                invalidate();
                requestLayout();
            } else {
                canDrawWinLine = false;
                if (listener != null) {
                    listener.finishedDrawing();
                }
            }
        } else if (MIDDLE_ROW.equals(type) || TOP_ROW.equals(type) || BOTTOM_ROW.equals(type)) {
            if (lineEndX <= lineStopX) {
                lineEndX += xStep;
                invalidate();
                requestLayout();
            } else {
                canDrawWinLine = false;
                if (listener != null) {
                    listener.finishedDrawing();
                }
            }
        } else if (EMPTY.equals(type)) {
            canDrawWinLine = false;
            listener.finishedDrawing();
        }
    }

    public void drawPiece(Point point, String piece) {
        canDrawPiece = true;
        tilePieces.put(point, piece);
        draw();
    }

    public void startTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    pointClicked.set(event.getX(), event.getY());
                }
                return false;
            }
        });
    }

    public void setTiles(int tiles) {
        if (tiles >= 2) {
            this.tiles = tiles;
        } else {
            this.tiles = 2;
        }
    }

    public void setStrokeWidth(float width) {
        strokeWidth = width;
    }

    public void draw() {
        isClearing = false;
        invalidate();
        requestLayout();
    }

    public void clear() {
        tilePieces.clear();
        isClearing = true;
        draw();
    }

    public Point getTicTacToeTile() {
        Point point = new Point(-1, -1);
        if (pointClicked.y < oneThirdHeight - strokeWidth) {
            if (pointClicked.x < oneThirdWidth - strokeWidth) {
                point.set(0, 0);
            } else if (pointClicked.x > oneThirdWidth && pointClicked.x < twoThirdWidth - strokeWidth) {
                point.set(0, 1);
            } else if (pointClicked.x > twoThirdWidth) {
                point.set(0, 2);
            }
        } else if (pointClicked.y > oneThirdHeight && pointClicked.y < twoThirdHeight - strokeWidth) {
            if (pointClicked.x < oneThirdWidth - strokeWidth) {
                point.set(1, 0);
            } else if (pointClicked.x > oneThirdWidth && pointClicked.x < twoThirdWidth - strokeWidth) {
                point.set(1, 1);
            } else if (pointClicked.x > twoThirdWidth) {
                point.set(1, 2);
            }
        } else if (pointClicked.y > twoThirdHeight) {
            if (pointClicked.x < oneThirdWidth - strokeWidth) {
                point.set(2, 0);
            } else if (pointClicked.x > oneThirdWidth && pointClicked.x < twoThirdWidth - strokeWidth) {
                point.set(2, 1);
            } else if (pointClicked.x > twoThirdWidth) {
                point.set(2, 2);
            }
        }

        return point;
    }

    public void drawWinLine(String lineType) {
//        float oneThirdWidth = getWidth() / 3f;
//        float twoThirdWidth = 2f * getWidth() / 3f;
//        float oneThirdHeight = getHeight() / 3f;
//        float twoThirdHeight = 2f * getHeight() / 3f;
        canDrawWinLine = true;
        switch (lineType) {
            case TOP_LEFT_DIAGONAL:
                lineStartX = winLineOffset;
                lineStartY = winLineOffset;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - winLineOffset;
                lineStopY = getHeight() - winLineOffset;
                break;
            case TOP_RIGHT_DIAGONAL:
                lineStartX = getWidth() - winLineOffset;
                lineStartY = winLineOffset;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = winLineOffset;
                lineStopY = getHeight() - winLineOffset;
                break;
            case MIDDLE_COLUMN:
                lineStartX = getWidth() / 2f;
                lineStartY = winLineOffset;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() / 2f;
                lineStopY = getHeight() - winLineOffset;
                break;
            case LEFT_COLUMN:
                lineStartX = oneThirdWidth / 2f;
                lineStartY = winLineOffset;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = oneThirdWidth / 2f;
                lineStopY = getHeight() - winLineOffset;
                break;
            case RIGHT_COLUMN:
                lineStartX = (twoThirdWidth + getWidth()) / 2f;
                lineStartY = winLineOffset;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = (twoThirdWidth + getWidth()) / 2f;
                lineStopY = getHeight() - winLineOffset;
                break;
            case MIDDLE_ROW:
                lineStartX = winLineOffset;
                lineStartY = getHeight() / 2f;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - winLineOffset;
                lineStopY = getHeight() / 2f;
                break;
            case TOP_ROW:
                lineStartX = winLineOffset;
                lineStartY = oneThirdHeight / 2f;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - winLineOffset;
                lineStopY = oneThirdHeight / 2f;
                break;
            case BOTTOM_ROW:
                lineStartX = winLineOffset;
                lineStartY = (twoThirdHeight + getHeight()) / 2f;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - winLineOffset;
                lineStopY = (twoThirdHeight + getHeight()) / 2f;
                break;
            default:
                canDrawWinLine = false;
                break;
        }

        this.lineType = lineType;
        xStep = Math.abs(lineStartX - lineStopX) / step;
        yStep = Math.abs(lineStartY - lineStopY) / step;
        draw();
    }

    public Map<Point, String> getTilePieces() {
        return tilePieces;
    }

    public void setOnFinishedDrawingListener(OnFinishedDrawingListener drawingListener) {
        listener = drawingListener;
    }

    public interface OnFinishedDrawingListener {
        void finishedDrawing();
    }

}
