package dev.mayuna.topgg4j.api.entities;

import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class VoteStatus extends TopGGAPIResponse {

    private int voted;

    public boolean hasVoted() {
        return voted == 1;
    }
}
