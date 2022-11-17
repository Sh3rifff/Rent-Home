package az.safekarabakh.renthome.helperClass;

public class FeatureHelperClass {

    int image;
    String title, description,descriptionAll,wifi,otaq,mertebe;

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionAll() {
        return descriptionAll;
    }

    public String getWifi() {
        return wifi;
    }

    public String getOtaq() {
        return otaq;
    }

    public String getMertebe() {
        return mertebe;
    }

    public FeatureHelperClass(int image, String title, String description, String descriptionAll, String wifi, String otaq, String mertebe) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.descriptionAll = descriptionAll;
        this.wifi = wifi;
        this.otaq = otaq;
        this.mertebe = mertebe;
    }
}
