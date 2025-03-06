package student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Operations enum.
 */
class OperationsTest {

    /**
     * Test the getOperator() method for each enum value.
     */
    @Test
    public void testGetOperator() {
        assertEquals("==", Operations.EQUALS.getOperator());
        assertEquals("!=", Operations.NOT_EQUALS.getOperator());
        assertEquals(">", Operations.GREATER_THAN.getOperator());
        assertEquals("<", Operations.LESS_THAN.getOperator());
        assertEquals(">=", Operations.GREATER_THAN_EQUALS.getOperator());
        assertEquals("<=", Operations.LESS_THAN_EQUALS.getOperator());
        assertEquals("~=", Operations.CONTAINS.getOperator());
    }

    /**
     * Test the fromOperator() method for each operator string.
     */
    @Test
    public void testFromOperator() {
        assertEquals(Operations.EQUALS, Operations.fromOperator("=="));
        assertEquals(Operations.NOT_EQUALS, Operations.fromOperator("!="));
        assertEquals(Operations.GREATER_THAN, Operations.fromOperator(">"));
        assertEquals(Operations.LESS_THAN, Operations.fromOperator("<"));
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.fromOperator(">="));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.fromOperator("<="));
        assertEquals(Operations.CONTAINS, Operations.fromOperator("~="));
    }

    /**
     * Test the fromOperator() method with an invalid operator string.
     */
    @Test
    public void testFromOperatorInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Operations.fromOperator("invalid");
        });
        assertEquals("No operator with name invalid", e.getMessage());
    }

    /**
     * Test the getOperatorFromStr() method for various strings containing operators.
     */
    @Test
    public void testGetOperatorFromStr() {
        assertEquals(Operations.EQUALS, Operations.getOperatorFromStr("rank == 3365"));
        assertEquals(Operations.NOT_EQUALS, Operations.getOperatorFromStr("rank != 3365"));
        assertEquals(Operations.GREATER_THAN, Operations.getOperatorFromStr("rank > 3365"));
        assertEquals(Operations.LESS_THAN, Operations.getOperatorFromStr("rank < 3365"));
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.getOperatorFromStr("rank >= 3365"));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.getOperatorFromStr("rank <= 3365"));
        assertEquals(Operations.CONTAINS, Operations.getOperatorFromStr("name ~= Clues"));
    }

    /**
     * Test the getOperatorFromStr() method with a string that does not contain a valid operator.
     */
    @Test
    public void testGetOperatorFromStrInvalid() {
        assertNull(Operations.getOperatorFromStr("invalid"));
    }
}