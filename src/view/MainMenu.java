package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controler.Controler;

public class MainMenu extends JPanel{
	private JButton jugar;
	private JButton rules;
	private JButton top5;
	private JButton config;
	private JButton exit;
	private JLabel logo;
	
	public MainMenu(Dimension tamañoVentana) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoVentana);
		setPreferredSize(tamañoVentana);
		setBackground(Color.black);
		initComponents();
		paintComponents(getGraphics());
		setVisible(true);
	}
	private void initComponents() {
		initLogo();
		initRules();
		initTop5();
		initConfig();
		initJugar();
		initExit();
	}
	
	private void initLogo() {
		logo = new JLabel(new ImageIcon("assets/images/mainmenu/logo.png"));
		logo.setBorder(null);
		logo.setLocation(38, 20);
		logo.setSize(logo.getPreferredSize());
		add(logo);
	}
	private void initExit() {
		exit = new JButton(new ImageIcon("assets/images/mainmenu/exitBt1.png"));
		exit.setBorder(null);
		exit.setRolloverIcon(new ImageIcon("assets/images/mainmenu/exitBt2.png"));
		exit.setBorderPainted(false);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setLocation(382 , 404);
		exit.setSize(exit.getPreferredSize());
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(exit);
	}
	private void initConfig() {
		config = new JButton(new ImageIcon("assets/images/mainmenu/configBt2.png"));
		config.setBorder(null);
		config.setRolloverIcon(new ImageIcon("assets/images/mainmenu/configBt1.png"));
		config.setBorderPainted(false);
		config.setOpaque(false);
		config.setContentAreaFilled(false);
		config.setLocation(195, 371);
		config.setSize(config.getPreferredSize());
		
		config.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().abrirConfig();
				setVisible(false);
			}
		});
		add(config);
	}
	private void initTop5() {
		top5 = new JButton(new ImageIcon("assets/images/mainmenu/topBt1.png"));
		top5.setBorder(null);
		top5.setRolloverIcon(new ImageIcon("assets/images/mainmenu/topBt2.png"));
		top5.setBorderPainted(false);
		top5.setOpaque(false);
		top5.setContentAreaFilled(false);
		top5.setLocation(258, 290);
		top5.setSize(top5.getPreferredSize());
		top5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().abrirTopScores();
				setVisible(false);
			}
		});
		
		add(top5);
	}
	private void initRules() {
		rules = new JButton(new ImageIcon("assets/images/mainmenu/rulesBt1.png"));
		rules.setBorder(null);
		rules.setRolloverIcon(new ImageIcon("assets/images/mainmenu/rulesBt2.png"));
		rules.setBorderPainted(false);
		rules.setOpaque(false);
		rules.setContentAreaFilled(false);
		rules.setLocation(45, 290);
		rules.setSize(rules.getPreferredSize());
		
		rules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().abrirReglas();
				setVisible(false);
			}
		});
		add(rules);
	}
	private void initJugar() {
		jugar = new JButton(new ImageIcon("assets/images/mainmenu/JugarBt1.png"));
		jugar.setBorder(null);
		jugar.setRolloverIcon(new ImageIcon("assets/images/mainmenu/JugarBt2.png"));
		jugar.setBorderPainted(false);
		jugar.setOpaque(false);
		jugar.setContentAreaFilled(false);
		jugar.setLocation(111, 200);
		jugar.setSize(jugar.getPreferredSize());
		
		jugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().abrirJuego();
//				setVisible(false);
			}
		});
		add(jugar);
	}
	public static void exitForm (WindowEvent e) {
		System.exit(0);
	}
}
