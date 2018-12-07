package ar.com.rp.ui.componentes;

import java.awt.Color;

public class RowColorDTO {

	private Color[] colorFondo;
	private Color[] colorLetra;

	public RowColorDTO() {
	}

	public RowColorDTO(Color[] colorFondo, Color[] colorLetra) {
		super();
		this.colorFondo = colorFondo;
		this.colorLetra = colorLetra;
	}

	public Color[] getColorFondo() {
		return colorFondo;
	}

	public void setColorFondo(Color[] colorFondo) {
		this.colorFondo = colorFondo;
	}

	public Color[] getColorLetra() {
		return colorLetra;
	}

	public void setColorLetra(Color[] colorLetra) {
		this.colorLetra = colorLetra;
	}

}
