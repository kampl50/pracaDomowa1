package models;

public class CarVisit {
    private final String title;
    private final Double price;
    private final String mark;

    public CarVisit(String title, Double price, String mark) {
        this.title = title;
        this.price = price;
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public String getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "CarVisit{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", mark='" + mark + '\'' +
                '}';
    }
}
