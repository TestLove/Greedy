import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public  class MyTextArea extends JTextArea {
    int colums = 0;
    int rows = 1;
    public MyTextArea() {
        try {
            this.rows =this.getLineOfOffset(this.getCaretPosition());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        this.setCaret(new DefaultCaret() {
            public boolean isVisible() {
                return true;
            }
        });//使光标可见
        this.setText("333333\n22222\n55555555555555");
        this.setCaretColor(Color.white);
        this.setAutoscrolls(true);
        this.setLineWrap(true);
        this.setAutoscrolls(true);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.white);
        this.setFont(new Font("楷体", Font.BOLD, 16));

    }

}