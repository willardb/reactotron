package reactotron_gui;

import java.awt.Graphics;
import java.awt.Color;

public class toggleZone extends reactotronZone {
	
	protected int centerX;
	protected int centerY;
	protected int radius;
	protected Color color;
	
	toggleZone(int centerX, int centerY, int radius)
	{
		super();
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = Color.green;
	}
	
	public void recomputeContainment(reactotronWidget widget)
	{
		if ( Math.sqrt( Math.pow(widget.getCenterX() - this.centerX, 2.0) + Math.pow(widget.getCenterY()-this.centerY, 2.0)) < (this.radius + widget.getRadius()) )
		{
			if (!containedWidgets.contains(widget))
			{
				this.containedWidgets.add(widget);
				widget.activate(this);
			}
		} else {
			this.containedWidgets.remove(widget);
			widget.deactivate(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
		g.setColor(Color.white);
		g.fillOval(centerX-radius+3, centerY-radius+3, (radius*2)-6, (radius*2)-6);
	}
	
}
