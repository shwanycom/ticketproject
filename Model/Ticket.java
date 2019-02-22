package Model;

public class Ticket {
	private String performanceCode; // fk
	private String performanceTitle;
	private String performanceDate;
	private String grade;
	private String ticketName; // pk
	private int price;
	private String customer;
	private String customerPhone;
	private String sellDate;
	
	public Ticket(String performanceCode, String performanceTitle, String performanceDate, String grade,
			String ticketName, int price, String customer, String customerPhone, String sellDate) {
		super();
		this.performanceCode = performanceCode;
		this.performanceTitle = performanceTitle;
		this.performanceDate = performanceDate;
		this.grade = grade;
		this.ticketName = ticketName;
		this.price = price;
		this.customer=customer;
		this.customerPhone=customerPhone;
		this.sellDate=sellDate;
	}
	public String getPerformanceCode() {
		return performanceCode;
	}
	public void setPerformanceCode(String performanceCode) {
		this.performanceCode = performanceCode;
	}
	public String getPerformanceTitle() {
		return performanceTitle;
	}
	public void setPerformanceTitle(String performanceTitle) {
		this.performanceTitle = performanceTitle;
	}
	public String getPerformanceDate() {
		return performanceDate;
	}
	public void setPerformanceDate(String performanceDate) {
		this.performanceDate = performanceDate;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getSellDate() {
		return sellDate;
	}
	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	
	
}
