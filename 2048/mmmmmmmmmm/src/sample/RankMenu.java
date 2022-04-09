package sample;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Models.Player;

public class RankMenu extends AbstractMenu
{


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> RankListView;

    @FXML
    private Button BackRankMenu;

    @Override
    public void selectOption()
    {

    }

    @FXML
    void changeMenuToEntryMenu(MouseEvent event) throws IOException
    {
        changeScene(event, "Resources/EntryMenu.fxml");

    }

    @FXML
    void initialize()
    {
        Player.getPlayers().sort(Comparator.comparingInt(Player::getHighScore));

        ObservableList<String> ranks = FXCollections.observableArrayList();

        for (int i = Player.getPlayers().size() - 1; i >= 0; i--)
            ranks.add(Player.getPlayers().get(i).getUserName() + " : " + Player.getPlayers().get(i).getHighScore());

        RankListView.setItems(ranks);

    }


}
