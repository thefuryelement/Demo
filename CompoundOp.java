package DatePack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CompoundOp 
{
	public static void main(String[] args) throws ParseException 
	{
		CompoundOp.setDates();	//JUST TO CALL SETDATES METHOD TO SET INITIAL VALUES FOR COMPOUNDING
	}
	
	public static void setDates() throws ParseException
	{
		// DATEFORMAT SPRECIFICATION TO SET THE DATE FORMAT FOR INPUT TYPE FROM THE USER
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Scanner sc = new Scanner(System.in);
		// INTITIALIZING THE INITIAL DATE START DATE
		System.out.println("Enter start date in format dd/mm/yyyy");
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(dateFormat.parse(sc.next()));
		// INITIALIZING THE END DATE VALUE
		System.out.println("Enter end date in format dd/mm/yyyy");
		Calendar eDate = Calendar.getInstance();
		eDate.setTime(dateFormat.parse(sc.next()));
		long principleAmt = sc.nextLong();
		
		float intrstRate = sc.nextFloat();
		sc.close();
		CompoundOp.CompoundCalc(sDate, eDate, principleAmt, intrstRate, sDate.getActualMaximum(Calendar.DAY_OF_YEAR));
	}
	// note annualNoDays is useless as if the two dates lie in two different year and one being leap and another regular so the days would vary as 366 and 365
	public static void CompoundCalc(Calendar transactionStartDate, Calendar transactionEndDate, long principleAmount, float rateOfInterest, int annualNoDays )
	{
		int currMonth=0;
		int maxMonth=11;
		int eYear = transactionEndDate.get(Calendar.YEAR);

		System.out.println("The current start date is :"+transactionStartDate.getTime());
		System.out.println("Calculating compound value for :"+(transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)-transactionStartDate.get(Calendar.DAY_OF_MONTH))+" days");

		// to calculate the interest for the remaining days of the first month
		principleAmount += (principleAmount*rateOfInterest*(transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)-transactionStartDate.get(Calendar.DAY_OF_MONTH)))/(eYear*100);
		transactionStartDate.set(Calendar.MONTH, transactionStartDate.get(Calendar.MONTH)+1);

		// to calculate the interest of the in between days
		for(int sYear=transactionStartDate.get(Calendar.YEAR);sYear<=eYear; sYear++)
		{
			transactionStartDate.set(Calendar.YEAR, sYear);
			if(sYear==eYear)
				maxMonth=transactionEndDate.get(Calendar.MONTH);
			
			for(currMonth=transactionStartDate.get(Calendar.MONTH);currMonth<=maxMonth; currMonth++ )
			{
				if(sYear==eYear&&currMonth==maxMonth)
				{
					System.out.println("The current start date is :"+transactionEndDate.getTime());
					System.out.println("Calculating compound value for :"+transactionEndDate.get(Calendar.DAY_OF_MONTH)+" days");
					// to calculate the interest of the covered days of last month
					principleAmount += principleAmount*rateOfInterest*transactionEndDate.get(Calendar.DAY_OF_MONTH)/annualNoDays*100;
					break;
				}

				transactionStartDate.set(2, currMonth);
				System.out.println("The current start date is :"+transactionStartDate.getTime());
				System.out.println("Calculating compound value for :"+transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)+" days");
				principleAmount += (principleAmount*rateOfInterest*transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)/annualNoDays)/100;
			}
			transactionStartDate.set(Calendar.MONTH,0);
			maxMonth=11;
		}
		
		System.out.println("The Principle is : "+principleAmount);
	}
}
