package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This class tests the methods of the CHAT class
class CHATTest {

    @Test
    void returnLastMessage() {
        //It tests the method returnLastMessage() and addMessage()
        CHAT Chat = new CHAT();
        MESSAGE message;
        message = Chat.returnLastMessage("Antonio");
        assertEquals("no data", message.header[0]);
        message.header[0] = "Antonio";
        message.header[1] = "11/05/2023";
        message.text = "Hello World";
        Chat.addMessage(message);
        assertEquals(Chat.chat.get(0), message);
        message = Chat.returnLastMessage("Bruno");
        assertEquals("no data", message.header[0]);
        message.header[0] = "Bruno";
        message.header[1] = "13/06/2023";
        message.text = "Hello Antonio";
        Chat.addMessage(message);
        message = Chat.returnLastMessage("Antonio");
        assertEquals("Hello World", message.text);
    }
}