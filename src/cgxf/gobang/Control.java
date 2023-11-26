package cgxf.gobang;

import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 控制模块
 */
public class Control {
    /**
     * 空构造
     */
    public Control() {

    }

    /**
     * 开始游戏
     * 
     * @param environment
     * @param state
     * @param chessBoard
     * @param pen
     */
    public void startGame(Environment environment, State state, JPanel chessBoard, Graphics pen) {
        // 设置状态为开始
        state.setGameState(1);

        // 清空棋盘
        chessBoard.repaint();

        // 清空棋子
        State.stoneCounts = 0;

        // 轮次为黑
        state.setTurn(1);

        // 画棋盘
        environment.drawChessBoard(pen);

        // 画棋子
        for (int i = 0; i < Gobang.N; i++) {
            for (int j = 0; j < Gobang.N; j++) {
                Gobang.isPlaced[i][j] = 0;
            }
        }
        environment.drawChessStone(pen);
    }

    /**
     * 悔棋
     */
    public void restartGame() {
        JOptionPane.showMessageDialog(null, "我没写这功能");
    }

    /**
     * 结束游戏
     * 
     * @param state
     * @param chessBoard
     */
    public void endGame(State state, JPanel chessBoard) {
        // 设置状态为未开始
        state.setGameState(0);
        // 弹出消息
        JOptionPane.showMessageDialog(null, "你输啦");
        // 清空棋盘
        chessBoard.repaint();
        // 清空棋子
        State.stoneCounts = 0;
    }
}
