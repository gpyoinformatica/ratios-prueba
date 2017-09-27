package gpyo.persistence.entity.constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Months implements Serializable, Comparable<Months>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7271541431120238958L;

	public final static String JANUARY = "january";
	
	public final static String FEBRUARY = "february";
	
	public final static String MARCH = "march";
	
	public final static String APRIL = "april";
	
	public final static String MAY = "may";
	
	public final static String JUNE = "june";
	
	public final static String JULY = "july";
	
	public final static String AUGUST = "august";
	
	public final static String SEPTEMBER = "september";
	
	public final static String OCTOBER = "october";
	
	public final static String NOVEMBER = "november";
	
	public final static String DECEMBER = "december";
	
	private int idMonth;
	private String month;
	private int days;
	
	public Months(){
		
	}
	
	public Months(String month){
		this.month=month;
	}
	
	public List<Months> getMonths(){
		List<Months> meses = new ArrayList<Months>();
		meses.add(new Months(JANUARY));
		meses.add(new Months(FEBRUARY));
		meses.add(new Months(MARCH));
		meses.add(new Months(APRIL));
		meses.add(new Months(MAY));
		meses.add(new Months(JUNE));
		meses.add(new Months(JULY));
		meses.add(new Months(AUGUST));
		meses.add(new Months(SEPTEMBER));
		meses.add(new Months(OCTOBER));
		meses.add(new Months(NOVEMBER));
		meses.add(new Months(DECEMBER));
		return meses;
	}
	
	public String getMonth4View(String month) {
		if(month == JANUARY)
			return "Enero";
		else if(month == FEBRUARY)
			return "Febrero";
		else if(month == MARCH)
			return "Marzo";
		else if(month == APRIL)
			return "Abril";
		else if(month == MAY)
			return "Mayo";
		else if(month == JUNE)
			return "Junio";
		else if(month == JULY)
			return "Julio";
		else if(month == AUGUST)
			return "Agosto";
		else if(month == SEPTEMBER)
			return "Septiembre";
		else if(month == OCTOBER)
			return "Octubre";
		else if(month == NOVEMBER)
			return "Noviembre";
		else 
			return "Diciembre";	
	}
	
	public String getMonthNumber(String month) {
		if(month.equals("Enero"))
			return "01";
		else if(month.equals("Febrero"))
			return "02";
		else if(month.equals("Marzo"))
			return "03";
		else if(month.equals("Abril"))
			return "04";
		else if(month.equals("Mayo"))
			return "05";
		else if(month.equals("Junio"))
			return "06";
		else if(month.equals("Julio"))
			return "07";
		else if(month.equals("Agosto"))
			return "08";
		else if(month.equals("Septiembre"))
			return "09";
		else if(month.equals("Octubre"))
			return "10";
		else if(month.equals("Noviembre"))
			return "11";
		else 
			return "12";	
	}
	
	public String getMonth4Search() {
		if(month == JANUARY)
			return "enero";
		else if(month == FEBRUARY)
			return "febrero";
		else if(month == MARCH)
			return "marzo";
		else if(month == APRIL)
			return "abril";
		else if(month == MAY)
			return "mayo";
		else if(month == JUNE)
			return "junio";
		else if(month == JULY)
			return "julio";
		else if(month == AUGUST)
			return "agosto";
		else if(month == SEPTEMBER)
			return "septiembre";
		else if(month == OCTOBER)
			return "octubre";
		else if(month == NOVEMBER)
			return "noviembre";
		else 
			return "diciembre";	
	}
	
	@Override
	public int compareTo(Months o){
		if(this.month.equals(Months.JANUARY))
			return -1;
		if(o.month.equals(Months.JANUARY))
			return 1;
		if(this.month.equals(Months.FEBRUARY))
			return -1;
		if(o.month.equals(Months.FEBRUARY))
			return 1;
		if(this.month.equals(Months.MARCH))
			return -1;
		if(o.month.equals(Months.MARCH))
			return 1;
		if(this.month.equals(Months.APRIL))
			return -1;
		if(o.month.equals(Months.APRIL))
			return 1;
		if(this.month.equals(Months.MAY))
			return -1;
		if(o.month.equals(Months.MAY))
			return 1;
		if(this.month.equals(Months.JUNE))
			return -1;
		if(o.month.equals(Months.JUNE))
			return 1;
		if(this.month.equals(Months.JULY))
			return -1;
		if(o.month.equals(Months.JULY))
			return 1;
		if(this.month.equals(Months.AUGUST))
			return -1;
		if(o.month.equals(Months.AUGUST))
			return 1;
		if(this.month.equals(Months.SEPTEMBER))
			return -1;
		if(o.month.equals(Months.SEPTEMBER))
			return 1;
		if(this.month.equals(Months.OCTOBER))
			return -1;
		if(o.month.equals(Months.OCTOBER))
			return 1;
		if(this.month.equals(Months.NOVEMBER))
			return -1;
		if(o.month.equals(Months.NOVEMBER))
			return 1;
		if(this.month.equals(Months.DECEMBER))
			return -1;
		if(o.month.equals(Months.DECEMBER))
			return 1;
		return this.month.compareTo(o.month);
	}
	
	public int numOfDays(String m){
		if(m.equals(JANUARY) || m.equals(MARCH) || m.equals(MAY) || m.equals(JULY) || m.equals(AUGUST) || m.equals(OCTOBER) || m.equals(DECEMBER))
			return 31;
		else{
			if(m.equals(FEBRUARY))
				return 28;
			else
				return 30;
		}
	}
	
	
	public int getIdMonth() {
		return idMonth;
	}
	public void setIdMonth(int idMonth) {
		this.idMonth = idMonth;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
