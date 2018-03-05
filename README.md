This is a little project to scratch my own itch. This should not be considered a code sample at this point, or supported software for that matter.

For crying out loud, it doesn't even have tests! The architecture is hideous! It's ugly! It has no tests!

But it's heading down the path of implementing http://markforster.squarespace.com/autofocus-system/ for my own purposes. While I use Zim Wiki for my personal documentation, it's not doing the job I need for managing a large personal backlog. 

Why JavaFX? Why Java? I've been asking myself that question, and I might choose to restart this twenty times. However, I wanted a desktop app. I write enough webapps on a daily basis; for a moment, I don't want to think about security, or deal with the hack that is electron. JavaFX just works, and I can do everything without a library. A switch to kotlin might come sooner than later, but I might as well move to pyQT or GTK and golang... 

A todo list is a weird itch to scratch... 

Quick notes: It uses Xodus from JetBrains and by default puts the database in ~/.organizeme . To build, use `gradle fatJar`. Since I'm running this on i3, I'm sure the proportions are wildly weird. 