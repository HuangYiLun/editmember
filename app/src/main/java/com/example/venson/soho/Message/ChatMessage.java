package com.example.venson.soho.Message;



public class ChatMessage {


    private String type;
    private String sender_ID;
    private String receiver_ID;
    private String content;
    private String messageType;

    public ChatMessage(String type,String sender_ID, String receiver_ID, String content, String messageType) {
        this.type = type;
        this.sender_ID = sender_ID;
        this.receiver_ID = receiver_ID;
        this.content = content;
        this.messageType = messageType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender_ID() {
        return sender_ID;
    }

    public void setSender_em(String sender_ID) {
        this.sender_ID = sender_ID;
    }

    public String getReceiver_ID() {
        return receiver_ID;
    }

    public void setReceiver_ID(String receiver_ID) {
        this.receiver_ID = receiver_ID;
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

//    private String type;
//    private String sender_em;
//    private String receiver_em;
//    private String content;
//    private String messageType;
//
//    public ChatMessage(String type,String sender_em, String receiver_em, String content, String messageType) {
//        this.type = type;
//        this.sender_em = sender_em;
//        this.receiver_em = receiver_em;
//        this.content = content;
//        this.messageType = messageType;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getSender_em() {
//        return sender_em;
//    }
//
//    public void setSender_em(String sender_em) {
//        this.sender_em = sender_em;
//    }
//
//    public String getReceiver_em() {
//        return receiver_em;
//    }
//
//    public void setReceiver_em(String receiver_em) {
//        this.receiver_em = receiver_em;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getMessageType() {
//        return messageType;
//    }
//
//    public void setMessageType(String messageType) {
//        this.messageType = messageType;
//    }
//


}
