# Minecraft Server Utility

Download: http://zgamelogic.com/MSU.jar
=======================================

What is it?
===========
This is a huge server utility for minecraft server owners. You're able to manage all kinds of minecraft servers from one window.

Why is it?
==========
Not only does it help in keeping everything in one spot, it also helps distribute and maintain custom modpack servers that you run.

How do?
=======
I have some tutorial videos on quick starting here:

Regular server management: [link soon]

Modded server management: [link soon]

Discord integration: [link soon]


How do? [text]
==============

This guide will take you through how to use the Minecraft Server Utility (MSU) to the best of your ability.

How to start
============
Simply place your MSU.jar anywhere you'd like and make a new bat file with this inside it: java -jar MSU.jar

When launched you'll be met with what we will call the summary screen. Here you will see your running servers, your connected players, the global chat log, and a command line. 
There is an alternative way to start your server, and that is to supply your MSU with a file in the start command. Example: java -jar MSU.jar startall.txt

This will go through every line in the startall.txt file and start all those servers based off of the lines inside (we will go over how to start servers next).

Start a minecraft server
=======================
To start a minecraft server, you can launch it either in the console or in a start file. Both will use the same syntax and rules regarding what you need to do. **One of those rules is if you require spaces in an argument, simply surround the whole argument with quotes, as the arguments are space delimited.**
Here is as simple as it gets: start Server "Server 1\server.jar"

Let's break it down:

	start: start command for the console to know how to start (this can be omitted for the start file)
	argument one: the first argument will be the name of the server
	argument two: the second argument is the file path to the jar of the server
	
What will it use to start the server? This is a good question. To answer that, let's talk about optional parameters and make things complicated. First off, a more friendly way of looking at the start command:

**start [server name] [file path to jar] [optional arguments]**

What are the optional arguments? Here is a list:

	-ip <server ip>
	-sc <start command/start bat>
	-fs <boolean for file server start when launched default:false>
	-gc <boolean for global chat or not default:true>
	
If the -sc argument is not provided, then the MSU will look for a bat file in the folder the server jar is located in to start the server. If there is none, it will launch with default arguments. -sc can be used to launch with arguments or a bat file like so:

-Bat file

start Server "Server 1\server.jar" -sc start.bat

-arguments

start Server "Server 1\server.jar" -sc "-Xmx 4G Xmn 1G -nogui"

Either will work just fine. -gc determines if the server will launch with global chat or not (this can be changed later). Default for this will be true. -fs is if the server should be launched with a file server. We will talk about this topic later. This is also a Boolean and default is false.

Lets look at an example start file called startall.txt

	"Server 1" "Server 1\server.jar" -gc true -fs true
	"Server 2" "Server 2\server.jar" -gc false
and the command to start the server: java -jar MSU.jar startall.txt

This will start 2 servers, with server 1 being on global chat and having a file server, and server 2 not being on global chat.

File server
==========

What is this for? This is for distributing modpacks that you make yourself as well as being able to update them. The client, which can be found in a different repository, will automatically install a modpack with the name of the server, and the server icon picture (if provided). It will also sync directories of files to the clients, the two most important being the mods and config folder.

Before you start the file server, lets talk a bit about setup. The first thing you need is the forge version installer jar you are running. Keep the file name the same and place it just in the modded servers directory. If the user does not have this forge version installed, it will download it and run the installer for them. 

Next, if you would like the profile on the minecraft launcher to have a custom icon, throw it in the same place as the forge version, named "server-icon.png". This will also change the server icon when players connect. The icon MUST be 128 by 128 pixels.

Next, go to the console and select the server using "**select [server name]**". This will  focus the server. Then type "**generateFSO**". This will generate the file server options file in the server directory. Please go through those options before launching the server.

Once this is all set, just type "**startFSO**" to start the file server. It will then proceed and wait for clients to connect to serve them the files needed for the modpack.

Discord bot
===========
More on this later, but for now if you know how to make a discord bot: add the bot to the discord server, create a text channel called "minecraft", and then put the login token for the bot in a file called bot.properties and put that file in the same place as the MSU.jar. 