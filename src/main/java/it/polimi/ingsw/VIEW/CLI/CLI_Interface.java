package it.polimi.ingsw.VIEW.CLI;

import it.polimi.ingsw.MODEL.COMMON_GOAL_CARD;
import it.polimi.ingsw.MODEL.PERSONAL_GOAL_CARD;
import it.polimi.ingsw.MODEL.item;

import java.util.List;

public interface CLI_Interface {

    public void notify(String message);
    public String getUsername();
    public int getLobbySize();
    public void printBoard(item[][] grid);
    public void printPersonalGoal(PERSONAL_GOAL_CARD personalGoalCard);
    public void printCommonGoals(List<COMMON_GOAL_CARD> commonGoalCards);

}
