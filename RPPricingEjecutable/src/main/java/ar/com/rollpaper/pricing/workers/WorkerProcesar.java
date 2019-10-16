package ar.com.rollpaper.pricing.workers;

import java.util.Map;

import ar.com.rollpaper.pricing.beans.ClienteLista;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;

public class WorkerProcesar extends WorkerBarraDeProgresoBase {

	private Map<String, ClienteLista> listaClienteLista;

	public WorkerProcesar(BarraDeProgreso pantalla) {
		super(pantalla, "Procesando...");
		listaClienteLista = GeneradorDePrecios.getAllClienteLista();
		setMax(listaClienteLista.size());
	}

	@Override
	protected void ejecutarWorker() throws Exception {
		for (Map.Entry<String, ClienteLista> entry : listaClienteLista.entrySet()) {

			if (cancelar) {
				break;
			} else {
				pantalla.setTitle("Procesando: " + entry.getValue().getCliente().getClieNombre());
				GeneradorDePrecios.impactarPrecios(entry.getValue().getCliente(), entry.getValue().getLista());
				publish(1);
			}
		}
	}

}
