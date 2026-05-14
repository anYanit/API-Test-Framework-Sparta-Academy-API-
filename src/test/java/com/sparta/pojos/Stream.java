package com.sparta.pojos;

public class Stream {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "name='" + name + '\'' +
                '}';
    }
}
