import Models.ModeloSenhora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final ModeloSenhora cipherModel;
    private final View view;

    public Controller() {
        this.cipherModel = new CipherModel();
        this.view = new View();

        view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());
    }

    public Controller(CipherModel cipherModel, View view) {
        this.cipherModel = cipherModel;
        this.view = view;

        view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
