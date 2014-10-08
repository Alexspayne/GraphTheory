package com.alexspayne.graph;

public class Edge {
	Vertex[] endPoints = new Vertex[2];
	double length;
	String label;
	public Edge(){
		
	}
	public Edge(String s){
		this.label = s;
	}
	public Edge(Vertex a, Vertex b) {
		endPoints[0] = a;
		endPoints[1] = b;
	}

	public Edge(Vertex a, Vertex b, double c) {
		endPoints[0] = a;
		endPoints[1] = b;
		length = c;
	}
}
