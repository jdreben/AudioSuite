package music_test;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException
	{
		if (args.length != 2){
			System.out.println("Proper Usage: java Main change_file target_file");
			System.exit(0);
		}
		testing t = new testing(args[0], args[1]);
		t.pitch_shift();
		System.out.println("yay");
	}
}
