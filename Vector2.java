package perlinNoise;

public class Vector2 {
	
	private double x;
	private double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double dot(Vector2 v) {
	    return this.x * v.x + this.y * v.y;
	}
	
	public void rotateBy(double angle) {
	    double cos = Math.cos(angle);
	    double sin = Math.sin(angle);
	    double rx = x * cos - y * sin;
	    y = x * sin + y * cos;
	    x = rx;
  	}
}
