package reactotron_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.sciss.jcollider.*;

public class SCManager implements ServerListener {

	protected Server		server	= null;
	protected NodeWatcher	nw		= null;
	protected Group			grpAll;
	
	protected reactotronFrame mainFrame;
	
	public SCManager(reactotronFrame mainFrame) 
	{
		final String		fs			= File.separator;
		this.mainFrame = mainFrame;
		
		try {
			

			server = new Server( "localhost" );


			File f = findFile( JCollider.isWindows ? "scsynth.exe" : "scsynth", new String[] {
				fs + "Applications" + fs + "SuperCollider_f",
				fs + "Applications" + fs + "SC3",
				fs + "usr" + fs + "local" + fs + "bin",
				fs + "usr" + fs + "bin",
				"C:\\Program Files\\SC3",
				"C:\\Program Files\\SuperCollider_f"
			});

			if( f != null ) Server.setProgram( f.getAbsolutePath() );
			server.addListener( this );
			
			try {
				server.start();
				server.startAliveThread();
			}
			catch( IOException e1 ) { /* ignored */ }
		}
		catch( IOException e1 ) {
			JOptionPane.showMessageDialog( mainFrame, "Failed to create a server :\n" + e1.getClass().getName() +
				e1.getLocalizedMessage(), "title goes here", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private static File findFile( String fileName, String[] folders )
	{
		File f;
	
		for( int i = 0; i < folders.length; i++ ) {
			f = new File( folders[ i ], fileName );
			if( f.exists() ) return f;
		}
		return null;
	}

	// ------------- ServerListener interface -------------

	public void serverAction( ServerEvent e )
	{
		switch( e.getID() ) {
		case ServerEvent.RUNNING:
			try {
				initServer();
			}
			catch( IOException e1 ) {
				reportError( e1 );
			}
			break;
		
		case ServerEvent.STOPPED:
			// re-run alive thread
			final javax.swing.Timer t = new javax.swing.Timer( 1000, new ActionListener() {
				public void actionPerformed( ActionEvent e )
				{
					try {
						if( server != null ) server.startAliveThread();
					}
					catch( IOException e1 ) {
						reportError( e1 );
					}
				}
			});
			t.setRepeats( false );
			t.start();
			break;
		
		default:
			break;
		}
	}
	
	protected static void reportError( Exception e ) {
		System.err.println( e.getClass().getName() + " : " + e.getLocalizedMessage() );
	}
	
	private void sendDefs()
	{
		try {
		UGenInfo.readBinaryDefinitions();
		} catch( IOException e1 ) {
			System.err.println( e1.getClass().getName() + " : " + e1.getLocalizedMessage() );
		}
		
		// I don't think we actually need to send any defs for playing buffers
		/*
		List	defs = DemoDefs.create();
		SynthDef		def;
		
		for( int j = 0; j < defs.size(); j++ ) {
			def = (SynthDef) defs.get( j );
			try {
				def.send( server );
			}
			catch( IOException e1 ) {
				System.err.println( "Sending Def " + def.getName() + " : " +
					e1.getClass().getName() + " : " + e1.getLocalizedMessage() );
			}
		}
		*/ 
	}
	
	private void initServer()
	throws IOException
	{
		sendDefs();
		if( !server.didWeBootTheServer() ) {
			server.initTree();
			server.notify( true );
		}
//		if( nw != null ) nw.dispose();
		nw		= NodeWatcher.newFrom( server );
		grpAll	= Group.basicNew( server );
		nw.register( server.getDefaultGroup() );
		nw.register( grpAll );
		server.sendMsg( grpAll.newMsg() );
	}
	
	public Buffer synthLoop1(String path)
	{
		Buffer		b;
		
		class action implements de.sciss.jcollider.Buffer.CompletionAction {
		    public void completion( de.sciss.jcollider.Buffer buf ) {
		    	System.out.println( "\nnumChannels = " + buf.getNumChannels() );
		    	try {
		    		Synth buffcap;
		    		buffcap = buf.play(true);
		    	}
		    	catch( IOException e1 ) {
					System.err.println( e1 );
				}
		    }
		}
		
		try {
			UGenInfo.readBinaryDefinitions();
			System.out.println("test LOL OMG \n\n\n\n");
			b = Buffer.read(this.server,path,0,0,new action());
			return b;
		}
		catch( IOException e1 ) {
			System.err.println( e1 );
			return null;
		}
	}
}
