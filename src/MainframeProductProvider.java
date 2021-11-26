import java.util.Random;

public class MainframeProductProvider implements ProductProvider {

    private Random random = new Random();

    @Override
    public Product get(String productId) {

        if (random.nextBoolean()) {
            throw new IllegalCallerException();
        }

        return new Product();
    }
}
