package com.moonjr;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;

public class MainFrame extends JFrame implements ActionListener, SerialDataEventListener {

	private Serial mSerial;

	private static final String ACTION_SECNARIO = "start scenario";
	private static final String ACTION_RANDOM = "start random";

	private ImagePanel mImagePanelRacing, mImagePanelWait, mImagePanel0, mImagePanel1, mImagePanel2, mImagePanel3;
	private JPanel mPanelMain;
	private JSpinner mSpinnerScenario;

	public static void main(String[] args) {
		new MainFrame();
	}

	public MainFrame() {
		try {
			mSerial = SerialFactory.createInstance();
			mSerial.addListener(this);
			mSerial.open(Serial.DEFAULT_COM_PORT, 9600);

			mImagePanelRacing = new ImagePanel("image_racing.jpg");
			mImagePanelWait = new ImagePanel("image_wait.png");
			mImagePanel0 = new ImagePanel("image_0.jpg");
			mImagePanel1 = new ImagePanel("image_1.jpg");
			mImagePanel2 = new ImagePanel("image_2.jpg");
			mImagePanel3 = new ImagePanel("image_3.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getContentPane().setLayout(new BorderLayout(0, 0));

		mPanelMain = new JPanel();
		mPanelMain.setLayout(new BorderLayout(0, 0));
		getContentPane().add(mPanelMain, BorderLayout.CENTER);
		setImageWait();

		Panel panel_1 = new Panel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		mSpinnerScenario = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		panel_1.add(mSpinnerScenario);

		JButton button = new JButton("시나리오 시작");
		button.setActionCommand(ACTION_SECNARIO);
		button.addActionListener(this);
		panel_1.add(button);

		JButton button_1 = new JButton("랜덤 시작");
		button_1.addActionListener(this);
		button_1.setActionCommand(ACTION_RANDOM);
		panel_1.add(button_1);

		setVisible(true);
		setSize(800, 480);
	}

	public void setImageRacing() {
		mPanelMain.removeAll();
		mPanelMain.add(mImagePanelRacing, BorderLayout.CENTER);
	}

	public void setImageWait() {
		mPanelMain.removeAll();
		mPanelMain.add(mImagePanelWait, BorderLayout.CENTER);
	}

	public void setImage(int num) {
		mPanelMain.removeAll();
		switch (num) {
		case 0:
			mPanelMain.add(mImagePanel0, BorderLayout.CENTER);
			break;
		case 1:
			mPanelMain.add(mImagePanel1, BorderLayout.CENTER);
			break;
		case 2:
			mPanelMain.add(mImagePanel2, BorderLayout.CENTER);
			break;
		case 3:
			mPanelMain.add(mImagePanel3, BorderLayout.CENTER);
			break;
		}

	}

	private class ImagePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6903814073369440054L;
		private BufferedImage image;

		public ImagePanel(File image) throws IOException {
			super();
			this.image = ImageIO.read(image);
		}

		public ImagePanel(String image) throws IOException {
			this(new File(image));
		}

		protected void paintComponent(java.awt.Graphics g) {
			g.drawImage(image, 0, 0, null);
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int scenario = 9999;
		if (e.getActionCommand().equals(ACTION_SECNARIO)) {
			scenario = (int) mSpinnerScenario.getValue();
		} else if (e.getActionCommand().equals(ACTION_RANDOM)) {
			scenario = 9999;
		}
		try {
			mSerial.writeln(Integer.toString(scenario));
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void dataReceived(SerialDataEvent arg0) {
		try {
			System.out.println(arg0.getAsciiString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
