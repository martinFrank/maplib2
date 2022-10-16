package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BypassFinalFieldsUtil {

    @SuppressWarnings("rawtypes")
    private static <F extends Field, N extends Node> void setNodeFields(N node, List<F> fields) {
        Class<Node> nodeClass = Node.class;
        try {
            java.lang.reflect.Field f = nodeClass.getField("fields");
            f.setAccessible(true);
            f.set(node, fields);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static <N extends Node, E extends Edge> void setNodeEdges(N node, List<E> edges) {
        Class<Node> nodeClass = Node.class;
        try {
            java.lang.reflect.Field f = nodeClass.getField("edges");
            f.setAccessible(true);
            f.set(node, edges);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static <E extends Edge, F extends Field> void setEdgeFields(E edge, List<F> nbgs) {
        Class<Edge> nodeClass = Edge.class;
        try {
            java.lang.reflect.Field fa = nodeClass.getField("fieldA");
            java.lang.reflect.Field fb = nodeClass.getField("fieldB");
            fa.setAccessible(true);
            fb.setAccessible(true);
            fa.set(edge, nbgs.get(0));
            if (nbgs.size() == 2) {
                fb.set(edge, nbgs.get(1));
            }
            fa.setAccessible(false);
            fb.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static <E extends Edge> void setEdgeEdges(E edge, List<E> nbgs) {
        Class<Edge> nodeClass = Edge.class;
        try {
            java.lang.reflect.Field f = nodeClass.getField("edges");
            f.setAccessible(true);
            f.set(edge, nbgs);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field> void setFieldFields(F field, List<F> nbgs) {
        Class<Field> nodeClass = Field.class;
        try {
            java.lang.reflect.Field f = nodeClass.getField("fields");
            f.setAccessible(true);
            f.set(field, nbgs);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <F extends Field, E extends Edge> void setFieldsToEdge(List<F> fields, List<E> edges) {
        for (E edge : edges) {
            List<F> nbgs = fields.stream().filter(f -> f.edges.contains(edge)).collect(Collectors.toList());
            BypassFinalFieldsUtil.setEdgeFields(edge, nbgs);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <E extends Edge> void setEdgesToEdge(List<E> edges) {
        for (E edge : edges) {
            List<E> ngbs = edges.stream().filter(e -> !e.equals(edge) && (
                    e.nodeA.equals(edge.nodeA) || e.nodeA.equals(edge.nodeB) || e.nodeB.equals(edge.nodeA) || e.nodeB.equals(edge.nodeB)
            )).collect(Collectors.toList());
            BypassFinalFieldsUtil.setEdgeEdges(edge, ngbs);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <E extends Edge, N extends Node> void setEdgesToNodes(List<E> edges, List<N> nodes) {
        for (N node : nodes) {
            List<E> nbgs = edges.stream().filter(e -> e.nodeA.equals(node) || e.nodeB.equals(node)).collect(Collectors.toList());
            BypassFinalFieldsUtil.setNodeEdges(node, nbgs);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <F extends Field, N extends Node>void setFieldsToNodes(List<F> fields, List<N> nodes) {
        for (N node : nodes) {
            List<F> nbgs = fields.stream().filter(f -> f.nodes.contains(node)).collect(Collectors.toList());
            BypassFinalFieldsUtil.setNodeFields(node, nbgs);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <F extends Field<?, E,?>, E extends Edge> void setFieldsToField(List<F> fields) {
        for (F field : fields) {
            List<F> nbgs = new ArrayList<>();
            for(F candidate: fields){
                if (!candidate.equals(field)){
                    for (Edge e: field.edges){
                        if(candidate.edges.contains(e)){ //FIXME what is this?
                            nbgs.add(candidate);
                            break;
                        }
                    }
                }
            }
            BypassFinalFieldsUtil.setFieldFields(field, nbgs);
        }
    }
}
