package it.polimi.ingsw.VIEW.CLI;

public class PrintCommonGoals {

    public void printCommon(int id) {
        switch (id){
            case 1:
                System.out.println("Common goal 1\n" +
                                   "Two groups each containing 4 tiles of\n" +
                                   "the same type in a 2x2 square. " +
                                   "The tiles of one square can be different from\n" +
                                   "those of the other square.");
                break;
            case 2:
                System.out.println("Common goal 2\n" +
                                   "Two columns each formed by 6 different types of tiles.");
                break;
            case 3:
                System.out.println("Common goal 3\n" +
                                   "Four groups each containing at least\n" +
                                   "4 tiles of the same type (not necessarily\n" +
                                   "in the depicted shape).\n" +
                                   "The tiles of one group can be different\n" +
                                   "from those of another group.\n");
                break;
            case 4:
                System.out.println("Common goal 4\n" +
                                   "Six groups each containing at least\n" +
                                   "2 tiles of the same type (not necessarily\n" +
                                   "in the depicted shape).\n" +
                                   "The tiles of one group can be different\n" +
                                   "from those of another group.");
                break;
            case 5:
                System.out.println("Common goal 5\n" +
                                   "Three columns each formed by 6 tiles\n" +
                                   "of maximum three different types. One\n" +
                                   "column can show the same or a different\n" +
                                   "combination of another column.");
                break;
            case 6:
                System.out.println("Common goal 6\n" +
                                   "Two lines each formed by 5 different\n" +
                                   "types of tiles. One line can show the\n" +
                                   "same or a different combination of the\n" +
                                   "other line.");
                break;
            case 7:
                System.out.println("Common goal 7\n" +
                                   "Two lines each formed by 5 different\n" +
                                   "types of tiles. One line can show the\n" +
                                   "same or a different combination of the\n" +
                                   "other line.");
                break;
            case 8:
                System.out.println("Common goal 8\n" +
                                   "Four tiles of the same type in the four\n" +
                                   "corners of the bookshelf.");
                break;
            case 9:
                System.out.println("Common goal 9\n" +
                                   "Eight tiles of the same type. There’s no\n" +
                                   "restriction about the position of these tiles.");
                break;
            case 10:
                System.out.println("Common goal 10\n" +
                                   "Five tiles of the same type forming an X");
                break;
            case 11:
                System.out.println("Common goal 11\n" +
                                   "Five tiles of the same type forming a diagonal.");
                break;
            case 12:
                System.out.println("Common goal 12\n" +
                                   "Five columns of increasing or decreasing\n" +
                                   "height. Starting from the first column on\n" +
                                   "the left or on the right, each next column\n" +
                                   "must be made of exactly one more tile.\n" +
                                   "Tiles can be of any type.");
                break;
        }
    }
}
