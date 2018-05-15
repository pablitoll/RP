package ar.com.rp.rpcutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class FechaManagerUtil {

	public static String FORMATO_HORA = "HH:mm:ss";
	public static String FORMATO_FECHA_COMPLETA = "dd/MM/yyyy HH:mm:ss";
	public static String FORMATO_FECHA = "dd/MM/yyyy";

	public static String Date2StringGenerica(Date fecha, String formato) {
		String date = "";
		try {
			DateFormat df = new SimpleDateFormat(formato);
			date = df.format(fecha);
		} catch (Exception ex) {
			// do something for invalid dateformat
		}

		return date;
	}

	public static Date String2Date(String fecha) {
		return String2DateGenerica(fecha, FORMATO_FECHA);
	}

	public static Date String2DateTime(String fecha) {
		return String2DateGenerica(fecha, FORMATO_FECHA_COMPLETA);
	}

	public static String Time2String(Date fecha) {
		return Date2StringGenerica(fecha, FORMATO_HORA);
	}

	public static String DateTime2String(Date fecha) {
		return Date2StringGenerica(fecha, FORMATO_FECHA_COMPLETA);
	}

	public static String Date2String(Date fecha) {
		return Date2StringGenerica(fecha, FORMATO_FECHA);
	}

	public static Date addDays(Date fecha, int dias) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.DATE, dias); // number of days to add
		return c.getTime();
	}

	public static Date add(Date fecha, int cantidad, int parte) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(parte, cantidad);
		return c.getTime();
	}

	public static XMLGregorianCalendar Date2XMLGregorianCalendar(Date fecha) throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(fecha);
		XMLGregorianCalendar retorno = null;
		try {
			retorno = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}

	public static Date XMLGregorianCalendar2Date(XMLGregorianCalendar fecha) {
		if (fecha != null) {
			XMLGregorianCalendar serverDate = fecha;
			return serverDate.toGregorianCalendar().getTime();
		} else {
			return null;
		}
	}

	public static Date getDateTimeFromPC() {
		// La uso solo cuando esta la posibilidad de no tener la hora local o en el
		// server
		// Se da esto cuando esta iniciando el sistema.
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		return fecha;
	}

	// Formatea el texto a una fecha valida con separadores de / y en formato
	// dd/mm/yyyy
	public static String formatearFecha(String texto) {
		String fechaAux = texto.replace("/", "").replace("-", "").trim();
		if ((fechaAux.length() >= 4) && (CommonUtils.isOnlyNumeric(fechaAux))) {

			String siglo = CommonUtils.strLeft(CommonUtils.strRigth(fechaAux, 4), 2);

			// Por default ano es de 2
			String anio = CommonUtils.strRigth(fechaAux, 2);
			String resto = CommonUtils.strLeft(fechaAux, fechaAux.length() - 2);
			if (siglo.equals("19") || siglo.equals("20")) {
				// Ano de 4 digitos
				anio = CommonUtils.strRigth(fechaAux, 4);
				resto = CommonUtils.strLeft(fechaAux, fechaAux.length() - 4);
			} else {
				if (Integer.valueOf(anio) < 50) {
					anio = "20" + anio;
				} else {
					anio = "19" + anio;
				}
			}

			// Si el length es igual a 2 es del defualt
			String dia = CommonUtils.strLeft(fechaAux, 1);
			String mes = CommonUtils.strRigth(fechaAux, 1);

			// igual a 4
			if (resto.length() == 4) {
				dia = CommonUtils.strLeft(resto, 2);
				mes = CommonUtils.strRigth(resto, 2);
			}

			// igual a 3
			if (resto.length() == 3) {
				mes = CommonUtils.strRigth(resto, 2);
				dia = CommonUtils.strLeft(resto, 1);

				if ((Integer.valueOf(mes) > 12) || (Integer.valueOf(dia) == 0)) {
					mes = CommonUtils.strRigth(resto, 1);
					dia = CommonUtils.strLeft(resto, 2);
				}
			}

			return CommonUtils.strRigth("0" + dia, 2) + "/" + CommonUtils.strRigth("0" + mes, 2) + "/" + anio;

		} else {
			return texto;
		}

	}

	public static Date String2DateGenerica(String fecha, String formato) {
		Date date = null;
		try {
			DateFormat df = new SimpleDateFormat(formato);
			df.setLenient(false);
			df.parse(fecha);
			date = df.parse(fecha);
		} catch (Exception ex) {
			// do something for invalid dateformat
		}

		return date;
	}

	public static boolean formatoFechaValido(String valor, String formato) {
		boolean retorno = false;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
		try {
			formatter.parse(valor);
			retorno = true;
		} catch (Exception e) {
			// Si falla no es valido
		}

		return retorno;
	}

	public static int getDay(Date fecha) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date fecha) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.MONTH);
	}

	public static int getYear(Date fecha) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.YEAR);
	}

	public static Date getDate(Date fecha) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static int getMaximunDay(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}

	public static boolean fechaIguales(Date fecha1, Date fecha2) {
		return getDateDiff(fecha1, fecha2, TimeUnit.SECONDS) == 0;
	}

}
