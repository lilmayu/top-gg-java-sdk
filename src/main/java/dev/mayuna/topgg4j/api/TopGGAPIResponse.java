package dev.mayuna.topgg4j.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.APIResponse;
import dev.mayuna.simpleapi.deserializers.GsonDeserializer;
import dev.mayuna.topgg4j.TopGGAPI;
import lombok.Getter;

public class TopGGAPIResponse extends APIResponse<TopGGAPI> implements GsonDeserializer {

    private @Getter String error = null;
    private @Getter @SerializedName("retry-after") long retryAfter = -1;

    public boolean wasSuccessful() {
        return error == null && retryAfter == -1;
    }

    @Override
    public Gson getGson() {
        return new Gson();
    }
}
