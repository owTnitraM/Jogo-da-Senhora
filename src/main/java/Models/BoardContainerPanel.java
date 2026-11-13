package Models;

import javax.swing.*;
import java.awt.*;

/**
 * Equivalente Swing do Panel anonimo {@code boardContainer} do View.java:
 * centraliza o tabuleiro quadrado no espaco disponivel.
 */
public class BoardContainerPanel extends JPanel {

    public BoardContainerPanel() {
        super(new GridBagLayout());
    }

    @Override
    public void doLayout() {
        int size = Math.min(getWidth(), getHeight());

        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if (getComponentCount() > 0) {
            getComponent(0).setBounds(x, y, size, size);
        }
    }
}
