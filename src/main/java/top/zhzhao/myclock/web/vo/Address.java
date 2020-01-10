/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.web.vo;

/**
 *
 *@author zhzhao
 *@version $ Id: Address.java,V 0.1 2020/1/9 16:08 zhzhao Exp $
 */
public class Address {
    private String latitude;
    private String longitude;
    private String place;

    public Address() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
