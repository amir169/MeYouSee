import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amir Shams on 2/28/2017.
 */
public class Mutator {

    private ArrayList<String> code;

    Mutator(ArrayList<String> code) {
        this.code = code;
    }

    Mutant mutate(int startingLineNumber)
    {
        long start = System.nanoTime();
        Mutant result = new Mutant();

        if(startingLineNumber >= code.size())
            return null;

        boolean found = false;
        for(int i=startingLineNumber;i<code.size();i++)
        {
            String predicate = getPredicate(code.get(i));
            String assignment = getAssignment(code.get(i));

            String expression = "";
            String mutatedExpression = "";

            if(predicate != null)
            {
                expression = predicate;
                mutatedExpression = mutatePredicate(predicate);
            }
            else if(assignment != null)
            {
                expression = assignment;
                mutatedExpression = mutateAssignment(assignment);
            }
            else
                continue;

            if(mutatedExpression == null)
                continue;

            result.setGoldenVersion(code.get(i));
            result.setMutantVersion(code.get(i).replace(expression,mutatedExpression));
            result.setLineNumber(i + 1);
            found = true;
            break;

        }

        if(!found)
            return null;

        long end = System.nanoTime();
        Double time = (double) ((end - start) / 1000000);
        result.setLastedTime(time);
        result.setFailExecutionCount(String.valueOf(result.getLineNumber()));

        return result;
    }


    private String getPredicate(String line) {
        Pattern ifPattern = Pattern.compile("\\s*if\\s*\\((.+)\\).*");
        Pattern whilePattern = Pattern.compile("\\s*while\\s*\\((.+)\\).*");
        Pattern forPattern = Pattern.compile("\\s*for\\s*\\(.*;(.+);.*\\).*");

        Matcher ifMatcher = ifPattern.matcher(line);
        Matcher whileMatcher = whilePattern.matcher(line);
        Matcher forMatcher = forPattern.matcher(line);

        if(ifMatcher.find())
            return ifMatcher.groupCount() == 1?ifMatcher.group(1):null;

        if(whileMatcher.find())
            return whileMatcher.groupCount() == 1?whileMatcher.group(1):null;

        if(forMatcher.find())
            return forMatcher.groupCount() == 1?forMatcher.group(1):null;

        return null;
    }

    private String mutatePredicate(String predicate) {

        String[] operators = {"||","&&","==","!=","<=",">=","<"};
        String[] suitableMutants = {"&&","||","!=","==","<",">","<="};

        for(int i=0;i<operators.length;i++)
            if(predicate.contains(operators[i]))
                return predicate.replace(operators[i],suitableMutants[i]);

        if(predicate.contains(">") && !predicate.contains("->"))
            return predicate.replace(">",">=");

        if(predicate.contains("!"))
            return predicate.replace("!","");

        return "!" + predicate;
    }

    private String getAssignment(String line) {

        Pattern assignmentPattern = Pattern.compile(".*=\\s*(.*);");
        Pattern incrementPattern = Pattern.compile(".*(\\++).*\\s*;");
        Pattern decrementPattern = Pattern.compile(".*(--).*\\s*;");
        Pattern shortAssignmentPattern = Pattern.compile("(.*)=.*");

        Matcher assignmentMatcher = assignmentPattern.matcher(line);
        Matcher incrementMatcher = incrementPattern.matcher(line);
        Matcher decrementMatcher = decrementPattern.matcher(line);
        Matcher shortAssignmentMatcher = shortAssignmentPattern.matcher(line);

        if(assignmentMatcher.find())
            return assignmentMatcher.groupCount() == 1?assignmentMatcher.group(1):null;

        if(incrementMatcher.find())
            return incrementMatcher.groupCount() == 1?incrementMatcher.group(1):null;

        if(decrementMatcher.find())
            return decrementMatcher.groupCount() == 1?decrementMatcher.group(1):null;

        if(shortAssignmentMatcher.find())
            return shortAssignmentMatcher.groupCount() == 1?shortAssignmentMatcher.group(1):null;

        return null;
    }

    private String mutateAssignment(String assignment) {
        String[] operators = {"|","&","++","--","+"};
        String[] suitableMutants = {"&","|","--","++","-"};

        for(int i=0;i<operators.length;i++)
            if(assignment.contains(operators[i]))
                return assignment.replace(operators[i],suitableMutants[i]);

        if(assignment.contains("-") && !assignment.contains("->"))
            return assignment.replace("-","+");

        return null;
    }
}
