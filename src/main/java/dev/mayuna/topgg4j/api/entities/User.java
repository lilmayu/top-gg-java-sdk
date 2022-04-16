package dev.mayuna.topgg4j.api.entities;

import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.Action;
import dev.mayuna.topgg4j.api.TopGGAPIResponse;
import lombok.Getter;

public class User extends TopGGAPIResponse {

    private @Getter String username;
    private @Getter String id;
    private @Getter String avatar;
    private @Getter String discriminator;
    private @Getter @SerializedName("defAvatar") String defaultAvatar;
    private @Getter String bio;
    private @Getter String banner;
    private @Getter Social social;
    private @Getter String color;
    private @Getter boolean supporter;
    private @Getter boolean certifiedDev;
    private @Getter boolean mod;
    private @Getter boolean webMod;
    private @Getter boolean admin;

    public Action<VoteStatus> fetchVoteStatus() {
        return api.fetchVoteStatus(id);
    }

    public static class Social {

        private @Getter String youtube;
        private @Getter String reddit;
        private @Getter String twitter;
        private @Getter String instagram;
        private @Getter String github;
    }
}
