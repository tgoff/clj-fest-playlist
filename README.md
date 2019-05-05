# clj-fest-playlist

A very crude and simple utility to generate a Spotify playlist for a music festival.

I noticed that official playlists for music festivals tend to be a few songs from the headliners and not much more... I wanted to create a playlist of as many artists as I could, and with more than a single track from each... I used to do this manually, but that got tedious to I scripted it.  This app will grab the top 5 most popular tracks for the first artist that comes up for each provided search string.  It will provide output to help hone your search strings before committing to a playlist.

This application is an example only and I make no guarantees that it wont mess something up or somehow create a playlist full of Rick Astley... but it does the job for me.  


## Installation

Download from http://example.com/FIXME.

## Usage

Set SPOTIFY_TOKEN and SPOTIFY_USER env vars before running.
Add band list and playlist name to an EDN file (see samples under `data/`).  I found that usually you can select the band list from the festival website and then use some VIM-foo to add quotes and properly format it.  

    $ java -jar clj-fest-playlist-0.1.0-standalone.jar config-file-name.clj
OR
    $ lein run config-file-name.clj

list of bands not found in spotify will be displayed followed by a list of bands found with their spotify name.  Review, and make changes to the config file as needed.  if an artist is in spotify but not being found by the search, you can manually add its spotify ID to the `:finicky-artist-ids` list.  You will be prompted whether to continue with the playlist creation.  `yes` will create the playlist, anything else will exit.



## Options


## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
