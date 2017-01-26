import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class TestArith {

    private final int operand1;
    private final int operand2;
    private final String operation;
    private final int actualResult;

    public TestArith(final int operand1, final int operand2, final int actualResult, final String operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.actualResult = actualResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        final List<Object[]> cases = new LinkedList<>();

        Path filePath = Paths.get(new File("").getAbsolutePath(), "testdata", "data.txt");

        try (final Scanner reader = new Scanner(new File(filePath.toString()))) {
            while (reader.hasNext()) {
                final String[] raw = reader.nextLine().trim().split(";");
                final int operand1 = Integer.parseInt(raw[0]);
                final int operand2 = Integer.parseInt(raw[1]);
                final String operation = raw[2];
                final int actualResult = Integer.parseInt(raw[3]);
                cases.add(new Object[] { operand1, operand2, actualResult, operation });
            }
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't load data", e);
        }
        return cases;
    }

    @Test
    public void test() {
        final int expectedResult;
        switch(operation) { //I know that looks ugly - just quick solution
            case "+":
                expectedResult = operand1 + operand2;
                break;
            case "-":
                expectedResult = operand1 - operand2;
                break;
            case "/":
                expectedResult = operand1 / operand2;
                break;
            case "*":
                expectedResult = operand1 * operand2;
                break;
            default:
                throw new IllegalArgumentException("Unable to resolve operation");
        }

        assertEquals(expectedResult, this.actualResult);
    }
}