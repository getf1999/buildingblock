package com.getf.buildingblock.infrastructure.util;

import lombok.var;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class CollectionUtil {
    public static <T> T firstOrDefault(Collection<T> collection, Predicate<? super T> predicate){
        return collection.stream().filter(predicate).findAny().orElse(null);
    }
}
