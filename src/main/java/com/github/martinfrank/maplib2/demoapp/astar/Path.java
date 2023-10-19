package com.github.martinfrank.maplib2.demoapp.astar;

import com.github.martinfrank.maplib2.demoapp.map.Field;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Path <F extends Field>{

    @SuppressWarnings("squid:S1700")
    private final List<F> path = new ArrayList<>();
    public final F destiny;
    public final F start;

    Path() {
        start = null;
        destiny = null;
    }
    Path(AStarFieldWrapper<F> end) {
        AStarFieldWrapper<F> wrapper = end;
        while(wrapper.getFrom() != null){
            AStarFieldWrapper<F> next = wrapper.getFrom();
            addFirst(wrapper.field);
            wrapper = next;
        }
        this.destiny = end.field;
        this.start = wrapper.field;
    }

    public static <F extends Field<F, ?, ?>> Path<F> emptyPath() {
        return new Path<>();
    }


    private void addFirst(F field ){
        path.add(0, field);
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public List<F> get(){
        return path;
    }
}
