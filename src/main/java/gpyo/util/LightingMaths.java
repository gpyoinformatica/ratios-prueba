package gpyo.util;

public class LightingMaths{
	
	/**
	 * Interpolación con la ecuación de la recta.
	 * 
	 * @param A Valor o límite superior de la incógnita que se quiere interpolar. 
	 * @param aa Valor o límite inferior de la incógnita que se quiere interpolar.
	 * @param B Valor o límite superior del valor que sí se tiene.
	 * @param bb Valor o límite inferior del valor que sí se tiene.
	 * @param b Valor que se tiene.
	 * @return Despeja 'a' en la ecuación, que será el valor interpolado entre A y aa.
	 */
	public static float interpolate(float A, float aa, float B, float bb, float b){
		return ((A-aa)*(b-bb) / (B-bb)) + aa; 
	}

	/**
	 * Devuelve el máximo valor de los 2 parámetros o el valor de b si son iguales.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Integer returnMaxIntegerValue(Integer a, Integer b){
		int comparation = a.compareTo(b);
		if(comparation > 0){
			return a;
		}else 
			return b;
	}

	/**
	 * Devuelve el mínimo valor de los 2 parámetros o el valor de b si son iguales.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Integer returnMinIntegerValue(Integer a, Integer b){
		int comparation = a.compareTo(b);
		if(comparation < 0){
			return a;
		}else 
			return b;
	}

	/**
	 * Devuelve el máximo valor de los 2 parámetros o el valor de b si son iguales.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Float returnMaxFloatValue(Float a, Float b){
		int comparation = a.compareTo(b);
		if(comparation > 0){
			return a;
		}else
			return b;
	}

	/**
	 * Devuelve el mínimo valor de los 2 parámetros o el valor de b si son iguales.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Float returnMinFloatValue(Float a, Float b){
		int comparation = a.compareTo(b);
		if(comparation < 0){
			return a;
		}else
			return b;
	}
}