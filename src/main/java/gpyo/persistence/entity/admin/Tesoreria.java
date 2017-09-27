package gpyo.persistence.entity.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tesoreria")
public class Tesoreria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764682576155102793L;
	
	private long id_tesoreria;
	private String titulos;
	private int year;
	private char eneroPrev = 'P';
	private float enero;
	private char febreroPrev = 'P';
	private float febrero;
	private char marzoPrev = 'P';
	private float marzo;
	private char abrilPrev = 'P';
	private float abril;
	private char mayoPrev = 'P';
	private float mayo;
	private char junioPrev = 'P';
	private float junio;
	private char julioPrev = 'P';
	private float julio;
	private char agostoPrev = 'P';
	private float agosto;
	private char septiembrePrev = 'P';
	private float septiembre;
	private char octubrePrev = 'P';
	private float octubre;
	private char noviembrePrev = 'P';
	private float noviembre;
	private char diciembrePrev = 'P';
	private float diciembre;
	private float total;
	private float iva;
	private float irpfSp;
	private float irpfAlquiler;
	
	public Tesoreria() {
		super();
	}

	public Tesoreria(String titulos) {
		super();
		this.titulos = titulos;
	}


	@Column(name="titulos")
	public String getTitulos() {
		return titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}

	@Column(name="enero")
	public float getEnero() {
		return enero;
	}

	public void setEnero(float enero) {
		this.enero = enero;
	}

	@Column(name="febrero")
	public float getFebrero() {
		return febrero;
	}

	public void setFebrero(float febrero) {
		this.febrero = febrero;
	}

	@Column(name="marzo")
	public float getMarzo() {
		return marzo;
	}

	public void setMarzo(float marzo) {
		this.marzo = marzo;
	}

	@Column(name="abril")
	public float getAbril() {
		return abril;
	}

	public void setAbril(float abril) {
		this.abril = abril;
	}

	@Column(name="mayo")
	public float getMayo() {
		return mayo;
	}

	public void setMayo(float mayo) {
		this.mayo = mayo;
	}

	@Column(name="junio")
	public float getJunio() {
		return junio;
	}

	public void setJunio(float junio) {
		this.junio = junio;
	}
	
	@Column(name="julio")
	public float getJulio() {
		return julio;
	}

	public void setJulio(float julio) {
		this.julio = julio;
	}

	@Column(name="agosto")
	public float getAgosto() {
		return agosto;
	}

	public void setAgosto(float agosto) {
		this.agosto = agosto;
	}

	@Column(name="septiembre")
	public float getSeptiembre() {
		return septiembre;
	}

	public void setSeptiembre(float septiembre) {
		this.septiembre = septiembre;
	}

	@Column(name="octubre")
	public float getOctubre() {
		return octubre;
	}

	public void setOctubre(float octubre) {
		this.octubre = octubre;
	}

	@Column(name="noviembre")
	public float getNoviembre() {
		return noviembre;
	}

	public void setNoviembre(float noviembre) {
		this.noviembre = noviembre;
	}

	@Column(name="diciembre")
	public float getDiciembre() {
		return diciembre;
	}

	public void setDiciembre(float diciembre) {
		this.diciembre = diciembre;
	}

	/**
	 * @return the id_tesoreria
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_tesoreria", unique = true)
	public long getId_tesoreria() {
		return id_tesoreria;
	}

	/**
	 * @param id_tesoreria the id_tesoreria to set
	 */
	public void setId_tesoreria(long id_tesoreria) {
		this.id_tesoreria = id_tesoreria;
	}

	@Column(name="total")
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * @return the year
	 */
	@Column(name="year")
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
	@Column(name="iva")
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
	@Column(name="irpf_sp")
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
	@Column(name="irpf_alquiler")
	public float getIrpfAlquiler() {
		return irpfAlquiler;
	}

	/**
	 * @param irpfAlquiler the irpfAlquiler to set
	 */
	public void setIrpfAlquiler(float irpfAlquiler) {
		this.irpfAlquiler = irpfAlquiler;
	}

	public char getEneroPrev() {
		return eneroPrev;
	}

	public void setEneroPrev(char eneroPrev) {
		this.eneroPrev = eneroPrev;
	}

	public char getFebreroPrev() {
		return febreroPrev;
	}

	public void setFebreroPrev(char febreroPrev) {
		this.febreroPrev = febreroPrev;
	}

	public char getMarzoPrev() {
		return marzoPrev;
	}

	public void setMarzoPrev(char marzoPrev) {
		this.marzoPrev = marzoPrev;
	}

	public char getAbrilPrev() {
		return abrilPrev;
	}

	public void setAbrilPrev(char abrilPrev) {
		this.abrilPrev = abrilPrev;
	}

	public char getMayoPrev() {
		return mayoPrev;
	}

	public void setMayoPrev(char mayoPrev) {
		this.mayoPrev = mayoPrev;
	}

	public char getJunioPrev() {
		return junioPrev;
	}

	public void setJunioPrev(char junioPrev) {
		this.junioPrev = junioPrev;
	}

	public char getJulioPrev() {
		return julioPrev;
	}

	public void setJulioPrev(char julioPrev) {
		this.julioPrev = julioPrev;
	}

	public char getAgostoPrev() {
		return agostoPrev;
	}

	public void setAgostoPrev(char agostoPrev) {
		this.agostoPrev = agostoPrev;
	}

	public char getSeptiembrePrev() {
		return septiembrePrev;
	}

	public void setSeptiembrePrev(char septiembrePrev) {
		this.septiembrePrev = septiembrePrev;
	}

	public char getOctubrePrev() {
		return octubrePrev;
	}

	public void setOctubrePrev(char octubrePrev) {
		this.octubrePrev = octubrePrev;
	}

	public char getNoviembrePrev() {
		return noviembrePrev;
	}

	public void setNoviembrePrev(char noviembrePrev) {
		this.noviembrePrev = noviembrePrev;
	}

	public char getDiciembrePrev() {
		return diciembrePrev;
	}

	public void setDiciembrePrev(char diciembrePrev) {
		this.diciembrePrev = diciembrePrev;
	}

		
  
}
