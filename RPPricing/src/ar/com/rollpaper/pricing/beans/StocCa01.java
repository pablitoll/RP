package ar.com.rollpaper.pricing.beans;
// Generated 21/05/2018 20:09:21 by Hibernate Tools 5.3.0.Beta2

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * StocCa01 generated by hbm2java
 */
public class StocCa01 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8932187471640165502L;
	private String ca01Clasif1;
	private String ca01Nombre;
	private boolean ca01Utilizable;
	private Set<StocArts> stocArtses = new Set<StocArts>() {
		
		@Override
		public <T> T[] toArray(T[] a) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Iterator<StocArts> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean contains(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean addAll(Collection<? extends StocArts> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean add(StocArts e) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public StocCa01() {
	}

	public StocCa01(String ca01Clasif1, String ca01Nombre, boolean ca01Utilizable) {
		this.ca01Clasif1 = ca01Clasif1;
		this.ca01Nombre = ca01Nombre;
		this.ca01Utilizable = ca01Utilizable;
	}

	public StocCa01(String ca01Clasif1, String ca01Nombre, boolean ca01Utilizable, Set<StocArts> stocArtses) {
		this.ca01Clasif1 = ca01Clasif1;
		this.ca01Nombre = ca01Nombre;
		this.ca01Utilizable = ca01Utilizable;
		this.stocArtses = stocArtses;
	}

	public String getCa01Clasif1() {
		return this.ca01Clasif1;
	}

	public void setCa01Clasif1(String ca01Clasif1) {
		this.ca01Clasif1 = ca01Clasif1;
	}

	public String getCa01Nombre() {
		return this.ca01Nombre;
	}

	public void setCa01Nombre(String ca01Nombre) {
		this.ca01Nombre = ca01Nombre;
	}

	public boolean isCa01Utilizable() {
		return this.ca01Utilizable;
	}

	public void setCa01Utilizable(boolean ca01Utilizable) {
		this.ca01Utilizable = ca01Utilizable;
	}

	public Set<StocArts> getStocArtses() {
		return this.stocArtses;
	}

	public void setStocArtses(Set<StocArts> stocArtses) {
		this.stocArtses = stocArtses;
	}

}
