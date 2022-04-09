package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.Models.Player;

public class LoginMenu extends AbstractMenu
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TextField UsernameTextFieldLoginMenu;

    @FXML
    private PasswordField PasswordTextFieldLoginMenu;

    @FXML
    private Button LoginButtonLoginMenu;

    @FXML
    private Button BackButtonLoginMenu;


    @Override
    public void selectOption()
    {

    }

    @FXML
    void changeToMainMenu(MouseEvent event) throws IOException
    {
        String userName = UsernameTextFieldLoginMenu.getText();
        Player playerHasLogin = Player.findPlayer(userName);
        String passWord = PasswordTextFieldLoginMenu.getText();

        if (playerHasLogin == null || !playerHasLogin.getPassWord().equals(passWord))
            ((DropShadow) LoginButtonLoginMenu.getEffect()).setColor(Color.RED);
        else
        {
            MainMenu.setCurrentPlayer(playerHasLogin);
            changeScene(event, "Resources/MainMenu.fxml");
      }
    }

    @FXML
    void changeToEntryMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/EntryMenu.fxml");
    }


    @FXML
    void initialize()
    {

    }
}
