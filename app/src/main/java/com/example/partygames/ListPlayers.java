package com.example.partygames;

import java.util.ArrayList;
import java.util.List;

public class ListPlayers {
    List<Player> objectList = new ArrayList<Player>();

    public List<Player> getObjectList() {
        return objectList;
    }

    public int getObjectListLenth() {
        return objectList.size();
    }

    public void addObjecToList(Player player) {
        objectList.add(player);
    }

    public Player getPlayerById(int id) {
        return objectList.get(id);
    }

}
