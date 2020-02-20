package com.riskgame.utility;

import java.util.*;

/**
 * the class for creating the graph of connectivity from the map file
 */
    class Graph {
        private int Value;
        private ArrayList<Integer> adj[];


        /**
         * Instantiates a new graph.
         *
         * @param val the val
         */
        Graph(int val) {
            this.Value = val;
            adj = new ArrayList[val];
            for (int i = 0; i < val; ++i) {
                adj[i] = new ArrayList();
            }
        }


        /**
         * Adds the edge.
         *
         * @param edge1 the edge 1
         * @param edge2 the edge 2
         */
        void addEdge(int edge1, int edge2) {
            adj[edge1].add(edge2);
        }







}
