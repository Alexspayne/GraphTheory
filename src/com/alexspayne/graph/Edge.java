package com.alexspayne.graph;

public class Edge {
	Vertex[] endPoints = new Vertex[2];
	/**
	 * @return the endPoints
	 */
	public Vertex getEndPoint(int i) {
		return endPoints[i];
	}
	/**
	 * @param endPoints the endPoints to set
	 */
	public void setEndPoints(Vertex[] endPoints) {
		this.endPoints = endPoints;
	}
	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

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
