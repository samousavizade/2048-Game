package sample;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Models.Player;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractMenu
{
    protected static Player currentPlayer;

    public void changeScene(Event event, String sceneFXMLAddress) throws IOException
    {
        Stage stage = (Stage) (((Node) event.getSource())).getScene().getWindow();
        Parent entryMenuParent = FXMLLoader.load(getClass().getResource(sceneFXMLAddress));
        Scene entryMenuScene = new Scene(entryMenuParent);
        doFadeTransaction(entryMenuParent);
        stage.setScene(entryMenuScene);
        stage.show();
    }

    public void onEnterButtonAction(MouseEvent event)
    {
        DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.rgb(246, 255, 148, 1), 0, .2, 0, 0);
        dropShadow.setHeight(70);
        dropShadow.setWidth(70);
        if (event.getTarget() instanceof Button)
            ((Button) event.getTarget()).setEffect(dropShadow);
    }

    public void onExitButtonAction(MouseEvent event)
    {
        if (event.getTarget() instanceof Button)
            ((Button) event.getTarget()).setEffect(null);
    }

    public void doFadeTransaction(Node node)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2500));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1000);
        fadeTransition.play();
    }

    public abstract void selectOption();

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer)
    {
        AbstractMenu.currentPlayer = currentPlayer;
    }
}
