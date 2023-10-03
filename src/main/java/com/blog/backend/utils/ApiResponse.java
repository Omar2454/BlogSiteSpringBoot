package com.blog.backend.utils;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    private final Map<String, Object> response;

    public ApiResponse(boolean success, String message) {
        this.response = new HashMap<>();
        this.response.put("success", success);
        this.response.put("message", message);
    }

    public void setData(String key, Object value) {
        this.response.put(key, value);
    }

    public Map<String, Object> getResponse() {
        return this.response;
    }
}
