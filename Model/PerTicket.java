package Model;

public class PerTicket {
	private String performanceCode; // pk
	private String genre;
	private String performanceTitle;
	private String location;
	private String performanceDate;
	private String tag;
	private String runningTime;
	private int seatR;
	private int seatA;
	private int seatB;
	public PerTicket(String performanceCode, String genre, String performanceTitle, String location,
			String performanceDate, String tag, String runningTime, int seatR, int seatA, int seatB) {
		super();
		this.performanceCode = performanceCode;
		this.genre = genre;
		this.performanceTitle = performanceTitle;
		this.location = location;
		this.performanceDate = performanceDate;
		this.tag = tag;
		this.runningTime = runningTime;
		this.seatR = seatR;
		this.seatA = seatA;
		this.seatB = seatB;
	}
	public String getPerformanceCode() {
		return performanceCode;
	}
	public void setPerformanceCode(String performanceCode) {
		this.performanceCode = performanceCode;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getPerformanceTitle() {
		return performanceTitle;
	}
	public void setPerformanceTitle(String performanceTitle) {
		this.performanceTitle = performanceTitle;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPerformanceDate() {
		return performanceDate;
	}
	public void setPerformanceDate(String performanceDate) {
		this.performanceDate = performanceDate;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	public int getSeatR() {
		return seatR;
	}
	public void setSeatR(int seatR) {
		this.seatR = seatR;
	}
	public int getSeatA() {
		return seatA;
	}
	public void setSeatA(int seatA) {
		this.seatA = seatA;
	}
	public int getSeatB() {
		return seatB;
	}
	public void setSeatB(int seatB) {
		this.seatB = seatB;
	}
	
	
	
	
}
