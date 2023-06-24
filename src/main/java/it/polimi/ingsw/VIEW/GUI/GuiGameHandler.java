package it.polimi.ingsw.VIEW.GUI;

public class GuiGameHandler extends Thread{
    public static GUI gui;

    @Override
    public void run(){
        while(getUpdate()){
            if (GUI.gameplayData.gameSceneOpen) {
                while(GUI.notificationBuffer.size() > 0) {
                    gui.setNotification(GUI.notificationBuffer.get(0));
                    GUI.notificationBuffer.remove(0);
                }
                GUI.gameHandlerNotActive = true;
                break;
            }
        }
        this.interrupt();
    }

    synchronized public boolean getUpdate(){
        boolean update = true;
        return update;
    }

}
