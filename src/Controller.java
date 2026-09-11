import Models.ModeloSenhora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final ModeloSenhora modeloSenhora;
    private final View view;

    public Controller() {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new View();

        /*view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());*/
    }

    public Controller(ModeloSenhora modeloSenhora, View view) {
        this.modeloSenhora = modeloSenhora;
        this.view = view;

        /*view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());*/
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
