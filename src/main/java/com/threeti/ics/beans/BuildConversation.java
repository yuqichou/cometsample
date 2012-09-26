package com.threeti.ics.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-13
 * Time: 上午4:13
 * To change this template use File | Settings | File Templates.
 */
public class BuildConversation {
    private String serviceToken;
    private String productId;
    private String productName;
    private String picture;
    private String description;



    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
