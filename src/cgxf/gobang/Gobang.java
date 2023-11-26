package cgxf.gobang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gobang extends JFrame {
    /**
     * 棋盘的大小
     */
    public static final int N = 15;
    public static final int x = 20;
    public static final int y = 20;
    /**
     * 棋盘间距
     */
    public static final int size = 40;
    public static final int depth = 4;

    /**
     * 交点是否落子
     */
    public static int[][] isPlaced = new int[N][N];

    /**
     * 画笔
     */
    public Graphics pen;

    /**
     * 游戏环境实例
     */
    protected Environment environment = new Environment();

    /**
     * 游戏控制实例
     */
    protected Control control = new Control();

    /**
     * 游戏状态实例
     */
    protected State state = new State(1, 0);

    /**
     * 游戏判断实例
     */
    protected Judge judge = new Judge();

    /**
     * 游戏ai实例
     */
    protected AI ai = new AI();

    /**
     * 窗口
     */
    private JPanel panel;

    /**
     * 棋盘
     */
    private JPanel chessBoard;

    /**
     * 选项栏
     */
    private JPanel optionBar;

    /**
     * 开始按钮
     */
    private JButton startButton;

    /**
     * 悔棋按钮
     */
    private JButton restartButton;

    /**
     * 认输按钮
     */
    private JButton endButton;

    /**
     * 计算时间标签
     */
    private JLabel timeLabel;

    /**
     * ai落子坐标标签
     */
    private JLabel positionLabel;

    /**
     * ai局面分标签
     */
    private JLabel scoreLabel;

    /**
     * 版本
     */
    private JLabel versonLabel;

    /**
     * 棋盘监听类
     */
    public class ChessBoardListener implements MouseListener {
        /**
         * 棋盘类
         */
        private Gobang gobang;

        /**
         * 构造方法
         */
        public ChessBoardListener() {

        }

        /**
         * 构造方法
         * 
         * @param gobang
         */
        public ChessBoardListener(Gobang gobang) {
            this.gobang = gobang;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // 获取鼠标坐标
            int px = e.getX();
            int py = e.getY();
            environment.playChess(state, gobang, pen, px, py);
            judge.checkWin(state, gobang.chessBoard);
            judge.isFull(state, chessBoard);
            if (state.getTurn() == 2) {
                Long startTime = System.currentTimeMillis();
                ai.maxMinSearch(Gobang.isPlaced, state.getTurn(), Gobang.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                Long endTime = System.currentTimeMillis();
                ai.aiPlayChess(state, gobang.chessBoard.getGraphics());
                gobang.getTimeLabel().setText(String.format("AI计算时间: %dms", endTime - startTime));
                gobang.getPositionLabel()
                        .setText(String.format("AI落子坐标: (%d, %d)", AI.position.getX(), AI.position.getY()));
                gobang.getScoreLabel().setText(String.format("AI局面分数: %d", AI.position.getScore()));
                judge.checkWin(state, gobang.chessBoard);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * 开始按钮监听类
     */
    public class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Graphics pen = chessBoard.getGraphics();
            control.startGame(environment, state, chessBoard, pen);
        }
    }

    /**
     * 悔棋按钮监听
     */
    public class RestartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            control.restartGame();
        }
    }

    /**
     * 认输按钮监听
     */
    public class EndButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            control.endGame(state, chessBoard);
        }
    }

    /**
     * 构造方法
     */
    public Gobang() {
        // 调用父类构造方法
        super("Gobang PVE");
    }

    public void initializeChessBoard() {
        // 面板
        panel = new JPanel();

        // 使用null布局
        panel.setLayout(null);

        // 设置棋盘大小
        chessBoard = new JPanel() {
            public void paint(Graphics pen) {
                super.paint(pen);
                if (state.getGameState() == 1) {
                    // 画棋盘
                    environment.drawChessBoard(pen);
                    // 画棋子
                    environment.drawChessStone(pen);
                } else {
                    ImageIcon cover = new ImageIcon("images/cover.jpg");
                    pen.drawImage(cover.getImage(), 0, 0, null);
                }
            }
        };
        chessBoard.setBounds(20, 20, 600, 600);
        chessBoard.setBackground(Color.LIGHT_GRAY);
        chessBoard.setLayout(null);
        panel.add(chessBoard);
        chessBoard.addMouseListener(new ChessBoardListener(this));

        // 设置选项栏大小
        optionBar = new JPanel();
        optionBar.setBounds(640, 20, 160, 600);
        optionBar.setBackground(Color.WHITE);
        optionBar.setLayout(null);
        panel.add(optionBar);

        // 开始按钮
        startButton = new JButton("开始游戏");
        startButton.setBounds(10, 20, 140, 40);
        startButton.setFocusPainted(false);
        optionBar.add(startButton);
        startButton.addActionListener(new StartButtonListener());

        // 悔棋按钮
        restartButton = new JButton("悔棋");
        restartButton.setBounds(10, 80, 140, 40);
        restartButton.setFocusPainted(false);
        optionBar.add(restartButton);
        restartButton.addActionListener(new RestartButtonListener());

        // 结束按钮
        endButton = new JButton("认输");
        endButton.setBounds(10, 140, 140, 40);
        endButton.setFocusPainted(false);
        optionBar.add(endButton);
        endButton.addActionListener(new EndButtonListener());

        // ai计算时间
        timeLabel = new JLabel("AI计算时间: ");
        timeLabel.setBounds(10, 300, 140, 20);
        optionBar.add(timeLabel);

        // ai落点坐标
        positionLabel = new JLabel("AI落子坐标: ");
        positionLabel.setBounds(10, 330, 140, 20);
        optionBar.add(positionLabel);

        // ai局面分数
        scoreLabel = new JLabel("AI局面分数: ");
        scoreLabel.setBounds(10, 360, 140, 20);
        optionBar.add(scoreLabel);

        // version
        versonLabel = new JLabel("VERSION 1.0 BY CGXF");
        versonLabel.setBounds(10, 570, 140, 20);
        optionBar.add(versonLabel);

        // 将面板添加到窗体中
        this.add(panel);

        // 窗体大小
        this.setSize(850, 680);

        // 窗体居中
        this.setLocationRelativeTo(null);

        // 窗体初始可见
        this.setVisible(true);

        // 窗体不可拉伸
        this.setResizable(false);

        // 退出进程
        this.setDefaultCloseOperation(3);
    }

    public JPanel getChessBoard() {
        return chessBoard;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getPositionLabel() {
        return positionLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public static void main(String[] args) {
        Gobang gobang = new Gobang();
        gobang.initializeChessBoard();
    }
}
