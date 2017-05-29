package mc.javapaint.tools;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToolBar;

import mc.javapaint.ImagePanel;
import mc.javapaint.JPAction;

public class JPToolBarRight extends JToolBar{

	private JButton drawButton, eraserButton, rectButton, ovalButton, undoButton, newLayerButton, defaultLayerButton;
	private int numLayers;
	
	ArrayList<JButton> layers;
	
	private JPTool activeTool;
	
	private DrawTool drawTool;
	private EraserTool eraserTool;
	private RectTool rectTool;
	private OvalTool ovalTool;
	private ActionListener toolListener;
	private static final Color SELECTED_LAYER_COLOR = Color.decode("#A5D6C2");
	
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
		this.add(undoButton);
		this.add(newLayerButton);
		
		imagePanel.getDefaultLayer().setLayerButton(defaultLayerButton);
		this.add(defaultLayerButton);		
	}
	
	public void initButtons(){
		drawButton = new JButton("Draw");
		eraserButton = new JButton("Eraser");
		rectButton = new JButton("Rectangle");
		ovalButton = new JButton("Oval");
		undoButton = new JButton("Undo");
		newLayerButton = new JButton("+");
		defaultLayerButton = new JButton("Layer 0");
		
		ButtonGroup bg = new ButtonGroup();
		ButtonGroup lbg = new ButtonGroup();
		
		bg.add(drawButton);
		bg.add(eraserButton);
		bg.add(rectButton);
		bg.add(ovalButton);
		bg.add(undoButton);
		bg.add(newLayerButton);
		lbg.add(defaultLayerButton);
		defaultLayerButton.setSelected(true);
		defaultLayerButton.setBackground(SELECTED_LAYER_COLOR);
		
		toolListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == drawButton){
					activeTool = drawTool;
					JButton srcButton = (JButton) e.getSource();
					setExclusiveSelected(bg, srcButton.getText());
				}else if(e.getSource() == eraserButton){
					activeTool = eraserTool;
					
					JButton srcButton = (JButton) e.getSource();
					setExclusiveSelected(bg, srcButton.getText());				
				}else if(e.getSource() == rectButton){
					activeTool = rectTool;
					
					JButton srcButton = (JButton) e.getSource();
					setExclusiveSelected(bg, srcButton.getText());
				}else if(e.getSource() == ovalButton){
					activeTool = ovalTool;
					
					JButton srcButton = (JButton) e.getSource();
					setExclusiveSelected(bg, srcButton.getText());
				}else if(e.getSource() == undoButton){
					JPAction action = imagePanel.popHistory();
					if(action != null){
						action.undo();
					}
				}else if(e.getSource() == newLayerButton){
					JButton newLayer = new JButton("Layer " + numLayers);
					
					lbg.add(newLayer);
					layers.add(newLayer);
					addLayerButton();
					newLayer.addActionListener(this);
					imagePanel.addLayer(numLayers, newLayer);
					
					numLayers++;
				}else{
					JButton tmpButton = (JButton) e.getSource();
					String btnText = tmpButton.getText();
					
					imagePanel.getLayer(btnText).makeActive();
					tmpButton.setSelected(true);
					//imagePanel.getGUI().getStatusLabel().setText(imagePanel.getLayer(index).getName());
					
					JButton srcButton = (JButton) e.getSource();
					
					setExclusiveSelected(lbg, srcButton.getText(), Color.decode("#A5D6C2"));
				}
				imagePanel.setActiveTool(activeTool);
			}
		};
		
		
		for(Enumeration<AbstractButton> buttons = bg.getElements();
				buttons.hasMoreElements();){
			JButton next = (JButton) buttons.nextElement();
			next.addActionListener(toolListener);
		}

		for(Enumeration<AbstractButton> buttons = lbg.getElements();
				buttons.hasMoreElements();){
			JButton next = (JButton) buttons.nextElement();
			next.addActionListener(toolListener);
		}
		
	}
	
	public void setExclusiveSelected(ButtonGroup bg, String buttonText){
		for(Enumeration<AbstractButton> buttons = bg.getElements();
				buttons.hasMoreElements();){
			JButton next = (JButton) buttons.nextElement();
			if(next.getText() == buttonText){
				next.setSelected(true);
			}else{
				next.setSelected(false);
			}
		}
	}

	public void setExclusiveSelected(ButtonGroup bg, String buttonText, Color color){
		for(Enumeration<AbstractButton> buttons = bg.getElements();
				buttons.hasMoreElements();){
			JButton next = (JButton) buttons.nextElement();
			if(next.getText() == buttonText){
				next.setSelected(true);
				next.setBackground(color);
			}else{
				next.setSelected(false);
				next.setBackground(null);
			}
		}
	}	
	
	public void removeLayerButton(JButton layerButton){
		this.remove(layerButton);
		layers.remove(layerButton);
		defaultLayerButton.setSelected(true);
		defaultLayerButton.setBackground(SELECTED_LAYER_COLOR);
		this.repaint();
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
