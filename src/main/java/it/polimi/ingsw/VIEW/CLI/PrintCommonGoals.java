package it.polimi.ingsw.VIEW.CLI;

public class PrintCommonGoals {

    public void printCommon(int id) {
        switch (id){
            case 1:
                System.out.println("Common goal 1\n" +
                                   "Two groups each containing 4 tiles of\n" +
                                   "the same type in a 2x2 square. The tiles\n" +
                                   "of one square can be different from\n" +
                                   "those of the other square.");
                break;
            case 2:
                System.out.println("Common goal 2\n" +
                                   "Two columns each formed by 6\n" +
                                   "different types of tiles.");
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
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }
    }
}
