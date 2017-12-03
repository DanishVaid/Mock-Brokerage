import java.util.ArrayList;

public class Date {
	
	ArrayList<Integer> monthsEndIn28 = new ArrayList<Integer>();
	ArrayList<Integer> monthsEndIn30 = new ArrayList<Integer>();
	ArrayList<Integer> monthsEndIn31 = new ArrayList<Integer>();
	
	public static final int monthsInAYear = 12;
	
	private int year = 0;
	private int month = 0;
	private int day = 0;

	public Date() {
		this(0, 0, 0);
	}
	
	public Date(int month, int day, int year) {
		setDate(month, day, year);
		
		monthsEndIn28.add(2);
		
		monthsEndIn30.add(4);
		monthsEndIn30.add(6);
		monthsEndIn30.add(9);
		monthsEndIn30.add(11);
		
		monthsEndIn31.add(1);
		monthsEndIn31.add(3);
		monthsEndIn31.add(5);
		monthsEndIn31.add(7);
		monthsEndIn31.add(8);
		monthsEndIn31.add(10);
		monthsEndIn31.add(12);
	}
	
	public void setDate(int month, int day, int year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public void addDays(int numDays) {
		day += numDays;
		
		boolean overflow = true;
		while (overflow) {
			if (monthsEndIn28.contains(month)) {
				if (day <= 28) {
					break;
				}
				else {
					day -= 28;
					month++;
				}
			}
			
			if (monthsEndIn30.contains(month)) {
				if (day <= 30) {
					break;
				}
				else {
					day -= 30;
					month++;
				}
			}
			
			if (monthsEndIn31.contains(month)) {
				if (day <= 31) {
					break;
				}
				else {
					day -= 31;
					month++;
				}
			}
			
			if (month > 12) {
				month -= 12;
				year++;
			}
		}
		
		//MAY NOT HAVE TO REPEAT
		if (month > 12) {
			month -= 12;
			year++;
		}
	}
	
	public boolean isPassedDate(Date d) {
		if (year > d.getYear()) {
			return true;
		}
		
		if (year == d.getYear()) {
			if (month > d.getMonth()) {
				return true;
			}
			
			if (month == d.getMonth()) {
				if (day > d.getDay()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		return false;
	}
	
	public String getDateString() {
		return "" + month + "/" + day + "/" + year;
	}

	public Date buildFromString(String inp) {
		String[] date_data = inp.split("/");
		return new Date(Integer.parseInt(date_data[0]), Integer.parseInt(date_data[1]), Integer.parseInt(date_data[2]));
	}
	
	public int[] getDateInt() {
		return new int[] {month, day, year};
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
}
