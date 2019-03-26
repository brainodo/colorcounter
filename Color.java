package com.upsellit.colorcounter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Lee Dudley
 * Created 3/25/2019
 */
public enum Color {

    BLACK("Black", Collections.singletonList("black")),
    BLUE("Blue", Arrays.asList("QBLUE", "4LUE", "RLUE")),
    GRAY("Gray", Arrays.asList("grey", "G8EY", "AREY", "GJEY", "EGREY", "GMEY", "VGREY", "BGREY", "NREY", "3REY", "FREY")),
    GREY("Grey", Arrays.asList("grey", "G8EY", "AREY", "GJEY", "EGREY", "GMEY", "VGREY", "BGREY", "NREY", "3REY", "FREY")),
    GREEN("Green", Arrays.asList("GEEEN", "GRXEN", "GRREN", "NREEN", "GZREEN")),
    ORANGE("Orange", Arrays.asList("ORYNGE", "MRANGE", "ODANGE")),
    PURPLE("Purple", Arrays.asList("PU1PLE", "PUJPLE", "PUROPLE", "KPURPLE", "PURVPLE")),
    RED("Red", Arrays.asList("3ED", "DRED", "IED", "GED", "2ED", "YED")),
    UNKNOWN("Unkown", Collections.singletonList("N/A")),
    WHITE("White", Arrays.asList("WHHTE", "WHHITE", "6HITE", "W0ITE", "WJHITE", "WHTITE", "WHOTE")),
    YELLOW("Yellow", Arrays.asList("YEQLLOW", "YEL2OW", "FELLOW", "YEOLLOW", "YELSOW", "YTLLOW", "YELVOW")),;

    private final String prettyName;
    private final List<String> missSpellingsList;

    Color(String name, List<String> missSpellingList) {
        this.prettyName = name;
        this.missSpellingsList = missSpellingList;
    }

    List<String> getMissSpellingsList() {
        return missSpellingsList;
    }

    String getPrettyName() {
        return prettyName;
    }
}
