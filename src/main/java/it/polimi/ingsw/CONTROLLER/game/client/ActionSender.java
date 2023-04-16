package it.polimi.ingsw.CONTROLLER.game.client;

/**
 * Sends actions generated from the view to the match controller
 */

public class ActionSender {

    private String username;
    private final CommFE network = new CommFE();
    private ClientController controller;

    /**
     * Tries to login
     *
     * @param host        host IP
     * @param requestPort request port
     * @param objectPort  object port
     * @param username    player's username
     * @param newUser     true if it's a new user
     * @param useRMI      true if the user want's to use RMI, false for Socket
     * @return null if it's successful, an error otherwise
     */
    public String login(String host, int requestPort, int objectPort, String username, boolean newUser, boolean useRMI) {
        String result = network.login(host, requestPort, objectPort, false, username, newUser, useRMI, controller);
        if (result == null) {
            this.username = username;
            controller.setLocalPlayer(username);
        }

        return result;
    }

    /**
     * Tries a logout
     */
    public void logout() {
        network.logout();
    }

    /**
     * Changes layer from Socket to RMI or vice versa
     *
     * @param toRMI       boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int requestPort, int objectPort) {
        network.changeLayer(toRMI, requestPort, objectPort);
    }


    /**
     * Leaves the current match
     */
    public void leaveMatch() {
        network.leaveMatch();
    }

    /**
     * Asks the lobby
     */
    public void askLobby() {
        network.requestUserList();
    }

    /**
     * Sends invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {
        network.inviteToMatch(invite);
        controller.setMId(invite);
    }

    /**
     * Accepts an invite from another user
     *
     * @param invite contains the matchIdentifier object of the match
     */
    public void acceptInvite(MatchIdentifier invite) {
        network.answerInvite(invite, true);
        controller.setMId(invite);
    }


    /**
     * Communicates to the Controller that View ended init operations
     */
    public void endInitGame() {
        network.sendObj(new ReadyView());
    }


    /**
     * Participates to a match making
     */
    public void joinMatchMaking() {
        network.joinMatchMakingQueue();
    }

    /**
     * Leaves matchmaking
     */
    public void leaveMatchMaking() {
        network.leaveMatchMakingQueue();
    }

