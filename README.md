# ytplaylist-
Goes through a Youtube channel to obtain its playlists' video list

This piece of software goes through Youtube to fetch playlists and its videos from a user, creates a text file containing all videos' name.
This allows users to track their or others Youtube's playlists without the fear of having some of its videos forgotten after getting deleted by Youtube/owner.

Uses Maven with these plugins :
  - Jsoup (HTTP Parser)
  - JUnit
  - Launch4j (creates .exe)
  - Some swing components
  
The GUI allows you to create these text files easily. Just enter a Youtube Channel username (https://www.youtube.com/user/[name]), enter search, choose your playlists by either Shift clicking (from one playlist to another) or Ctrl clicking (adds to your selection) and hit enter. It will then prompt you with a new folder under {home}\ytplaylist++ with playlists' text files.

<b>TODO :</b>
- Working .exe (For some reason, launch4j doesn't create running GUI .exe on Eclipse ?)
- If you have any suggestion let me know

Contact : louis.krempp@tuta.io
