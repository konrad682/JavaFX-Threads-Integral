package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
public class Controller implements Initializable{

    @FXML
    private Canvas canvas;
    @FXML
    private TextField textField;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonStop;
    @FXML
    private Label resultInteger;

    PrintInteger task;
    GraphicsContext gc;

    @FXML
    private void handleRunBtnAction(ActionEvent event){
        this.drawShapes(this.gc);
        this.task = new PrintInteger(this.gc, Integer.parseInt(this.textField.getText()));
        this.task.setOnSucceeded((event2) -> {
            Float var = (Float)this.task.getValue();
            this.resultInteger.setText("Result of Integral= " + Float.toString(var));
        });
        this.progressBar.progressProperty().bind(this.task.progressProperty());
        (new Thread(this.task)).start();

    }
    private void drawShapes(GraphicsContext gc)
    { gc.setFill(Color.BLACK);
        gc.fillRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(),gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    @FXML
    void handleStop(ActionEvent event) {

            this.task.cancel();


    }
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = this.canvas.getGraphicsContext2D();
        this.drawShapes(this.gc);
    }

}
