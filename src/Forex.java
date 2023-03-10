import java.util.Random;

public class Forex extends Asset {
    private final Currency from;
    private final Currency to;

    public Forex(Currency from, Currency to, double quantity, int volatility) {
        super(from.getName() + "/" + to.getName(), from.getPrice() * to.getPrice(), quantity, volatility);
        this.from = from;
        this.to = to;
    }

    @Override
    public double getPrice() {
        return from.getPrice() * to.getPrice();
    }

    @Override
    public void setPrice(double newPrice) {
        from.setPrice(from.getPrice() + subPriceUpdate());
        to.setPrice(to.getPrice() + subPriceUpdate());
        super.setPrice(getPrice());
    }

    private double subPriceUpdate() {
        Random random = new Random();
        double subPrice = random.nextDouble();

        while (subPrice > 0.01) {
            subPrice = random.nextDouble();
        }

        return random.nextBoolean() ? subPrice : subPrice * -1;
    }
}