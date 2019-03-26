package com.upsellit.colorcounter;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

/**
 * ColorCounter application
 *
 * @author Lee Dudley
 * Created: 3/25/2019
 */
class ColorCounter {

    private final String fileLocation;

    private String errorMsg;
    private URL url;
    private File file;

    private Map<Color, Long> resultMap;

    ColorCounter(String fileLocation) {
        this.fileLocation = fileLocation;
        this.errorMsg = null;
        this.resultMap = new HashMap<>();
    }

    void load() {
        if (fileLocation.toLowerCase().startsWith("http")) {
            url = null;
            try {
                new URL(fileLocation);
            } catch (MalformedURLException murle) {
                errorMsg = "Invalid URL to file";
            }

        } else {
            file = new File(fileLocation);
            if (!file.exists() || !file.canRead() || !file.isFile()) {
                errorMsg = "Invalid file loaded";
            }
        }
    }

    void process() {
        try {
            if (url != null) {
                processURL();
            } else {
                processFile();
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    private void processURL() {
        // todo
        throw new UnsupportedOperationException("Processing File by URL is not supported yet");
    }

    private void processFile() throws RarException, IOException {
        Archive archive = new Archive(file);
        archive.getMainHeader().print();
        FileHeader fh = archive.nextFileHeader();
        while (fh != null) {
            File fileToParse = new File(fh.getFileNameString().trim());
            FileOutputStream os = new FileOutputStream(fileToParse);
            archive.extractFile(fh, os);
            os.close();
            fh = archive.nextFileHeader();

            parseFile(fileToParse.getAbsolutePath());
        }
    }

    private void parseFile(String fileToParse) throws FileNotFoundException {
        FileReader fileReader = new FileReader(fileToParse);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNext()) {
            String text = scanner.next();
            if (text != null && text.length() > 0) {
                final String textSearch = text.trim().toUpperCase();
                Color color = null;
                try {
                    color = Enum.valueOf(Color.class, textSearch);
                } catch (IllegalArgumentException iae) {
                    // not an exact match so we will try to find it:
                }

                if (color == null) {
                    for (Color aColor : Color.values()) {
                        int nameSize = aColor.name().length();
                        if (textSearch.startsWith(aColor.name().substring(0, 3)) || textSearch.endsWith(aColor.name().substring((nameSize - 2), nameSize))) {
                            color = aColor;
                            break;
                        }

                        List<String> missSpellingList = aColor.getMissSpellingsList();
                        for (String aMisSpell : missSpellingList) {
                            if (textSearch.equals(aMisSpell.toUpperCase())) {
                                color = aColor;
                                break;
                            }
                        }
                    }

                    if (color == null) {
                        color = Color.UNKNOWN;
                        System.out.println("Log Failed To Match: " + textSearch);
                    }
                }

                // convert to standard "gray"
                if (Color.GREY.equals(color)) {
                    color = Color.GRAY;
                }

                if (resultMap.containsKey(color)) {
                    long count = resultMap.get(color) + 1;
                    resultMap.put(color, count);
                } else {
                    resultMap.put(color, 1L);
                }

            }
        }
    }

    boolean hasError() {
        return errorMsg != null && errorMsg.length() > 0;
    }

    String getErrorMsg() {
        return errorMsg;
    }

    String getDisplayResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("Color Count Results:\r\n");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        for (Color aColor : resultMap.keySet()) {
            long result = resultMap.get(aColor);
            sb.append(aColor.getPrettyName()).append(": ").append(numberFormat.format(result)).append("\r\n");
        }
        return sb.toString();
    }

}
