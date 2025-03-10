package GameState;

import Entity.PlayerData;
import Main.MyFile;
import java.util.ArrayList;

public class ScoreState {

    /**
     * @param args the command line arguments
     */
    public ArrayList<PlayerData> sortPlayers(ArrayList<PlayerData> myList) {
        for (int i = 0; i < myList.size() - 1; i++) {
            for (int j = i; j < myList.size(); j++) {
                if (myList.get(j).getScore() > (myList.get(i).getScore())) {
                    PlayerData tmp = myList.get(i);
                    myList.set(i, myList.get(j));
                    myList.set(j, tmp);
                }
            }
        }
        return myList;

    }

    public ArrayList<PlayerData> getPlayerData(String fname) {
        ArrayList<String> playerList = MyFile.myFileReader(fname);
        ArrayList<PlayerData> playerInfo = new ArrayList<PlayerData>();

        for (int i = 0; i < playerList.size(); i++) {
            String str = playerList.get(i);
            String fields[] = str.split(",");
            PlayerData pD = new PlayerData(fields[0].trim(), Integer.parseInt(fields[1].trim()));
            playerInfo.add(pD);
        }

        return playerInfo;
    }

    public void savePlayerData(ArrayList<PlayerData> playerArray) {

        ArrayList<PlayerData> playerD = sortPlayers(playerArray);
        ArrayList<String> stringList = new ArrayList<String>();
        PlayerData p;
        for (int i = 0; i < playerArray.size(); i++) {
            p = playerD.get(i);
            String playerObj = p.getName() + " , " + p.getScore();
            stringList.add(playerObj);

        }
        stringList.remove(5);
        MyFile.myFileWriter(stringList, "player.csv");
    }

    public boolean checkForHighScore(int lScore, String lName) {
        ArrayList<PlayerData> player = getPlayerData("player.csv");
        ////take score and name from level1state
        ///compare to score and names in table, starting from the top
        ///save score if higher and remove lowest
        if (lScore > player.get(0).getScore()) {
            PlayerData pd = new PlayerData(lName, lScore);
            player.add(0, pd);
            savePlayerData(player);
          
        } else if (lScore > player.get(1).getScore()) {
            PlayerData pd = new PlayerData(lName, lScore);
            player.add(1, pd);
            savePlayerData(player);
        } else if (lScore > player.get(2).getScore()) {
            PlayerData pd = new PlayerData(lName, lScore);
            player.add(2, pd);
            savePlayerData(player);
        } else if (lScore > player.get(3).getScore()) {
            PlayerData pd = new PlayerData(lName, lScore);
            player.add(3, pd);
            savePlayerData(player);
        } else if (lScore > player.get(4).getScore()) {
            PlayerData pd = new PlayerData(lName, lScore);
            player.add(4, pd);
            savePlayerData(player);
        }
       
        return false;
    }
}
