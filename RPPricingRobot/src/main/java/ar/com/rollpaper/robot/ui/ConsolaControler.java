package ar.com.rollpaper.robot.ui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.robot.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.robot.ManejoDeError;
import ar.com.rollpaper.robot.worker.InterEntradaWorker;
import ar.com.rollpaper.robot.worker.InterSalidaWorker;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;

public class ConsolaControler implements InterSalidaWorker {

	private ConsolaView view;
	private ConsolaModel model;
	private InterEntradaWorker interEntradaWorker;

	public ConsolaControler(ConsolaView view, ConsolaModel model) throws Exception {
		super();
		this.view = view;
		this.model = model;

		view.txtHs.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					String hora[] = view.txtHs.getText().split(":");
					if (!validarHora(hora)) {
						Dialog.showMessageDialog("El formato debe ser HH:MM", "Formato invalido",
								WebOptionPane.ERROR_MESSAGE);
						view.txtHs.requestFocus();
					} else {
						model.setHora(hora[0], hora[1]);
						actualizarParametrosWS();
					}

				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error al guardar hora");
				}
			}
		});

		view.chkActivado.setSelected(model.getHabilitado());
		view.chkActivado.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				try {
					if (view.chkActivado.isSelected() && view.txtHs.getText().equals("")) {
						Dialog.showMessageDialog("Antes de Habilitar debe ingresar una hora de comienzo valida");
					} else {
						habilitar(view.chkActivado.isSelected());
					}
				} catch (Exception e) {
					ManejoDeError.showError(e, "Erro");
				}
			}
		});

		Date start = model.getStart();
		if (start == null) {
			view.txtHs.setText("");
		} else {
			view.txtHs.setText(FechaManagerUtil.Date2StringGenerica(start, "HH:mm"));
		}

		actualizarDatos();
		try {
			view.tableResultado.clear();
			for (RegistroData reg : model.getRegistrosData()) {
				view.tableResultado
						.addRow(new Object[] { FechaManagerUtil.DateTime2String(reg.getFecha()), reg.getDetalle() });
			}
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al cargar datos de grilla");
		}

		view.lblDB.setText(ArchivoDePropiedadesBusiness.getConecctionString());
	}

	public void setInterEntradaWorker(InterEntradaWorker interEntradaWorker) throws Exception {
		this.interEntradaWorker = interEntradaWorker;
		actualizarParametrosWS();
	}

	private void actualizarParametrosWS() throws Exception {
		if (interEntradaWorker != null) {
			interEntradaWorker.setHabilitar(model.isHabilitado());
			interEntradaWorker.setStart(model.getStart());
		}

	}

	protected void habilitar(boolean isSelected) throws Exception {
		model.setHabilitado(isSelected);
		actualizarParametrosWS();
		actualizarDatos();
	}

	protected boolean validarHora(String[] hora) {
		if (hora.length == 2) {
			if (CommonUtils.isOnlyNumeric(hora[0]) && CommonUtils.isOnlyNumeric(hora[1])) {
				if ((Integer.valueOf(hora[0]) <= 24) && (Integer.valueOf(hora[1]) <= 59)) {
					return true;
				}
			}
		}
		return false;
	}

	public void show() {
		view.setVisible(true);
	}

	private void actualizarDatos() {
		view.lblStatus.setText(CommonUtils.SetHTMLColor(model.getStatus(), "blue"));
	}

	@Override
	public void setEntroEjecutarWorker() {
		model.setEjecutandoWorker(true);
		actualizarDatos();
	}

	@Override
	public void setSalioEjecutarWorker() {
		model.setEjecutandoWorker(false);
		actualizarDatos();
	}

	@Override
	public void setResultadoEjecucion(Date fecha, String resultado) {
		try {
			model.setResultadoEjecucion(fecha, resultado);
			view.tableResultado.addFirst(new Object[] { FechaManagerUtil.DateTime2String(fecha), resultado });

		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al guardar resultado");
		}
	}

}
