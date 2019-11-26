package view.entidades;

import javax.swing.JLabel;

import controler.Constantes;
import model.entidades.Felix;
import model.entidades.Ralph;
import view.View;

public class JRalph extends JLabel{
	public JRalph (){
			setLayout(null);
			setBorder(null);
//			setOpaque(true);
			setSize(Constantes.RALPHANCHO, Constantes.RALPHALTO);
			setIcon(View.getImagenes().get("ralphParado"));
			int x = Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXVISUAL;
			int y = Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYVISUAL;
			setLocation(x, y);
		}

	public void actualizar() {
		setIcon(View.getImagenes().get(Ralph.getRalph().getNombreImagen()));
		int x = Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXVISUAL;
		int y = Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYVISUAL;
		setLocation(x, y);
	}
}
