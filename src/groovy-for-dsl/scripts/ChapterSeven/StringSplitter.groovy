@Grab(group='org.apache.commons', module='commons-lang3', version='3.0')
import org.apache.commons.lang3.StringUtils;

public class StringSplitter {

   public static void main(String[] args) {
      if (!args) {
          System.out.println("USAGE : StringSplitter string seperator");
          System.exit(0);
      }
      String [] splits = StringUtils.split(args[0], args[1]);

      for (int i = 0; i < splits.length; i++) {
         System.out.println("token : " + splits[i]);
      }
   }
}
