package thegame.com.Menu;

import java.util.Date;

/**
 * This class contains code for messages.
 * @author laure
 */
public class Message {

    private int id;
    private String text;
    private Date time;
    private Account account;

    /**
     * This method creates a message. It needs a sender and text.
     * Date is added automatically.
     * @param account
     * @param text
     */
    public Message(Account account, String text)
    {
        this.account = account;
        this.text = text;
        time = new Date();
    }
    
    /**
     * This method gets the message text.
     * @return message text string
     */
    public String getText ()
    {
        return text;
    }
    
    /**
     * This method gets the message date.
     * @return message date
     */
    public Date getDate ()
    {
        return time;
    }
    
    /**
     * This method gets the message sender.
     * @return message sender account
     */
    public Account getSender ()
    {
        return account;
    }
}
