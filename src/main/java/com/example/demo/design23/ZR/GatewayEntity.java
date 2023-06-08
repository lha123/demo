package com.example.demo.design23.ZR;


import lombok.Data;

@Data
public class GatewayEntity {

    private Integer handlerId;

    private String name;

    private String conference;

    private Integer preHandlerId;

    private Integer nextHandlerId;

    public GatewayEntity(Integer handlerId, String name, String conference, Integer preHandlerId, Integer nextHandlerId) {
        this.handlerId = handlerId;
        this.name = name;
        this.conference = conference;
        this.preHandlerId = preHandlerId;
        this.nextHandlerId = nextHandlerId;
    }
}
