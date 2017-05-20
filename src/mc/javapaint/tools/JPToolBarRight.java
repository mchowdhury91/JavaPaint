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

	private JButton drawButton, eraserButton, rectButton, ovalButton, newLayerButton, defaultLayerButton;
	private int numLayers;
	
	ArrayList<JButton> layers;
	
	private JPTool activeTool;
	
	private DrawTool drawTool;
	private EraserTool eraserTool;
	private RectTool rectTool;
	private OvalTool ovalTool;
	private ActionListener toolListener;
	
	private ImagePanel imagePanel;
	
	public JPToolBarRight(ImagePanel imagePanel) {
		
		super(JToolBar.VERTICAL);
		numLayers = 1;
		layers = new ArrayList<>();
		this.setFloatable(false);
		this.imagePanel = imagePanel;

		drawTool = new DrawTool(imagePanel);
		eraserTool = new EraserTool(imagePanel);
		rectTool = new RectTool(imagePanel);
		ovalTool = new OvalTool(imagePanel);
		
		activeTool = drawTool;
		initButtons();
		
		drawButton.setSelected(true);
		
		this.add(drawButton);
		this.add(eraserButton);
		this.add(rectButton);
		this.add(ovalButton);
		this.add(newLayerButton);
		
		
		imagePanel.getDefaultLayer().setLayerButton(defaultLayerButton);
		this.add(defaultLayerButton);		
	}
	
	public void initButtons(){
		drawButton = new JButton("Draw");
		eraserButton = new JButton("Eraser");
		rectButton = new JButton("Rectangle");
		ovalButton = new JButton("Oval");
		newLayerButton = new JButton("+");
		defaultLayerButton = new JButton("Layer 0");
		
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(drawButton);
		bg.add(eraserButton);
		bg.add(rectButton);
		bg.add(ovalButton);
		bg.add(newLayerButton);
		bg.add(defaultLayerButton);
		
		toolListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == drawButton){
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
				}else if(e.getSource() == eraserButton){
					activeTool = eraserTool;
					
					for(Enumeration<AbstractButton> buttons = bg.getElements();
							buttons.hasMoreElements();){
						JButton next = (JButton) buttons.nextElement();
						if(next.getText() == "Eraser"){
							next.setSelected(true);
						}else{
							next.setSelected(false);
						}
					}					
				}else if(e.getSource() == rectButton){
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
				}else if(e.getSource() == ovalButton){
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
				}else if(e.getSource() == newLayerButton){
					JButton newLayer = new JButton("Layer " + numLayers);
					
					bg.add(newLayer);
					layers.add(newLayer);
					addLayerButton();
					newLayer.addActionListener(this);
					imagePanel.addLayer(numLayers, newLayer);
					
					numLayers++;
				}else{
					JButton tmpButton = (JButton) e.getSource();
					String btnText = tmpButton.getText();
					
					// TODO: fix index so it works with multiple digits
					int index = Character.getNumericValue(btnText.charAt(btnText.length()-1));
					imagePanel.getLayer(index).makeActive();
					imagePanel.getGUI().getStatusLabel().setText(imagePanel.getLayer(index).getName());
					System.out.println(btnText.charAt(btnText.length()-1));
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
		
	public void addLayerButton(){
		if(!layers.isEmpty()){
			for(JButton button : layers){
				this.add(button);
			}
		}
	}
}
