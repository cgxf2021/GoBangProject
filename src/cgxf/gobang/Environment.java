package cgxf.gobang;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * 棋盘环境类
 * 包含draw board, draw stone, play chess三个方法
 */
public class Environment {
    /**
     * 棋盘参数
     */
    public static int N = Gobang.N;
    public static int x = Gobang.x;
    public static int y = Gobang.y;
    public static int size = Gobang.size;
    public static int[][] isPlaced = Gobang.isPlaced;

    /**
     * 构造方法
     */
    public Environment() {

    }

    /**
     * 画棋盘
     * 
     * @param pen
     */
    public void drawChessBoard(Graphics pen) {

        // 画棋盘
        pen.setColor(Color.BLACK);

        for (int i = 0; i < N; i++) {
            pen.drawLine(x, y + size * i, x + size * (N - 1), y + size * i);
        }
        for (int j = 0; j < N; j++) {
            pen.drawLine(x + size * j, y, x + size * j, y + size * (N - 1));
        }
    }

    /**
     * 画棋子
     * 
     * @param pen
     */
    public void drawChessStone(Graphics pen) {
        // 画棋子
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 计算交点
                int countX = j * size + x;
                int countY = i * size + y;
                if (isPlaced[i][j] == 1) {
                    pen.setColor(Color.BLACK);
                    pen.fillOval(countX - size / 4, countY - size / 4, size / 2, size / 2);
                }
                if (isPlaced[i][j] == 2) {
                    pen.setColor(Color.WHITE);
                    pen.fillOval(countX - size / 4, countY - size / 4, size / 2, size / 2);
                }
            }
        }
    }

    /**
     * 下棋
     * 
     * @param state
     * @param gobang
     * @param pen
     * @param px
     * @param py
     */
    public void playChess(State state, Gobang gobang, Graphics pen, int px, int py) {
        // 处理坐标
        int modX = (px - x) % size;
        int modY = (py - y) % size;
        int rangeX = size / 4 > modX ? modX : size - modX;
        int rangeY = size / 4 > modY ? modY : size - modY;

        // 判断游戏是否开始
        if (state.getGameState() == 0) {
            return;
        }

        if (rangeX < size / 4 && rangeY < size / 4) {
            // 交点位置
            int isPlacedX = (int) ((px - x) / size);
            if (modX >= size / 4) {
                isPlacedX++;
            }
            int isPlacedY = (int) ((py - y) / size);
            if (modY >= size / 4) {
                isPlacedY++;
            }

            // 计算交点
            int countX = isPlacedX * size + x;
            int countY = isPlacedY * size + y;

            // 获取画笔
            pen = gobang.getChessBoard().getGraphics();

            if (Gobang.isPlaced[isPlacedY][isPlacedX] != 0) {
                JOptionPane.showMessageDialog(null, "此处已有棋子");
            } else {
                if (state.getTurn() == 1) {
                    pen.setColor(Color.BLACK);
                    // 画棋子
                    pen.fillOval(countX - size / 4, countY - size / 4, size / 2,
                            size / 2);
                    // 状态改为已有棋子
                    Gobang.isPlaced[isPlacedY][isPlacedX] = state.getTurn();
                    // 记录棋子
                    state.setStone(new Stone(isPlacedY, isPlacedX, state.getTurn()));
                    state.setTurn(state.getTurn() + 1);
                    State.stoneCounts++;
                } else {
                    pen.setColor(Color.WHITE);
                    pen.fillOval(countX - size / 4, countY - size / 4, size / 2,
                            size / 2);
                    Gobang.isPlaced[isPlacedY][isPlacedX] = state.getTurn();
                    state.setStone(new Stone(isPlacedY, isPlacedX, state.getTurn()));
                    state.setTurn(state.getTurn() - 1);
                    State.stoneCounts++;
                }
            }
        }
    }
}
