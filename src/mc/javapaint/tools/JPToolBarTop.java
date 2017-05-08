package mc.javapaint.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import mc.javapaint.ImagePanel;


public class JPToolBarTop extends JToolBar {

	private Color strokeColor;
	private Stroke stroke;
	private JButton colorButton;
	private BufferedImage colorSample;
	private ImagePanel imagePanel;
	
	public JPToolBarTop(ImagePanel imagePanel) {
		super();
		this.setFloatable(false);
		strokeColor = Color.BLACK;
		this.imagePanel = imagePanel;
		initColorButton();
		initBrushSizeSpinner();
	}
	
	private void initColorButton(){
		colorButton = new JButton("Color");
		
		ActionListener colorListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(imagePanel, "Pick a color", strokeColor);
				if(c != null){
					strokeColor = c;
					imagePanel.setStrokeColor(strokeColor);
					updateColorSample();
				}
			}
			
		};
		
		createColorSample();
		
		colorButton.setIcon(new ImageIcon(colorSample));
		colorButton.addActionListener(colorListener);
		this.add(colorButton);		
	}
	
	private void createColorSample(){
		colorSample = new BufferedImage(30,20,BufferedImage.TYPE_INT_RGB);
		updateColorSample();
	}
	
	private void updateColorSample(){
		Graphics2D g = colorSample.createGraphics();
		g.setColor(strokeColor);
		g.fillRect(0, 0, 50, 20);
		g.dispose();
	}
	
	public Color getColor(){
		return strokeColor;
	}

	private void initBrushSizeSpinner(){
		SpinnerNumberModel brushModel = new SpinnerNumberModel(5,1,200,1);
		JSpinner brushSizeSpinner = new JSpinner(brushModel);
		
		
		JComponent comp = brushSizeSpinner.getEditor();
		
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		
		
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		
		formatter.setAllowsInvalid(false);
		
		ChangeListener brushListener = new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){
				Object o = brushModel.getValue();
				Integer i = (Integer)o;
				System.out.println(i);
				stroke = new BasicStroke(i.intValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);
				imagePanel.setStroke(stroke);
			}
		};
		
		//TODO: fix spinner so that user can completely backspace
		
		JLabel brushLabel = new JLabel("Brush Size");
		brushLabel.setLabelFor(brushSizeSpinner);
		brushLabel.setBorder(new EmptyBorder(0,5,0,5));
		brushSizeSpinner.setMaximumSize(brushSizeSpinner.getPreferredSize());
		brushSizeSpinner.addChangeListener(brushListener);
		this.add(brushSizeSpinner);
		this.add(brushLabel);
	}
	
}
