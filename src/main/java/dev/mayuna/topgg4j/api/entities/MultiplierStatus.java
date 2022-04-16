package dev.mayuna.topgg4j.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class MultiplierStatus extends TopGGAPIResponse {

    private @Getter @SerializedName("is_weekend") boolean weekend;

}
