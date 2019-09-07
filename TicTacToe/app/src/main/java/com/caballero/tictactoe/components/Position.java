package com.caballero.tictactoe.components;

import android.support.annotation.NonNull;

public class Position {

    private int row;
    private int col;

    @NonNull
    @Override
    public String toString() {
        return "row: " + getRow()
                + " col: " + getCol();
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
