<p align="center">
  <h1 align="center"><a href="https://top.gg/">Tog.gg</a> Java SDK</h1>
</p>
<p align="center">
  <img src="http://ForTheBadge.com/images/badges/made-with-java.svg" alt="Made with Java">
  <br>
  <img src="https://img.shields.io/github/license/lilmayu/top-gg-java-sdk.svg" alt="License">
  <img src="https://img.shields.io/github/v/release/lilmayu/top-gg-java-sdk.svg" alt="Version">
</p>
<p align="center">
    Java wrapper for <a href="https://docs.top.gg/">Top.gg's API</a> using <a href="https://github.com/lilmayu/SimpleJavaAPIWrapper">Simple Java API Wrapper</a>
  <br>
  Made by <a href="https://mayuna.dev">Mayuna</a>
</p>

## Instalation
### Maven
```xml
<dependency>
    <groupId>dev.mayuna</groupId>
    <artifactId>top-gg-java-sdk</artifactId>
    <version>VERSION</version>
</dependency>

<!-- Gson is used for deserializing -->
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.9.0</version>
</dependency>
```
### Gradle
```gradle
repositories {
    mavenCentral()
}

dependencies {
    // Change 'implementation' to 'compile' in old Gradle versions
    implementation 'dev.mayuna:top-gg-java-sdk:VERSION'

    // Gson is used for deserializing
    implementation 'com.google.code.gson:gson:2.9.0'
}
```
- Replace `VERSION` with your desired version. (Remove "v" before version number)
- For version number see latest [Maven Repository](https://mvnrepository.com/artifact/dev.mayuna/top-gg-java-sdk) release (should be same with Github Release though)
- You can also use [GitHub Releases](https://github.com/lilmayu/top-gg-java-sdk/releases)

## Documentation
- [docs.mayuna.dev](https://docs.mayuna.dev/) - In making...
- [Javadoc](https://data.mayuna.dev/javadocs/top-gg-java-sdk/)

## Requirements
- Java 11 or newer
- [Gson](https://github.com/google/gson)

## How to use
You must have top.gg's token to authenticate within their API. You can get your token here: https://top.gg/bot/:bot_id/webhooks (replace :bot_id with your bot's ID on top.gg)

### `TopGGAPI`'s methods
```java
// Using constructor with bot's ID argument is recommended, but not mandatory.
TopGGAPI api = new TopGGAPI("token", "bot id");

api.searchBots(); // Used for searching on top.gg's page (overloaded method)

api.fetchBot(); // Fetches information about your bot
api.fetchBot("bot id"); // Fetches information about different bot

api.fetchLast1000Votes(); // Fetches the last 1000 voters for your bot
api.fetchBotStats() // Fetches specific stats about your bot.
api.fetchVoteStatus("user id") // Fetches status whether a user has voted for your bot

api.updateBotStats(); // Used for updating your bot's stats like server count and shard count (overloaded method)

api.fetchUser("user id"); // Fetches information about specified user
api.fetchMultiplierStatus(); // Checks if currently it is weekend (during weekends, bots receive double votes)
```

### Example usage
```java
int serverCount = ...; // Number of guilds the bot is on
int shardCount = ...; // Number of shards
TopGGAPI api = new TopGGAPI("token", "bot id");

api.updateBotStats(serverCount, shardCount).execute(); // #execute() returns CompletableFuture

api.fetchUser("680508886574170122").execute().thenAcceptAsync(user -> {
    System.out.println("Username: " + user.getUsername());
});

VoteStatus voteStatus = api.fetchVoteStatus("680508886574170122").execute().join();
if (voteStatus.hasVoted()) {
    System.out.println("User has voted.");
} else {
    System.out.println("User has not voted.");
}
```
**Do not forget to call `#execute()` on every API request**
