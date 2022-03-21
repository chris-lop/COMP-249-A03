public class CSVDataMissing extends Exception {
    public CSVDataMissing() {
        super("Error: Input row cannot be parsed due to missing information");
    }
    
    public CSVDataMissing(String aString) {
        super(aString);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
