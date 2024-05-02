package servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;

import java.io.IOException;

public abstract class Servicio {
    protected final ObjectMapper objectMapper;
    public Servicio() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public Response sendPost(String url, Object object) throws IOException {
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public Response sendGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }
}
