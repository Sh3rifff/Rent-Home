package az.safekarabakh.renthome.helperClass;

public class NearestHelperClass {

    int image;
    String title,description;


    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public NearestHelperClass(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }
}
