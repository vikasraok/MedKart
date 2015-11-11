package in.medkart.pocketpharma.Model;

/**
 * Created by SESA249903 on 9/20/2014.
 */
public class GridData {
    int image;
    String title;

    public GridData() {
    }

    ;

    public GridData(String title, int image) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getImage() {
        return this.image;
    }
}
