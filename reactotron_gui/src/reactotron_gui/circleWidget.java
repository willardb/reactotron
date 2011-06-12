package reactotron_gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

import de.sciss.jcollider.Buffer;


public class circleWidget extends reactotronWidget {	
	protected SCManager manager;
	protected Buffer b;
	protected String samplePath;
	
	circleWidget(int centerX, int centerY, int radius, SCManager manager, String samplePath)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.manager = manager;
		this.samplePath = samplePath;
		this.color = Color.red;
	}
	
	public void render(Graphics g)
	{
		g.setColor(this.color);
		g.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
	}

	@Override
	public boolean containsPoint(Point p) {
		// TODO Auto-generated method stub
		return ( Math.sqrt( Math.pow(p.x - this.centerX, 2.0) + Math.pow(p.y-this.centerY, 2.0)) < radius);
	}

	@Override
	public void moveCenter(Point p) {
		// TODO Auto-generated method stub
		this.centerX = p.x;
		this.centerY = p.y;
	}
	
	public void activate(reactotronZone z)
	{
		this.color = Color.blue;
		b = manager.synthLoop1(samplePath);
	}
	
	public void deactivate(reactotronZone z)
	{
		if (b != null) { try { b.zero(); } catch(Exception e) { System.out.println(e.getStackTrace()); } }
		this.color = Color.red;
	}
	
}
