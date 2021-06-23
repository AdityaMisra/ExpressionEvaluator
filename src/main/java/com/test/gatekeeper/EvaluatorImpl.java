package com.test.gatekeeper;


import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.test.gatekeeper.EvaluatorConstants.openBracket;


public class EvaluatorImpl implements Evaluator {

    private List<String> params;
    private Map<String, Object> userAttributes;

    public EvaluatorImpl(List<String> params, Map<String, Object> userAttributes) {
        this.params = params;
        this.userAttributes = userAttributes;
    }

    @Override
    public boolean evaluate() {

        Stack<String> stack = new Stack<>();

        for (String param : this.params) {
//            System.out.println(stack);

            if (param.equals(openBracket)) {
                stack.push(param);
            } else if (param.equals(EvaluatorConstants.closedBracket)) {
                processingClosedBracket(stack);
            } else if (EvaluatorConstants.operators.contains(param)) {
                stack.push(param);
            } else {
                stack.push(param);
            }
        }

        return Boolean.parseBoolean(stack.pop());
    }

    /**
     * updates the stack after processing
     * @param stack
     */
    private void processingClosedBracket(Stack<String> stack) {
        String rhs = stack.pop();
        String operator = stack.pop();
        String lhs = stack.pop();

        Object lhsValueFromMap = userAttributes.get(lhs);
        // it is an boolean value
        if (lhsValueFromMap == null) {
            lhsValueFromMap = Boolean.parseBoolean(lhs);
        }

        Boolean computedValue = false;

        if (lhsValueFromMap instanceof Number) {
            Integer rhObj = Integer.parseInt(rhs);
            Integer lhsObj = (Integer) lhsValueFromMap;

            computedValue = process(operator, lhsObj, rhObj);
        } else if (lhsValueFromMap instanceof String) {
            String lhsObj = (String) lhsValueFromMap;

            computedValue = process(operator, lhsObj, rhs);
        } else if (lhsValueFromMap instanceof Boolean) {
            Boolean lhsObj = Boolean.parseBoolean(lhsValueFromMap.toString());
            Boolean rhsObj = Boolean.parseBoolean(rhs);

            computedValue = process(operator, lhsObj, rhsObj);
        }

        stack.pop(); // removing open bracket
        stack.push(computedValue.toString());
    }

    /**
     * Processes the operand based in the operator passed
     * @param operator
     * @param lhs
     * @param rhs
     * @param <T>
     * @return boolean
     */
    private <T extends Comparable> boolean process(String operator, T lhs, T rhs) {
        boolean value = false;

        switch (operator) {

            case ">":
                value = Integer.parseInt(lhs.toString()) > Integer.parseInt(rhs.toString());
                break;
            case "<":
                value = Integer.parseInt(lhs.toString()) < Integer.parseInt(rhs.toString());
                break;
            case "<=":
                value = Integer.parseInt(lhs.toString()) <= Integer.parseInt(rhs.toString());
                break;
            case ">=":
                value = Integer.parseInt(lhs.toString()) >= Integer.parseInt(rhs.toString());
                break;
            case "==":
                value = lhs.equals(rhs);
                break;
            case "!=":
                value = !lhs.equals(rhs);
                break;
            case "&&":
                value = Boolean.parseBoolean(lhs.toString()) && Boolean.parseBoolean(rhs.toString());
                break;
            case "||":
                value = Boolean.parseBoolean(lhs.toString()) || Boolean.parseBoolean(rhs.toString());
                break;
        }
        return value;
    }
}
