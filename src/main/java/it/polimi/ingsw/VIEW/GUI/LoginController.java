package it.polimi.ingsw.VIEW.GUI;



/*
public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private FXMLLoader gameloader;
    private Parent parent;



    //fxml variables
    @FXML
    private SubScene SubScene;
    @FXML
    private TextField nickname;
    @FXML
    private TextField ip;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Label waiting_communication;
    public boolean already_connected;

    private void sendLogInRequest() throws SocketException {
        if(already_connected){
            Action act = new Action();
            act.setUsername(nickname.getText());
            msg.send(act);
        } else {
            //Creating the action
            Action act = new Action();
            act.setGamePhase(GamePhase.START);
            act.setUsername(nickname.getText());
            act.LobbySize(Integer.parseInt(choiceBox.getValue()));
            //Initializing the game controller
            GameGraphicController.username = act.getUsername();
            GameGraphicController.LobbySize = act.getLobbySize();
            GameGraphicController.msg = msg;
            gameloader = new FXMLLoader(GUI.class.getResource("gameGraphic.fxml"));
            try {
                gameparent = gameloader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            game_controller = gameloader.getController();
            //Sending the message
            msg.send(act);
            last_sent = act.getGamePhase();
            already_connected = true;
        }
    }

    public void switchScene(ActionEvent event) throws IOException {
        if(!already_connected)
            initializeNetwork();
        PopUpLauncher alert = new PopUpLauncher();
        String input_user = nickname.getText();
        if(InputUtils.isNullOrEmpty(input_user)){
            alert.setTitle("Bad request");
            alert.setMessage("Username can not be empty");
            alert.show();
            return;
        } else {
            sendLogInRequest();
        }

        showWaitingScene(true);
        waiting_communication.setText("You're being connected to " + msg.getUsername() + "'s lobby");
    }

    private void showWaitingScene(boolean visible){
        SubScene.setVisible(visible);
        waiting_communication.setVisible(visible);
    }


}


  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ignore_network = false;
        choiceBox.getItems().addAll(nof_players);
        choiceBox.setValue("2");
        waiting_communication.setVisible(false);
        confetti = new ImageView[]{confetti_left, confetti_right};
        nickname.textProperty().addListener((observableValue, oldValue, newValue) -> checkName());

        FXMLLoader fxmlLoader = new FXMLLoader(GUILauncher.class.getResource("waiting.fxml"));
        try {
            SubScene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGameBoard(GameBoard model) {
        if(ignore_network) return;
    }

    @Override
    public void notifyResponse(Action action) {
        if(ignore_network) return;
        PopUpLauncher error = new PopUpLauncher();
        error.setTitle("Error!");
        switch(action.getGamePhase()){
            case CORRECT:
                game_controller.setUsername(action.getUsername());
                Platform.runLater(() -> {
                    game_controller.setStarted();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream(".jpg")));
                    stage.setTitle("MyShelfie Match");
                    stage.setScene(new Scene(gameparent, 1290, 690));
                    stage.setResizable(false);
                    stage.show();
                    stage.setOnCloseRequest(windowEvent -> System.exit(-1));
                    this.stage.close();
                });
                ignore_network = true;
                break;
            case ERROR_PHASE:
                error.setMessage("Username already taken!");
                error.show();
                showWaitingScene(false);
                ip.setDisable(true);
                choiceBox.setDisable(true);
                break;
            case CONNECTION_ERROR:
                error.setMessage("A connection error occurred");
                error.show();
                System.exit(0);
                break;
        }
    }

    @Override
    public void notifyResponse(List<GamePhase> gamephases) {
        return;
    }

    public void setOwnStage(Stage stage) {
        this.stage = stage;
    }
}


*/
