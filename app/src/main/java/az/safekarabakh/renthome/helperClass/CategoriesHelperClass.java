package az.safekarabakh.renthome.helperClass;

public class CategoriesHelperClass {
    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public CategoriesHelperClass(int image, String title) {
        this.image = image;
        this.title = title;
    }

    int image;
    String title;
}
