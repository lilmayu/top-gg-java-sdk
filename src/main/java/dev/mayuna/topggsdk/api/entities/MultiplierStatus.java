package dev.mayuna.topggsdk.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.topggsdk.api.TopGGAPIResponse;
import lombok.Getter;

/**
 * Tells if currently is weekend (during weekends, bots receive double votes).
 */
public class MultiplierStatus extends TopGGAPIResponse {

    private @Getter @SerializedName("is_weekend") boolean weekend;

}
