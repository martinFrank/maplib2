package com.github.martinfrank.maplib2.maze;

public class MazeGenerationParams {

    public final MazeAlgorithmType algorithm;

    private MazeGenerationParams(MazeAlgorithmType algorithm) {
        this.algorithm = algorithm;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        private MazeAlgorithmType algorithm = MazeAlgorithmType.RECURSIVE_BACKTRACKER_FIELDS;

        public Builder algorithm(MazeAlgorithmType algorithm){
            this.algorithm = algorithm;
            return this;
        }

        public MazeGenerationParams build(){
            if(algorithm == null){
                throw new IllegalStateException("mapstyle must be properly set");
            }
            return new MazeGenerationParams(algorithm);
        }
    }

}
