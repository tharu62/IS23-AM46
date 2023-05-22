package it.polimi.ingsw.VIEW.CLI;



public enum Color {
    CATS_GREEN_BG("\u001B[42m"),
    BOOKS_WHITE_BG("\u001B[47m"),
    GAMES_YELLOW_BG("\u001B[43m"),
    FRAMES_BLUE_BG("\u001B[44m"),
    PLANTS_RED_BG("\u001B[41m"),
    TROPHIES_CYAN_BG("\u001B[46m");

    static final String RESET = "\u001B[0m";

    private final String escape;

    Color(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }

}