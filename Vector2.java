package perlinNoise;

public class Vector2 {
	
	private double x;
	private double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double[] getComponents() {
		return new double[] {x, y};
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getLength() {
		return Math.sqrt(x * x + y * y);
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
    
    public void normalize() {
    	double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }
}
