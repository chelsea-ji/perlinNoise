package perlinNoise;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;

public class PerlinNoise extends Canvas{
	public static int gridSize = 360;
	public static int octaves = 8;
	
	public static double[][] map = new double[gridSize][gridSize];
	
	public static void main(String[] args) {	
		
		Vector2[][] vectors = new Vector2[gridSize*octaves][gridSize*octaves];
		Random r = new Random();		

		// generate random influence vectors for each grid point
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[0].length; j++) {
				vectors[i][j] = new Vector2(1, 0);
				vectors[i][j].rotateBy(r.nextInt(360));
			}
		}

		// input all coordinate points into noise function
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				double value = 0;
				double frequency = 1;
				double amplitude = 1;

				for (int k = 0; k < octaves; k++) {
					value += perlin(x*frequency/map.length, y*frequency/map.length, vectors)*amplitude;

					frequency *= 2;
					amplitude /= 2;
				}

				value *= 1.7;
				if (value < -1.0) {
					value = -1.0;
				} else if (value > 1.0) {
					value = 1.0;
				}
				value = (value + 1.0)/2.0*255.0;
				
				map[x][y] = (int)value;
			}
		}
		
		// set up canvas
		JFrame frame = new JFrame("map");
		Canvas canvas = new PerlinNoise();
		canvas.setSize(gridSize,gridSize);
		canvas.setBackground(Color.WHITE);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * calculate the dot product of the influence and distance vectors
	 */
	public static double dotGradient(Vector2 influence, int x0, int y0, double x, double y) {
		Vector2 distance = new Vector2(x - (double)x0, y - (double)y0);
		return influence.dot(distance);
	}
	
	/**
	 * linear interpolation between two values with a given weight
	 */
	public static double lerp(double val1, double val2, double w) {
		return val1 + w*(val2 - val1);
	}
	
	/**
	 * fade function applied to lerp weights to smooth grid lines
	 */
	public static double fade(double t) {
	    return t*t*t*(t*(t*6-15)+10);
	}
	
	/**
	 * calculates value from 0-1 given a coordinate point and list of influence vectors for a grid
	 */
	public static double perlin(double x, double y, Vector2[][] vectors) {
		// calculate corners of the grid cell the point is located in
		int x0 = (int) x;
		int y0 = (int) y;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		
		// horizontal and vertical weights (distance between point and upper left corner)
		double hw = x - (double)x0;
		double vw = y - (double)y0;
		
		hw = fade(hw);
		vw = fade(vw);
		
		// dot products for upper two corners
		double dot0 = dotGradient(vectors[y0][x0], x0, y0, x, y);
		double dot1 = dotGradient(vectors[y0][x1], x1, y0, x, y);
		double i0 = lerp(dot0, dot1, hw);

		// dot products for bottom two corners
		dot0 = dotGradient(vectors[y1][x0], x0, y1, x, y);
		dot1 = dotGradient(vectors[y1][x1], x1, y1, x, y);
		double i1 = lerp(dot0, dot1, hw);
		
		double value = lerp(i0, i1, vw);
		
		return value;
	}
	
	/**
	 * paints map on canvas
	 */
	public void paint(Graphics g) {			
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {	

				int val = (int) (map[i][j]);
				g.setColor(new Color(val, val, val));
				g.fillRect(i, j, 1, 1);
			}
		}
	}
}
