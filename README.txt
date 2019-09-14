Welcome to my creation. 

Watch the game on Youtube: https://youtu.be/1cMcnLu5n0w

--Introduction-- 

This game is a arcade-style zombie shooter game in which the player has to defend a swarm of
zombies that keep coming at him for 13 stages. The funny part is that the 13th stage never 
ends and so the game continues until the player loses all 10 lives.


--Explaining the Game Features--

So to start, the player must see how long he can defend the four barricades by shooting at
the zombies with a bow and arrow. Here are the things you should know:


	- There are three different types of zombies (look at Zombie file to see what exactly a Zombie 
	is and does)
	- Each zombie has a unique set of attributes (look at the ZombieEnum file)
	- After each kill, the player will receive some money which can be used to upgrade
	- You can upgrade your weapon's damage, weapon's reload time, and player's movement as well as 
	barricades
	- you can repair damaged barricades
	- Each stage has a unique set of probabilities of which type of zombies will spawn
	- At and after stage 8, each zombie has a unique set of power ups
		- Normal zombies: they teleport to a different lane (only once)
		- Fast zombies: they teleport directly to the barricade (be careful, they are quite scary)
		- Armored zombies: they regenerate health past their max health (shoot them quickly)
	- Maxing weapon damage allows for arrow penetration 
	- Maxing weapon reload allows player to shoot two arrow at once (I know, that's super cool!)
	- Player has 10 lives, if a zombie crosses a barricade, the lives drops by one. Once all lives
	are lost, game is over
	- At the end, type your name on the console to be added to the leaderboard.
	
	
--Code Implementation-- 	

--@images
Open @Src. You will see 5 packages/folders. @images folder contain (you guessed it!) the game images 
used (sprites, background, etc.). The @images.barricade folder contain all the images used to draw
the barricades.
 
--@zombie.game
Here you will see the core files that includes a @Game file where the gameloop and logic are located
as well as the main method that runs the actual game. @GlobalPosition contains a superclass that 
determines every object's global position within the game. @Score and @Tuple are special classes 
used to help me store the high scores (it takes in player's name and the time they lasted and 
extends Comparable. @Stages is the class that controls the different stages in the game. 
@TimerDisplay is a Timer class I created to store the time that the user takes.

--@zombie.game.input
Just contains the KeyInput class that extends KeyAdapter so that the game state changes as the user
presses buttons.

--@zombie.game.objects
Contains all the objects of the game. I have a @Zombie class with a @ZombieEnum and a 
@ZombieInterfaceas well as three different zombie subclasses (@ArmoredZombie, @FastZombie, 
@NormalZombie). The @Zombie class lists all the methods a zombie can do and when. The @Zombie 
subclasses overwrite the powerUp method because each one has a unique powerUp (neat, huh?). The 
@ZombieEnum contains the different attributes each zombie has. Then there is the @Barricade class
with the @BarricadeSeparator with the methods describing how to define the barricade states based
on what's occuring in the game (is the user repairing or upgrade it, are zombies hitting it, etc).
We have a @Weapon's class that describes the function of the Bow. Note that there is an @WeaponEnum
and @Turret class. The @WeaponEnum describes some attributes of the Bow, but it also includes a 
turret because I was supposed to add turrets to the game. I didn't want to remove them because 
in a later version of the game, I might include them (although not for this assignment), so I'll
just leave them there (they don't bother anyone, I promise). 


--Code Concepts--
1. Collections
I used collections (see @Game) to store the arrows and zombies currently in the gamefield. 
Specifically, I used an arraylist to help me iterate through all the arrows and zombies
and affect each state. The reason why I used arraylists is because of its ease to store and add 
an unspecified amount of things to it and the ease to remove it. It worked quite well

2. Inheritance/Subtyping
Originally, I decided to include 3 different zombie classes and an interface, each class aligning
with each zombie type. However, I then decided to use an enum because each zombie did the same
thing, but just with different values (the amount of health, how fast it walked, how hard it hits
the barricades, etc). But them, I decided to add the three classes regardless which extends the 
@Zombie class because I thought it would be cool if each zombie has a special powerup (and so each
zombie subclass overrides the powerup method). Moreover, a lot of objects classes are extending 
the GlobalPosition class which controls the position of the objects (as opposed to using complex/
laggy 2D-arrays.

3. I/O 
I used I/O to store user scores into a leaderboard and display them at the end of the game. As 
stated above, once the dies, they will enter their name and the time they lasted will be added to 
a database. At the end, the top 5 high scores will be shown. It's important to note that you type 
your name inside the console!

4. JUnit Testing
Go to @tests -> @myTests. Here you will see all the JUnit tests I've created. This checking for
the different scenarios of upgrading and seeing if they are actually, checking if zombies are being
affecting the damage/speed/etc., making sure it's not possible to do anything unless you have the 
sufficient amoutn of cash, etc. I also test to make sure zombies are being hit, barricades are 
being repaired correctly, etc. 


Well, if you've read this far, I thank you and I hope you have a lot of fun playing the game! Try
to  beat my high score of 13 minutes and 21 seconds!



--External Resource--

Concepts from this playlist 
(https://www.youtube.com/watch?v=fqdgrFuFZqU&list=PLWms45O3n--4t1cUhKrqgOLeHE_sRtr0S) has been used
in the initialize design process of this game. Major design and structural alterations have been 
made removing early in the development part, but I am citing this source anyway because there still
may be a residue of concepts (although very minor) that have been taught in this tutorial.