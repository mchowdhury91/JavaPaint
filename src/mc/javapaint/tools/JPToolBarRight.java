package mc.javapaint.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToolBar;

import mc.javapaint.ImagePanel;

public class JPToolBarRight extends JToolBar{

	private JButton draw, rect;
	private JPTool activeTool;
	
	private DrawTool drawTool;
	private RectTool rectTool;
	
	private ImagePanel imagePanel;
	
	public JPToolBarRight(ImagePanel imagePanel) {
		super(JToolBar.VERTICAL);
		this.setFloatable(false);
		this.imagePanel = imagePanel;

		drawTool = new DrawTool(imagePanel);
		rectTool = new RectTool(imagePanel);
		
		activeTool = drawTool;
		initButtons();
		
		draw.setSelected(true);
		
		this.add(draw);
		this.add(rect);
	}
	
	public void initButtons(){
		draw = new JButton("Draw");
		rect = new JButton("Rectangle");
		
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(draw);
		bg.add(rect);
		
		ActionListener toolListener = new ActionListener(){
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
