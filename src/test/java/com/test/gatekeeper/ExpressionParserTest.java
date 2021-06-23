package com.test.gatekeeper;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {
    private Map<String, Object> userAttributes = new HashMap<>();

    ExpressionParserTest() {
        userAttributes = new HashMap<>();
        userAttributes.put("AGE", 44);
        userAttributes.put("GENDER", "male");
        userAttributes.put("IS_AFFLUENT", true);
    }

    @Test
    void testSingleExpression() throws Exception {
        String expression = "( AGE >= 25 )";
        final Evaluator parsedExpression = ExpressionParser.tokenize(expression, userAttributes);
        final boolean evaluate = parsedExpression.evaluate();
        assertTrue((Boolean) evaluate);
    }

    @Test()
    void testEmpty() {
        String expression = "";
        try {
            ExpressionParser.tokenize(expression, userAttributes);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid Expression");
        }


    }

    @Test()
    void testInvalidSingle() {
        String expression = "AGE >= 25";
        try {
            ExpressionParser.tokenize(expression, userAttributes);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid Expression");
        }
    }

    @Test
    void testInvalid() {
        String expression = "( AGE >= 25";
        try {
            ExpressionParser.tokenize(expression, userAttributes);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid Expression");
        }
    }

    @Test
    void testExpression1() throws Exception {
        String expression = "( ( AGE >= 25 ) && ( GENDER == male ) )";
        final Evaluator parsedExpression = ExpressionParser.tokenize(expression, userAttributes);
        assertTrue(parsedExpression.evaluate());
    }

    @Test
    void testExpression2() throws Exception {
        String expression = "( ( AGE > 25 ) && ( GENDER != female ) )";
        final Evaluator parsedExpression = ExpressionParser.tokenize(expression, userAttributes);
        assertTrue(parsedExpression.evaluate());
    }

    @Test
    void testExpression3() throws Exception {
        String expression = "( ( ( AGE > 25 ) && ( GENDER == male ) ) || ( IS_AFFLUENT == true ) )";
        final Evaluator parsedExpression = ExpressionParser.tokenize(expression, userAttributes);
        assertTrue(parsedExpression.evaluate());
    }

    @Test
    void testExpression4() throws Exception {
        String expression = "( ( AGE >= 25 ) && ( GENDER == female ) )";
        final Evaluator parsedExpression = ExpressionParser.tokenize(expression, userAttributes);
        assertFalse(parsedExpression.evaluate());
    }
}
