import java.io.Serializable;
import java.util.ArrayList;

public class Portfolio implements Serializable {
    private final ArrayList<Asset> assets;

    public Portfolio() {
        this.assets = new ArrayList<>();
    }

    public ArrayList<Asset> getArray() {
        return this.assets;
    }

    public int getSize() {
        return this.assets.size();
    }

    public Asset getAssetAtIndex(int index) {
        return this.assets.get(index);
    }

    public void addAsset(Asset i) {
        this.assets.add(i);
    }

    public void removeAsset(Asset i) {
        this.assets.remove(i);
    }
}