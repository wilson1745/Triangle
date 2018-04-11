/* Print out the classification of a triangle, given three integer lengths
given on the command line.  With no arguments, run the unit tests. */
import java.math.*; 
import java.lang.*;
public class Triangle1 {
    String classify(String a, String b, String c) { 
        long x = number(a), y = number(b), z = number(c); 
        String type; 
        
        if (x == 0 || y == 0 || z == 0) type = "Illegal"; 
        else if (impossible(x, y, z)) type = "Impossible"; 
        else if (flat(x, y, z)) type = "Flat"; 
        else if (equilateral(x, y, z)) type = "Equilateral"; 
        else if (isosceles(x, y, z)) type = "Isosceles"; 
        else if (right(x, y, z)) type = "Right"; 
        else type = "Scalene"; 
        return type;
    }

    long number(String s) { 
        long x, m = Integer.MAX_VALUE;      // Integer.MAX_VALUE = 2147483647
        try { x = Long.parseLong(s); } 
        catch (Exception e) { x = 0; } 
        if (s.startsWith("0")) x = 0; 
        if (x < 1 || x > m) x = 0; 
        return x;
    }

    // Is a triangle impossible? Assume it is legal. 
    boolean impossible(long x, long y, long z) { 
        return x + y < z || x + z < y || y + z < x;
    }
    // Is a triangle flat? Assume it is legal. 
    boolean flat(long x, long y, long z) { 
        return x + y == z || x + z == y || y + z == x;
    } 
    // A 'normal' triangle is not illegal or impossible or flat.
    // Is a normal triangle equilateral? 
    boolean equilateral(long x, long y, long z) { 
        return x == y && y == z;
    }
    // Is a normal, non-equilateral triangle isosceles? 
    boolean isosceles(long x, long y, long z) { 
        return x == y || y == z || x == z;
    }
    // Is a normal triangle right-angled? 
    boolean right(long x, long y, long z) { 
        return x*x + y*y == z*z || x*x + z*z == y*y || y*y + z*z == x*x;
    }
    
    // Check an assertion (like assert, but without -ea and with numbering)
    void claim(boolean b) {
        if (!b) throw new Error("Test " + testNumber + " fails");
        testNumber++;
    }
    private int testNumber = 1;

    // Equilateral: all equal (test 1)
    void testEquilateral() {
        claim(classify("8", "8", "8").equals("Equilateral"));
    }

    // Isosceles: any two equal (tests 2 to 7)
    void testIsosceles() {
        claim(classify("5", "5", "3").equals("Isosceles"));
        claim(classify("5", "3", "5").equals("Isosceles"));
        claim(classify("3", "5", "5").equals("Isosceles"));
        claim(classify("5", "5", "7").equals("Isosceles"));
        claim(classify("5", "7", "5").equals("Isosceles"));
        claim(classify("7", "5", "5").equals("Isosceles"));
    }

    // Scalene: all three different (but not special) (tests 8 to 13)
    void testScalene() {
        claim(classify("12", "14", "15").equals("Scalene"));
        claim(classify("14", "12", "15").equals("Scalene"));
        claim(classify("12", "15", "14").equals("Scalene"));
        claim(classify("14", "15", "12").equals("Scalene"));
        claim(classify("15", "12", "14").equals("Scalene"));
        claim(classify("15", "14", "12").equals("Scalene"));
    }

    // Right-angled: Pythagoras's theorem (tests 14 to 19)
    void testRight() {
        claim(classify("5", "12", "13").equals("Right"));
        claim(classify("12", "5", "13").equals("Right"));
        claim(classify("5", "13", "12").equals("Right"));
        claim(classify("12", "13", "5").equals("Right"));
        claim(classify("13", "5", "12").equals("Right"));
        claim(classify("13", "12", "5").equals("Right"));
    }

    // Flat: two sides add up to the third (tests 20 to 26)
    void testFlat() {
        claim(classify("7", "7", "14").equals("Flat"));
        claim(classify("7", "14", "7").equals("Flat"));
        claim(classify("14", "7", "7").equals("Flat"));
        claim(classify("7", "9", "16").equals("Flat"));
        claim(classify("7", "16", "9").equals("Flat"));
        claim(classify("9", "16", "7").equals("Flat"));
        claim(classify("16", "7", "9").equals("Flat"));
    }

    // Impossible: two sides add up to less than the third (tests 27 to 29)
    void testImpossible() {
        claim(classify("2", "3", "13").equals("Impossible"));
        claim(classify("2", "13", "3").equals("Impossible"));
        claim(classify("13", "2", "3").equals("Impossible"));
    }

    // Illegal: a side is zero (tests 30 to 33)
    void testIllegal1() {
        claim(classify("0", "0", "0").equals("Illegal"));
        claim(classify("0", "10", "12").equals("Illegal"));
        claim(classify("10", "0", "12").equals("Illegal"));
        claim(classify("10", "12", "0").equals("Illegal"));
    }

    // Illegal: a side is negative (tests 34 to 37)
    void testIllegal2() {
        claim(classify("-1", "-1", "-1").equals("Illegal"));
        claim(classify("-1", "10", "12").equals("Illegal"));
        claim(classify("10", "-1", "12").equals("Illegal"));
        claim(classify("10", "12", "-1").equals("Illegal"));
    }

    // Illegal: invalid characters (tests 38 to 41)
    void testIllegal3() {
        claim(classify("x", "y", "z").equals("Illegal"));
        claim(classify("3x", "4", "5").equals("Illegal"));
        claim(classify("3", "4x", "5").equals("Illegal"));
        claim(classify("3", "4", "5x").equals("Illegal"));
    }

    // Illegal: the numbers must be integers, not starting with zeros (because
    // there is an ambiguity - this could indicate octal) (tests 42, 43)
    void testIllegal4() {
        claim(classify("3.0", "4.0", "5.0").equals("Illegal"));
        claim(classify("03", "04", "05").equals("Illegal"));
    }

    // Illegal: numbers outside of the int range are rejected (tests 44 to 47)
    void testIllegal5() {
        claim(classify(
            "2147483647", "2147483647", "2147483647").equals("Equilateral"));
        claim(classify(
            "2147483648", "2147483647", "2147483647").equals("Illegal"));
        claim(classify(
            "2147483647", "2147483648", "2147483647").equals("Illegal"));
        claim(classify(
            "2147483647", "2147483647", "2147483648").equals("Illegal"));
    }

    // Overflow: check that the program doesn't have overflow problems due to
    // using int, float or double. If there are overflow problems, the program
    // will say Equilateral (tests 48 to 50)
    void testIllegal6() {
        claim(classify(
            "1100000000", "1705032704", "1805032704").equals("Scalene"));
        claim(classify(
            "2000000001", "2000000002", "2000000003").equals("Scalene"));
        claim(classify(
            "150000002", "666666671", "683333338").equals("Scalene"));
    }

    // Run all the unit tests.
    void test() {
        testEquilateral();
        testIsosceles();
        testScalene();
        testRight();
        testFlat();
        testImpossible();
        testIllegal1();
        testIllegal2();
        testIllegal3();
        testIllegal4();
        testIllegal5();
        testIllegal6();
    }

    // Deal with the command line arguments
    void run(String[] args) {
        if (args.length == 0) test();
        else if (args.length == 3) {
            System.out.println(classify(args[0], args[1], args[2]));
        }
        else {
            System.err.println("Use:");
            System.err.println("  java Triangle         for testing or");
            System.err.println("  java Triangle a b c   three int lengths");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Triangle program = new Triangle();
        program.run(args);
    }
}
