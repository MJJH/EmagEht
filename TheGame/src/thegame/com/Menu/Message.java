package thegame.com.Menu;

import java.util.Date;

/**
 *
 * @author laure
 */
public class Message {

    private int id;
    private String text;
    private Date time;
    private Account account;

    /**
     *
     * @param account
     * @param text
     */
    public Message(Account account, String text)
    {
        this.account = account;
        this.text = text;
        time = new Date();
    }

}
