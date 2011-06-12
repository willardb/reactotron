package reactotron_gui;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class reactotronZone {
	protected ArrayList<reactotronWidget> containedWidgets;
	
	reactotronZone()
	{
		containedWidgets = new ArrayList<reactotronWidget>();
	}
	
	public abstract void recomputeContainment(reactotronWidget widget);
	public abstract void render(Graphics g);
}
