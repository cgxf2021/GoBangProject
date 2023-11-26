package cgxf.gobang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;

public class AI {
    /**
     * 定义一些棋型权重
     */
    public static final int OTHER = 0;
    public static final int WHITE_FIVE = 1000000;
    public static final int BLACK_FIVE = -10000000;
    public static final int WHITE_LIVE_FOUR = 50000;
    public static final int BLACK_LIVE_FOUR = -100000;
    public static final int WHITE_RUSH_FOUR = 400;
    public static final int BLACK_RUSH_FOUR = -100000;
    public static final int WHITE_LIVE_THREE = 400;
    public static final int BLACK_LIVE_THREE = -8000;
    public static final int WHITE_SLEEP_THREE = 20;
    public static final int BLACK_SLEEP_THREE = -50;
    public static final int WHITE_LIVE_TWO = 20;
    public static final int BLACK_LIVE_TWO = -50;
    public static final int WHITE_SLEEP_TWO = 1;
    public static final int BLACK_SLEEP_TWO = -3;
    public static final int WHITE_ONE = 1;
    public static final int BLACK_ONE = -3;

    public static Position position;

    /**
     * 映射表
     */
    public static final Map<String, Integer> mapTable = new HashMap<String, Integer>();
    static {
        // 白方连5
        mapTable.put("222222", WHITE_FIVE);
        mapTable.put("222220", WHITE_FIVE);
        mapTable.put("022222", WHITE_FIVE);
        mapTable.put("222221", WHITE_FIVE);
        mapTable.put("122222", WHITE_FIVE);
        mapTable.put("322222", WHITE_FIVE);
        mapTable.put("222223", WHITE_FIVE);
        // 黑方连5
        mapTable.put("111111", BLACK_FIVE);
        mapTable.put("111110", BLACK_FIVE);
        mapTable.put("011111", BLACK_FIVE);
        mapTable.put("111112", BLACK_FIVE);
        mapTable.put("211111", BLACK_FIVE);
        mapTable.put("111113", BLACK_FIVE);
        mapTable.put("311111", BLACK_FIVE);
        // 白活4
        mapTable.put("022220", WHITE_LIVE_FOUR);
        // 黑活4
        mapTable.put("011110", BLACK_LIVE_FOUR);
        // 白活3
        mapTable.put("022200", WHITE_LIVE_THREE);
        mapTable.put("002220", WHITE_LIVE_THREE);
        mapTable.put("020220", WHITE_LIVE_THREE);
        mapTable.put("022020", WHITE_LIVE_THREE);
        // 黑活3
        mapTable.put("011100", BLACK_LIVE_THREE);
        mapTable.put("001110", BLACK_LIVE_THREE);
        mapTable.put("010110", BLACK_LIVE_THREE);
        mapTable.put("011010", BLACK_LIVE_THREE);
        // 白活2
        mapTable.put("022000", WHITE_LIVE_TWO);
        mapTable.put("020200", WHITE_LIVE_TWO);
        mapTable.put("020020", WHITE_LIVE_TWO);
        mapTable.put("002200", WHITE_LIVE_TWO);
        mapTable.put("002020", WHITE_LIVE_TWO);
        mapTable.put("000220", WHITE_LIVE_TWO);
        // 黑活2
        mapTable.put("011000", BLACK_LIVE_TWO);
        mapTable.put("010100", BLACK_LIVE_TWO);
        mapTable.put("010010", BLACK_LIVE_TWO);
        mapTable.put("001100", BLACK_LIVE_TWO);
        mapTable.put("001010", BLACK_LIVE_TWO);
        mapTable.put("000110", BLACK_LIVE_TWO);
        // 白活1
        mapTable.put("020000", WHITE_ONE);
        mapTable.put("002000", WHITE_ONE);
        mapTable.put("000200", WHITE_ONE);
        mapTable.put("000020", WHITE_ONE);
        // 黑活1
        mapTable.put("010000", BLACK_ONE);
        mapTable.put("001000", BLACK_ONE);
        mapTable.put("000100", BLACK_ONE);
        mapTable.put("000010", BLACK_ONE);

        // 计算冲4、眠3、眠2
        int[] p = new int[6];
        for (p[0] = 0; p[0] < 4; p[0]++) {
            for (p[1] = 0; p[1] < 3; p[1]++) {
                for (p[2] = 0; p[2] < 3; p[2]++) {
                    for (p[3] = 0; p[3] < 3; p[3]++) {
                        for (p[4] = 0; p[4] < 3; p[4]++) {
                            for (p[5] = 0; p[5] < 4; p[5]++) {
                                int leftWhiteCount = 0;
                                int leftBlackCount = 0;
                                int rightWhiteCount = 0;
                                int rightBlackCount = 0;
                                // 将数组保存为字符串
                                String mapKey = Integer.toString(p[0]) + Integer.toString(p[1]) + Integer.toString(p[2])
                                        + Integer.toString(p[3]) + Integer.toString(p[4]) + Integer.toString(p[5]);
                                // 记录左右黑白棋子个数
                                if (p[0] == 1) {
                                    leftBlackCount++;
                                } else if (p[0] == 2) {
                                    leftWhiteCount++;
                                }

                                if (p[1] == 1) {
                                    leftBlackCount++;
                                    rightBlackCount++;
                                } else if (p[1] == 2) {
                                    leftWhiteCount++;
                                    rightWhiteCount++;
                                }

                                if (p[2] == 1) {
                                    leftBlackCount++;
                                    rightBlackCount++;
                                } else if (p[2] == 2) {
                                    leftWhiteCount++;
                                    rightWhiteCount++;
                                }

                                if (p[3] == 1) {
                                    leftBlackCount++;
                                    rightBlackCount++;
                                } else if (p[3] == 2) {
                                    leftWhiteCount++;
                                    rightWhiteCount++;
                                }

                                if (p[4] == 1) {
                                    leftBlackCount++;
                                    rightBlackCount++;
                                } else if (p[4] == 2) {
                                    leftWhiteCount++;
                                    rightWhiteCount++;
                                }

                                if (p[5] == 1) {
                                    rightBlackCount++;
                                } else if (p[5] == 2) {
                                    rightWhiteCount++;
                                }

                                // 有边界
                                if (p[0] == 3 || p[5] == 3) {
                                    if (p[0] == 3 && p[5] != 3) {
                                        // 白冲4
                                        if (rightBlackCount == 0 && rightWhiteCount == 4) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_RUSH_FOUR);
                                            }
                                        }
                                        // 黑冲4
                                        if (rightBlackCount == 4 && rightWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_RUSH_FOUR);
                                            }
                                        }
                                        // 白眠3
                                        if (rightBlackCount == 0 && rightWhiteCount == 3) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_SLEEP_THREE);
                                            }
                                        }
                                        // 黑眠3
                                        if (rightBlackCount == 3 && rightWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_SLEEP_THREE);
                                            }
                                        }
                                        // 白眠2
                                        if (rightBlackCount == 0 && rightWhiteCount == 2) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_SLEEP_TWO);
                                            }
                                        }
                                        // 黑眠2
                                        if (rightBlackCount == 2 && rightWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_SLEEP_TWO);
                                            }
                                        }
                                    } else if (p[0] != 3 && p[5] == 3) {
                                        // 白冲4
                                        if (leftBlackCount == 0 && leftWhiteCount == 4) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_RUSH_FOUR);
                                            }
                                        }
                                        // 黑冲4
                                        if (leftBlackCount == 4 && leftWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_RUSH_FOUR);
                                            }
                                        }
                                        // 白眠3
                                        if (leftBlackCount == 0 && leftWhiteCount == 3) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_SLEEP_THREE);
                                            }
                                        }
                                        // 黑眠3
                                        if (leftBlackCount == 3 && leftWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_SLEEP_THREE);
                                            }
                                        }
                                        // 白眠2
                                        if (leftBlackCount == 0 && leftWhiteCount == 2) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, WHITE_SLEEP_TWO);
                                            }
                                        }
                                        // 黑眠2
                                        if (leftBlackCount == 2 && leftWhiteCount == 0) {
                                            if (mapTable.get(mapKey) == null) {
                                                mapTable.put(mapKey, BLACK_SLEEP_TWO);
                                            }
                                        }
                                    }
                                } else {
                                    // 白冲4
                                    if ((leftBlackCount == 0 && leftWhiteCount == 4)
                                            || (rightBlackCount == 0 && rightWhiteCount == 4)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, WHITE_RUSH_FOUR);
                                        }
                                    }
                                    // 黑冲4
                                    if ((leftBlackCount == 4 && leftWhiteCount == 0)
                                            || (rightBlackCount == 4 && rightWhiteCount == 0)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, BLACK_RUSH_FOUR);
                                        }
                                    }
                                    // 白眠3
                                    if ((leftBlackCount == 0 && leftWhiteCount == 3)
                                            || (rightBlackCount == 0 && rightWhiteCount == 3)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, WHITE_SLEEP_THREE);
                                        }
                                    }
                                    // 黑眠3
                                    if ((leftBlackCount == 3 && leftWhiteCount == 0)
                                            || (rightBlackCount == 3 && rightWhiteCount == 0)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, BLACK_SLEEP_THREE);
                                        }
                                    }
                                    // 白眠2
                                    if ((leftBlackCount == 0 && leftWhiteCount == 2)
                                            || (rightBlackCount == 0 && rightWhiteCount == 2)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, WHITE_SLEEP_TWO);
                                        }
                                    }
                                    // 黑眠2
                                    if ((leftBlackCount == 2 && leftWhiteCount == 0)
                                            || (rightBlackCount == 2 && rightWhiteCount == 0)) {
                                        if (mapTable.get(mapKey) == null) {
                                            mapTable.put(mapKey, BLACK_SLEEP_TWO);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 空构造方法
     */
    public AI() {

    }

    /**
     * 局面评估函数
     * 
     * @param isPlaced
     * @return
     */
    public int evaluate(int[][] isPlaced) {
        // 六元组
        SixTuple sixTuple;
        // 棋局分数
        int score = 0;
        // 加入棋盘边界
        int[][] newBoard = new int[Gobang.N + 2][Gobang.N + 2];
        for (int i = 0; i < Gobang.N + 2; i++) {
            newBoard[i][0] = 3;
            newBoard[i][Gobang.N + 1] = 3;
            newBoard[0][i] = 3;
            newBoard[Gobang.N + 1][i] = 3;
        }

        for (int i = 0; i < Gobang.N; i++) {
            for (int j = 0; j < Gobang.N; j++) {
                newBoard[i + 1][j + 1] = isPlaced[i][j];
            }
        }

        // 判断水平棋型
        for (int i = 1; i < Gobang.N; i++) {
            for (int j = 0; j <= Gobang.N - 4; j++) {
                sixTuple = new SixTuple(newBoard[i][j], newBoard[i][j + 1], newBoard[i][j + 2], newBoard[i][j + 3],
                        newBoard[i][j + 4], newBoard[i][j + 5]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }

        // 判断垂直棋型
        for (int j = 1; j < Gobang.N; j++) {
            for (int i = 0; i <= Gobang.N - 4; i++) {
                sixTuple = new SixTuple(newBoard[i][j], newBoard[i + 1][j], newBoard[i + 2][j], newBoard[i + 3][j],
                        newBoard[i + 4][j], newBoard[i + 5][j]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }

        // 判断左斜棋型
        for (int i = 0; i <= Gobang.N - 5; i++) {
            for (int j = 0; j <= Gobang.N - i - 4; j++) {
                int k = i + j;
                sixTuple = new SixTuple(newBoard[k][j], newBoard[k + 1][j + 1], newBoard[k + 2][j + 2],
                        newBoard[k + 3][j + 3], newBoard[k + 4][j + 4], newBoard[k + 5][j + 5]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }
        for (int j = 1; j <= Gobang.N - 5; j++) {
            for (int i = 0; i <= Gobang.N - j - 4; i++) {
                int k = j + i;
                sixTuple = new SixTuple(newBoard[i][k], newBoard[i + 1][k + 1], newBoard[i + 2][k + 2],
                        newBoard[i + 3][k + 3], newBoard[i + 4][k + 4], newBoard[i + 5][k + 5]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }

        // 判断右斜棋型
        for (int i = 6; i <= Gobang.N + 1; i++) {
            for (int j = 0; j <= i - 5; j++) {
                int k = i - j;
                sixTuple = new SixTuple(newBoard[k][j], newBoard[k - 1][j + 1], newBoard[k - 2][j + 2],
                        newBoard[k - 3][j + 3], newBoard[k - 4][j + 4], newBoard[k - 5][j + 5]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }
        for (int j = 1; j <= Gobang.N - 5; j++) {
            for (int i = 0; i <= Gobang.N - j - 4; i++) {
                int k = Gobang.N + 1 - i;
                int s = j + i;
                sixTuple = new SixTuple(newBoard[k][s], newBoard[k - 1][s + 1], newBoard[k - 2][s + 2],
                        newBoard[k - 3][s + 3], newBoard[k - 4][s + 4], newBoard[k - 5][s + 5]);
                if (mapTable.get(sixTuple.toString()) != null) {
                    score += mapTable.get(sixTuple.toString());
                }
            }
        }

        return score;
    }

    /**
     * 局部搜索
     * 
     * @param isPlaced
     * @param turn
     * @return
     */
    public ArrayList<Position> seekBestPoints(int[][] isPlaced, int turn) {
        // 记录要搜索的点(初始值为false)
        boolean[][] boolBoard = new boolean[Gobang.N][Gobang.N];
        // 搜索偏移量
        int offset = 3;
        // 局部搜索棋子可以放置的点
        for (int i = 0; i < Gobang.N; i++) {
            for (int j = 0; j < Gobang.N; j++) {
                if (isPlaced[i][j] != 0) {
                    for (int k = 1; k <= offset; k++) {
                        // 水平搜索
                        if (j + k < Gobang.N && isPlaced[i][j + k] == 0) {
                            boolBoard[i][j + k] = true;
                        }
                        if (j - k >= 0 && isPlaced[i][j - k] == 0) {
                            boolBoard[i][j - k] = true;
                        }
                        // 垂直搜索
                        if (i + k < Gobang.N && isPlaced[i + k][j] == 0) {
                            boolBoard[i + k][j] = true;
                        }
                        if (i - k >= 0 && isPlaced[i - k][j] == 0) {
                            boolBoard[i - k][j] = true;
                        }
                        // 左斜搜索
                        if (i + k < Gobang.N && j + k < Gobang.N && isPlaced[i + k][j + k] == 0) {
                            boolBoard[i + k][j + k] = true;
                        }
                        if (i - k >= 0 && j - k >= 0 && isPlaced[i - k][j - k] == 0) {
                            boolBoard[i - k][j - k] = true;
                        }
                        // 右斜搜索
                        if (i - k >= 0 && j + k < Gobang.N && isPlaced[i - k][j + k] == 0) {
                            boolBoard[i - k][j + k] = true;
                        }
                        if (i + k < Gobang.N && j - k >= 0 && isPlaced[i + k][j - k] == 0) {
                            boolBoard[i + k][j - k] = true;
                        }
                    }
                }
            }
        }

        // 搜寻最佳位置
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < Gobang.N; i++) {
            for (int j = 0; j < Gobang.N; j++) {
                if (boolBoard[i][j] == true) {
                    isPlaced[i][j] = turn;
                    positions.add(new Position(i, j, this.evaluate(isPlaced)));
                    isPlaced[i][j] = 0;
                }
            }
        }
        // 进行排序
        Collections.sort(positions);

        return positions;
    }

    /**
     * alpha - beta 剪枝
     * 
     * @param isPlaced
     * @param turn
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    public int maxMinSearch(int[][] isPlaced, int turn, int depth, int alpha, int beta) {

        if (depth == 0) {
            ArrayList<Position> positions = this.seekBestPoints(isPlaced, turn);
            return positions.get(0).getScore();
        } else if (depth % 2 == 0) {
            // max层
            ArrayList<Position> positions = this.seekBestPoints(isPlaced, turn);

            for (int i = 0; i < positions.size(); i++) {
                isPlaced[positions.get(i).getX()][positions.get(i).getY()] = turn;
                turn--;
                int a = this.maxMinSearch(isPlaced, turn, depth - 1, alpha, beta);
                turn++;
                isPlaced[positions.get(i).getX()][positions.get(i).getY()] = 0;
                // 记录棋子
                if (a >= alpha && depth == 4) {
                    position = new Position(positions.get(i).getX(), positions.get(i).getY(),
                            positions.get(i).getScore());
                }
                alpha = Math.max(beta, Math.max(alpha, a));
                // 剪枝
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else {
            // min层
            ArrayList<Position> positions = this.seekBestPoints(isPlaced, turn);

            for (int i = 0; i < positions.size(); i++) {
                isPlaced[positions.get(i).getX()][positions.get(i).getY()] = turn;
                turn++;
                int b = - this.maxMinSearch(isPlaced, turn, depth - 1, alpha, beta);
                turn--;
                isPlaced[positions.get(i).getX()][positions.get(i).getY()] = 0;
                if (depth == 1) {
                    beta = Math.min(b, beta);
                } else {
                    beta = Math.min(alpha, Math.min(beta, b));
                }
                // 剪枝
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    /**
     * ai画棋子
     * 
     * @param state
     * @param pen
     */
    public void aiPlayChess(State state, Graphics pen) {
        int isPlacedX = position.getY();
        int isPlacedY = position.getX();

        // 计算交点
        int countX = isPlacedX * Gobang.size + Gobang.x;
        int countY = isPlacedY * Gobang.size + Gobang.y;

        if (state.getTurn() == 2) {
            pen.setColor(Color.WHITE);
            pen.fillOval(countX - Gobang.size / 4, countY - Gobang.size / 4, Gobang.size / 2,
                    Gobang.size / 2);
            Gobang.isPlaced[isPlacedY][isPlacedX] = state.getTurn();
            state.setStone(new Stone(isPlacedY, isPlacedX, state.getTurn()));
            state.setTurn(state.getTurn() - 1);
            State.stoneCounts++;
        }
    }
}
