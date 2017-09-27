package gpyo.util.statistics;
	
	/**
	 Implementation of Chauvenet's criterion for the rejections of glitches in data
	 sets. The criterion assumes that 1/2 or less of the observations can be "anomolous".
	 Points are rejected if they lie in the "tails" of the normal distribution; that is,
	 far from the mean. 1/2 (0.5) is the standard cutoff value, the reject() method does
	 <bold>This may be done to a dataset only ONCE. Do not do it iteratively.</bold><p>
	 This approach was adopted by Kanamori for rejecting anomolous amplitude readings
	 in magnitudes.
	*/
	public class Chauvenet {
	
	     public static final int MIN_MEMBERS = 3;  // need at least this many values in the set
	     public static final double STANDARD_CUTOFF_VALUE = 0.5;
	
	     public Chauvenet() {
	     }
	
	      /** Returns the portion of a Normal distribution that is in the "tails"
	      * of the curve beyond this standard deviation in both directions from the mean. */
	      public static double amountInTails (double stdDevsAway) {
	         return 2.0 * Ztable.getAbove(stdDevsAway);
	      }
	
	      /** Uses standard cutoff of 0.5 */
	      public static boolean reject(double stdDevsAway, int count) {
	    	  return reject(stdDevsAway, count, STANDARD_CUTOFF_VALUE);
	      }
	      
	      /** Allows non-standard user-specified cutoff value. */
	      public static boolean reject(double stdDevsAway, int count, double cutOff) {
	    	  double inTails = amountInTails(stdDevsAway);
	    	  return ((inTails * count) < cutOff);
	      }
/*	
	  public static final class Tester {
	     public static void main (String args[]) {
	
	         //double data[] = {1.8, 3.8, 3.5, 3.9, 3.9, 3.4};
	         double data[] = {46, 48, 44, 38, 45, 58, 44, 45, 43};
	
	         double mean = Stats.mean(data);
	         double stdDev = Stats.standardDeviation(data);
	         double stdDevsAway;
	
	         System.out.println("mean = "+mean + "  stdDev = "+ stdDev);
	
	         for (int i = 0; i< data.length; i++ ){
	
	            stdDevsAway = (mean - data[i])/stdDev;
	
	            if (Chauvenet.reject(stdDevsAway, data.length)) {
	               System.out.println("Reject "+data[i]);
	            } else {
	               System.out.println("Accept "+data[i]);
	            }
	         }
	
	     }
	  } // end of Tester */
	}