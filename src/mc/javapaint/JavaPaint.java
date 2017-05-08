package mc.javapaint;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// main launcher/entry point

public class JavaPaint {

	public static void main(String[] args){
		Runnable r = new Runnable(){

			@Override
			public void run() {
				
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}catch(Exception e){
					
				}
				GUI g = new GUI();
			}
			
		};
		
		SwingUtilities.invokeLater(r);
	}

}
