package codingwithmitch.com.googlemapsgoogleplaces.models;

public class PlaceMode {
    String main;
    String second;
    String description;
    String placeid;

    public PlaceMode(String main, String second, String description, String placeid) {
        this.main = main;
        this.second = second;
        this.description = description;
        this.placeid = placeid;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }
}
