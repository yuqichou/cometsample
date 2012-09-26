package com.threeti.ics.server.domain.protocoldefinition.commandrequest;


/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class SdkVerificationRequest {
	
    private String appKey;
    private String cpu;
    private String uid;
    private String resolution;
    private String model;
    private String network;
    private String carrier;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


}
