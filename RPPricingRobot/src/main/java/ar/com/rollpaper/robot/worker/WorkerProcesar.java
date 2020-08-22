package ar.com.rollpaper.robot.worker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.swing.SwingWorker;

import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.beans.ClienteLista;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;
import ar.com.rollpaper.pricing.dao.ClienteFactorDAO;
import ar.com.rollpaper.robot.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.robot.LogBusiness;
import ar.com.rp.rpcutils.FechaManagerUtil;

public class WorkerProcesar extends SwingWorker<Void, Integer> implements InterEntradaWorker {

	private String nombreHilo;
	private boolean salir;
	private InterSalidaWorker inter;
	private Date setStart = null;
	private Boolean habilitar;
	private Date ultimaEjecutacion = null;

	public WorkerProcesar(InterSalidaWorker inter) {
		this.inter = inter;
	}

	public void salir() {
		salir = true;
	}

	protected synchronized void ejecutarWorker() throws Exception {
		while (!salir) {

			if (debeEntrar()) {
				ultimaEjecutacion = FechaManagerUtil.getDateTimeFromPC();
				inter.setEntroEjecutarWorker();

				String respuestas = impactarPrecios();

				inter.setResultadoEjecucion(FechaManagerUtil.getDateTimeFromPC(), respuestas);
				inter.setSalioEjecutarWorker();
			}

			wait(10000);
		}
	}

	private String impactarPrecios() throws Exception {

		Map<String, ClienteLista> listaClienteLista = GeneradorDePrecios.getAllClienteLista();

		int cantAProcesar = listaClienteLista.size();
		int procesado = 0;
		int fallado = 0;
		String lastError = "";

		for (Map.Entry<String, ClienteLista> entry : listaClienteLista.entrySet()) {

			if (salir) {
				break;
			} else {
				procesado++;
				try {

					// pantalla.setTitle("Procesando: " +
					// entry.getValue().getCliente().getClieNombre());

					ClienteFactor clienteFactor = ClienteFactorDAO
							.findById(entry.getValue().getCliente().getClieCliente());

					BigDecimal factor = null;
					if ((clienteFactor != null) && (clienteFactor.getPricFactor() != null)) {
						factor = new BigDecimal(clienteFactor.getPricFactor());
					}
					GeneradorDePrecios.impactarPrecios(entry.getValue().getCliente(), entry.getValue().getLista(),
							factor, ArchivoDePropiedadesBusiness.getidListaEspecial());
				} catch (Exception e) {
					LogBusiness.logearError("En robot", e);
					e.printStackTrace();
					fallado++;
					lastError = e.getMessage();
				}
			}
		}

		if (fallado == 0) {
			return String.format("TERMINO BIEN - Total de Cliente a procesados: %s", cantAProcesar);
		} else {
			return String.format(
					"FALLO - Algunos Clientes no se procesaron - Total de Cliente a Procesar: %s - Procesados: %s, Fallados: %s - ultimo Error: %s",
					cantAProcesar, procesado, fallado, lastError);
		}
	}

	private boolean debeEntrar() throws Exception {
		if (habilitar) {
			if (setStart != null) {
				Date hoy = FechaManagerUtil.getTime(FechaManagerUtil.getDateTimeFromPC());

				Date ultimaEjecutacionSinH = null;
				if (ultimaEjecutacion != null) {
					ultimaEjecutacionSinH = FechaManagerUtil.getTime(ultimaEjecutacion);
				}
//				System.out.println(ultimaEjecutacionSinH);
//				System.out.println(setStart);
//				System.out.println(hoy);

				if (FechaManagerUtil.isMenor(setStart, hoy)
						&& ((ultimaEjecutacion == null) || FechaManagerUtil.isMenor(ultimaEjecutacionSinH, setStart))) {
					System.out.println("Ejecuta");
					return true;
				}
			}

		}
		return false;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Thread.currentThread().setName("WORKER - ROBOT");
		nombreHilo = Thread.currentThread().getName();
		ejecutarWorker();
		return null;
	}

	@Override
	public void setStart(Date start) {
		this.setStart = start;
	}

	@Override
	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}

}
