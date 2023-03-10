import java.io.Serializable;
import java.math.BigDecimal;

public class Asset implements Serializable {
    private final String name;
    private final double quantity;
    private double price;
    private double originalPrice;
    private int volatility;
    private int investmentID;

    public Asset(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Asset(String name, double price, double quantity, int volatility) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.volatility = volatility;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getValue() {
        return price * quantity;
    }

    public double getProfit() {
        return getValue() - originalPrice;
    }

    public void setPrice(double newPrice) {
        price = Math.max(newPrice, 0.01);
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getVolatility() {
        return volatility;
    }

    public int getInvestmentID() {
        return investmentID;
    }

    public void setInvestmentID(int investmentID) {
        this.investmentID = investmentID;
    }

    public String format(double value) {
        return String.format("%.2f", value);
    }

    public String convertProfitToString() {
        String sign = originalPrice > getValue() ? "-$" : "+$";
        String result = sign + format(getProfit());

        if (sign.equals("-$")) {
            result = "-$" + format(getProfit() * -1);
        }

        return result;
    }

    public String convertQuantityToString() {
        String result = "" + new BigDecimal(Double.toString(quantity));

        if (quantity == Math.floor(quantity)) {
            result = String.valueOf((int) quantity);
        }

        return result;
    }

    public String assetString() {
        return getName() + ": $" + format(getValue());
    }

    public String portfolioString() {
        return "(" + convertQuantityToString() + "x) " + getName() + ": " + convertProfitToString();
    }
}