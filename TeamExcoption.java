package com.wagyeke.controller.service;

public class TeamExcoption extends Exception{

    static final long serialVersionUID = -3387516931254332648L;

    public TeamExcoption() {
    }

    public TeamExcoption(String message) {
        super(message);
    }

}
