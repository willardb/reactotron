package reactotron_gui;

import javax.swing.JFrame;

public class reactotron_gui {

	public static void main(String[] args)
	{
		System.out.println("hello world");
		
		reactotronFrame fm = new reactotronFrame();
		SCManager manager = new SCManager(fm);
		reactotronPanel fp = new reactotronPanel(manager);
		
		
		fm.setSize(800,600);
		fm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fm.add(fp);
		fm.setVisible(true);
	}
}
