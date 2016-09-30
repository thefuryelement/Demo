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

		// JUST TO SHOW THE MAXIMUM NUMBER OF DAYS IN THE YEAR .. 365/366
		System.out.println(sDate.getActualMaximum(Calendar.DAY_OF_YEAR));

		CompoundOp.CompoundCalc(sDate, eDate, sc.nextFloat(), sc.nextFloat(), sDate.getActualMaximum(Calendar.DAY_OF_YEAR));
	}
	
	public static void CompoundCalc(Calendar transactionStartDate, Calendar transactionEndDate, float rateOfInterest, float principleAmount, int annualNoDays )
	{
		int j = transactionEndDate.get(Calendar.MONTH);
		// to calculate the interest for the remaining days of the first month
		principleAmount += (principleAmount*rateOfInterest*(transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)-transactionStartDate.get(Calendar.DAY_OF_MONTH)))/(annualNoDays*100);
		// to calculate the interest of the inbetween days
		for(int i=transactionStartDate.get(Calendar.MONTH)+1; i<j; i++)
		{
			transactionStartDate.set(2, i);
			principleAmount += (principleAmount*rateOfInterest*transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)/annualNoDays)/100;
		}

		// to calculate the interest of the covered days of last month
		principleAmount += principleAmount*rateOfInterest*transactionStartDate.get(Calendar.DAY_OF_MONTH)/annualNoDays*100;
	}
}
