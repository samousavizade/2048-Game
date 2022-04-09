package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Models.Gender;
import sample.Models.Player;

public class MainMenu extends AbstractMenu
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label WellComeLabelMainMenu;
    @FXML
    private Button StartGameButtonGameMenu;

    @FXML
    private Button LogOutButtonMainMenu;

    @FXML
    private Button SettingButtonMainMenu;

    @FXML
    void changeToEntryMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/EntryMenu.fxml");
    }

    @FXML
    void changeToSettingMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/SettingMenu.fxml");
    }

    @FXML
    void changeToStartGame(MouseEvent event) throws Exception
    {
        Stage currentStage = ((Stage) StartGameButtonGameMenu.getScene().getWindow());
        GameController.setCurrentPlayer(currentPlayer);
        GameController.getInstance().initializeGame(currentStage);
    }

    @Override
    public void selectOption()
    {

    }

    @FXML
    void initialize()
    {
        WellComeLabelMainMenu.setText("Welcome to 2048 Dear (" + currentPlayer.getUserName() + ")");

    }
}
