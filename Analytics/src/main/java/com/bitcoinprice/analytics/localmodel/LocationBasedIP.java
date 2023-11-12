package com.bitcoinprice.analytics.localmodel;

public class LocationBasedIP {

	private String continent;
	private String country;
	private String city;
	private String regionName;
	private String timezone;
	private String isp;
	private Double Long;
	private Double Lat;
	private boolean isMobile;
	private boolean isProxy;
	private boolean isHosting;

	public LocationBasedIP() {
	}

	public LocationBasedIP(Object object, Object object2, Object object3, Object object4, Object object5, Object object6,
			Object object7, Object object8, Object object9 , Object object10, Object object11) {
		super();
		this.continent = (String) object;
		this.country = (String) object2;
		this.city = (String) object3;
		this.regionName = (String) object4;
		this.timezone = (String) object5;
		this.isp = (String) object6;
		this.isMobile = (boolean) object7;
		this.isProxy = (boolean) object8;
		this.isHosting = (boolean) object9;
		this.Long = (Double) object10;
		this.Lat = (Double) object11;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public boolean isHosting() {
		return isHosting;
	}

	public void setHosting(boolean isHosting) {
		this.isHosting = isHosting;
	}

	public Double getLong() {
		return Long;
	}

	public void setLong(Double l) {
		Long = l;
	}

	public Double getLat() {
		return Lat;
	}

	public void setLat(Double lat) {
		Lat = lat;
	}
}
