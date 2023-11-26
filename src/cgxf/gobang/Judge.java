package cgxf.gobang;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 判断模块
 */
public class Judge {
    /**
     * 构造方法
     */
    public Judge() {

    }

    /**
     * 检查是否胜利
     * 
     * @param state
     * @param chessBoard
     */
    public void checkWin(State state, JPanel chessBoard) {
        // 获取当前棋子
        Stone stone = state.getStone();

        // 棋子参数
        int x = stone.getX();
        int y = stone.getY();
        int color = stone.getColor();

        // 水平、竖直、左斜、右斜计数器
        int rowCount = 0;
        int columnCount = 0;
        int leftCount = 0;
        int rightCount = 0;

        // 检查水平棋子
        for (int i = x; i < Gobang.N; i++) {
            if (Gobang.isPlaced[i][y] == color) {
                rowCount++;
            } else {
                break;
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (Gobang.isPlaced[i][y] == color) {
                rowCount++;
            } else {
                break;
            }
        }

        // 检查垂直的棋子
        for (int j = y; j < Gobang.N; j++) {
            if (Gobang.isPlaced[x][j] == color) {
                columnCount++;
            } else {
                break;
            }
        }
        for (int j = y - 1; j >= 0; j--) {
            if (Gobang.isPlaced[x][j] == color) {
                columnCount++;
            } else {
                break;
            }
        }

        // 检查左斜的
        int k = 0;
        while (x + k < Gobang.N && y + k < Gobang.N) {
            if (Gobang.isPlaced[x + k][y + k] == color) {
                leftCount++;
                k++;
            } else {
                k = 1;
                break;
            }
        }
        while (x - k >= 0 && y - k >= 0) {
            if (Gobang.isPlaced[x - k][y - k] == color) {
                leftCount++;
                k++;
            } else {
                k = 0;
                break;
            }
        }

        // 检查右斜的
        while (x + k < Gobang.N && y - k >= 0) {
            if (Gobang.isPlaced[x + k][y - k] == color) {
                rightCount++;
                k++;
            } else {
                k = 1;
                break;
            }
        }
        while (x - k >= 0 && y + k < Gobang.N) {
            if (Gobang.isPlaced[x - k][y + k] == color) {
                rightCount++;
                k++;
            } else {
                k = 0;
                break;
            }
        }

        // 当任意一个计数器达到5时结束
        if (rowCount == 5 || columnCount == 5 || leftCount == 5 || rightCount == 5) {
            String message = null;
            if (state.getTurn() == 2 && state.getGameState() == 1) {
                message = "黑方获胜";
            } else if (state.getTurn() == 1 && state.getGameState() == 1) {
                message = "白方获胜";
            }
            JOptionPane.showMessageDialog(null, "游戏结束: " + message);
            // 设置状态为未开始
            state.setGameState(0);
            // 清空棋盘
            chessBoard.repaint();
            // 清空棋子
            State.stoneCounts = 0;
        }
    }

    /**
     * 判断棋盘满
     * 
     * @param state
     * @param chessBoard
     */
    public void isFull(State state, JPanel chessBoard) {
        if (State.stoneCounts == Gobang.N * Gobang.N) {
            JOptionPane.showMessageDialog(null, "游戏结束: 平局 -- 棋盘满了");
            // 设置状态为未开始
            state.setGameState(0);
            // 清空棋盘
            chessBoard.repaint();
            // 清空棋子
            State.stoneCounts = 0;
        }
    }
}
