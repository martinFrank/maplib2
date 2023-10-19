package com.github.martinfrank.maplib2.demoapp.maze;

import com.github.martinfrank.maplib2.demoapp.map.Map;

public class MazeGenerator {

    private MazeGenerator(){

    }

    public static void generateMaze(Map map, MazeGenerationParams mazeGenerationParams) {
        if (mazeGenerationParams.algorithm == MazeAlgorithmType.RECURSIVE_BACKTRACKER_FIELDS) {
            RecursiveBackTrackerAlgorithm.FIELDS.createMaze(map);
        }
        if (mazeGenerationParams.algorithm == MazeAlgorithmType.RECURSIVE_BACKTRACKER_EDGES) {
            RecursiveBackTrackerAlgorithm.EDGES.createMaze(map);
        }
        //throw illegal algorithm exception?
    }
}
