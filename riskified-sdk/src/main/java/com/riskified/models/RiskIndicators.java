package com.riskified.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RiskIndicators {
    private final Map<String, Object> properties = new HashMap<>();

    // Generic setter - accepts any field name and value
    public RiskIndicators set(String fieldName, Object value) {
        properties.put(fieldName, value);
        return this;
    }

    // Generic getter with type casting
    @SuppressWarnings("unchecked")
    public <T> T get(String fieldName, Class<T> type) {
        Object value = properties.get(fieldName);
        if (value == null) return null;
        return (T) value;
    }

    // Simple getter (returns Object)
    public Object get(String fieldName) {
        return properties.get(fieldName);
    }

    // Check if field exists
    public boolean has(String fieldName) {
        return properties.containsKey(fieldName);
    }

    // Get all defined fields
    public Set<String> getFields() {
        return properties.keySet();
    }

    // Remove field
    public RiskIndicators remove(String fieldName) {
        properties.remove(fieldName);
        return this;
    }

    // Get all properties
    public Map<String, Object> getAllProperties() {
        return new HashMap<>(properties);
    }

    @Override
    public String toString() {
        return "RiskIndicators" + properties;
    }
}

