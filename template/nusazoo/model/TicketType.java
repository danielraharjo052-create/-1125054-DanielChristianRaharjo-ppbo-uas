package nusazoo.model;

public enum TicketType {
    REGULER(75000), ANAK(40000), VIP(250000), DONASI_ADOPSI(50000);

    private final double basePrice;

    TicketType(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
