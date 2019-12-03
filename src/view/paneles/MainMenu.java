package view.paneles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Constantes;
import controler.Controler;
import view.View;

public class MainMenu extends JPanel{
	private JButton jugar;
	private JButton rules;
	private JButton top5;
	private JButton config;
	private JButton exit;
	private JLabel logo;
	
	public MainMenu() {
		setLayout(null);
		setLocation(0,0);
		setSize(Constantes.TAMAÑOFRAME);
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
		logo.setLocation(Constantes.ANCHOFRAME/2 - 172, 20);
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
		exit.setLocation(Constantes.ANCHOFRAME - exit.getPreferredSize().width , Constantes.ALTURAFRAME - exit.getPreferredSize().height);
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
		config.setLocation(550, 330);
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
		top5.setLocation(30, 380);
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
		rules.setLocation(30, 290);
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
		jugar.setLocation(Constantes.ANCHOFRAME/2 - 83, 330);
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
