package com.alexspayne.graph;

public class Vertex {
	
	String label = "vertex";
	int degree = 0;
	public int index;
	public Vertex(String s, int i) {
		this.label = s;
		this.index = i;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
