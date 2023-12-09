package domain.intro;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;

public class IntroPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JXMapViewer jXMapViewer = new JXMapViewer();

	
	@Override
	public void paintComponent(Graphics g) {
		Image bg = new ImageIcon("src/resource/backgroundimage.png <-- required").getImage();
		g.drawImage(bg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	/**
	 * Create the panel.
	 */
	public IntroPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(76, 23, 102, 61);
		add(lblNewLabel);
		
		
		
		
				
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		GeoPosition geo = new GeoPosition(37.44913, 126.6572);
		
		
		jXMapViewer.setTileFactory(tileFactory);
		jXMapViewer.setBounds(59, 95, 923, 820);
		jXMapViewer.setSize(800,800);
		jXMapViewer.setAddressLocation(geo);
		jXMapViewer.setZoom(3);
		add(jXMapViewer);
		
		
		// Create event mouse move
		MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
		jXMapViewer.addMouseListener(mm);
		jXMapViewer.addMouseMotionListener(mm);
		jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
		
		
		
		
		
		
		

	}

}
