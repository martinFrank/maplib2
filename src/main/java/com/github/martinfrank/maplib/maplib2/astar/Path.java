package com.github.martinfrank.maplib.maplib2.astar;

import com.github.martinfrank.maplib.maplib2.map.Field;

import java.util.ArrayList;
import java.util.List;

public class Path {

    @SuppressWarnings("squid:S1700")
    private final List<Field> path = new ArrayList<>();
    public final Field destiny;
    public final Field start;

    Path() {
        start = null;
        destiny = null;
    }
    Path(AStarFieldWrapper end) {
        AStarFieldWrapper wrapper = end;
        while(wrapper.getFrom() != null){
            AStarFieldWrapper next = wrapper.getFrom();
            addFirst(wrapper.field);
            wrapper = next;
        }
        this.destiny = end.field;
        this.start = wrapper.field;
    }

    public static Path emptyPath() {
        return new Path();
    }


    private void addFirst(Field field ){
        path.add(0, field);
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public List<Field> get(){
        return path;
    }
}
