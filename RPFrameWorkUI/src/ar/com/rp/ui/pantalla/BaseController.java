package ar.com.rp.ui.pantalla;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import ar.com.rp.ui.common.FrameworkCommon;
import ar.com.rp.ui.interfaces.PermisosInterface;

public abstract class BaseController implements ActionListener {

	private boolean setFirstFocusAutomatic = true;

	protected abstract String getNombrePantalla();

	protected abstract Container getContenedor();

	public abstract void ejecutarAccion(String accion);

	protected abstract void cerrarVentana();

	private PermisosInterface permisos = null;

	protected PermisosInterface getPermisos() {
		return permisos;
	}

	public BaseController(PermisosInterface permisos) throws Exception {
		this.permisos = permisos;
	}

	public void cargarPermisos() throws Exception {
		if (permisos != null) {
			permisos.cargarPermisos(getContenedor(), getNombrePantalla());
		}
	}

	public boolean presionoTecla(KeyEvent ke) {
		return FrameworkCommon.buscarKey(getContenedor(), ke);
	}
	
	public boolean liberoTecla(KeyEvent ke) {
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String msg = ae.getActionCommand();
		if (msg.equals("CERRAR_VENTANA_FRAMEWORK")) {
			cerrarVentana();
		} else {
			ejecutarAccion(msg);
		}
	}

	protected void requestFocusFirstComponent() {
		if (setFirstFocusAutomatic) {
			FrameworkCommon.requestFocusFirstComponent(getContenedor());
		}
	}

	public boolean isSetFirstFocusAutomatic() {
		return setFirstFocusAutomatic;
	}

	public void setSetFirstFocusAutomatic(boolean setFirstFocusAutomatic) {
		this.setFirstFocusAutomatic = setFirstFocusAutomatic;
	}
}
