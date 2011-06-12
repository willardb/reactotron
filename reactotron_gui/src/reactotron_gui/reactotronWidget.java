package reactotron_gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class reactotronWidget {
	protected int centerX;
	protected int centerY;
	protected int radius;
	protected Color color;
	
	public abstract void render(Graphics g);
	public abstract boolean containsPoint(Point p);
	public abstract void moveCenter(Point p);
	public abstract void activate(reactotronZone z);
	public abstract void deactivate(reactotronZone z);
	
	
	public int getCenterX()
	{
		return this.centerX;
	}
	
	public int getCenterY()
	{
		return this.centerY;
	}
	
	public int getRadius()
	{
		return this.radius;
	}
}
