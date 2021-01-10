package apgrison.codingtask.model;

public enum FileFormat {
    JSON (".json"),
    XML(".xml");

    private final String extension;

    FileFormat(String extension) {
        this.extension = extension;
    }

    public static FileFormat getByName(String name) {
        if (name == null) {
            return null;
        }
        for (FileFormat fileFormat : values()) {
            if (name.toUpperCase().equals(fileFormat.name())) {
                return fileFormat;
            }
        }
        return null;
    }

    public String extension() {
        return extension;
    }
}
