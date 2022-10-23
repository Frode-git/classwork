package org.weiwei.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import java.util.TimerTask;


public class Map extends JFrame {
    static int num = 50;
    private Container container;
    private Cell[][] cells = new Cell[num][num];
    private JButton[][] buttons = new JButton[num][num];
    private int times = 0;

    public boolean getCells(int x, int y) {
        return cells[x][y].getStatus();
    }

    public void setCells(int x, int y, boolean status) {
        cells[x][y].setStatus(status);
    }

    public Map() {
        super("生命游戏");
        container = getContentPane();
        JPanel controller = new JPanel();
        JPanel btn = new JPanel();

        JButton txt = new JButton("演变总次数:" + times);
        txt.setBackground(Color.ORANGE);
        txt.setEnabled(false);
        btn.setLayout(new GridLayout(num, num));
        JButton con = new JButton("下一次");
        con.setBackground(Color.CYAN);
        con.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                liveOrDeath();
                update();
                times++;
                txt.setText("总演变次数：" + times);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        controller.add(con);


        controller.add(txt);

        for(int i = 0; i < num; i ++) {
            for(int j = 0; j < num; j ++) {
                cells[i][j] = new Cell(false, i, j);
                buttons[i][j] = new JButton();
                buttons[i][j].setEnabled(false);
                btn.add(buttons[i][j]);
            }
        }
        btn.setSize(400, 400);
        controller.setSize(100,100);
        container.add(controller, "South");
        container.add(btn);
        container.setSize(500, 500);
        setSize(600,600);
        setVisible(true);
        int times = 1000;
        boolean[][] have = new boolean[num][num];
        Random random = new Random();
        while (times -- > 0) {
            int x, y;
            do{
                x = random.nextInt(num - 1);
                y = random.nextInt(num - 1);
            } while (have[x][y]);
            have[x][y] = true;

//            int life = random.nextInt()
            cells[x][y].setStatus(true);

        }
    }

    // 更新cells状态，ui状态
    public void update() {
        // 生命游戏规则，修改cells的状态
        for (int i = 0; i < num; i ++) {
            for (int j = 0; j < num; j ++) {
                if(cells[i][j].getStatus()) {
                    buttons[i][j].setBackground(Color.red);
                }
                else buttons[i][j].setBackground(Color.gray);
            }
        }
    }

    public void liveOrDeath() {
        // 源数据备份，根据oldCells改变细胞的状态
        Cell[][] oldCells = new Cell[num][num];
        for (int i = 0; i < num; i ++) {
            for (int j = 0; j < num; j ++) {
                oldCells[i][j] = new Cell();
                copy(oldCells[i][j], cells[i][j]); // 拷贝源数据
            }
        }

        /*for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j ++) {
                int count = 0;
                // 最外围，四个角，其他地方，中间区域，六种情况
                if (i == 0 && j == 0) { // 左上角
                    count = 0;
                    if (oldCells[i][j + 1].getStatus()) count++;
                    if (oldCells[i + 1][j + 1].getStatus()) count++;
                    if (oldCells[i + 1][j].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (i == 0 && j == cells[0].length - 1) { // 右上角
                    count = 0;
                    if (oldCells[i][j - 1].getStatus()) count++;
                    if (oldCells[i + 1][j].getStatus()) count++;
                    if (oldCells[i + 1][j - 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (i == cells[0].length - 1 && j == 0) { // 左下角
                    count = 0;
                    if (oldCells[i - 1][j].getStatus()) count++;
                    if (oldCells[i][j + 1].getStatus()) count++;
                    if (oldCells[i - 1][j + 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (i == cells[0].length - 1 && j == cells[0].length - 1) { // 右下角
                    count = 0;
                    if (oldCells[i][j - 1].getStatus()) count++;
                    if (oldCells[i - 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j - 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (i == 0 && j != 0 && j != cells[0].length - 1) { // 第一行除了最外侧两列
                    count = 0;
                    if (oldCells[i][j - 1].getStatus()) count++;
                    if (oldCells[i][j + 1].getStatus()) count++;
                    if (oldCells[i + 1][j - 1].getStatus()) count++;
                    if (oldCells[i + 1][j].getStatus()) count++;
                    if (oldCells[i + 1][j + 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            System.out.println(i + ' ' + j);
                            System.out.println(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (i == cells[0].length - 1 && j != 0 && j != cells[0].length - 1) { // 最后一行除了最外侧两列
                    count = 0;
                    if (oldCells[i][j - 1].getStatus()) count++;
                    if (oldCells[i][j + 1].getStatus()) count++;
                    if (oldCells[i - 1][j - 1].getStatus()) count++;
                    if (oldCells[i - 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j + 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (j == 0 && i != 0 && i != cells[0].length - 1) { // 第一列除了最外侧两行
                    count = 0;
                    if (oldCells[i + 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j + 1].getStatus()) count++;
                    if (oldCells[i][j + 1].getStatus()) count++;
                    if (oldCells[i + 1][j + 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else if (j == cells[0].length - 1 && i != 0 && i != cells[0].length - 1) { // 第后一列除了最外侧两行
                    count = 0;
                    if (oldCells[i + 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j].getStatus()) count++;
                    if (oldCells[i - 1][j - 1].getStatus()) count++;
                    if (oldCells[i][j - 1].getStatus()) count++;
                    if (oldCells[i + 1][j - 1].getStatus()) count++;
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }
                }
                else { // 其他内容
                    count = 0;

                    for (int k = -1; k <= 1; k ++) {
                        for (int m = -1; m <= 1; m ++) {
                            if (k != 0 && m != 0) continue;
                            else if (oldCells[i + k][j + m].getStatus()) {
                                count++;
                            }
                        }
                    }
                    switch (count) {
                        case 3:
                            cells[i][j].setStatus(true);
                            break;
                        case 2:
                            cells[i][j].setStatus(cells[i][j].getStatus());
                            break;
                        default:
                            cells[i][j].setStatus(false);
                    }

                }
            }
        }
*/
        for (int i = 0; i < num; i ++) {
            for (int j = 0; j < num; j ++) {
                int count = 0;
                // 每一个点周围的活的细胞的统计
                for (int k = -1; k <= 1; k ++) {
                    for (int m = -1; m <= 1; m ++) {
                        if (i + k >= 0 && i + k < cells[0].length && j + m >= 0 && j + m < cells[0].length && oldCells[i + k][j + m].getStatus()) {
                            if (k == 0 && m == 0) continue;
                            else {
                                count++;
                                int a = i + k;
                                int b = j + m;
//                                System.out.println(a + " " + b + " " + cells[a][b].getStatus());
                            }
                        }
                    }
                }
//                System.out.println(i + " " + j + " " + count);
                switch (count) {
                    case 3:
                        cells[i][j].setStatus(true);
                        break;
                    case 2:
//                        cells[i][j].setStatus(cells[i][j].getStatus());
                        /*System.out.println(i + ' ' + j);
                        System.out.println(cells[i][j].getStatus());*/
                        break;
                    default:
                        cells[i][j].setStatus(false);
                }

            }
        }

       /* for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j ++) {
                System.out.println(i + " "  + j + " " + cells[i][j].getStatus());
            }
        }*/

    }

    public void copy(Cell a, Cell b) {
        a.setX(b.getX());
        a.setY(b.getY());
        a.setStatus(b.getStatus());
    }

    public static void main(String[] args) {
        Map map = new Map();
        map.setDefaultCloseOperation(EXIT_ON_CLOSE);
        map.update();
    }
}