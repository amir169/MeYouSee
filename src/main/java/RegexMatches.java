import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

    public static void main( String args[] ) {
        // String to be scanned to find the pattern.
        String line = "if ((a<b)>c)wlfhgei";
        String pattern = "\\s*if\\s*\\((.+)\\).*";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        while (m.find()) {

            System.out.println(m.group(0));
            System.out.println(m.group(1));
            System.out.println(m.start());
            System.out.println(m.end());
        }
    }
}