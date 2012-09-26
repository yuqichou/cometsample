package com.threeti.ics.server.domain.protocoldefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class Conversation {
	
    private Long id;
    private String visitor;
    private ConversationTopic topic;
    private ConversationStatus status;
    private Date createDate;
    private List<Message> messages = new ArrayList<Message>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitor() {
        return visitor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public ConversationTopic getTopic() {
        return topic;
    }

    public void setTopic(ConversationTopic topic) {
        this.topic = topic;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    public void addMessage(Message message) {
        if (!messages.contains(message)) {
            messages.add(message);
        }
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public void removeMessage(Message message) {
        if (messages.contains(message)) {
            messages.add(message);
        }
    }




    @JsonIgnore
    public String getProductId() {
        return getTopic().getProductId();
    }

}
