package mc.javapaint.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToolBar;

import mc.javapaint.ImagePanel;

public class JPToolBarRight extends JToolBar{

	private JButton draw, rect, oval;
	private int numLayers;
	
	ArrayList<JButton> layers;
	
	private JPTool activeTool;
	
	private DrawTool drawTool;
	private RectTool rectTool;
	private OvalTool ovalTool;
	private ActionListener toolListener;
	
	private ImagePanel imagePanel;
	
	public JPToolBarRight(ImagePanel imagePanel) {
		
		super(JToolBar.VERTICAL);
		numLayers = 1;
		this.setFloatable(false);
		this.imagePanel = imagePanel;

		drawTool = new DrawTool(imagePanel);
		rectTool = new RectTool(imagePanel);
		ovalTool = new OvalTool(imagePanel);
		
		activeTool = drawTool;
		initButtons();
		
		draw.setSelected(true);
		
		this.add(draw);
		this.add(rect);
		this.add(oval);
	}
	
	public void initButtons(){
		draw = new JButton("Draw");
		rect = new JButton("Rectangle");
		oval = new JButton("Oval");
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(draw);
		bg.add(rect);
		bg.add(oval);
		
		toolListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == draw){
					activeTool = drawTool;
					
					for(Enumeration<AbstractButton> buttons = bg.getElements();
							buttons.hasMoreElements();){
						JButton next = (JButton) buttons.nextElement();
						if(next.getText() == "Draw"){
							next.setSelected(true);
						}else{
							next.setSelected(false);
						}
					}
				}else if(e.getSource() == rect){
					activeTool = rectTool;
					
					for(Enumeration<AbstractButton> buttons = bg.getElements();
							buttons.hasMoreElements();){
						JButton next = (JButton) buttons.nextElement();
						if(next.getText() == "Rectangle"){
							next.setSelected(true);
						}else{
							next.setSelected(false);
						}
					}
				}else if(e.getSource() == oval){
					activeTool = ovalTool;
					
					for(Enumeration<AbstractButton> buttons = bg.getElements();
							buttons.hasMoreElements();){
						JButton next = (JButton) buttons.nextElement();
						if(next.getText() == "Oval"){
							next.setSelected(true);
						}else{
							next.setSelected(false);
						}
					}
				}
				
				imagePanel.setActiveTool(activeTool);
			}
		};
		
		
		for(Enumeration<AbstractButton> buttons = bg.getElements();
				buttons.hasMoreElements();){
			JButton next = (JButton) buttons.nextElement();
			next.addActionListener(toolListener);
		}
		
	}
	
	public JPTool getActiveTool(){
		return activeTool;
	}
		

}
