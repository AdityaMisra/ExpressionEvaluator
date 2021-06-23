package com.test.gatekeeper;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 14/6/21, 7:22 PM
 * EvaluatorConstants.java
 *
 * @author aditya.misra
 */


public class EvaluatorConstants {

    public final static String openBracket = "(";
    public final static String closedBracket = ")";

    public final static List<String> operators = Arrays.asList(">", "<", "<=", ">=", "==", "!=", "&&", "||");
    public final static List<String> brackets = Arrays.asList(openBracket, closedBracket);
}
