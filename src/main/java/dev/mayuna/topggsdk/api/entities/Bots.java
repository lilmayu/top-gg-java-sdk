package dev.mayuna.topggsdk.api.entities;

import dev.mayuna.topggsdk.api.TopGGAPIResponse;
import lombok.Getter;

/**
 * Search result with found bots in an array
 */
public class Bots extends TopGGAPIResponse {

    private @Getter Bot[] results;
    private @Getter int limit;
    private @Getter int offset;
    private @Getter int count;
    private @Getter int total;
}
