import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Amir Shams on 2/28/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        FinalResult result = new FinalResult();
        ArrayList<Mutant> mutants = new ArrayList<>();

        ArrayList<String> code = readTheCode(args[0]);

        Mutator mutator = new Mutator(code);

        int startingLineNumber = 0;
        Mutant mutant = mutator.mutate(startingLineNumber);
        String name = args[3];
        result.setProgramName(name);

        while(mutant != null)
        {
            mutants.add(mutant);
            startingLineNumber = Integer.valueOf(mutant.getLineNumber());

            writeTheCode(name,code,mutant,args[1]);
            mutant = mutator.mutate(startingLineNumber);
        }

        result.setMutants(mutants);
        result.setTestCaseCount(Integer.valueOf(args[4]));
        createFinalResult(result,args[2]);
    }




    private static ArrayList<String> readTheCode(String path) throws IOException {
        ArrayList<String> result;
        result = (ArrayList<String>) Files.readAllLines(Paths.get(path),Charset.defaultCharset());

        return result;
    }

    private static void writeTheCode(String name,ArrayList<String> code,Mutant mutant, String address) throws IOException {
        address = address + "/" + name + "__" + mutant.getLineNumber() + ".c";
        BufferedWriter bw = new BufferedWriter(new FileWriter(address));

        int changedLineNumber = Integer.valueOf(mutant.getLineNumber()) - 1;

        for(int i = 0;i < code.size();i++) {
            if(i == changedLineNumber)
                bw.append(mutant.getMutantVersion()).append("\n");
            else
                bw.append(code.get(i)).append("\n");
        }

        bw.close();
    }

    private static void createFinalResult(FinalResult result, String destinationPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File(destinationPath),result);
    }
}
