package ar.com.rollpaper.pricing.business;

import java.util.Date;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rp.rpcutils.FechaManagerUtil;

/*-
 * Tengo 4 casos de solapamiento
  * 1) A ---------------------
	 *            B--------------------
	 * 1B)A    -------
	*     B          --------
	 * 
	 * 
	 * 2)         A ----------------
	 *    B --------------
	 *  
	 *2B)         A ------
	 *    B ---------
	 * 
	 * 
	 * 3)	      A -------------
	 *    B -----------------------------
	 *    
	 *    
	 * 4) A ----------------------
	*             B ------
	*
	*  5) A ----
	*     B ----
	*
	* 6) A          ----------
	*    B   -----------------
	* 
	*    
	* 7) A    -------
	*    B    ------------------   
	* 
	 */
public class OverloadManager {

	public static boolean isCase1(DescuentoXFamilias dateA, DescuentoXFamilias dateB) {
		if ((dateA.getPricFamiliaId() != dateB.getPricFamiliaId())
				&& FechaManagerUtil.isMenor(dateA.getPricFamiliaFechaDesde(), dateB.getPricFamiliaFechaDesde())
				&& isEnElMedio(dateA.getPricFamiliaFechaHasta(), dateB)) {
			return true;
		}

		return false;
	}

	public static boolean isCase2(DescuentoXFamilias dateA, DescuentoXFamilias dateB) {
		if ((dateA.getPricFamiliaId() != dateB.getPricFamiliaId())
				&& FechaManagerUtil.isMenor(dateB.getPricFamiliaFechaDesde(), dateA.getPricFamiliaFechaDesde())
				&& isEnElMedio(dateB.getPricFamiliaFechaHasta(), dateA)) {
			return true;
		}

		return false;
	}

	public static boolean isCase3(DescuentoXFamilias dateA, DescuentoXFamilias dateB) {
		if ((dateA.getPricFamiliaId() != dateB.getPricFamiliaId())
				&& isEnElMedio(dateA.getPricFamiliaFechaDesde(), dateB)
				&& isEnElMedio(dateA.getPricFamiliaFechaHasta(), dateB)) {
			return true;
		}

		return false;
	}

	public static boolean isCase4(DescuentoXFamilias dateA, DescuentoXFamilias dateB) {
		if ((dateA.getPricFamiliaId() != dateB.getPricFamiliaId())
				&& isEnElMedio(dateB.getPricFamiliaFechaDesde(), dateA)
				&& isEnElMedio(dateB.getPricFamiliaFechaHasta(), dateA)) {
			return true;
		}

		return false;
	}

	private static boolean isEnElMedio(Date dateMedio, DescuentoXFamilias rango) {
		Boolean mayor = FechaManagerUtil.isMayor(dateMedio, rango.getPricFamiliaFechaDesde())
				|| FechaManagerUtil.fechaIguales(dateMedio, rango.getPricFamiliaFechaDesde());

		Boolean menor = FechaManagerUtil.isMenor(dateMedio, rango.getPricFamiliaFechaHasta())
				|| FechaManagerUtil.fechaIguales(dateMedio, rango.getPricFamiliaFechaHasta());

		if (mayor && menor) {
			return true;
		}

		return false;
	}

	public static boolean isCase5(DescuentoXFamilias regTablaFamilia, DescuentoXFamilias registroNuevo) {
		if ((regTablaFamilia.getPricFamiliaId() != registroNuevo.getPricFamiliaId())
				&& FechaManagerUtil.fechaIguales(regTablaFamilia.getPricFamiliaFechaDesde(),
						registroNuevo.getPricFamiliaFechaDesde())
				&& FechaManagerUtil.fechaIguales(regTablaFamilia.getPricFamiliaFechaHasta(),
						registroNuevo.getPricFamiliaFechaHasta())) {
			return true;
		}

		return false;
	}

	public static boolean isCase6(DescuentoXFamilias regTablaFamilia, DescuentoXFamilias registroNuevo) {
		if ((regTablaFamilia.getPricFamiliaId() != registroNuevo.getPricFamiliaId())
				&& FechaManagerUtil.fechaIguales(regTablaFamilia.getPricFamiliaFechaHasta(),
						registroNuevo.getPricFamiliaFechaHasta())
				&& FechaManagerUtil.isMayor(regTablaFamilia.getPricFamiliaFechaDesde(),
						registroNuevo.getPricFamiliaFechaDesde())) {
			return true;
		}

		return false;
	}

	public static boolean isCase7(DescuentoXFamilias regTablaFamilia, DescuentoXFamilias registroNuevo) {
		if ((regTablaFamilia.getPricFamiliaId() != registroNuevo.getPricFamiliaId())
				&& FechaManagerUtil.fechaIguales(regTablaFamilia.getPricFamiliaFechaDesde(),
						registroNuevo.getPricFamiliaFechaDesde())
				&& FechaManagerUtil.isMenor(regTablaFamilia.getPricFamiliaFechaHasta(),
						registroNuevo.getPricFamiliaFechaHasta())) {
			return true;
		}

		return false;
	}
}
