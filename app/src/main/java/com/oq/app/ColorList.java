package com.oq.app;

public class ColorList {
    private String name;
    private int imageId;
    public ColorList(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
}

