package me.astropvp.astropearls.utils;

import me.astropvp.astropearls.AstroPearls;

public class Handler {
    private AstroPearls instance;

    public Handler(AstroPearls instance) {
        this.instance = instance;
    }

    public AstroPearls getInstance() {
        return this.instance;
    }
}
