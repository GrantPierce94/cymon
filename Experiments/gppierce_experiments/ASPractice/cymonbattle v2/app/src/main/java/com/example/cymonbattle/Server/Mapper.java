package com.example.cymonbattle.Server;

import java.io.Serializable;

public class Mapper implements Serializable {
    private String key;
    private Object value;

    public Mapper() {
    }

    public Mapper(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Mapper{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
