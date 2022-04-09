package sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Models.Player;

public class GameController extends Application
{
    private GameController()
    {
    }

    private static GameController instance = new GameController();

    public static GameController getInstance()
    {
        return instance;
    }


    public void initializeGame(Stage currentStage) throws Exception
    {
        currentGame = new Game();
        scoreStage.setScene(scoreScene);
        scoreStage.setTitle("Score");
        scoreStage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 3);
        scoreStage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 20);
        start(currentStage);
    }

    private Game currentGame;
    private static Player currentPlayer;
    private static int numberOfRowCol = 4;
    private static Stage scoreStage = new Stage();
    private static Label scoreLabel = new Label();
    private static Scene scoreScene = new Scene(scoreLabel, 500, 200);

    public static int getNumberOfRowCol()
    {
        return numberOfRowCol;
    }

    public static void setNumberOfRowCol(int numberOfRowCol)
    {
        GameController.numberOfRowCol = numberOfRowCol;
    }

    public static void setCurrentPlayer(Player currentPlayer)
    {
        GameController.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private class Game
    {
        private Map gameMap = new Map();
        private int scoreInCurrentGame = 0;

        private class Map
        {
            SecureRandom random = new SecureRandom();
            GridPane gridPane = new GridPane();
            Cell[][] cells = new Cell[numberOfRowCol][numberOfRowCol];
            double rowColSize;

            private void makeGridPane()
            {
                cells = new Cell[numberOfRowCol][numberOfRowCol];
                gridPane.setHgap(7);
                gridPane.setVgap(7);
                gridPane.setGridLinesVisible(false);
                gridPane.setPadding(new Insets(40, 40, 40, 40));

                gridPane.setStyle("-fx-background-color: Gray");

                rowColSize = (500 - (2 * currentGame.gameMap.gridPane.getPadding().getTop() + currentGame.gameMap.gridPane.getHgap() * (currentGame.gameMap.gridPane.getRowCount() - 1))) / numberOfRowCol;

                for (int i = 0; i < numberOfRowCol; i++)
                {
                    gridPane.getRowConstraints().add(new RowConstraints(rowColSize));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(rowColSize));
                }

                for (int i = 0; i < numberOfRowCol; i++)
                    for (int j = 0; j < numberOfRowCol; j++)
                    {
                        Pane cellBackGroundPane = getBackGroundPane();
                        gridPane.add(cellBackGroundPane, i, j);
                    }

                gridPane.setPrefSize(500, 500);
                gridPane.setAlignment(Pos.CENTER);
            }

            public Pane getBackGroundPane()
            {
                Pane cellBackGroundPane = new Pane();
                cellBackGroundPane.resize(rowColSize, rowColSize);
                cellBackGroundPane.setStyle("-fx-background-radius: 15;-fx-background-color: #ededed");
                return cellBackGroundPane;
            }

            public void upMove()
            {
                for (int colNumber = 0; colNumber < numberOfRowCol; colNumber++)
                {
                    for (int rowNumber = numberOfRowCol - 1; rowNumber >= 0; rowNumber--)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1)
                        {
                            Label labelOnPane = ((Label) pane.getChildren().get(0));
                            int labelOnPaneNumber = Integer.valueOf(labelOnPane.getText());
                            for (int i = rowNumber - 1; i >= 0; i--)
                            {
                                Pane paneUpper = findingPaneInPosition(i, colNumber);
                                if (paneUpper.getChildren().size() == 1)
                                {
                                    Label labelOnPaneUpper = ((Label) paneUpper.getChildren().get(0));
                                    int labelOnPaneUpperNumber = Integer.valueOf(labelOnPaneUpper.getText());
                                    if (labelOnPaneNumber == labelOnPaneUpperNumber)
                                    {
                                        cells[i][colNumber].setValue(cells[i][colNumber].value * 2);

                                        pane.getChildren().remove(labelOnPane);
                                        cells[rowNumber][colNumber] = null;

                                        break;
                                    }
                                    else
                                        break;
                                }
                            }
                        }
                    }
                    int counter = 0;
                    for (int rowNumber = 0; rowNumber < numberOfRowCol; rowNumber++)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1 && counter != rowNumber)
                        {
                            Pane p = findingPaneInPosition(counter, colNumber);

                            p.getChildren().add(cells[rowNumber][colNumber].label);
                            cells[counter][colNumber] = cells[rowNumber][colNumber];

                            pane.getChildren().remove(cells[rowNumber][colNumber].label);
                            cells[rowNumber][colNumber] = null;


                            counter++;
                        }
                        else if (pane.getChildren().size() == 1 && counter == rowNumber)
                            counter++;
                    }
                }
            }

            public void downMove()
            {
                for (int colNumber = 0; colNumber < numberOfRowCol; colNumber++)
                {
                    for (int rowNumber = 0; rowNumber < numberOfRowCol; rowNumber++)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1)
                        {
                            Label labelOnPane = ((Label) pane.getChildren().get(0));
                            int labelOnPaneNumber = Integer.valueOf(labelOnPane.getText());
                            for (int i = rowNumber + 1; i < numberOfRowCol; i++)
                            {
                                Pane paneUpper = findingPaneInPosition(i, colNumber);
                                if (paneUpper.getChildren().size() == 1)
                                {
                                    Label labelOnPaneUpper = ((Label) paneUpper.getChildren().get(0));
                                    int labelOnPaneUpperNumber = Integer.valueOf(labelOnPaneUpper.getText());
                                    if (labelOnPaneNumber == labelOnPaneUpperNumber)
                                    {
                                        pane.getChildren().remove(labelOnPane);
                                        cells[rowNumber][colNumber] = null;
                                        cells[i][colNumber].setValue(cells[i][colNumber].value * 2);
                                        break;
                                    }
                                    else
                                        break;
                                }
                            }
                        }
                    }
                    int counter = numberOfRowCol - 1;
                    for (int rowNumber = numberOfRowCol - 1; rowNumber >= 0; rowNumber--)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1 && counter != rowNumber)
                        {
                            Pane p = findingPaneInPosition(counter, colNumber);

                            p.getChildren().add(cells[rowNumber][colNumber].label);
                            cells[counter][colNumber] = cells[rowNumber][colNumber];

                            pane.getChildren().remove(cells[rowNumber][colNumber].label);
                            cells[rowNumber][colNumber] = null;

                            counter--;
                        }
                        else if (pane.getChildren().size() == 1 && counter == rowNumber)
                            counter--;
                    }
                }
            }

            public void rightMove()
            {
                for (int rowNumber = 0; rowNumber < numberOfRowCol; rowNumber++)
                {
                    for (int colNumber = 0; colNumber < numberOfRowCol; colNumber++)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1)
                        {
                            Label labelOnPane = ((Label) pane.getChildren().get(0));
                            int labelOnPaneNumber = Integer.valueOf(labelOnPane.getText());
                            for (int i = colNumber + 1; i < numberOfRowCol; i++)
                            {
                                Pane paneUpper = findingPaneInPosition(rowNumber, i);
                                if (paneUpper.getChildren().size() == 1)
                                {
                                    Label labelOnPaneUpper = ((Label) paneUpper.getChildren().get(0));
                                    int labelOnPaneUpperNumber = Integer.valueOf(labelOnPaneUpper.getText());
                                    if (labelOnPaneNumber == labelOnPaneUpperNumber)
                                    {
                                        pane.getChildren().remove(labelOnPane);
                                        cells[rowNumber][colNumber] = null;
                                        cells[rowNumber][i].setValue(cells[rowNumber][i].value * 2);
                                        break;
                                    }
                                    else
                                        break;
                                }
                            }
                        }
                    }
                    int counter = numberOfRowCol - 1;
                    for (int colNumber = numberOfRowCol - 1; colNumber >= 0; colNumber--)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1 && counter != colNumber)
                        {
                            Pane p = findingPaneInPosition(rowNumber, counter);

                            p.getChildren().add(cells[rowNumber][colNumber].label);
                            cells[rowNumber][counter] = cells[rowNumber][colNumber];

                            pane.getChildren().remove(cells[rowNumber][colNumber].label);
                            cells[rowNumber][colNumber] = null;

                            counter--;
                        }
                        else if (pane.getChildren().size() == 1 && counter == colNumber)
                            counter--;
                    }
                }

            }

            public void leftMove()
            {
                for (int rowNumber = 0; rowNumber < numberOfRowCol; rowNumber++)
                {
                    for (int colNumber = numberOfRowCol - 1; colNumber >= 0; colNumber--)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1)
                        {
                            Label labelOnPane = ((Label) pane.getChildren().get(0));
                            int labelOnPaneNumber = Integer.valueOf(labelOnPane.getText());
                            for (int i = colNumber - 1; i >= 0; i--)
                            {
                                Pane paneUpper = findingPaneInPosition(rowNumber, i);
                                if (paneUpper.getChildren().size() == 1)
                                {
                                    Label labelOnPaneUpper = ((Label) paneUpper.getChildren().get(0));
                                    int labelOnPaneUpperNumber = Integer.valueOf(labelOnPaneUpper.getText());
                                    if (labelOnPaneNumber == labelOnPaneUpperNumber)
                                    {
                                        pane.getChildren().remove(labelOnPane);
                                        cells[rowNumber][colNumber] = null;
                                        cells[rowNumber][i].setValue(cells[rowNumber][i].value * 2);
                                        break;
                                    }
                                    else
                                        break;
                                }
                            }
                        }
                    }
                    int counter = 0;
                    for (int colNumber = 0; colNumber < numberOfRowCol; colNumber++)
                    {
                        Pane pane = findingPaneInPosition(rowNumber, colNumber);
                        if (pane.getChildren().size() == 1 && counter != colNumber)
                        {
                            Pane p = findingPaneInPosition(rowNumber, counter);

                            p.getChildren().add(cells[rowNumber][colNumber].label);
                            cells[rowNumber][counter] = cells[rowNumber][colNumber];

                            pane.getChildren().remove(cells[rowNumber][colNumber].label);
                            cells[rowNumber][colNumber] = null;

                            counter++;
                        }
                        else if (pane.getChildren().size() == 1 && counter == colNumber)
                            counter++;
                    }
                }
            }


            public void motion(String direction)
            {
                switch (direction)
                {
                    case "up":
                        upMove();
                        break;
                    case "down":
                        downMove();
                        break;
                    case "right":
                        rightMove();
                        break;
                    case "left":
                        leftMove();
                        break;
                }
            }

            private void addCellAfterMotion()
            {
                Position position = getFreeRandomPosition();

                cells[position.row][position.col] = new Cell();
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), cells[position.row][position.col].label);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                findingPaneInPosition(position.row, position.col).getChildren().add(cells[position.row][position.col].label);
                fadeTransition.play();

            }

            private Position getFreeRandomPosition()
            {
                int row;
                int col;
                while (true)
                {
                    row = random.nextInt(numberOfRowCol);
                    col = random.nextInt(numberOfRowCol);

                    Pane paneInPosition = findingPaneInPosition(row, col);

                    if (paneInPosition.getChildren().size() == 0)
                        return new Position(row, col);
                }
            }

            private Pane findingPaneInPosition(int row, int col)
            {
                for (Node pane : gridPane.getChildren())
                    if (GridPane.getRowIndex(pane) == row && GridPane.getColumnIndex(pane) == col)
                        return ((Pane) pane);

                return null;
            }
        }

        private class Cell
        {

            int value = 2;
            Label label;

            public Cell()
            {
                label = new Label(String.valueOf(value));

                if (numberOfRowCol <= 4)
                    label.setFont(Font.font("Comic Sans MS", 54));
                else
                    label.setFont(Font.font("Comic Sans MS", 36));

                label.setPrefSize(gameMap.rowColSize, gameMap.rowColSize);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setAlignment(Pos.CENTER);
                label.setStyle(changeColor());
            }


            private String changeColor()
            {
                switch (this.value)
                {
                    case 2:
                        return "-fx-background-color: Red; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 4:
                        return "-fx-background-color: Blue; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 8:
                        return "-fx-background-color: Green; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 16:
                        return "-fx-background-color: Yellow; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 32:
                        return "-fx-background-color: Purple; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 64:
                        return "-fx-background-color: Orange; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 128:
                        return "-fx-background-color: Aqua; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 256:
                        return "-fx-background-color: Violet; -fx-background-radius: 15;-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 512:
                        return "-fx-background-color: Azure; -fx-background-radius: 15-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 1024:
                        return "-fx-background-color: Silver; -fx-background-radius: 15-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    case 2048:
                        return "-fx-background-color: Cyan; -fx-background-radius: 15-fx-border-color: Black;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 2";
                    default:
                        return null;
                }
            }

            public int getValue()
            {
                return value;
            }

            public void setValue(int value)
            {
                this.value = value;
                this.label.setText(String.valueOf(this.value));
                label.setStyle(this.changeColor());
                currentGame.scoreInCurrentGame += value / 2;
                scoreLabel.setText("Score : " + currentGame.scoreInCurrentGame + "  HighScore : " + currentPlayer.getHighScore());
            }
        }

        private class Position
        {
            Position(int row, int col)
            {
                this.row = row;
                this.col = col;
            }

            int row;
            int col;
        }

    }

    @Override
    public void start(Stage stage) throws IOException
    {
        Scene gameScene = new Scene(currentGame.gameMap.gridPane, 500, 500);

        stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 3);
        stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 3);

        makeScoreLabel();

        scoreStage.show();

        currentGame.gameMap.makeGridPane();

        gameScene.setOnKeyPressed(keyEvent ->
        {
            switch (keyEvent.getCode())
            {
                case W:
                case UP:
                    currentGame.gameMap.motion("up");
                    currentGame.gameMap.addCellAfterMotion();
                    checkGameOver(keyEvent);
                    break;
                case A:
                case LEFT:
                    currentGame.gameMap.motion("left");
                    currentGame.gameMap.addCellAfterMotion();
                    checkGameOver(keyEvent);
                    break;
                case S:
                case DOWN:
                    currentGame.gameMap.motion("down");
                    currentGame.gameMap.addCellAfterMotion();
                    checkGameOver(keyEvent);
                    break;
                case D:
                case RIGHT:
                    currentGame.gameMap.motion("right");
                    currentGame.gameMap.addCellAfterMotion();
                    checkGameOver(keyEvent);
                    break;
                case ESCAPE:
                    try
                    {
                        exit(keyEvent);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
        });
        stage.setScene(gameScene);
        stage.show();
    }

    public void checkGameOver(Event event)
    {
        int counter = 0;
        for (Node child : currentGame.gameMap.gridPane.getChildren())
        {
            Pane pane = ((Pane) child);
            if (pane.getChildren().size() == 1)
                counter++;
        }
        if (counter == numberOfRowCol * numberOfRowCol)
        {
            try
            {
                exit(event);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // TODO // throw new alert javafx
    }

    private void exit(Event event) throws IOException
    {
        currentPlayer.setHighScore(currentGame.scoreInCurrentGame);
        currentGame = null;

        scoreStage.close();

        Stage stage = ((Stage) ((Scene) event.getSource()).getWindow());
        Parent entryMenuParent = FXMLLoader.load(getClass().getResource("Resources/MainMenu.fxml"));
        Scene entryMenuScene = new Scene(entryMenuParent);
        stage.setScene(entryMenuScene);
        stage.show();
    }

    private void makeScoreLabel()
    {
        scoreLabel.setStyle("-fx-background-color: #313131;-fx-background-radius: 15;-fx-border-color: #edd94f;-fx-border-style: solid;-fx-border-radius: 15;-fx-border-width: 3");
        scoreLabel.setTextFill(Color.rgb(247, 217, 80, 1));
        scoreLabel.setText("Score : " + currentGame.scoreInCurrentGame + "  HighScore : " + currentPlayer.getHighScore());
        scoreLabel.prefWidth(500);
        scoreLabel.prefHeight(500);
        scoreLabel.resize(500, 200);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setFont(Font.font("Comic Sans MS", 32));
    }


}
