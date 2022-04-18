package dev.mayuna.topggsdk.api.entities;

import dev.mayuna.topggsdk.api.TopGGAPIResponse;

/**
 * Tells if user voted
 */
public class VoteStatus extends TopGGAPIResponse {

    private int voted;

    public boolean hasVoted() {
        return voted == 1;
    }
}
