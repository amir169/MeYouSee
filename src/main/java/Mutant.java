import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amir Shams on 2/28/2017.
 */
public class Mutant {

    @JsonProperty("LineNumber")
    private int lineNumber;

    @JsonProperty("ColdenVersion")
    private String goldenVersion;

    @JsonProperty("MutantVersion")
    private String mutantVersion;

    @JsonProperty("FailExecutionCount")
    private String failExecutionCount;

    @JsonProperty("LastedTime")
    private double lastedTime;


    public String getGoldenVersion() {
        return goldenVersion;
    }

    public void setGoldenVersion(String goldenVersion) {
        this.goldenVersion = goldenVersion;
    }

    public String getMutantVersion() {
        return mutantVersion;
    }

    public void setMutantVersion(String mutantVersion) {
        this.mutantVersion = mutantVersion;
    }

    public String getFailExecutionCount() {
        return failExecutionCount;
    }

    public void setFailExecutionCount(String failExecutionCount) {
        this.failExecutionCount = failExecutionCount;
    }

    public double getLastedTime() {
        return lastedTime;
    }

    public void setLastedTime(double lastedTime) {
        this.lastedTime = lastedTime;
    }

    public int getLineNumber() {

        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
