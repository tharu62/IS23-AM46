package it.polimi.ingsw.VIEW.CLI;

public class PrintCommonGoals {

    public void printCommon(int id, int value) {
        switch (id){
            case 1:
                System.out.println("Common goal 1 - token value: " + value +
                                   "\nTwo groups each containing 4 tiles of the same type in a 2x2 square. \n" +
                                   "The tiles of one square can be different from those of the other square.");
                break;
            case 2:
                System.out.println("Common goal 2 - token value: " + value +
                                   "\nTwo columns each formed by 6 different types of tiles.");
                break;
            case 3:
                System.out.println("Common goal 3 - token value: " + value +
                                   "\nFour groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).\n" +
                                   "The tiles of one group can be different from those of another group.");
                break;
            case 4:
                System.out.println("Common goal 4 - token value: " + value +
                                   "\nSix groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).\n" +
                                   "The tiles of one group can be different from those of another group.");
                break;
            case 5:
                System.out.println("Common goal 5 - token value: " + value +
                                   "\nThree columns each formed by 6 tiles of maximum three different types. " +
                                   "One column can show the same or a different combination of another column.");
                break;
            case 6:
                System.out.println("Common goal 6 - token value: " + value +
                                   "\nTwo lines each formed by 5 different types of tiles. " +
                                   "One line can show the same or a different combination of the other line.");
                break;
            case 7:
                System.out.println("Common goal 7 - token value: " + value +
                                   "\nTwo lines each formed by 5 different types of tiles." +
                                   "One line can show the same or a different combination of the other line.");
                break;
            case 8:
                System.out.println("Common goal 8 - token value: " + value +
                                   "\nFour tiles of the same type in the four corners of the bookshelf.");
                break;
            case 9:
                System.out.println("Common goal 9 - token value: " + value +
                                   "\nEight tiles of the same type. \n" +
                                   "There’s no restriction about the position of these tiles.");
                break;
            case 10:
                System.out.println("Common goal 10 - token value: " + value +
                                   "\nFive tiles of the same type forming an X");
                break;
            case 11:
                System.out.println("Common goal 11 - token value: " + value +
                                   "\nFive tiles of the same type forming a diagonal.");
                break;
            case 12:
                System.out.println("Common goal 12 - token value: " + value +
                                   "\nFive columns of increasing or decreasing height.\n" +
                                   "Starting from the first column on the left or on the right, each next column\n" +
                                   "must be made of exactly one more tile. Tiles can be of any type.");
                break;
        }
    }
}
