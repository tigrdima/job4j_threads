package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(
                model.getId(), (k, v) -> {
                    v.setVersion(v.getVersion() + 1);
                    if (v.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    v.setName(model.getName());
                    return model;
                }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
