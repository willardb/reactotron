package reactotron_gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;

import javax.swing.*;


public class reactotronPanel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5853323692741074976L;
	protected ArrayList<reactotronWidget> widgets;
	protected ArrayList<reactotronZone> zones;
	protected ArrayList<reactotronWidget> grabbedWidgets;
	protected SCManager manager;
	
	reactotronPanel(SCManager manager) {
		this.manager = manager;
		widgets = new ArrayList<reactotronWidget>();
		zones = new ArrayList<reactotronZone>();
		grabbedWidgets = new ArrayList<reactotronWidget>();
		
		String pathBase = getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "../media/";
		pathBase = pathBase.substring(1);
		pathBase = pathBase.replace("%20", " ");
		System.out.println("Path base: " + pathBase);
		
		
		widgets.add(new circleWidget(100,100,50,manager,pathBase + "looptest.wav"));
		widgets.add(new circleWidget(200,200,25,manager,pathBase+"looperman_265632_48122_MONSTERBOUNCE2.wav"));
		widgets.add(new circleWidget(500,37,37,manager,pathBase+"looperman_158495_48058_octane6_137bpm.wav"));
		widgets.add(new circleWidget(550,37,10,manager,pathBase+"looperman_158495_48058_octane6_137bpm.wav"));
		widgets.add(new circleWidget(200,300,10,manager,pathBase+"looperman_158495_48058_octane6_137bpm.wav"));

		
		zones.add(new toggleZone(500,300,200));
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public reactotronWidget[] getWidgets()
	{
		return ((reactotronWidget[])(widgets.toArray()));
	}
	
	public reactotronWidget getWidget(int i)
	{
		return widgets.get(i);
	}
	
	public reactotronZone[] getZones()
	{
		return ((reactotronZone[])(zones.toArray()));
	}
	
	public reactotronZone getZone(int i)
	{
		return zones.get(i);
	}
	
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		for(int i=0;i<this.zones.size();i++)
		{
			this.getZone(i).render(g);
		}
		
		for(int i=0;i<this.widgets.size();i++)
		{
			this.getWidget(i).render(g);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

		// grab any widgets that are under the cursor
		for (int i=0;i<widgets.size();i++)
		{
			if (this.getWidget(i).containsPoint(arg0.getPoint()))
			{
				this.grabbedWidgets.add(this.getWidget(i));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.grabbedWidgets.clear();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for (int i=0;i<grabbedWidgets.size();i++)
		{
			this.grabbedWidgets.get(i).moveCenter(arg0.getPoint());
			
			for (int j=0;j<this.zones.size();j++)
			{
				this.zones.get(j).recomputeContainment(grabbedWidgets.get(i));
			}
		}
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
