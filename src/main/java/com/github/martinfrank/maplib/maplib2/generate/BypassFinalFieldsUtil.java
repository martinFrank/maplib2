package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.map.Edge;
import com.github.martinfrank.maplib.maplib2.map.Field;
import com.github.martinfrank.maplib.maplib2.map.Node;

import java.util.ArrayList;
import java.util.List;

class BypassFinalFieldsUtil {

    private BypassFinalFieldsUtil(){

    }

    private static void setNodeFields(Node node, List<Field> fields) {
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

    private static void setNodeEdges(Node node, List<Edge> edges) {
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

    private static void setEdgeFields(Edge edge, List<Field> nbgs) {
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

    private static void setEdgeEdges(Edge edge, List<Edge> nbgs) {
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

    private static void setFieldFields(Field field, List<Field> nbgs) {
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

    static void setFieldsToEdge(List<Field> fields, List<Edge> edges) {
        for (Edge edge : edges) {
            List<Field> nbgs = fields.stream().filter(f -> f.edges.contains(edge)).toList();
            BypassFinalFieldsUtil.setEdgeFields(edge, nbgs);
        }
    }

    static void setEdgesToEdge(List<Edge> edges) {
        for (Edge  edge : edges) {
            List<Edge> ngbs = edges.stream().filter(e -> !e.equals(edge) && (
                    e.nodeA.equals(edge.nodeA) || e.nodeA.equals(edge.nodeB) || e.nodeB.equals(edge.nodeA) || e.nodeB.equals(edge.nodeB)
            )).toList();
            BypassFinalFieldsUtil.setEdgeEdges(edge, ngbs);
        }
    }

    static void setEdgesToNodes(List<Edge> edges, List<Node> nodes) {
        for (Node node : nodes) {
            List<Edge> nbgs = edges.stream().filter(e -> e.nodeA.equals(node) || e.nodeB.equals(node)).toList();
            BypassFinalFieldsUtil.setNodeEdges(node, nbgs);
        }
    }

    static void setFieldsToNodes(List<Field> fields, List<Node> nodes) {
        for (Node node : nodes) {
            List<Field> nbgs = fields.stream().filter(f -> f.nodes.contains(node)).toList();
            BypassFinalFieldsUtil.setNodeFields(node, nbgs);
        }
    }

    static void setFieldsToField(List<Field> fields) {
        for (Field field : fields) {
            List<Field> nbgs = new ArrayList<>();
            for(Field candidate: fields){
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
