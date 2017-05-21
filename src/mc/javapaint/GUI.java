package mc.javapaint;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import mc.javapaint.tools.JPToolBarRight;
import mc.javapaint.tools.JPToolBarTop;

public class GUI {

	// swing components
	private JFrame frame;
	private JPanel contentPane;
	private JScrollPane imageScroll;
	private ImagePanel imagePanel;
	private JPToolBarTop jpToolBarTop;
	private JPToolBarRight jpToolBarRight;
	private JPStatusLabel statusLabel;
	/*
	 * frame contains contentPane
	 *   contentPane houses imageScroll
	 *     imageScroll contains imageView
	 *      imageView contains imageLabel
	 *       imageLabel has a BufferedImage as its icon
	 */
	
	public GUI() {
		initFrame();
		
		frame.setVisible(true);
	}
	
	private void initFrame(){
		frame = new JFrame("JavaPaint!");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationByPlatform(true);
		
		contentPane = new JPanel(new BorderLayout(4,4));
		contentPane.setBorder(new EmptyBorder(5,3,5,3));
		imagePanel = new ImagePanel(this);
		imageScroll = new JScrollPane(imagePanel);
		jpToolBarTop = new JPToolBarTop(imagePanel);
		jpToolBarRight = new JPToolBarRight(imagePanel);
		statusLabel = new JPStatusLabel("Layer 0");
		
		contentPane.add(jpToolBarTop, BorderLayout.PAGE_START);
		contentPane.add(jpToolBarRight,BorderLayout.LINE_END);
		contentPane.add(statusLabel,BorderLayout.PAGE_END);
		contentPane.add(imageScroll);
		
		frame.setContentPane(contentPane);
	}
	
	public JPStatusLabel getStatusLabel(){
		return statusLabel;
	}

}
