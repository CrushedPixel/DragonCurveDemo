package eu.crushedpixel.dragoncurve;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JLayeredPane pane = new JLayeredPane();
		pane.setVisible(true);
		frame.add(pane);

		pane.setSize(800, 800);
		frame.setSize(800, 800);
		frame.setResizable(false);
		frame.setTitle("Dragon Curve");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		pane.setLayout(null);

		final PaintCanvas canvas = new PaintCanvas();
		canvas.setSize(800, 800);
		canvas.setVisible(true);

		pane.add(canvas);

		final JLabel label = new JLabel("Iterations: 0");
		label.setBounds(250, 10, 100, 20);
		label.setVisible(true);
		pane.add(label, 2, 0);
		
		final JSlider slider = new JSlider(0, 18, 0);
		slider.setBounds(10, 10, 200, 40);

		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setVisible(true);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				label.setText("Iterations: "+slider.getValue());
				canvas.redraw(slider.getValue());
			}
		});

		pane.add(slider, 2, 0);

		pane.repaint();
		frame.setVisible(true);
	}

}
