package gpyo.persistence.entity.admin;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class TesoreriaDesplegable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764682576155102793L;
	
	private long id_tesoreria;
	private String titulos;
	private int year;
	private float enero;
	private float febrero;
	private float marzo;
	private float abril;
	private float mayo;
	private float junio;
	private float julio;
	private float agosto;
	private float septiembre;
	private float octubre;
	private float noviembre;
	private float diciembre;
	private float total;
	private float iva;
	private float irpfSp;
	private float irpfAlquiler;
	private List<Tesoreria> tesoreriasDesplegables = new ArrayList<Tesoreria>();
	
	public TesoreriaDesplegable() {
		super();
	}

	public TesoreriaDesplegable(String titulos) {
		super();
		this.titulos = titulos;
	}
	
	public TesoreriaDesplegable(String titulos, float enero, float febrero,
			float marzo, float abril, float mayo, float junio, float julio,
			float agosto, float septiembre, float octubre, float noviembre,
			float diciembre, float total) {
		super();
		this.titulos = titulos;
		this.enero = enero;
		this.febrero = febrero;
		this.marzo = marzo;
		this.abril = abril;
		this.mayo = mayo;
		this.junio = junio;
		this.julio = julio;
		this.agosto = agosto;
		this.septiembre = septiembre;
		this.octubre = octubre;
		this.noviembre = noviembre;
		this.diciembre = diciembre;
		this.total = total;
	}


	public String getTitulos() {
		return titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}

	public float getEnero() {
		return enero;
	}

	public void setEnero(float enero) {
		this.enero = enero;
	}

	public float getFebrero() {
		return febrero;
	}

	public void setFebrero(float febrero) {
		this.febrero = febrero;
	}

	public float getMarzo() {
		return marzo;
	}

	public void setMarzo(float marzo) {
		this.marzo = marzo;
	}

	public float getAbril() {
		return abril;
	}

	public void setAbril(float abril) {
		this.abril = abril;
	}

	public float getMayo() {
		return mayo;
	}

	public void setMayo(float mayo) {
		this.mayo = mayo;
	}

	public float getJunio() {
		return junio;
	}

	public void setJunio(float junio) {
		this.junio = junio;
	}
	
	public float getJulio() {
		return julio;
	}

	public void setJulio(float julio) {
		this.julio = julio;
	}

	public float getAgosto() {
		return agosto;
	}

	public void setAgosto(float agosto) {
		this.agosto = agosto;
	}

	public float getSeptiembre() {
		return septiembre;
	}

	public void setSeptiembre(float septiembre) {
		this.septiembre = septiembre;
	}

	public float getOctubre() {
		return octubre;
	}

	public void setOctubre(float octubre) {
		this.octubre = octubre;
	}

	public float getNoviembre() {
		return noviembre;
	}

	public void setNoviembre(float noviembre) {
		this.noviembre = noviembre;
	}

	public float getDiciembre() {
		return diciembre;
	}

	public void setDiciembre(float diciembre) {
		this.diciembre = diciembre;
	}

	/**
	 * @return the id_tesoreria
	 */
	public long getId_tesoreria() {
		return id_tesoreria;
	}

	/**
	 * @param id_tesoreria the id_tesoreria to set
	 */
	public void setId_tesoreria(long id_tesoreria) {
		this.id_tesoreria = id_tesoreria;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the iva
	 */
	public float getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(float iva) {
		this.iva = iva;
	}

	/**
	 * @return the irpfSp
	 */
	public float getIrpfSp() {
		return irpfSp;
	}

	/**
	 * @param irpfSp the irpfSp to set
	 */
	public void setIrpfSp(float irpfSp) {
		this.irpfSp = irpfSp;
	}

	/**
	 * @return the irpfAlquiler
	 */
	public float getIrpfAlquiler() {
		return irpfAlquiler;
	}

	/**
	 * @param irpfAlquiler the irpfAlquiler to set
	 */
	public void setIrpfAlquiler(float irpfAlquiler) {
		this.irpfAlquiler = irpfAlquiler;
	}

	/**
	 * @return the tesoreriasDesplegables
	 */
	public List<Tesoreria> getTesoreriasDesplegables() {
		return tesoreriasDesplegables;
	}

	/**
	 * @param tesoreriasDesplegables the tesoreriasDesplegables to set
	 */
	public void setTesoreriasDesplegables(List<Tesoreria> tesoreriasDesplegables) {
		this.tesoreriasDesplegables = tesoreriasDesplegables;
	}

}
