package com.example.partygames;

import java.util.ArrayList;
import java.util.List;

public class AliasTeams {
    List<Player> redTeam = new ArrayList<Player>();
    List<Player> blueTeam = new ArrayList<Player>();
    String blueTeamColor;
    String redTeamColor;

    public AliasTeams(String blueTeamColor, String redTeamColor) {
        this.blueTeamColor = blueTeamColor;
        this.redTeamColor = redTeamColor;
        randomTeamSelection();
    }

    int currentTeam;

    public String currentTeamName() {
        if(currentTeam == 1){

            return blueTeamColor;
        } else {
            return redTeamColor;
        }

    }

    public void randomTeamSelection(){
        int a=1;
        int b=2;
        this.currentTeam = a + (int)(Math.random() * ((b - a) + 1));
    }


    public Player getPlayerByIdRedTeam(int id) {
        return redTeam.get(id);
    }

    public Player getPlayerByIdBlueTeam(int id) {
        return blueTeam.get(id);
    }

    public void addPlayerToRedTeam(Player player) {
        redTeam.add(player);
    }

    public void addPlayerToBlueTeam(Player player) {
        blueTeam.add(player);
    }

    public List<Player> getRedTeam() {
        return redTeam;
    }

    public int getRedTeamLength() {
        return redTeam.size();
    }

    public void setRedTeam(List<Player> redTeam) {
        this.redTeam = redTeam;
    }

    public List<Player> getBlueTeam() {
        return blueTeam;
    }

    public int getBlueTeamLength() {
        return blueTeam.size();
    }

    public void setBlueTeam(List<Player> blueTeam) {
        this.blueTeam = blueTeam;
    }

}
