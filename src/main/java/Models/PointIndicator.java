package Models;

import javax.swing.*;
import java.awt.*;

/**
 * Equivalente Swing dos Canvas anonimos do View.java.
 * Desenha o circulo de ponto/vez (verde quando ativo, cinza quando inativo).
 */
public class PointIndicator extends JComponent {

    public static final Color OUTER_GREEN = new Color(0, 119, 32);
    public static final Color INNER_GREEN = new Color(64, 194, 64);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color LIGHT_GRAY = new Color(226, 226, 226);

    private boolean active = false;

    public PointIndicator() {
        setOpaque(false);
        setPreferredSize(new Dimension(30, 30));
        setMinimumSize(new Dimension(10, 10));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(active ? INNER_GREEN : LIGHT_GRAY);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        g2.setColor(active ? OUTER_GREEN : GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);

        g2.dispose();
    }
}
