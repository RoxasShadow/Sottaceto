Sottaceto
==============================================================
Game engine to make in a simple way Visual Novels for Android. I made a porting in HTML5/javascript for internet browsers too called [sottolio](https://github.com/RoxasShadow/sottolio).

How the engine works
-------------------

Main game class is `Game`. It makes an instance for each widget in its layout, loads the `keyvalues` (an Hashtable<String, String> where ingame variables are stored) and asks `Parser` to parse the script in order to make a `Stack` of `DialogueType` (that is the interface given to each `Types`).

The stack contains each command to execute in the game (show a text, invoke an event, do a condition, ask for choice or input, etc).

Then, `Dialogue.print` is invoked and is attached to the class a `onStop` method that stops all the musics and saves the `keyvalues`.


`Dialogue.print` first of all takes the current dialogue to run from the stack and discover what `Types` is it. `Dialogue.print` is a recursive method, and is invoked until the stack is not empty or there is a `GoTo`.

In this case, the activity dies and `Game` is recalled with another script.

How to make a game with Sottaceto
---------------------------------

Sottaceto allows you to make visual novels in a very simple way, without touching any type of programming languages.

Scripts are files which contains the flow of visual novel and you can understand how it is simple just reading the current script that you can find in `raw/script.xml`.

Images are stored in `drawable` while musics and sounds in `raw`. Strings are stored in `values`, just edit them like you want.

How you have surely understood, you have essentially edit `script.xml` and store resources like images, musics and sounds and compile all with Android SDK.

Going in the deep
-----------------

How you have surely seen in `script.xml`, characters can appears with animations.

Animations are handled by `AnimationHandler` and you can add more just adding their names in `AnimationHandler.AnimationType`, coding and storing them in `anim` and loading their respective resources in `AnimationHandler.execute`.

> In updating
