package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

public class MazeGenerator<F extends Field<E, N>, E extends Edge<F, N>, N extends Node<F,E>> {

    public static <F extends Field<E, N>, E extends Edge<F,N>, N extends Node> void generateMaze(Map<F, E, N> map, MazeGenerationParams mazeGenerationParams) {
        if (mazeGenerationParams.algorithm == MazeAlgorithmType.RECURSIVE_BACK_TRACKER_BLOCKS) {
            RecursiveBackTrackerAlgorithm.createMaze(map);
        }
    }
}
