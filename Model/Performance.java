package Model;

public class Performance  {
	private String performanceCode; // pk
	private String performanceTitle;
	private String genre;
	private String location;
	private String performanceDate;
	private String runningTime;
	private String tag;
	private String casting;
	private String synopsis;
	private String poster;
	private String produce;
	private String notice;
	public Performance(String performanceCode, String performanceTitle, String genre,  String location,
			String performanceDate, String tag,  String runningTime, String casting, String synopsis,  String poster,
			String produce, String notice) {
		super();
		this.performanceCode = performanceCode;
		this.genre = genre;
		this.performanceTitle = performanceTitle;
		this.location = location;
		this.performanceDate = performanceDate;
		this.tag = tag;
		this.runningTime = runningTime;
		this.synopsis = synopsis;
		this.casting = casting;
		this.poster = poster;
		this.produce = produce;
		this.notice = notice;
	}
	public String getPerformanceCode() {
		return performanceCode;
	}
	public void setPerformanceCode(String peformanceCode) {
		this.performanceCode = peformanceCode;
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
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getCasting() {
		return casting;
	}
	public void setCasting(String casting) {
		this.casting = casting;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
}
