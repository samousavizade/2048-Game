package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import sample.Models.Gender;
import sample.Models.Player;

public class RegisterMenu extends AbstractMenu
{


    @FXML
    private Label RegisterInformation;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button RegisterButtonRegisterMenu;

    @FXML
    private TextField UsernameTextFieldRegisterMenu;

    @FXML
    private PasswordField PasswordTextFieldRegisterMenu;

    @FXML
    private ChoiceBox<String> ChoiceBoxRegisterMenu = new ChoiceBox<>();

    @Override
    public void selectOption()
    {

    }

    @FXML
    public void registerActions(MouseEvent event) throws IOException
    {
        String userName = UsernameTextFieldRegisterMenu.getText();
        String passWord = PasswordTextFieldRegisterMenu.getText();
        String genderString = ChoiceBoxRegisterMenu.getValue();

        Player player = Player.findPlayer(userName);
        RegisterInformation.setEffect(new Glow());
        if (player == null && passWord != null && genderString != null)
        {
            Player.getPlayers().add(new Player(userName, passWord, Gender.valueOf(genderString)));
            changeScene(event,"Resources/LoginMenu.fxml");
        }

        else if (player != null)
            RegisterInformation.setText("Username Already Used!");

        else if(userName.equals(""))
            RegisterInformation.setText("Please Set Username!");

        else if (passWord.equals(""))
            RegisterInformation.setText("Please Set Password!");

        else RegisterInformation.setText("Please Set Gender!");
    }

    @FXML
    void initialize()
    {
        ChoiceBoxRegisterMenu.getItems().addAll(Gender.BOY.toString(), Gender.GIRL.toString());


    }
}
