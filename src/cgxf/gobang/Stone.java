package cgxf.gobang;

/**
 * 棋子类
 */
public class Stone {
    /**
     * 棋子的横坐标
     */
    private int x;

    /**
     * 棋子的纵坐标
     */
    private int y;

    /**
     * 棋子的颜色
     */
    private int color;

    /**
     * 空构造方法
     */
    public Stone() {

    }

    /**
     * 构造方法
     * 
     * @param x
     * @param y
     * @param color
     */
    public Stone(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
