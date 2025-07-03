import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class M04Assignment2 {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java M04Assignment2 filename.java");
            return;
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            System.out.println("File " + args[0] + " does not exist");
            return;
        }

        System.out.println("The number of keywords in " + args[0] + " is " + countKeywords(file));
    }

    public static int countKeywords(File file) throws FileNotFoundException {
        String[] keywordArray = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false", "null"
        };

        Set<String> keywords = new HashSet<>(Arrays.asList(keywordArray));

        Scanner input = new Scanner(file);
        boolean inBlockComment = false;
        int count = 0;

        while (input.hasNextLine()) {
            String line = input.nextLine().trim();

            // block comment skips
            if (inBlockComment) {
                if (line.contains("*/")) {
                    inBlockComment = false;
                    line = line.substring(line.indexOf("*/") + 2);
                } else {
                    continue;
                }
            }

            // remove the line comments
            if (line.contains("//")) {
                line = line.substring(0, line.indexOf("//"));
            }

            // handle the block comment start
            if (line.contains("/*")) {
                inBlockComment = true;
                line = line.substring(0, line.indexOf("/*"));
            }

            // remove the string literals
            line = line.replaceAll("\".*?\"", " ");

            // tokenize and check for keywords
            String[] tokens = line.split("[\\s+{}();,]");
            for (String token : tokens) {
                if (keywords.contains(token)) {
                    count++;
                }
            }
        }

        input.close();
        return count;
    }
}
