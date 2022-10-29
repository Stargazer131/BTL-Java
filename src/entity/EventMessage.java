package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventMessage implements Serializable
{
    private static final long serialVersionUID = 1312002L;

    private String messageContent,
                   messageTime;

    private static SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/YYYY"),
                                    sdfHour = new SimpleDateFormat("HH:mm");

    public EventMessage(String MESSAGE_CONTENT)
    {
        this.messageContent = MESSAGE_CONTENT;

        Date d = new Date();

        this.messageTime = String.format("%s ngày %s", sdfHour.format(d), sdfDay.format(d));
    }

    public String getContent()
    {
        return messageContent;
    }

    public String getTime()
    {
        return this.messageTime;
    }
}