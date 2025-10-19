# Mapify

Mapify is a plugin that is used to convert your images into Minecraft
maps that can be placed in item frames or held in hand.

To install the plugin, you need to add it into your `plugins/` folder
and start the server.  This will generate a [config](#config) that you
can configure and then reload with `/mapify reload`.

> For support, join my Discord: <https://discord.gg/qsUP2t5VpW>

![](./img/screenshot.png)
> Original photo is ["brown and black mountain under white clouds"](https://unsplash.com/photos/brown-and-black-mountain-under-white-clouds-80x3QULJDN4) by Tetiana Grypachevska

## Commands

There are two commands: `/mapify` and `/refreshmaps`.

### `/mapify`

**Usage:** `/mapify <url> [dimensions]`

This will take a url and convert it into a set of maps, depending on the dimensions.

Dimensions are provided using the format `WIDTHxHEIGHT` (in terms of blocks).

Your images will be stretched to fit into the desired size.

### `/mapify reload`

Reload config file

### `/refreshmaps [radius]`

Refresh the maps in your inventory or in item frames within a given
radius.

## Config

- Configurable whitelist using raw strings or regular expressions.
    - Regular Expressions are in the form of `REGEXP:.*\.example\.com`
    - Whitelist can be treated as a blacklist with the
      `whitelist-is-blacklist` field.
- Configurable cooldown
- Https only toggle

## Permissions

- `mapify.command.mapify` - Permission to use the `/mapify` command - Default: OP
- `mapify.command.mapify.reload` - Permission to use the `/mapify reload` command - Default: OP
- `mapify.command.refreshmaps` - Permission to use the `/refreshmaps` command - Default: OP
- `mapify.operator` - Permission to determine whether mapify should treat a player as an operator - Default: OP

## FAQ

### Error message of "This is not a valid domain..."

By default, the whitelist in the [`config.yml`](#config) needs to have
every domain from which a map should be allowed to be loaded.  If you're
a server admin, you can run `/mapify config whitelist add <domain>` as
suggested in the command.

This is to help with security on your server as having untrusted domains
can allow users to download malicious software on your server.  If you
don't want this protection, set `whitelist-disabled` to `true` in the
configuration.

### Error message of "An internal error occurred while attempting to perform this command"

Please join my Discord and give me the following information:
- The large error message that was printed in the server console
- The output of `/version`
- The version of Mapify that you are using

<!-- MODRINTH_EXCLUDE_START -->
## Development

To build the project, you'll need [maven](https://maven.apache.org/) and
JDK 17 installed.

Most Java IDEs support maven operations in them, but if you're using the
terminal, you'll need to do the following:

```sh
mvn install # install dependencies
mvn package # build project (into target/)
```

<!-- MODRINTH_EXCLUDE_END -->
