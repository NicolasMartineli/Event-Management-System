package repositories;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Repository<T> {
    protected Map<Integer, T> items = new LinkedHashMap<>();

    public void save(Integer id, T item) {
        items.put(id, item);
    }

    public Collection<T> findAll() {
        return items.values();
    }

    public T findById(Integer id) {
        return items.get(id);
    }

}

