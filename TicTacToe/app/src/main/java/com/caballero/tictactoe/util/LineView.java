package com.caballero.tictactoe.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LineView extends View {

    private static final String TAG = "LineView";
    public static final String EMPTY = "com.caballero.tictactoe.util.empty";
    public static final String TOP_LEFT_DIAGONAL = "com.caballero.tictactoe.util.top.left.diagonal";
    public static final String TOP_RIGHT_DIAGONAL = "com.caballero.tictactoe.util.top.right.diagonal";
    public static final String MIDDLE_COLUMN = "com.caballero.tictactoe.util.middle.column";
    public static final String LEFT_COLUMN = "com.caballero.tictactoe.util.left.column";
    public static final String RIGHT_COLUMN = "com.caballero.tictactoe.util.right.column";
    public static final String MIDDLE_ROW = "com.caballero.tictactoe.util.middle.row";
    public static final String TOP_ROW = "com.caballero.tictactoe.util.top.row";
    public static final String BOTTOM_ROW = "com.caballero.tictactoe.util.bottom.row";
    private static final float OFFSET = .075f;
    private static final float QUARTER = .25f - OFFSET;
    private static final float THREE_QUARTERS = .75f + OFFSET;

    private DrawingFinishedListener listener;

    Paint lineStroke = new Paint(Paint.ANTI_ALIAS_FLAG) {
        {
            setDither(true);
        }
    };
    Paint mask = new Paint();
    private float strokeWidth = 20f;
    private float lineStartX = 0f;
    private float lineStartY = 0f;
    private float lineEndX = 0f;
    private float lineEndY = 0f;
    private float lineStopX = 0f;
    private float lineStopY = 0f;
    private float step = 25f;
    private float xStep = lineStopX / step;
    private float yStep = lineStopY / step;
    private int color = Color.BLACK;
    private boolean canDraw;
    private String state = EMPTY;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canDraw) {
            lineStroke.setColor(color);
            lineStroke.setStyle(Paint.Style.STROKE);
            lineStroke.setStrokeWidth(strokeWidth);
//            canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, lineStroke);
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, lineStroke);
            updateCoords(state);
        } else {
            Log.d(TAG, "onDraw: clearing");
            mask.setStyle(Paint.Style.FILL);
            mask.setColor(Color.TRANSPARENT);
            canvas.drawRect(0f, 0f, getWidth(), getHeight(), mask);
        }
        super.onDraw(canvas);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        super.onMeasure(widthSize, heightSize);
//    }

    private void updateCoords(String type) {
        if (TOP_LEFT_DIAGONAL.equals(type)) {
            if (lineEndX <= lineStopX && lineEndY <= lineStopY) {
                lineEndX += xStep;
                lineEndY += yStep;
                invalidate();
                requestLayout();
            } else {
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
                if (listener != null) {
                    listener.finishedDrawing();
                }
            }
        } else if (EMPTY.equals(type)) {
            listener.finishedDrawing();
        }
    }

    public void setStrokeWidth(float width) {
        strokeWidth = width;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setLine(String type) {
        switch (type) {
            case TOP_LEFT_DIAGONAL:
                lineStartX = 2f * strokeWidth;
                lineStartY = 2f * strokeWidth;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - (3f * strokeWidth);
                lineStopY = getHeight() - (3f * strokeWidth);
                break;
            case TOP_RIGHT_DIAGONAL:
                lineStartX = getWidth() - (3f * strokeWidth);
                lineStartY = 2f * strokeWidth;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = 2f * strokeWidth;
                lineStopY = getHeight() - (3f * strokeWidth);
                break;
            case MIDDLE_COLUMN:
                lineStartX = getWidth() / 2f;
                lineStartY = 2f * strokeWidth;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() / 2f;
                lineStopY = getHeight() - (3f * strokeWidth);
                break;
            case LEFT_COLUMN:
                lineStartX = getWidth() * QUARTER;
                lineStartY = 2f * strokeWidth;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() * QUARTER;
                lineStopY = getHeight() - (3f * strokeWidth);
                break;
            case RIGHT_COLUMN:
                lineStartX = getWidth() * THREE_QUARTERS;
                lineStartY = 2f * strokeWidth;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() * THREE_QUARTERS;
                lineStopY = getHeight() - (3f * strokeWidth);
                break;
            case MIDDLE_ROW:
                lineStartX = 2f * strokeWidth;
                lineStartY = getHeight() / 2f;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - (3f * strokeWidth);
                lineStopY = getHeight() / 2f;
                break;
            case TOP_ROW:
                lineStartX = 2f * strokeWidth;
                lineStartY = getHeight() * QUARTER;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - (3f * strokeWidth);
                lineStopY = getHeight() * QUARTER;
                break;
            case BOTTOM_ROW:
                lineStartX = 2f * strokeWidth;
                lineStartY = getHeight() * THREE_QUARTERS;
                lineEndX = lineStartX;
                lineEndY = lineStartY;
                lineStopX = getWidth() - (3f * strokeWidth);
                lineStopY = getHeight() * THREE_QUARTERS;
                break;
            default:
                lineStartX = 0f;
                lineStartY = 0f;
                lineEndX = 0f;
                lineEndY = 0f;
                lineStopX = 0f;
                lineStopY = 0f;
//                clearLine();
                break;
        }
        state = type;
        xStep = Math.abs(lineStartX - lineStopX) / step;
        yStep = Math.abs(lineStartY - lineStopY) / step;
    }

    public void setStep(float stepPerTic) {
        step = stepPerTic;
    }

    public void clearLine() {
        canDraw = false;
        lineStartX = 0f;
        lineStartY = 0f;
        lineEndX = 0f;
        lineEndY = 0f;
        lineStopX = 0f;
        lineStopY = 0f;
        invalidate();
        requestLayout();
    }

    public void draw() {
        canDraw = true;
        invalidate();
        requestLayout();
    }

    public void setDrawingFinishedListener(DrawingFinishedListener drawingFinishedListener) {
        listener = drawingFinishedListener;
    }

    public interface DrawingFinishedListener {
        void finishedDrawing();
    }
}
