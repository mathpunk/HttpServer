package HttpServer.core.utility.logger;

public class StringDefinedInterval {
    public Integer lower;
    public Integer upper;

    public StringDefinedInterval(String definition) {

        int hyphenPosition = definition.indexOf("-");
        if (hyphenPosition == 0) {
            String upperString = definition.replace("-", "");
            setUpper(upperString);
        } else if (hyphenPosition == definition.length()-1) {
            String lowerString = definition.replace("-", "");
            setLower(lowerString);
        } else {
            String[] stringInterval = definition.split("-");
            setLower(stringInterval[0]);
            setUpper(stringInterval[1]);
        }
    }

    private void setUpper(String upperString) {
        upper = Integer.valueOf(upperString);
    }

    private void setLower(String lowerString) {
        lower = Integer.valueOf(lowerString);
    }
}
