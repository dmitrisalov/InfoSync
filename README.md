# InfoSync
Created by Dmitri Salov, 2017

Notes
-----
- This tool wasa written in Java. All work was done in IntelliJ IDEA with its automatic building functions. Gradle or Maven are not used.
- The Dropbox API is used for storing files. Users will need to have a Dropbox account to use InfoSync.

Description
-----------
This simple command-line tool is used for synchronizing files across multiple machines with their respective filepaths using the user's
Dropbox account.

Example Usage
-------------
A user has a file used for storing save data for a video game with a file path of C:\Games\SaveData. Due to the nature of these
save files, the data stored in the file is changed every time the user plays the game. If the user has a second machine with the same
game installed, they will likely want to further their progress when playing said game on the second machine. To do this, the user must 
transfer the save file from their first machine to their second one by using a flash drive, email services, or cloud services. The user
must go through the process of recieving the file on their second machine, then moving it to its proper location "C:\Games\SavaData".
Through InfoSync, the user may bypass this tedious process by simply launching the tool from their first machine, uploading the save file,
launching InfoSync from their other machine, and synchronizing. This will quickly place the save file in the same directory on the second
machine that it can be found in on the first machine. From now on, the user may synchronize this file through InfoSync so that the most
up-to-date version of the file will be stored in their Dropbox account and on their machines.

Goals
-----
- One-time authorization of the user's Dropbox account -- COMPLETE
- Save authorization data so that only the first log-in is required -- COMPLETE
- Upload single files
- Synchronize files by either downloading or uploading the most recent version of them

Stretch Goals
-------------
- Upload and synchronize entire directories
