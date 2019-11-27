package view.paneles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controler.Controler;
import view.View;

public class MainMenu extends JPanel{
	private JButton jugar;
	private JButton rules;
	private JButton top5;
	private JButton config;
	private JButton exit;
	private JLabel logo;
	
	public MainMenu(Dimension tamañoFrame) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoFrame);
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
		logo = new JLabel(View.getImagenes().get("logo"));
		logo.setBorder(null);
		logo.setLocation(38, 20);
		logo.setSize(logo.getPreferredSize());
		add(logo);
	}
	private void initExit() {
		exit = new JButton(View.getImagenes().get("exitBt1"));
		exit.setBorder(null);
		exit.setRolloverIcon(View.getImagenes().get("exitBt2"));
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
		config = new JButton(View.getImagenes().get("configBt2"));
		config.setBorder(null);
		config.setRolloverIcon(View.getImagenes().get("configBt1"));
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
		top5 = new JButton(View.getImagenes().get("topBt1"));
		top5.setBorder(null);
		top5.setRolloverIcon(View.getImagenes().get("topBt2"));
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
		rules = new JButton(View.getImagenes().get("rulesBt1"));
		rules.setBorder(null);
		rules.setRolloverIcon(View.getImagenes().get("rulesBt2"));
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
		jugar = new JButton(View.getImagenes().get("JugarBt1"));
		jugar.setBorder(null);
		jugar.setRolloverIcon(View.getImagenes().get("JugarBt2"));
		jugar.setBorderPainted(false);
		jugar.setOpaque(false);
		jugar.setContentAreaFilled(false);
		jugar.setLocation(111, 200);
		jugar.setSize(jugar.getPreferredSize());
		
		jugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().abrirJuego();
			}
		});
		add(jugar);
	}
	public static void exitForm (WindowEvent e) {
		System.exit(0);
	}
}
