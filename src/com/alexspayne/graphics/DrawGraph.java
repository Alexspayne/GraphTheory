package com.alexspayne.graphics;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JApplet;

import com.alexspayne.graph.Edge;
import com.alexspayne.graph.Graph;
//I began with the code from an Oracle demonstration in order to learn how to draw.
import com.alexspayne.graph.Vertex;

@SuppressWarnings("serial")
public class DrawGraph extends JApplet {
	final static int maxCharHeight = 15;
	final static int minFontSize = 6;

	final static Color bg = Color.getHSBColor(46f/360, .50f, 0.92f);
	final static Color edgeColor = Color.getHSBColor(130f/360, .90f, .30f);
	final static Color vertexLabelColor = Color.blue;
	final static Color vertexColor = Color.black;

	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static BasicStroke wideStroke = new BasicStroke(8.0f);

	Dimension totalSize;
	FontMetrics fontMetrics;
	Graph graph;

	public DrawGraph(Graph graph) {
		this.graph = graph;
	}

	public void init() {
		//Initialize drawing colors
		setBackground(bg);
		setForeground(edgeColor);
	}

	FontMetrics pickFont(Graphics2D g2,	String longString,int xSpace) {
		Font font = g2.getFont();
		FontMetrics fontMetrics = g2.getFontMetrics();
		int size = font.getSize();
		String name = font.getName();
		int style = Font.BOLD;
		g2.setFont(font = new Font(name,style,--size));
		fontMetrics = g2.getFontMetrics();
		return fontMetrics;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();

		fontMetrics = pickFont(g2, "Randomly generated graph",d.width);
		Color fg3D = Color.lightGray;

		g2.setPaint(fg3D);


		int x = 5;
		int y = 7;
		int rectWidth = d.width - 2*x;
		int stringY = d.height - 3 - fontMetrics.getDescent();
		int rectHeight = stringY - fontMetrics.getMaxAscent() - y - 2;

		drawNGon(g2, x, y, rectWidth, stringY, rectHeight, graph);
	}

	private void drawNGon(Graphics2D g2, int x, int y, int rectWidth,
			int stringY, int rectHeight, Graph graph) {
		Vertex[] vertexSpace = graph.getVertexSpace();
		int n = vertexSpace.length;
		//the index of each x/y point should match the index of each vertex in graph.vertexspace
		double[] xPoints = new double[n];
		double[] yPoints = new double[n];
		double r = Math.min(rectWidth, rectHeight)/2;
		ArrayList<Edge> edgeSpace = graph.getEdgeSpace();

		for(int i=0; i<n; i++) {
			//sets the coordinates for the vertices
			xPoints[i] = x + r*Math.cos(i*2*Math.PI/n) + rectWidth/2;
			yPoints[i] = y + r*Math.sin(i*2*Math.PI/n) + rectHeight/2 + 7;
		}
		g2.setPaint(edgeColor);
		for (int i = 0; i < edgeSpace.size(); i++) {
			//draw the edges between the corresponding points to the endpoints of each edge.
			Edge edge = edgeSpace.get(i);
			Vertex endPoint0 = edge.getEndPoint(0);
			Vertex endPoint1 = edge.getEndPoint(1);
			g2.draw(new Line2D.Double(xPoints[endPoint0.index], yPoints[endPoint0.index],
					xPoints[endPoint1.index], yPoints[endPoint1.index]));
		}
		g2.setPaint(vertexColor);
		for(int i=0; i<n; i++) {
			//drawing the vertices
			g2.fill(new Ellipse2D.Double(xPoints[i] - 3.5, yPoints[i] - 3.5, 7, 7));
		}
		g2.setPaint(vertexLabelColor);
		for(int i=0; i<n; i++) {
			//sets the coordinates for the vertex labels and draws them.
			int labelWidth = fontMetrics.charsWidth(vertexSpace[i].getLabel().toCharArray(), 0, vertexSpace[i].getLabel().length());
			Double xVertexLabel =xPoints[i] - labelWidth/2;
			int xVertexLabelInt = xVertexLabel.intValue();
			Double yVertexLabel = yPoints[i] - 4;
			int yVertexLabelInt = yVertexLabel.intValue();
			g2.drawString(vertexSpace[i].getLabel(), xVertexLabelInt, yVertexLabelInt);
		}
	}
}