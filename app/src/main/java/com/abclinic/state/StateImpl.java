package com.abclinic.state;

public abstract class StateImpl<T> implements State<T> {
    private final int type;
    private final long id;

    StateImpl(int type, long id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }
}
