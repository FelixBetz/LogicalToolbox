package de.arlab.formulas.parser;

import de.arlab.formulas.F;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormulaParserTest {

  private Parser parser = new Parser();

  @Test
  public void testParseFalsum() {
    assertEquals("0", parser.parseFormula("0").toString());
  }

  @Test
  public void testParseVerum() {
    assertEquals("1", parser.parseFormula("1").toString());
  }

  @Test
  public void testParseVariable() {
    assertEquals("x102", parser.parseFormula("x102").toString());
  }

  @Test
  public void testParseAVO1() {
    String expected = "((a0 AND a1) OR (NOT ((NOT a1) OR (a2 AND a3))))";
    assertEquals(expected, parser.parseFormula(F.avo1).toString());
  }

  @Test
  public void testParseAVO2() {
    String expected = "((((a0 AND a1) OR a2) AND (((NOT a1) OR (a2 OR ((NOT a3) OR b0))) OR b1)) OR ((NOT ((a1 AND (NOT b1)) AND (NOT b0))) AND ((NOT a3) OR (NOT a1))))";
    assertEquals(expected, parser.parseFormula(F.avo2).toString());
  }

  @Test
  public void testParseAVO3() {
    String expected = "(NOT ((x1 AND x3) OR (NOT (0 OR x4))))";
    assertEquals(expected, parser.parseFormula(F.avo3).toString());
  }

  @Test
  public void testParseAVO4() {
    String expected = "((NOT ((x1 AND 0) OR x2)) OR x3)";
    assertEquals(expected, parser.parseFormula(F.avo4).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException01() {
    parser.parseFormula("(NOT x1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException02() {
    parser.parseFormula("x1 AND x2 OR");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException03() {
    parser.parseFormula("x1 : x2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException04() {
    parser.parseFormula("x1 / x22 :");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException05() {
    parser.parseFormula("x1 / / x2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException06() {
    System.out.println(parser.parseFormula("x1 - x2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException07() {
    parser.parseFormula("+ x1 + x2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseException08() {
    parser.parseFormula("");
  }
}