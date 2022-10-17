package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

public class MazeGenerator<F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node> void generateMaze(Map<F, E, N> map, MazeGenerationParams mazeGenerationParams) {
        if (mazeGenerationParams.algorithm == MazeAlgorithmType.RECURSIVE_BACKTRACKER_FIELDS) {
            RecursiveBackTrackerAlgorithm.FIELDS.createMaze(map);
        }
        if (mazeGenerationParams.algorithm == MazeAlgorithmType.RECURSIVE_BACKTRACKER_EDGES) {
            RecursiveBackTrackerAlgorithm.EDGES.createMaze(map);
        }
        //throw illegal algorithm exception?
    }
}
