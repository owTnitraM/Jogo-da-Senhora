import Models.ModeloSenhora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final ModeloSenhora modeloSenhora;
    private final View_old view3;

    public Controller() {
        this.modeloSenhora = new ModeloSenhora();
        this.view3 = new View_old();

        /*view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());*/
    }

    public Controller(ModeloSenhora modeloSenhora, View_old view3) {
        this.modeloSenhora = modeloSenhora;
        this.view3 = view3;

        /*view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());*/
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
