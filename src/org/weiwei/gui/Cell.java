package org.weiwei.gui;

public class Cell {

    private Boolean status;

    private int x; // 细胞的位置
    private int y; // 细胞位置

    /**
     * 构造方法
     * @param status
     * @param x
     * @param y
     */
    public Cell(Boolean status, int x, int y) {
        this.status = status;
        this.x = x;
        this.y = y;
    }

    public Cell(){}

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
