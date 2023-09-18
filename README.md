# Mapify

Mapify is a plugin that is used to convert your images into Minecraft maps that can be put in item frames or held in hand.

> For support, join my Discord: <https://discord.gg/qsUP2t5VpW>

## Commands

There are two commands: `/mapify` and `/refreshmaps`.

### `/mapify`

**Usage:** `/mapify <url> [dimensions]`

This will take a url and convert it into a set of maps, depending on the dimensions.

Dimensions are provided using the format `WIDTHxHEIGHT` (in terms of blocks).

Your images will be stretched to fit into the desired size.

### `/refreshmaps`

Refresh the renderers of all maps in your inventory.

This is only needed if the plugin's maps are not working.

If this does not help, feel free to ask on the Discord (linked above).

## Config

- Configurable whitelist using raw strings or regular expressions.
- Configurable cache duration
- Https only toggle

## Permissions

`mapify.command.mapify` - Permission to use the `/mapify` command - Default: OP

`mapify.command.refreshmaps` - Permission to use the `/refreshmaps` command - Default: OP

## FAQ

### Error message of "This is not a valid domain..."

You need to edit the config and add the domain that you wish to the
`whitelist` field.  You can add a domain, like `i.imgur.com` or a
regular expression like `REGEXP:.*\.google\.com` to match all domains
which end with `google.com`

This is to help with security on your server as having untrusted domains
can allow users to break your server and do bad things.  If you don't
want this protection, you can enable the `whitelist-is-blacklist` config
option and remove all entries from the `whitelist`.

### Error message of "An internal error occurred while attempting to perform this command"

When this message occurs in chat, there's a large error message that will
print to the console (usually red or yellow, if the console supports
colour).  Please join my Discord, tell me what command you ran, and send
the error message.
