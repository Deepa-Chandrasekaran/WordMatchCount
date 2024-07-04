import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length !=2) //Checking if two arguments - input file path and the predefined file path exists
        {
            System.out.println("ERROR : INPUT FILE and the PREDEFINED FILE need to be given as input");
            return;
        }
        String inputFile = args[0]; 
        String predefinedFile = args[1];

        try {
            wordMatchCount(inputFile, predefinedFile);
        } catch(IOException e)
        {
            System.out.println("ERROR : Reading files");
        }
    }
    private static void wordMatchCount(String inputFile, String predefinedFile) throws IOException
    {
        HashSet<String> wordsInPredefinedFile = new HashSet<>();
        HashMap<String, Integer> wordMatchCount = new HashMap<>();
        try(BufferedReader read = new BufferedReader(new FileReader(predefinedFile)))
        {       // Reading the predefined file line by line using BufferedReader and storing the words in the hashset
                // Using hashset to avoid duplicate entries and for easy lookup
            String lineTraversal;
            while((lineTraversal = read.readLine()) != null)
            {
                String word = lineTraversal.trim().toLowerCase();
                wordsInPredefinedFile.add(word);
                wordMatchCount.put(word,0); 
                // Storing each of these words in a hashmap and initializing the value to 0
            }
        }
        try (Stream<String> each = Files.lines(Paths.get(inputFile)))
        {       // Using Streams from java 8 to traverse through the input file and checking if the word from the
                // predefined file exists here
                // NOTE : Since the search is case insensitive, converting the words to lower case
                // If there is a match, incrementing the value of the word in the resultant hashmap by 1
            each.flatMap(line -> Stream.of(line.split("\\W+")))
            .map(String::toLowerCase)
            .filter(wordsInPredefinedFile::contains)
            .forEach(word -> wordMatchCount.put(word , wordMatchCount.get(word) + 1));
        }

        System.out.printf("%-25s %s%n", "Predefined word", "Match count");
                // Traversing through the resultant hashmap using EntrySet and printing the values 
        for (Map.Entry<String, Integer> entry :wordMatchCount.entrySet()) {
            System.out.printf("%-25s %d%n", entry.getKey(), entry.getValue());
        }
    } 
}
