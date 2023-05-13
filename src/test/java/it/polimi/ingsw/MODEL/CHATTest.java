package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CHATTest {

    @Test
    void returnLastMessage() {
        CHAT Chat = new CHAT();
        MESSAGE message;
        message = Chat.returnLastMessage("Antonio");
        assertEquals("no username in list", message.header[0]);
        message.header[0] = "Antonio";
        message.header[1] = "11/05/2023";
        message.text = "Hello World";
        Chat.addMessage(message);
        message = Chat.returnLastMessage("Bruno");
        assertEquals("no username in list", message.header[0]);
    }
}