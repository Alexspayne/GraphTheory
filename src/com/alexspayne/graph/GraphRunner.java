package com.alexspayne.graph;

import java.util.ArrayList;
import java.util.Random;

import com.alexspayne.graphics.DrawGraph;

public class GraphRunner {

	public static void main(String[] args) {

		Graph graph = generateGraph(5, 8);	
		//		for (int s = 0; s < graph.edgeSpace.size(); s++) {
		//			System.out.println(graph.edgeSpace.get(s).label + " " + graph.edgeSpace.get(s).endPoints[0].degree);
		//		}
		//		Collections.sort(graph.edgeSpace, new CustomComparator());
		//		for (int s = 0; s < graph.edgeSpace.size(); s++) {
		//			System.out.println(graph.edgeSpace.get(s).label + " " + graph.edgeSpace.get(s).endPoints[0].degree);
		//		}
		graph.listEdges();
		graph.listVertices();
		DrawGraph applet = new DrawGraph();
//		applet.runGraphApplet();
	}

	private static Graph generateGraph(int vn, int en) {
		//This generates a graph with vn vertices and en edges
		if (en > (vn * ( vn - 1))/2) {
			//this makes sure that the graph has no more edges than a 
			//complete graph would have.
			en = (vn * ( vn - 1))/2;
		}
		Vertex[] v = generateVertices(vn);
		ArrayList<Edge> e = generateEdges(en);
		populateEdgeEndPoints(v,e,1);
		Graph graph = new Graph(v,e);
		return graph;
	}


	/**
	 * @param e
	 */
	private static ArrayList<Edge> generateEdges(int x) {
		//returns arraylist e of x edges labeled e1, e2,...,vx
		//The edges are not complete until they have been assigned
		//end points using one of the appropriate methods.
		ArrayList<Edge> e = new ArrayList<Edge>();
		for (int i = 0; i < x; i++) {
			e.add(new Edge("e" + (i+1)));
		}
		return e;
	}

	/**
	 * @param v
	 */
	private static Vertex[] generateVertices(int x) {
		//this returns array v of x vertices labeled v1, v2,...,vx
		Vertex[] v = new Vertex[x];
		for (int i = 0; i < v.length; i++) {
			v[i] = new Vertex("v" + (i+1));
		}
		return v;
	}

	/**
	 * @param v
	 * @param e
	 */
	private static void populateEdgeEndPoints(Vertex[] v, ArrayList<Edge> e, int type) {
		//each edge in e is assigned end points by iterating through v.

		int i = 0;
		int numEdges = (v.length * ( v.length - 1))/2;
		ArrayList<Edge> allEdges = generateEdges(numEdges);
		for (int j = 0; j < v.length; j++) {
			//this will generate all possible edges for the graph
			for (int j2 = j+1; j2 < v.length; j2++) {
				Edge placeHolderEdge = allEdges.get(i);
				placeHolderEdge.endPoints[0]=v[j];
				placeHolderEdge.endPoints[1]=v[j2];
				allEdges.set(i, placeHolderEdge);
				i ++;
			}			
		}
		if (type == 0) {
			Random r = new Random();
			int z = 0;
			for (int j = 0; j < e.size(); j++) {
				//this will randomly select edges from the complete graph
			}
		}
		else 
		{
			int totalDegrees = e.size() * 2;
			int remainderDegrees = totalDegrees % v.length;
			int averageDegree = (totalDegrees - remainderDegrees)/ v.length;
			System.out.println("Average degree: " + averageDegree);
			System.out.println("Remainder: " + remainderDegrees);
			System.out.println("totalDegrees: " + totalDegrees);
			for (int j = 0; j < e.size(); j++) {
				//this will randomly select edges from the complete graph
				//Also, the degrees of the vertices will have an even distribution

				Random r = new Random();
				int z = 0;
				try {
					z = r.nextInt(allEdges.size());
				} catch (Exception e1) {
//					System.out.println("Random Error: No edges left");
				}	

				Graph stepGraph = new Graph(v, allEdges);
				stepGraph.listVertices();
				try {
					stepGraph.listEdges();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("------------------");
				//This is to disallow a edge from being selected that would
				//let the difference of degree of any two vertices to be 
				//greater than 1
				if(allEdges.size() != 0){
					while(allEdges.get(z).endPoints[0].degree == averageDegree && 
							allEdges.get(z).endPoints[1].degree == averageDegree && 
							remainderDegrees <=1){
						System.out.println(allEdges.get(z).label + ": " + "<" + 
								allEdges.get(z).endPoints[0].label + 
								", " + allEdges.get(z).endPoints[1].label  + "> Removed");
						allEdges.remove(z);
						z = r.nextInt(allEdges.size());
					}
					e.get(j).endPoints[0]=allEdges.get(z).endPoints[0];
					e.get(j).endPoints[1]=allEdges.get(z).endPoints[1];

					allEdges.get(z).endPoints[0].degree ++;
					allEdges.get(z).endPoints[1].degree ++;
					String toRemove = allEdges.get(z).endPoints[0].label;
					int currentVertexDegree = allEdges.get(z).endPoints[0].degree;
					String toRemove2 = allEdges.get(z).endPoints[1].label;
					int currentVertexDegree2 = allEdges.get(z).endPoints[1].degree;
					System.out.println(allEdges.get(z).label + ": " + "<" + 
							allEdges.get(z).endPoints[0].label + 
							", " + allEdges.get(z).endPoints[1].label  + "> Removed");
					allEdges.remove(z);
					if(currentVertexDegree == averageDegree){
						if (remainderDegrees <= 0) {
							for (int k = 0; k < allEdges.size(); k++) {					
								if(toRemove.equals(allEdges.get(k).endPoints[0].label)||
										toRemove.equals(allEdges.get(k).endPoints[1].label)){
									//this removes all remaining edges that include the current vertex
									//in their list of endpoints.
									System.out.println(allEdges.get(k).label + ": " + "<" + 
											allEdges.get(k).endPoints[0].label + 
											", " + allEdges.get(k).endPoints[1].label  + "> Removed");
									allEdges.remove(k);
									k--;
									remainderDegrees --;
								}
							}
						}
						else 
						{
							remainderDegrees --;
						}
					}
					if(currentVertexDegree2 == averageDegree){
						if (remainderDegrees <= 0) {
							for (int k = 0; k < allEdges.size(); k++) {

								if(toRemove2.equals(allEdges.get(k).endPoints[0].label)||
										toRemove2.equals(allEdges.get(k).endPoints[1].label)){
									System.out.println(allEdges.get(k).label + ": " + "<" + 
											allEdges.get(k).endPoints[0].label + 
											", " + allEdges.get(k).endPoints[1].label  + "> Removed");
									allEdges.remove(k);
									remainderDegrees --;
									k--;
								}
							}
						}
						else 
						{
							remainderDegrees --;
						}
					} 
				}
				else
				{
					System.out.println("All possible edges were eliminated before "
							+ "generating the desired amount of edges!");
					e.remove(j);
					j--;
				}
			}
		}
	}
}


