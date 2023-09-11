package pt.com.minesweep.vision;
import javax.swing.*;
import pt.com.minesweep.model.Field;
import pt.com.minesweep.model.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Console extends JFrame
    implements MouseListener {

    int lines = 10;
    int columns = 10;
    int scale = 60;
    int height = lines * scale;
    int width = columns * scale;
    Color neutral = new Color(184, 184, 184);
    Map<String, Function<EventHandler, ?>> buttonFunctions = new HashMap<>();
    private Field mineField = null;

    public Console() {
        buttonFunctions.put("block", this::blockAction);
        buttonFunctions.put(null, this::functionTypeError);
    }

    private void initWindow() {
        mineField = new Field(lines, columns, 10);
        mineField.getButtons().forEach(b -> {
            this.add(b);
            b.addMouseListener(this);
        });
        setLayout(new GridLayout(lines, columns));
        setTitle("MineSweeper");
        setBackground(neutral);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        addMouseListener(this);
    }

    private int functionTypeError(Object object) {
        System.out.println("Bad Function type call.");
        return (1);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        EventHandler clicked = new EventHandler(e);
        buttonFunctions.get(clicked.type).apply(clicked);
    }

    public int blockAction(EventHandler click) {
        if (click == null)
                return (1);
        if (mineField.stepOnMine() || mineField.checkWin()) {
            restartGame();
            return (0);
        }
        Block block = (Block) click.content;
        if (click.mouseButton == MouseEvent.BUTTON1)
            mineField.openBlock(block);
        else if (click.mouseButton == MouseEvent.BUTTON3)
            mineField.flagBlock(block);
        return (0);
    }

    private void    restartGame() {
        setVisible(false);
        this.getContentPane().removeAll();
        mineField = new Field(lines, columns, 10);
        mineField.getButtons().forEach(b -> {
            this.add(b);
            b.addMouseListener(this);
        });
        this.revalidate();
        this.repaint();
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    public static void main(String[] args) {

        Console mineSweeper = new Console();

        mineSweeper.initWindow();
    }
}
