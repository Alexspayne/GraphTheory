package com.alexspayne.graph;

import java.util.Comparator;

public class CustomComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge o1, Edge o2) {
        return o1.endPoints[0].degree - o2.endPoints[0].degree;
    }
}
