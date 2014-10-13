package com.alexspayne.graph;

import java.util.ArrayList;

public class Graph {
	ArrayList<Edge> edgeSpace;
	Vertex[] vertexSpace;

	public Graph(Vertex[] V, ArrayList<Edge> e) {
		vertexSpace = V;
		edgeSpace = e;
	}

	public void listEdges() {
		System.out.println("Edges: ");
		for (int i = 0; i < this.edgeSpace.size(); i++) {
//			try {
				System.out.print(this.edgeSpace.get(i).label + 
						": <" + 
						this.edgeSpace.get(i).endPoints[0].label + 
						", " + 
						this.edgeSpace.get(i).endPoints[1].label +
						">");
				if(i < this.edgeSpace.size() -1){
					System.out.println(",");
				};
//			} catch (Exception e) {
//				System.out.print(this.edgeSpace.get(i).label + 
//						": " + 
//						this.edgeSpace.get(i).endPoints[0]);
//			}
		}
		System.out.println();
	}
	public void listVertices() {
		System.out.println("Vertices:");
		int sumOfDegrees = 0;
		for (int i = 0; i < this.vertexSpace.length; i++) {
			System.out.print(this.vertexSpace[i].label + " Degree: " + this.vertexSpace[i].degree);
			sumOfDegrees += this.vertexSpace[i].degree;
			if(i < this.vertexSpace.length - 1){
				System.out.println(",");
			}
		}
		System.out.println("");
		System.out.println("sumOfDegrees: " + sumOfDegrees);

	}
}
