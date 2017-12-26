import java.sql.Date;

public class AccidentData {

	int cscode;
	String province,town;
	String date;
	String carno;
	String polno;
	//String chk_process;
	int casualty,dead,injured;
	String type;
	double longitude,latitude;
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
	public String getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date.toString();
	}
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public String getPolno() {
		return polno;
	}
	public void setPolno(String polno) {
		this.polno = polno;
	}
	public int getCasualty() {
		return casualty;
	}
	public void setCasualty(int casualty) {
		this.casualty = casualty;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	
	
	
	
}
