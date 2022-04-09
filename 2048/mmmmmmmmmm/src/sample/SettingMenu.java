package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.Models.Player;

public class SettingMenu extends AbstractMenu
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NewPasswordSettingMenu;

    @FXML
    private TextField NewUsernameSettingMenu;

    @FXML
    private Pagination RowNumberHandlerSettingMenu;

    @FXML
    private Button SubmitSettingMenu;


    @Override
    public void selectOption()
    {

    }

    @FXML
    void changePassword(KeyEvent event)
    {
        String oldPassword = currentPlayer.getPassWord();
        if (oldPassword.equals(NewPasswordSettingMenu.getText()))
            ((InnerShadow) NewPasswordSettingMenu.getEffect()).setColor(Color.RED);
        else
            ((InnerShadow) NewPasswordSettingMenu.getEffect()).setColor(Color.rgb(253, 214, 43, 1));
    }

    @FXML
    void changeUserName(KeyEvent event)
    {
        String newUsername = NewUsernameSettingMenu.getText();
        Player player = Player.findPlayer(newUsername);
        if (player != null || newUsername == null)
            ((InnerShadow) NewUsernameSettingMenu.getEffect()).setColor(Color.RED);
        else
            ((InnerShadow) NewUsernameSettingMenu.getEffect()).setColor(Color.rgb(253, 214, 43, 1));
    }

    @FXML
    public void submitAccountChangesInformation(MouseEvent event) throws IOException
    {
        Color uColor = ((InnerShadow) NewUsernameSettingMenu.getEffect()).getColor();
        Color pColor = ((InnerShadow) NewPasswordSettingMenu.getEffect()).getColor();

        if (uColor.equals(Color.RED) || pColor.equals(Color.RED))
            ((DropShadow) SubmitSettingMenu.getEffect()).setColor(Color.RED);
        else
        {
            if (!NewUsernameSettingMenu.getText().equals(""))
                currentPlayer.setUserName(NewUsernameSettingMenu.getText());
            if (!NewPasswordSettingMenu.getText().equals(""))
                currentPlayer.setPassWord(NewPasswordSettingMenu.getText());

            System.out.println(RowNumberHandlerSettingMenu.getCurrentPageIndex());

            GameController.setNumberOfRowCol(RowNumberHandlerSettingMenu.getCurrentPageIndex() + 1);
            changeScene(event, "Resources/MainMenu.fxml");
        }
    }

    @FXML
    void initialize()
    {
        assert NewPasswordSettingMenu != null : "fx:id=\"NewPasswordSettingMenu\" was not injected: check your FXML file 'SettingMenu.fxml'.";
        assert NewUsernameSettingMenu != null : "fx:id=\"NewUsernameSettingMenu\" was not injected: check your FXML file 'SettingMenu.fxml'.";
        RowNumberHandlerSettingMenu.setCurrentPageIndex(GameController.getNumberOfRowCol() - 1);
    }
}
