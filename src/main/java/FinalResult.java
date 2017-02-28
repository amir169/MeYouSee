import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Amir Shams on 2/28/2017.
 */
public class FinalResult {

    @JsonProperty("ProgramName")
    private String programName;

    @JsonProperty("TestCaseCount")
    private int testCaseCount;

    @JsonProperty("Mutants")
    private ArrayList<Mutant> mutants;

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getTestCaseCount() {
        return testCaseCount;
    }

    public void setTestCaseCount(int testCaseCount) {
        this.testCaseCount = testCaseCount;
    }

    public ArrayList<Mutant> getMutants() {
        return mutants;
    }

    public void setMutants(ArrayList<Mutant> mutants) {
        this.mutants = mutants;
    }

}
