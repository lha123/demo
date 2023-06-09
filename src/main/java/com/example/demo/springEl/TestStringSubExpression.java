package com.example.demo.springEl;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestStringSubExpression {

    public static void main(String[] args) {
        String expressionStr = "'hello world'.toUpperCase().substring(1,5)";

        String expressionStr1 = "'hello world'.toUpperCase().substring(#start, #end)";
        //指定SpelExpressionParser解析器实现类
        ExpressionParser parser = new SpelExpressionParser();
        //解析表达式
        Expression expression = parser.parseExpression(expressionStr);
        Expression expression1 = parser.parseExpression(expressionStr1);

        //设置对象模型基础
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("start", 2);
        context.setVariable("end", 5);
        System.out.println(expression1.getValue(context));
        System.out.println(expression.getValue());
    }
}
