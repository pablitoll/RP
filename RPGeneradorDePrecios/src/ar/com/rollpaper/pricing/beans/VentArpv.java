package ar.com.rollpaper.pricing.beans;
// Generated 24/05/2018 09:15:54 by Hibernate Tools 5.3.0.Beta2

import java.math.BigDecimal;
import java.util.Date;

/**
 * VentArpv generated by hbm2java
 */
public class VentArpv implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentArpvId id;
	private SistMone sistMoneByArpvMoneda;
	private SistMone sistMoneByArpvMonedaAnt;
	private VentLipv ventLipv;
	private BigDecimal arpvPrecioVta;
	private BigDecimal arpvPrecioVtaAn;
	private Date arpvFechaUltAct;
	private Short arpvClaseRefLp;
	private BigDecimal arpvValorRef;
	private BigDecimal arpvFactor;
	private String arpvCpbteRef;

	public VentArpv() {
	}

	public VentArpv(VentArpvId id, SistMone sistMoneByArpvMoneda, VentLipv ventLipv, BigDecimal arpvPrecioVta,
			BigDecimal arpvPrecioVtaAn, Date arpvFechaUltAct) {
		this.id = id;
		this.sistMoneByArpvMoneda = sistMoneByArpvMoneda;
		this.ventLipv = ventLipv;
		this.arpvPrecioVta = arpvPrecioVta;
		this.arpvPrecioVtaAn = arpvPrecioVtaAn;
		this.arpvFechaUltAct = arpvFechaUltAct;
	}

	public VentArpv(VentArpvId id, SistMone sistMoneByArpvMoneda, SistMone sistMoneByArpvMonedaAnt, VentLipv ventLipv,
			BigDecimal arpvPrecioVta, BigDecimal arpvPrecioVtaAn, Date arpvFechaUltAct, Short arpvClaseRefLp,
			BigDecimal arpvValorRef, BigDecimal arpvFactor, String arpvCpbteRef) {
		this.id = id;
		this.sistMoneByArpvMoneda = sistMoneByArpvMoneda;
		this.sistMoneByArpvMonedaAnt = sistMoneByArpvMonedaAnt;
		this.ventLipv = ventLipv;
		this.arpvPrecioVta = arpvPrecioVta;
		this.arpvPrecioVtaAn = arpvPrecioVtaAn;
		this.arpvFechaUltAct = arpvFechaUltAct;
		this.arpvClaseRefLp = arpvClaseRefLp;
		this.arpvValorRef = arpvValorRef;
		this.arpvFactor = arpvFactor;
		this.arpvCpbteRef = arpvCpbteRef;
	}

	public VentArpvId getId() {
		return this.id;
	}

	public void setId(VentArpvId id) {
		this.id = id;
	}

	public SistMone getSistMoneByArpvMoneda() {
		return this.sistMoneByArpvMoneda;
	}

	public void setSistMoneByArpvMoneda(SistMone sistMoneByArpvMoneda) {
		this.sistMoneByArpvMoneda = sistMoneByArpvMoneda;
	}

	public SistMone getSistMoneByArpvMonedaAnt() {
		return this.sistMoneByArpvMonedaAnt;
	}

	public void setSistMoneByArpvMonedaAnt(SistMone sistMoneByArpvMonedaAnt) {
		this.sistMoneByArpvMonedaAnt = sistMoneByArpvMonedaAnt;
	}

	public VentLipv getVentLipv() {
		return this.ventLipv;
	}

	public void setVentLipv(VentLipv ventLipv) {
		this.ventLipv = ventLipv;
	}

	public BigDecimal getArpvPrecioVta() {
		return this.arpvPrecioVta;
	}

	public void setArpvPrecioVta(BigDecimal arpvPrecioVta) {
		this.arpvPrecioVta = arpvPrecioVta;
	}

	public BigDecimal getArpvPrecioVtaAn() {
		return this.arpvPrecioVtaAn;
	}

	public void setArpvPrecioVtaAn(BigDecimal arpvPrecioVtaAn) {
		this.arpvPrecioVtaAn = arpvPrecioVtaAn;
	}

	public Date getArpvFechaUltAct() {
		return this.arpvFechaUltAct;
	}

	public void setArpvFechaUltAct(Date arpvFechaUltAct) {
		this.arpvFechaUltAct = arpvFechaUltAct;
	}

	public Short getArpvClaseRefLp() {
		return this.arpvClaseRefLp;
	}

	public void setArpvClaseRefLp(Short arpvClaseRefLp) {
		this.arpvClaseRefLp = arpvClaseRefLp;
	}

	public BigDecimal getArpvValorRef() {
		return this.arpvValorRef;
	}

	public void setArpvValorRef(BigDecimal arpvValorRef) {
		this.arpvValorRef = arpvValorRef;
	}

	public BigDecimal getArpvFactor() {
		return this.arpvFactor;
	}

	public void setArpvFactor(BigDecimal arpvFactor) {
		this.arpvFactor = arpvFactor;
	}

	public String getArpvCpbteRef() {
		return this.arpvCpbteRef;
	}

	public void setArpvCpbteRef(String arpvCpbteRef) {
		this.arpvCpbteRef = arpvCpbteRef;
	}

}