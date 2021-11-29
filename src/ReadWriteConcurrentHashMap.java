import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteConcurrentHashMap<S, O> {
    private final ConcurrentHashMap<S, O> readWriteHashMap = new ConcurrentHashMap<S, O>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();


    public void putIfAbsent(S productId, O product) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();

        try {
            readWriteHashMap.putIfAbsent(productId, product);
        } finally {
            writeLock.unlock();
        }
    }

    public Product get(S productId) {
        Lock readLock = rwLock.readLock();
        readLock.lock();

        try {
            return (Product) readWriteHashMap.get(productId);
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        Lock readLock = rwLock.readLock();
        readLock.lock();

        try {
            return readWriteHashMap.size();
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsKey(S productId) {
        Lock readLock = rwLock.readLock();
        readLock.lock();

        try {
            return readWriteHashMap.get(productId) != null;
        } finally {
            readLock.unlock();
        }
    }

}


