package sample.Models;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Player
{
    private static ArrayList<Player> players = new ArrayList<>();

    public Player(String userName, String passWord, Gender gender)
    {
        this.userName = userName;
        this.passWord = passWord;
        this.gender = gender;
        this.highScore = 0;
    }


    private String userName;
    private String passWord;
    private int highScore;
    private Gender gender;


    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public int getHighScore()
    {
        return highScore;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public static Player findPlayer(String userName)
    {
        for (Player player : players)
            if (userName.equals(player.userName))
                return player;

        return null;
    }
}
