import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

// This function evaluates the postfix expression
public class Main
{

    // This function evaluates the postfix expression and returns the result
    public static int evaluatePostfix(ArrayList<String> postfix) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < postfix.size(); i++) {
            String c = postfix.get(i);

            // If the current character is '^', it represents a negative number
            if (c.equals("^")) {
                i++;
                if (i < postfix.size() && isNumber(postfix.get(i))) {
                    stack.push(-Integer.parseInt(postfix.get(i)));
                }
            } else if (isNumber(c)) {
                stack.push(Integer.parseInt(c));
                // If the current character is an operator, pop two values from the stack, apply the operator, and push the result back onto the stack
            } else {
                int num2 = stack.pop();
                int num1 = stack.pop();
                switch (c) {
                    case "+":
                        int add = num1 + num2;
                        int value = checkOverflow(add);
                        if (value == 0) {
                            stack.push(num1 + num2);

                } else {
                    System.out.println("Overflow!! ");
                    value = getOverflowValue(num1, num2);
                    stack.push(value);
                }
                break;

                    case "-":
                        int sub = num1 - num2;
                        int value1 = checkOverflow(sub);
                        if (value1 == 0) {
                            stack.push(num1 - num2);
                        } else {
                            System.out.println("Overflow!! ");
                            value1 = getOverflowValue1(num1, num2);
                            stack.push(value1);
                        }
                        break;
                    case "*":
                        int mul = num1 * num2;
                        int value2 = checkOverflow(mul);
                        if (value2 == 0) {
                            stack.push(num1 * num2);
                        } else {
                            System.out.println("Overflow!! ");
                            value2 = getOverflowValue2(num1, num2);
                            stack.push(value2);
                        }
                        break;


                    case "/":
                        int div = num1 / num2;
                        int value3 = checkOverflow(div);
                        if (value3 == 0) {
                            stack.push(num1 * num2);
                        } else {
                            System.out.println("Overflow!! ");
                            value3 = getOverflowValue3(num1, num2);
                            stack.push(value3);
                        }
                        stack.push(num1 / num2);
                        break;
                }
            }
        }
        return stack.pop();
    }


    // This function gets the overflow value of two numbers when added together
    private static byte getOverflowValue(int num1, int num2) {
        byte a = (byte) num1;
        byte b = (byte) num2;

        return (byte) (a + b);
    }
    // This function gets the overflow value of two numbers when subtracted together
    private static byte getOverflowValue1(int num1, int num2) {
        byte a = (byte) num1;
        byte b = (byte) num2;

        return (byte) (a - b);
    }
    // This function gets the overflow value of two numbers when multiplied together

    private static byte getOverflowValue2(int num1, int num2) {
        byte a = (byte) num1;
        byte b = (byte) num2;

        return (byte) (a * b);
    }
    // This function gets the overflow value of two numbers when divided

    private static byte getOverflowValue3(int num1, int num2) {
        byte a = (byte) num1;
        byte b = (byte) num2;

        return (byte) (a / b);
    }
    private static int checkOverflow(int result) {
        int num = result;
        if (num > Byte.MAX_VALUE || num < Byte.MIN_VALUE)
            return 1;
        return 0;
    }
    // This method converts an infix expression to a postfix expression
    public static ArrayList<String> convertInfixToPostfix(ArrayList<String> infix) {
        Stack<String> stack = new Stack<>();
        ArrayList<String> postfix = new ArrayList<String>();
        int i = 0;
        if (infix.get(i).equals("-")) {
            postfix.add("^");
            i++;
        }

        for (; i < infix.size(); i++) {
            String c = infix.get(i);

            if (c.equals("-") && i > 0 && infix.get(i - 1).equals("(")) {
                postfix.add(infix.get(i + 1));
                postfix.add("^");
                i++;
            } else if (isNumber(c)) {
                postfix.add(c);
            } else if (c.equals("(")) {
                stack.push(c);
            } else if (c.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;
    }
    // This method checks if a string is a number or not
    private static boolean isNumber(String c) {
        try {
            Double.parseDouble(c);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // This method returns the precedence of the operators
    public static int precedence
            (String c) {
        switch (c) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }


    public static void main(String[] args) {
        try {
            String str;
            do {
                System.out.println("****\n");
                System.out.println("Please Enter Infix Expression (or Enter to Exit):");
                BufferedReader bfn = new BufferedReader(
                        new InputStreamReader(System.in));
                str = bfn.readLine();
                if (str.isEmpty()) {
                    break;
                }
                ArrayList<String> str1 = convertToStringArray(str);
                ArrayList<String> postfix = convertInfixToPostfix(str1);
                StringBuilder sb = new StringBuilder();
                for (String st : postfix) {
                    sb.append(st);
                }
                System.out.println("Postfix Expression: " + sb);
                int result = evaluatePostfix(postfix);
                System.out.println("Output: " + result);
                System.out.println("****\n");
            } while (!str.isEmpty());

        } catch (IOException e) {
            System.out.println("ERROR! Invalid Input! Please Enter Valid Infix Expression " + e.getMessage());
        }
    }

    private static ArrayList<String> convertToStringArray(String str) {
        ArrayList<String> stringArrays = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        String c;
        for (int i = 0; i < str.length(); i++) {
            c = String.valueOf(str.charAt(i));
            if (isNumber(c)) {
                value.append(c);
            } else {
                if (value.length() > 0) {
                    stringArrays.add(value.toString());
                }
                stringArrays.add(c);
                value = new StringBuilder();
            }
        }
        if (value.length() > 0) {
            stringArrays.add(value.toString());
        }
        return stringArrays;
    }

}