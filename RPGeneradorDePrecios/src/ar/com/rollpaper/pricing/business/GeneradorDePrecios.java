package ar.com.rollpaper.pricing.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentArpc;
import ar.com.rollpaper.pricing.beans.VentArpcId;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentArpvId;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.beans.generarListaDePreciosResponse;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentArpcDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.data.HibernateUtil;

public class GeneradorDePrecios {

	public static void generarPrecios() {

		// primero busco la lista de precios por familia

		List<VentLipv> Listas = BuscarListasPrecios();
		for (Object lista : Listas) {

			generarPreciosPorFamilia(lista);
			generarPreciosEspeciales(lista);

		}
	}

	/**
	 * Genera la lista de precios para el cliente pasado por parametro
	 * 
	 * 
	 * @author pmv1283
	 * @param recibe
	 *            como parametro un cliente y una lista de precios
	 * @return
	 * 
	 **/
	public static generarListaDePreciosResponse generarListaPreciosPorClienteLista(CcobClie cliente, VentLipv lista) {

		generarListaDePreciosResponse response = new generarListaDePreciosResponse();
		// Listo los precios para la combinacion cliente lista

		List<VentArpv> listaPrecios = VentArpvDAO.findByListaID(lista.getLipvListaPrecvta());

		// Agrego los productos que estan fuera de la lista pero customizados
		// busco todos los productos que estan en la precios especiales(osea que tienen
		// un precio especifico) y no en la lista
		List<PreciosEspeciales> listaPreciosEspecialesNoEnLista = PreciosEspecialesDAO.getPreciosByCliente(cliente,
				lista, new Date());

		// listo los precios originales
		System.out.println("Precios Originales " + lista.getLipvNombre() + " Cliente : " + cliente.getClieNombre());

		// lista de precios finales a insertar
		LinkedHashMap<VentArpv, BigDecimal> listaPreciosFinal = new LinkedHashMap<>();

		for (VentArpv v : listaPrecios) {
			System.out.println("Articulo: " + v.getId().getArpvArticulo() + " Precio ->" + v.getArpvPrecioVta());
		}

		for (PreciosEspeciales pe : listaPreciosEspecialesNoEnLista) {
			System.out.println("Articulo: " + pe.getPricArticulo() + " Precio ->" + pe.getPricPrecio());
		}

		// TODO ver aca la fecha del dia y como manejarla
		Date hoy = new Date();
		// aplico los descuentos por familia
		// busco las familias que tienen un descuento configurado en la tabla de pricing
		// para esta relacion Cliente-Lista
		// TODO : falta filtrar correctamente por fecha!!!!!
		List<DescuentoXFamilias> listaDescuentosXFamiliaVigentes = DescuentoXFamiliasDAO
				.getByClienteListaVigente(cliente, lista, hoy);

		List<PreciosEspeciales> listaPreciosEspeciales = PreciosEspecialesDAO.getByClienteLista(cliente, lista, hoy);
		// busco los esclavos de este cliente
		List<MaestroEsclavo> listaMaestroEsclavo = MaestroEsclavoDAO.getListaEsclavosByCliente(cliente);

		// por cada producto que pertenezca a una familia debo aplicar el descuento
		for (VentArpv articulo : listaPrecios) {

			// inicio calculo de descuentos por familia para cada articulo
			System.out.println("<<<<<<<<<<<<<<inicio Descuentos por familia>>>>>>>>>>>>>");
			BigDecimal precio = calcularDescuentosXFamilia(listaDescuentosXFamiliaVigentes, articulo);
			System.out.println("<<<<<<<<<<<<<<fin Descuentos por familia>>>>>>>>>>>>>");
			System.out.println("<<<<<<<<<<<<<<Inicio Descuentos precio Especial>>>>>>>>>>>>>");
			// TODO : solo desde el precio de lista general y siempre va ultimo el precio
			// especifico
			// no entrar si no tiene descuento especial. para no alterar el precio calculado
			// por familia
			if (getPrecioEspecial(listaPreciosEspeciales, articulo).size() > 0) {
				precio = calcularPreciosEspeciales(listaPreciosEspeciales, articulo, articulo.getArpvPrecioVta());
			} else {
				System.out.println("<<<<<<<<<<<<<<<<No Posee Descuentos precio especial>>>>>>>>>>>>>>");
			}
			System.out.println("<<<<<<<<<<<<<<<<fin Descuentos precio especial>>>>>>>>>>>>>>");

			listaPreciosFinal.put(articulo, precio);
			// aplico los precios a los esclavos.
			// para cada esclavo le aplico los precios de este producto en la lista
			System.out.println("<<<<<<<<<<<<<<<<inicio Descuentos esclavos>>>>>>>>>>>>>>");
			for (MaestroEsclavo maestroEsclavo : listaMaestroEsclavo) {

				System.out.println("Maestro:" + maestroEsclavo.getPricMaestroCliente() + " Esclavo:"
						+ maestroEsclavo.getPricEsclavoCliente());

			}
			System.out.println("<<<<<<<<<<<<<<<<fin Descuentos esclavos>>>>>>>>>>>>>>");

		}

		System.out.println("<<<<<Lista de Precios final para " + lista.getLipvNombre() + ">>>>>");
		System.out.println("A Aplicar a los siguientes clientes:");
		System.out.println("Maestro :" + cliente.getClieCliente() + "-" + cliente.getClieNombre());
		for (

		MaestroEsclavo maestroEsclavo : listaMaestroEsclavo) {
			System.out.println("Esclavo " + maestroEsclavo.getPricEsclavoCliente());

		}

		// agrego el precio especial a la listapreciosfinal
		for (PreciosEspeciales pe : listaPreciosEspecialesNoEnLista) {
			VentArpv k = new VentArpv();
			// creo el objeto con la lista que corresponde para poder insertarlo en la tabla
			// lista.getLipvListaPrecvta()
			k.setId(new VentArpvId(pe.getPricArticulo(), lista.getLipvListaPrecvta()));
			SistMone m = SistMoneDAO.getByDesc(pe.getPricMoneda());
			k.setSistMoneByArpvMoneda(m);
			listaPreciosFinal.put(k, pe.getPricPrecio());
		}

		for (Entry<VentArpv, BigDecimal> art : listaPreciosFinal.entrySet()) {

			String nombreArticulo = StocArtsDAO.getArticuloByID(art.getKey().getId().getArpvArticulo()).getArtsNombre();
			int Lista = art.getKey().getId().getArpvListaPrecvta();
			System.out.println("Lista ->" + Lista + " Articulo ->" + nombreArticulo + "---->$" + art.getValue());
		}

		System.out.println("<<<<<fin proceso>>>>>");

		response.setListaDeEsclavos(listaMaestroEsclavo);
		response.setListaDePrecios(listaPreciosFinal);
		return response;

	}

	/**
	 * @param listaPreciosEspeciales
	 * @param articulo
	 * @param precio
	 */
	private static BigDecimal calcularPreciosEspeciales(List<PreciosEspeciales> listaPreciosEspeciales,
			VentArpv articulo, BigDecimal precio) {
		// inicio del proceso que calcula el descuento especial para el articulo

		List<PreciosEspeciales> descuentoEspecial = getPrecioEspecial(listaPreciosEspeciales, articulo);

		BigDecimal precioFinal = BigDecimal.ZERO;
		BigDecimal precio_original = precio;// articulo.getArpvPrecioVta();
		precioFinal = precio_original;

		if (descuentoEspecial.size() > 0) {
			BigDecimal descuento1 = descuentoEspecial.get(0).getPricDescuento1();
			BigDecimal descuento2 = descuentoEspecial.get(0).getPricDescuento2();
			BigDecimal precioEspecifico = descuentoEspecial.get(0).getPricPrecio();
			if (descuento1 != null) {
				precioFinal = (precio_original
						.multiply(BigDecimal.ONE.subtract(descuento1.divide(BigDecimal.valueOf(100.00)))));
			}
			if (descuento2 != null) {
				precioFinal = precioFinal
						.multiply(BigDecimal.ONE.subtract(descuento2.divide(BigDecimal.valueOf(100.00))));
			}
			if (precioEspecifico != null) {
				precioFinal = precioEspecifico;
			}
		}

		String nombreArticulo = StocArtsDAO.getArticuloByID(articulo.getId().getArpvArticulo()).getArtsNombre();
		System.out.println("articulo " + nombreArticulo + " Precio Original " + precio_original
				+ " Precio con descuento especial:" + precioFinal);
		return precioFinal;
	}

	/**
	 * @param listaPreciosEspeciales
	 * @param articulo
	 * @return
	 */
	private static List<PreciosEspeciales> getPrecioEspecial(List<PreciosEspeciales> listaPreciosEspeciales,
			VentArpv articulo) {
		// busco en los la tabla de descuentos si tiene algun registro para ese articulo
		List<PreciosEspeciales> descuentoEspecial = listaPreciosEspeciales.stream()
				.filter(item -> item.getPricArticulo() == articulo.getId().getArpvArticulo())
				.collect(Collectors.toList());
		return descuentoEspecial;
	}

	/**
	 * @param listaDescuentosXFamiliaVigentes
	 * @param v
	 */

	private static BigDecimal calcularDescuentosXFamilia(List<DescuentoXFamilias> listaDescuentosXFamiliaVigentes,
			VentArpv v) {
		// obtengo la familia de cada articulo (ArtsClasif1)
		StocArts articulo = StocArtsDAO.getArticuloByID(v.getId().getArpvArticulo());
		System.out.println("Articulo 1 :  " + articulo.getArtsArticuloEmp() + "-" + v.getId().getArpvArticulo()
				+ " familia ->" + articulo.getArtsClasif1());

		// busco en la tabla de descuentos
		List<DescuentoXFamilias> DescuentoxFamiliaAAplicar = listaDescuentosXFamiliaVigentes.stream()
				.filter(item -> item.getPricCa01Clasif1().equals(articulo.getArtsClasif1()))
				.collect(Collectors.toList());

		// si hay algo en la DescuentoxFamiliaAAplicar debo aplicarse ese descuento al
		// producto

		BigDecimal precioFinal = BigDecimal.ZERO;
		BigDecimal precio_original = v.getArpvPrecioVta();
		precioFinal = precio_original;

		if (DescuentoxFamiliaAAplicar.size() > 0) {

			BigDecimal descuento1 = DescuentoxFamiliaAAplicar.get(0).getPricFamiliaDescuento1();
			BigDecimal descuento2 = DescuentoxFamiliaAAplicar.get(0).getPricFamiliaDescuento2();
			precioFinal = (precio_original
					.multiply(BigDecimal.ONE.subtract(descuento1.divide(BigDecimal.valueOf(100.00)))));
			if (descuento2 != null) {
				precioFinal = precioFinal
						.multiply(BigDecimal.ONE.subtract(descuento2.divide(BigDecimal.valueOf(100.00))));
			}

		}

		System.out.println("articulo " + articulo.getArtsNombre() + " Precio Original " + precio_original
				+ " Precio con descuento x familia:" + precioFinal);
		return precioFinal;
	}

	/**
	 * Busca todas las listas de Precios Activas y recopila en el objeto lista todos
	 * los datos necesarios para el calculo
	 * 
	 * @param sin
	 *            parametros de entrada
	 * @return an list of ListaPrecios
	 */
	private static List<VentLipv> BuscarListasPrecios() {

		return VentLipvDAO.getAllLists();
	}

	private static void generarPreciosEspeciales(Object lista) {
		System.out.println("Generando precios especiales");

	}

	private static List<Object> generarPreciosPorFamilia(Object lista) {

		System.out.println("Generando precios por familia");
		return null;

	}

	private void impactarPrecios() {
	}

	public static void impactarPrecios(CcobClie clienteCargado, VentLipv listaCargada) {
		generarListaDePreciosResponse response = GeneradorDePrecios.generarListaPreciosPorClienteLista(clienteCargado,
				listaCargada);

		CcobClie c = clienteCargado;

		// borro los registros de la ventarpc
		eliminarListaCustomizada(c, listaCargada);

		List<VentArpc> listaAInsertar = new ArrayList<>();
		for (Entry<VentArpv, BigDecimal> entry : response.getListaDePrecios().entrySet()) {
			VentArpv articulo = entry.getKey();
			BigDecimal precio = entry.getValue();
			// creo el registro a insertar
			VentArpcId id = new VentArpcId(articulo.getId().getArpvArticulo(), articulo.getId().getArpvListaPrecvta(),
					c.getClieCliente());
			VentArpc registro = new VentArpc(id, c, precio, articulo.getSistMoneByArpvMoneda().getMoneMoneda(), precio,
					new Date());
			listaAInsertar.add(registro);

		}

		// genero los registros para las familias
		for (MaestroEsclavo slave : response.getListaDeEsclavos()) {
			// CcobClie slaveClient = CcobClieDAO.findById(id)
			eliminarListaCustomizada(CcobClieDAO.findById(slave.getPricEsclavoCliente()), listaCargada);
			for (Entry<VentArpv, BigDecimal> entry : response.getListaDePrecios().entrySet()) {
				VentArpv articulo = entry.getKey();
				BigDecimal precio = entry.getValue();
				// creo el registro a insertar
				VentArpcId id = new VentArpcId(articulo.getId().getArpvArticulo(),
						articulo.getId().getArpvListaPrecvta(), slave.getPricEsclavoCliente());
				VentArpc registro = new VentArpc(id, c, precio, articulo.getSistMoneByArpvMoneda().getMoneMoneda(),
						precio, new Date());
				listaAInsertar.add(registro);

			}
		}

		try {
			for (VentArpc ventArpc : listaAInsertar) {
				HibernateGeneric.persist(ventArpc);
			}
			HibernateUtil.getSession().clear();
		} catch (Exception e) {
			// .showMessageDialog("ERROR en la aplicacion de precios", "Aplicacion de
			// Precios",
			// JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			throw (e);
		}

	}

	/**
	 * @param cliente
	 * @param lista
	 */
	private static void eliminarListaCustomizada(CcobClie cliente, VentLipv lista) {
		List<VentArpc> listaABorrar = VentArpcDAO.findByListaByClient(cliente, lista);

		for (VentArpc ventArpc : listaABorrar) {
			HibernateGeneric.remove(ventArpc);
		}
		HibernateUtil.getSession().clear();
	}
}
