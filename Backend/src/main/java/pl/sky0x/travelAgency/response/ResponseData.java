package pl.sky0x.travelAgency.response;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public class ResponseData {

    private final Map<String, Object> data;

    public ResponseData() {
        this.data = new IdentityHashMap<>();
    }

    public ResponseData(Map<String, Object> map) {
        this.data = map;
    }

    public static ResponseData create(String key, Object value) {
        return new ResponseData().addValue(key, value);
    }

    public ResponseData addValue(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public ResponseData removeValue(String key) {
        data.remove(key);
        return this;
    }

    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }

}
