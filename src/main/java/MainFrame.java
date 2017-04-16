import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.util.Date;

public class MainFrame {
	private static void GUI(){
		
		JFrame frame = new JFrame();

		JLabel time = new JLabel("Time");
		JLabel weather = new JLabel("Weather");
		JLabel calendarEvents = new JLabel("Events");	
		
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		
        Font font = new Font("Arial", Font.PLAIN, 25);
        Font clockFont = new Font("Arial", Font.PLAIN, 175);
        weather.setFont(font);
        weather.setForeground(Color.white);
        calendarEvents.setFont(font);
        calendarEvents.setForeground(Color.white);
        time.setFont(clockFont);
        time.setForeground(Color.white);
        try {
			GoogleCalendarReader.Init();			
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
        try {
			Weather.Init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        calendarEvents.setText(GoogleCalendarReader.getEvents());
        weather.setText("Temperature: " + Weather.getTemp().toString() + 
        		" Celsius degrees in " + Weather.getCity() + " city.");     
        time.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
        
        listPane.add(time,BorderLayout.EAST);
        listPane.add(weather,BorderLayout.EAST);
        listPane.add(calendarEvents,BorderLayout.EAST);
        listPane.setBackground(new Color(0,0,0,255));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground( Color.black );
        frame.setUndecorated(true);
        frame.setSize(300, 300);
        
        Container contentPane = frame.getContentPane();
        contentPane.add(listPane, BorderLayout.LINE_END);
        
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        @SuppressWarnings("serial")
		Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {      
            	System.exit(0);
            }
        };
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);     
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
	}
	
	
	public static void main(String[] args) {
		GUI();
	}

}
