package com.example.venson.soho.Message;



public class ChatMessage {

    private String type;
    private String sender_em;
    private String receiver_em;
    private String content;
    private String messageType;

    public ChatMessage(String type,String sender_em, String receiver_em, String content, String messageType) {
        this.type = type;
        this.sender_em = sender_em;
        this.receiver_em = receiver_em;
        this.content = content;
        this.messageType = messageType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender_em() {
        return sender_em;
    }

    public void setSender_em(String sender_em) {
        this.sender_em = sender_em;
    }

    public String getReceiver_em() {
        return receiver_em;
    }

    public void setReceiver_em(String receiver_em) {
        this.receiver_em = receiver_em;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }



}
