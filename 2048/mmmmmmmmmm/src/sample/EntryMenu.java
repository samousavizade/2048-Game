package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class EntryMenu extends AbstractMenu
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    RotateTransition rotateAnimation = new RotateTransition();

    @FXML
    private Button PlayButtonEntryMenu;

    @FXML
    private Button RegisterButtonEntryMenu;

    @FXML
    private Button ExitButtonEntryMenu;

    @FXML
    private Button RankButtonEntryMenu;

    @Override
    public void selectOption()
    {

    }

    @FXML
    void changeToRegisterMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/RegisterMenu.fxml");
    }

    @FXML
    void changeToLoginMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/LoginMenu.fxml");
    }

    @FXML
    void changeToRankedMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/RankMenu.fxml");

    }

    @FXML
    void exit(MouseEvent event)
    {
        System.exit(1);
    }



    @FXML
    void rotate(MouseEvent event)
    {
//        onEnterButtonAction(event);
//        rotateAnimation.setNode(((Node) event.getSource()));
//        rotateAnimation.setDuration(Duration.seconds(1));
//        rotateAnimation.setByAngle(36);
//        rotateAnimation.setFromAngle(-18);
//        rotateAnimation.setAutoReverse(true);
//        rotateAnimation.setCycleCount(1000);
//        rotateAnimation.play();




    }

    @FXML
    void rotateOff(MouseEvent event)
    {
        onExitButtonAction(event);
        rotateAnimation.stop();

    }

    @FXML
    void initialize()
    {

    }
}
