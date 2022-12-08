package az.safekarabakh.renthome.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Place  {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "latitude")
    private Double latitude;
    @ColumnInfo(name = "longitude")
    private Double longitude;
    @ColumnInfo(name = "Rayon")
    private String rayon;
    @ColumnInfo(name = "qiymet")
    private String qiymet;
    @ColumnInfo(name = "melumat")
    private String melumat;
    @ColumnInfo(name = "wifi")
    private String wifi;
    @ColumnInfo(name = "otaq")
    private String otaq;
    @ColumnInfo(name = "mertebe")
    private String mertebe;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBlob;

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public Place(String name, Double latitude, Double longitude
            , String rayon, String qiymet, String melumat, String wifi, String otaq, String mertebe,byte[] imageBlob) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rayon = rayon;
        this.qiymet = qiymet;
        this.melumat = melumat;
        this.wifi = wifi;
        this.otaq = otaq;
        this.mertebe = mertebe;
        this.imageBlob=imageBlob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    public String getQiymet() {
        return qiymet;
    }

    public void setQiymet(String qiymet) {
        this.qiymet = qiymet;
    }

    public String getMelumat() {
        return melumat;
    }

    public void setMelumat(String melumat) {
        this.melumat = melumat;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getOtaq() {
        return otaq;
    }

    public void setOtaq(String otaq) {
        this.otaq = otaq;
    }

    public String getMertebe() {
        return mertebe;
    }

    public void setMertebe(String mertebe) {
        this.mertebe = mertebe;
    }

}
