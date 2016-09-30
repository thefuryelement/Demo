package datepack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CompoundOp 
{
	public static void main(String[] args) throws ParseException 
	{
		CompoundOp.setDates();
	}
	
	public static void setDates() throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter start date in format dd/mm/yyyy");
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(dateFormat.parse(sc.next()));

		System.out.println("Enter end date in format dd/mm/yyyy");
		Calendar eDate = Calendar.getInstance();
		eDate.setTime(dateFormat.parse(sc.next()));

		System.out.println(sDate.getActualMaximum(Calendar.DAY_OF_YEAR));

		System.out.println(eDate.getTime());
		int days = sDate.getActualMaximum(Calendar.DAY_OF_MONTH)-sDate.get(Calendar.DAY_OF_MONTH);
		System.out.println(days);
		CompoundOp.CompoundCalc(sDate, eDate, sc.nextFloat(), sc.nextFloat(), sDate.getActualMaximum(Calendar.DAY_OF_YEAR));
	}
	
	public static void CompoundCalc(Calendar transactionStartDate, Calendar transactionEndDate, float rateOfInterest, float principleAmount, int annualNoDays )
	{
		int j = transactionEndDate.get(Calendar.MONTH);
		principleAmount = principleAmount*rateOfInterest*(transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH)-transactionStartDate.get(Calendar.DAY_OF_MONTH))/annualNoDays*100;
		for(int i=transactionStartDate.get(Calendar.MONTH)+1; i<j; i++)
		{
			int days = transactionStartDate.get(Calendar.DAY_OF_MONTH)-transactionStartDate.getActualMaximum(Calendar.DAY_OF_MONTH);
			principleAmount = principleAmount*7;
			transactionStartDate.set(2, i);
		}
		principleAmount = principleAmount*rateOfInterest*transactionStartDate.get(Calendar.DAY_OF_MONTH)/annualNoDays*100;
	}
}
