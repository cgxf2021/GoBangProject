package cgxf.gobang;

public class Position implements Comparable<Position> {
    /**
     * 位置横坐标
     */
    private int x;

    /**
     * 位置纵坐标
     */
    private int y;

    /**
     * 位置分数
     */
    private int score;

    /**
     * 空构造
     */
    public Position() {

    }

    /**
     * 带参构造方法
     * 
     * @param x
     * @param y
     * @param score
     */
    public Position(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Position o) {
        // TODO Auto-generated method stub
        return o.score - this.score;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Integer.toString(this.x) + " " + Integer.toString(this.y) + " " + Integer.toString(this.score);
    }
}
