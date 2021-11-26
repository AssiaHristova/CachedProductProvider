import java.util.concurrent.*;

public class CachedProductProvider implements ProductProvider {

    private final MainframeProductProvider mainframeProductProvider;
    private Product product;

    public CachedProductProvider(MainframeProductProvider mainframeProductProvider) {
        this.mainframeProductProvider = mainframeProductProvider;
    }
    public ConcurrentHashMap<String, Object> cachedProductIdMap = new ConcurrentHashMap<>();

    public int getMapSize(ConcurrentHashMap<String, Object> cachedProductIdMap){
        return cachedProductIdMap.size();
    }

    @Override
    public Product get(String productId) {
        if (cachedProductIdMap.containsKey(productId)){
            product = (Product) cachedProductIdMap.get(productId);
        }
        else {
            while (product == null) {
                try {
                    product = mainframeProductProvider.get(productId);
                    if (product != null){
                        return product;
                    }}
                catch (RuntimeException e) {
                    continue;
                }
                return product;
            }
            cachedProductIdMap.putIfAbsent(productId, product);
            }

        return product;

    }
}
