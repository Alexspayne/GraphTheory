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
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;

import javax.swing.*;

//I began with the code from an Oracle demonstration in order to learn how to draw.

public class DrawGraph extends JApplet {
	final static int maxCharHeight = 15;
	final static int minFontSize = 6;

	final static Color bg = Color.white;
	final static Color fg = Color.black;
	final static Color red = Color.red;
	final static Color white = Color.white;

	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static BasicStroke wideStroke = new BasicStroke(8.0f);

	final static float dash1[] = {10.0f};
	final static BasicStroke dashed = new BasicStroke(1.0f, 
			BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_MITER, 
			10.0f, dash1, 0.0f);
	Dimension totalSize;
	FontMetrics fontMetrics;

	public void init() {
		//Initialize drawing colors
		setBackground(bg);
		setForeground(fg);
	}

	FontMetrics pickFont(Graphics2D g2,
			String longString,
			int xSpace) {
		boolean fontFits = false;
		Font font = g2.getFont();
		FontMetrics fontMetrics = g2.getFontMetrics();
		int size = font.getSize();
		String name = font.getName();
		int style = font.getStyle();

		while ( !fontFits ) {
			if ( (fontMetrics.getHeight() <= maxCharHeight)
					&& (fontMetrics.stringWidth(longString) <= xSpace) ) {
				fontFits = true;
			}
			else {
				if ( size <= minFontSize ) {
					fontFits = true;
				}
				else {
					g2.setFont(font = new Font(name,
							style,
							--size));
					fontMetrics = g2.getFontMetrics();
				}
			}
		}
		return fontMetrics;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();

		fontMetrics = pickFont(g2, "Filled and Stroked GeneralPath",
				d.width);

		Color fg3D = Color.lightGray;

		g2.setPaint(fg3D);
		g2.setPaint(fg);

		int x = 5;
		int y = 7;
		int rectWidth = d.width - 2*x;
		int stringY = d.height - 3 - fontMetrics.getDescent();
		int rectHeight = stringY - fontMetrics.getMaxAscent() - y - 2;

		int n = 100;
		drawNGon(g2, x, y, rectWidth, stringY, rectHeight, n);
	}

	private void drawNGon(Graphics2D g2, int x, int y, int rectWidth,
			int stringY, int rectHeight, int n) {
		// draw NGon
		//To Do: Find a way to generate as many points as there are vertices.
		//Then index each point with one of the vertices from the vertex space.
		//iterate through each edge in the edge space, 
		//drawing a line connecting the endpoints of each edge.
		double[] xPoints = new double[n];
		double[] yPoints = new double[n];
		double r = Math.min(rectWidth, rectHeight)/2;
		for(int i=0; i<n; i++) {
			xPoints[i] = x + r*Math.cos(i*2*Math.PI/n) + rectWidth/2;
			yPoints[i] = y + r*Math.sin(i*2*Math.PI/n) + rectHeight/2;
		}
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				xPoints.length);
		polygon.moveTo(xPoints[0], yPoints[0]);
		for ( int index = 1; index < xPoints.length; index++ ) {
			polygon.lineTo(xPoints[index], yPoints[index]);
		};
		polygon.closePath();

		g2.draw(polygon);
		g2.drawString("Regular " + n + "gon", x, stringY);
	}

	public void runGraphApplet() {
		JFrame f = new JFrame("ShapesDemo2D");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		JPanel c = new JPanel();
		f.add(c);
		AbstractAction anAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You pressed F2!");
			}
		};
		c.getInputMap().put(KeyStroke.getKeyStroke("F2"),"doSomething");
		c.getActionMap().put("doSomething",	anAction);

		JApplet applet = new DrawGraph();
		f.getContentPane().add("Center", applet);
		applet.init();
		f.pack();
		f.setSize(new Dimension(550,100));
		f.setVisible(true);
	}

}