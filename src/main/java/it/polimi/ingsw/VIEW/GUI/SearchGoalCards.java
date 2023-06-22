package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.Image;

public class SearchGoalCards {
    public Image search(CardType cardType,int id){
        if(cardType == CardType.COMMON){
            switch (id) {
                case 1:
                    return new Image("1.jpg");
                case 2:
                    return new Image("2.jpg");
                case 3:
                    return new Image("3.jpg");
                case 4:
                    return new Image("4.jpg");
                case 5:
                    return new Image("5.jpg");
                case 6:
                    return new Image("6.jpg");
                case 7:
                    return new Image("7.jpg");
                case 8:
                    return new Image("8.jpg");
                case 9:
                    return new Image("9.jpg");
                case 10:
                    return new Image("10.jpg");
                case 11:
                    return new Image("11.jpg");
                case 12:
                    return new Image("12.jpg");
            }
        }
        if(cardType == CardType.PERSONAL){
            switch (id) {
                case 1:
                    return new Image("Personal_Goals.png");
                case 2:
                    return new Image("Personal_Goals2.png");
                case 3:
                    return new Image("Personal_Goals3.png");
                case 4:
                    return new Image("Personal_Goals4.png");
                case 5:
                    return new Image("Personal_Goals5.png");
                case 6:
                    return new Image("Personal_Goals6.png");
                case 7:
                    return new Image("Personal_Goals7.png");
                case 8:
                    return new Image("Personal_Goals8.png");
                case 9:
                    return new Image("Personal_Goals9.png");
                case 10:
                    return new Image("Personal_Goals10.png");
                case 11:
                    return new Image("Personal_Goals11.png");
                case 12:
                    return new Image("Personal_Goals12.png");
            }
        }
        return new Image("");
    }
}
