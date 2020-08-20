package ar.com.rollpaper.robot.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.rollpaper.robot.ArchivoDePropiedadesBusiness;
import ar.com.rp.rpcutils.FechaManagerUtil;

public class ConsolaModel {

	private static final String ESPERANDO = "Esperando para ejecutar";
	private static final String NO_HABILITADO = "Deshabilitada la ejecucion";
	private static final String EJECUTANDO_WORKER = "Actualizando Precios";
	private static final String DATA_REGISTRO = "dataLog";
	private boolean habilitado = true;
	private Date start;
	private boolean ejecutandoWorker;
	private List<RegistroData> listaRegistroData;

	public ConsolaModel() throws Exception {
		try {

			String hora = ArchivoDePropiedadesBusiness.getHora();
			String minuto = ArchivoDePropiedadesBusiness.getMinuto();
			if (hora.equals("") || minuto.equals("")) {
				hora = "22";
				minuto = "00";
				setHora(hora, minuto);
			}

			start = FechaManagerUtil.String2DateGenerica(hora + ":" + minuto, "HH:mm");

		} catch (Exception e) {
			e.printStackTrace();
			start = null;
			habilitado = false;
		}
		listaRegistroData = deseralizar();
		if (listaRegistroData == null) {
			listaRegistroData = new ArrayList<RegistroData>();
		}

	}

	public String getStatus() {
		if (!habilitado) {
			return NO_HABILITADO;
		} else {
			if (ejecutandoWorker) {
				return EJECUTANDO_WORKER;
			} else {
				return ESPERANDO;
			}
		}
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public void setHora(String hora, String minuto) throws Exception {
		ArchivoDePropiedadesBusiness.setHora(hora);
		ArchivoDePropiedadesBusiness.setMinuto(minuto);

		start = FechaManagerUtil.String2DateGenerica(hora + ":" + minuto, "HH:mm");
	}

	public Date getStart() throws Exception {
		if (start != null) {
			return FechaManagerUtil.getTime(start);
		}

		return null;
	}

	public void setEjecutandoWorker(boolean ejecutandoWorker) {
		this.ejecutandoWorker = ejecutandoWorker;

	}

	public void setResultadoEjecucion(Date fecha, String resultado) throws IOException {
		listaRegistroData.add(0, new RegistroData(fecha, resultado));
		if (listaRegistroData.size() > 300) {
			listaRegistroData.remove(listaRegistroData.size() - 1);
		}
		serializar(listaRegistroData);
	}

	public List<RegistroData> getRegistrosData() throws Exception {
		return listaRegistroData;
	}

	private void serializar(List<RegistroData> listaRegistroData2) throws IOException {
		FileOutputStream fos = new FileOutputStream(DATA_REGISTRO);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listaRegistroData2);
		oos.close();
		fos.close();
	}

	@SuppressWarnings("unchecked")
	private List<RegistroData> deseralizar() {
		List<RegistroData> retorno = null;
		try {
			FileInputStream fis = new FileInputStream(DATA_REGISTRO);
			ObjectInputStream ois = new ObjectInputStream(fis);
			retorno = (ArrayList<RegistroData>) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception ioe) {
			return null;
		}
		return retorno;
	}

	public boolean getHabilitado() {
		return habilitado;
	}

}
