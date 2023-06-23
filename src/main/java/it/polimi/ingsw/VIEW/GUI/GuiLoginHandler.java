package it.polimi.ingsw.VIEW.GUI;

public class GuiLoginHandler extends Thread{
    public static GUI gui;
    @Override
    public void run(){
        while(true){
            if(GUI.notificationBuffer.size() > 0){
                if(GUI.loginData.loginSceneOpen){
                    GUI.loginSceneController.InputStatus.setText("Insert username");
                    if(GUI.notificationBuffer.get(0).equals("FIRST_TO_CONNECT")){
                        GUI.loginData.firstToConnect = true;
                    }
                    GUI.loginSceneController.notification.setText(GUI.notificationBuffer.get(0));
                    GUI.notificationBuffer.remove(0);
                    if(GUI.notificationBuffer.size() == 0){
                        GUI.loginHandlerNotActive = true;
                        break;
                    }

                }
                if (GUI.gameplayData.gameSceneOpen) {
                    GUI.loginSceneController.notification.setText(GUI.notificationBuffer.get(0));
                    GUI.notificationBuffer.remove(0);
                    if(GUI.notificationBuffer.size() == 0){
                        GUI.loginHandlerNotActive = true;
                        break;
                    }
                }
            }
        }
        this.interrupt();
    }
}
