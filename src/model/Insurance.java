package model;

public class Insurance {

    private String provider;
    private int validFromYear;
    private int validUntilYear;

    public Insurance(String provider, int validFromYear, int validUntilYear) {
        this.provider = provider;
        this.validFromYear = validFromYear;
        this.validUntilYear = validUntilYear;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "provider='" + provider + '\'' +
                ", validFrom=" + validFromYear +
                ", validUntil=" + validUntilYear +
                '}';
    }
}
