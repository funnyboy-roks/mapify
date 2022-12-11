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