package cgxf.gobang;

/**
 * 状态类
 */
public class State {
    /**
     * 棋盘状态
     */
    public static int stoneCounts = 0;
    
    /**
     * 当前伦次是黑还是白
     */
    private int turn;

    /**
     * 棋盘状态是开始还是结束
     */
    private int gameState;

    /**
     * 记录最新的棋子
     */
    private Stone stone = new Stone();

    /**
     * 空构造方法
     */
    public State() {

    }

    /**
     * 构造方法
     * 
     * @param turn
     * @param gameState
     */
    public State(int turn, int gameState) {
        this.turn = turn;
        this.gameState = gameState;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }
}
