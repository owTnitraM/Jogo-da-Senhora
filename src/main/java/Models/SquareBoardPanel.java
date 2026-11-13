package Models;

import javax.swing.*;
import java.awt.*;

/**
 * Equivalente Swing do Panel anonimo {@code board} do View.java:
 * mantem o tabuleiro sempre quadrado.
 */
public class SquareBoardPanel extends JPanel {

    public SquareBoardPanel() {
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        int size = Math.min(width, height);
        super.setBounds(x, y, size, size);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int size = Math.max(d.width, d.height);
        return new Dimension(size, size);
    }
}
