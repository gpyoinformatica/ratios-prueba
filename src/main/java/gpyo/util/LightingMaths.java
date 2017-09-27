package gpyo.util;

public class LightingMaths{
	
	/**
	 * Interpolaci�n con la ecuaci�n de la recta.
	 * 
	 * @param A Valor o l�mite superior de la inc�gnita que se quiere interpolar. 
	 * @param aa Valor o l�mite inferior de la inc�gnita que se quiere interpolar.
	 * @param B Valor o l�mite superior del valor que s� se tiene.
	 * @param bb Valor o l�mite inferior del valor que s� se tiene.
	 * @param b Valor que se tiene.
	 * @return Despeja 'a' en la ecuaci�n, que ser� el valor interpolado entre A y aa.
	 */
	public static float interpolate(float A, float aa, float B, float bb, float b){
		return ((A-aa)*(b-bb) / (B-bb)) + aa; 
	}

	/**
	 * Devuelve el m�ximo valor de los 2 par�metros o el valor de b si son iguales.
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
	 * Devuelve el m�nimo valor de los 2 par�metros o el valor de b si son iguales.
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
	 * Devuelve el m�ximo valor de los 2 par�metros o el valor de b si son iguales.
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
	 * Devuelve el m�nimo valor de los 2 par�metros o el valor de b si son iguales.
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