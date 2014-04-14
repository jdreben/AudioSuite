<code>Draft Speculation

James Dreben, Mirhee Kim, Maria Emily Lovett, Vinh Nguyen
</code></pre>

<p>Brief Overview:
We hope to utilize algorithms to make a music editing suite, which focuses initially on Autotune. If this doesn't prove challenging, we will increase the functionality to include volume, pitch, voice, etc. We want to specify that files we will be editing should solely contain one singer. We will also focus on a single file type such as mp3 only.
Feature List:
Core:
● Convert music (.mp3) to editable variable (probably an object
that stores each note’s Hertz and amplitude )
● Edit list of Hertz (for autotune)
● Convert edited waves back to music
Extensions:
● Increase/Decrease volume (individual and overall)
● Increase/Decrease individual pitch
● Modulate music into different keys (changing all the pitches by
the same value)
● Change the voice/timbre (eg: ke$ha)
● Increase/decrease speed to .5x, 1.5x, 2x (user provides an int
and it adjusts by said amount)
● Chipmunk Version</p>

<p>Technical Specification:
The obvious breakdown of the project is converting the music into an editable state (whether an array or object), editing that state, and then converting back to a music file. We will most likely collaborate on the first step, so that each member understands how the overall project is implemented and because this will require much discussion as we attempt to find the best way to do this. The portion that can be cleanly separated is the actual editing step. Each member can work on a specific modifier.
￼However, autotune will obviously take precedence and will mostly likely be most algorithmically complex. Therefore we will probably assign at least two to work on it as well as discuss its implementation among the entire group. The conversion to the editable state will be the most problematic step. Converting back should be easier after this is achieved because it should just be the reverse of what we did.
Converting to music: The user will provide mp3 file. This part of the program will convert each second (or millisecond/half-second) of the file into a hertz value &amp; amplitude and store this probably in an array list (or whatever means seems fit).
Structure:
-Function that goes second by second through an mp3 file, applying the pitch detection algorithm and then stores the hertz value and amplitude
-Pitch detection algorithm - converts an mp3 file note into a hertz value and amplitude, notes with significantly less volume should be ignored (to get rid of background noise)</p>

<p>Autotune: We obviously need to do more research on Hertz and pitch before we can say how were really going to implement this. However, so far we think we will have predefined array (or whatever means seems fit) of every pitch in Hertz. With this we can compare the user’s input to the predefined array and change the user’s pitch to match the closest predefined pitch. This will effectively “autotune” even the worst singing. We will research the best way to predefine this array, but it will most likely be to the 440 pitch standard. However, if through our research it
￼
becomes clear that pitch can be adjusted purely through a mathematical algorithm, we will just write this math as a function.
Converting back: This could just be another function in the conversion to
part.
Structure
-function that builds an mp3 file out of an array of 1 second of Hertz values
File Structure:
AudioSuite - will act as the main, using all other files
Conversion - convert: takes a file as input -> array list
- convert_back : takes array list -> output file
Autotune - fix: will take an array list and fix the pitches accordingly - helper functions: will be used within Autotune’s fix
Other edits - will have one main function that changes the array</p>

<p>Next Steps:
Since we’re writing these algorithms in Java, we will research the best data types to store all the info.
The things we need to research soon is how the algorithms that Autotune uses actually work. We also want to gain a better understanding of the details of pitch.
We want to create a common repository in gitHub. (despite some members objections)</p>