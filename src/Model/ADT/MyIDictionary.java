package Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Alex on 20.10.2017.
 */
public interface MyIDictionary<K,V>{
    boolean isEmpty();
    void put(K key, V value);
    V get(K key);
    void update(K key,V value);
    boolean isDefined(K key);
    HashMap<K, V> getDictionary();
    Collection<V> values();
    Set<K> keySet();

}
