
public class AccidentCase {

	private int cscode;   			//사고 번호
	private String province;        //시,도
	private String town;            //군,구
	private String year;            //년도
	private String month;           //월
	private String day;             //일
	private String policeno;        //경찰번호
	private int casulity;           //사상자 = 사망자 + 부상자
	private int dead;               //사망자
	private int injured;            //부상자 
	private String actype;          //사고유형 (차대차, 차대사람, 차량단독)
	private double latitude;        //위도
	private double longitude;       //경도
	
	public int getCscode() {
		return cscode;
	}
	public void setCscode(int cscode) {
		this.cscode = cscode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getPoliceno() {
		return policeno;
	}
	public void setPoliceno(String policeno) {
		this.policeno = policeno;
	}
	public int getCasulity() {
		return casulity;
	}
	public void setCasulity() {
		this.casulity = this.dead + this.injured;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	public int getInjured() {
		return injured;
	}
	public void setInjured(int injured) {
		this.injured = injured;
	}
	public String getActype() {
		return actype;
	}
	public void setActype(String actype) {
		this.actype = actype;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}