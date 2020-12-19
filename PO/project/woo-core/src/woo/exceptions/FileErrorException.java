package woo.exceptions;

/** Launched when there is a file reading error. */
public class FileErrorException extends Exception {
        
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201708301010L;

    /** Client key */
    private String _description;

    /**
    * @param name
    */
    public FileErrorException(String description) {
        _description = description;
    }

    /** @return key */
    public String getDescription() {
        return _description;
    } 
}
