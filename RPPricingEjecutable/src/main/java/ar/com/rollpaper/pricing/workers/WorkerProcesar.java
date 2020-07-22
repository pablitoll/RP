package ar.com.rollpaper.pricing.workers;

import java.math.BigDecimal;
import java.util.Map;

import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.beans.ClienteLista;
import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;
import ar.com.rollpaper.pricing.dao.ClienteFactorDAO;

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

				ClienteFactor clienteFactor = ClienteFactorDAO.findById(entry.getValue().getCliente().getClieCliente());

				BigDecimal factor = null;
				if ((clienteFactor != null) && (clienteFactor.getPricFactor() != null)) {
					factor = new BigDecimal(clienteFactor.getPricFactor());
				}
				GeneradorDePrecios.impactarPrecios(entry.getValue().getCliente(), entry.getValue().getLista(), factor,
						ArchivoDePropiedadesBusiness.getidListaEspecial());
				publish(1);
			}
		}
	}

}
