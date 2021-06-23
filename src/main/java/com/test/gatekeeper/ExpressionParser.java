package com.test.gatekeeper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExpressionParser {

    /**
     * Private Constructor
     */
    private ExpressionParser() {
    }

    /**
     * @param expression     The Expression sent as a string. Example -
     * @param userAttributes User Attributes shared as an input context.
     * @return Parsed Expression (which can be evaluated using Evaluator Interface)
     */
    public static Evaluator tokenize(String expression, Map<String, Object> userAttributes) throws Exception {
        if (expression.length() == 0) {
            throw new Exception("Invalid Expression");
        }

        List<String> params = tokenize(expression);

        return new EvaluatorImpl(params, userAttributes);
    }

    private static List<String> tokenize(String expression) throws Exception {

        List<String> params = Arrays.asList(expression.split(" "));

        if (!EvaluatorConstants.openBracket.equals(params.get(0))) {
            throw new Exception("Invalid Expression");
        }

        int bracketCount = 0;
        for (String param : params) {

            if (param.equals(EvaluatorConstants.openBracket)) {
                bracketCount++;
            } else if (param.equals(EvaluatorConstants.closedBracket)) {
                bracketCount--;
            } else if (EvaluatorConstants.operators.contains(param)) {
                // can add check if it's not an infix expression
                // pass
            } else {
                // can add check if it's not an infix expression
                // pass
            }
        }

        if (bracketCount > 0) {
            throw new Exception("Invalid Expression");
        }

        return params;
    }
}
