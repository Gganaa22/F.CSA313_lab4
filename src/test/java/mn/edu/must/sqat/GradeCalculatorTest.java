package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    @Test
    @DisplayName("Ердийн оноонуудад үсгэн дүн зөв гарах ёстой (95→A, 85→B, 75→C, 65→D, 30→F)")
    void letterGradeNormalValues() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        String a = calc.letterGrade(95), b = calc.letterGrade(85), c = calc.letterGrade(75),
               d = calc.letterGrade(65), f = calc.letterGrade(30); // Act
        assertEquals("A", a);                                  // Assert
        assertEquals("B", b);
        assertEquals("C", c);
        assertEquals("D", d);
        assertEquals("F", f);
    }

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        String grade = calc.letterGrade(90.0);                 // Act
        assertEquals("A", grade);                              // Assert
    }

    @Test
    @DisplayName("Хязгаарын утгууд: 89.99→B, 60→D, 59.99→F, 0→F, 100→A")
    void letterGradeOtherBoundaries() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        String g8999 = calc.letterGrade(89.99);                // Act
        String g60 = calc.letterGrade(60.0);
        String g5999 = calc.letterGrade(59.99);
        String g0 = calc.letterGrade(0.0);
        String g100 = calc.letterGrade(100.0);
        assertEquals("B", g8999);                              // Assert
        assertEquals("D", g60);
        assertEquals("F", g5999);
        assertEquals("F", g0);
        assertEquals("A", g100);
    }

    @Test
    @DisplayName("letterGrade: 0-100-аас гарсан оноонд IllegalArgumentException шидэх")
    void letterGradeInvalidScoreThrows() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(100.01));
    }

    @Test
    @DisplayName("totalScore: 10, 40, 10, 10, 30 → 100 байх ёстой")
    void totalScoreValidCalculation() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        double total = calc.totalScore(10, 40, 10, 10, 30);    // Act
        assertEquals(100.0, total, 0.0001);                    // Assert
    }

    @Test
    @DisplayName("totalScore: сөрөг утга (att = -5) үед IllegalArgumentException шидэх")
    void totalScoreNegativeThrows() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(-5, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("totalScore: дээд хязгаараас хэтэрсэн утга (lab = 41) үед IllegalArgumentException шидэх")
    void totalScoreOverLimitThrows() {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    @ParameterizedTest
    @DisplayName("Parameterized: letterGrade — оноо {0} → {1}")
    @CsvSource({"95,A", "90,A", "89.99,B", "80,B", "79.99,C", "70,C", "69.99,D", "60,D", "59.99,F", "0,F", "100,A"})
    void letterGradeParameterized(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        String actual = calc.letterGrade(score);               // Act
        assertEquals(expected, actual);                        // Assert
    }

    @ParameterizedTest
    @DisplayName("Parameterized: totalScore зөв нийлбэр")
    @CsvSource({"10,40,10,10,30,100", "0,0,0,0,0,0", "5,20,5,5,15,50", "8.5,35,7,9.5,25,85"})
    void totalScoreParameterized(double att, double lab, double q1, double q2, double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        double actual = calc.totalScore(att, lab, q1, q2, exam); // Act
        assertEquals(expected, actual, 0.0001);                // Assert
    }

    @ParameterizedTest
    @DisplayName("Parameterized: totalScore талбар бүрийн сөрөг/хэтэрсэн утгад exception")
    @CsvSource({
        "-1,40,10,10,30", "10,-1,10,10,30", "10,40,-1,10,30", "10,40,10,-1,30", "10,40,10,10,-1",
        "10.1,40,10,10,30", "10,40.1,10,10,30", "10,40,10.1,10,30", "10,40,10,10.1,30", "10,40,10,10,30.1"
    })
    void totalScoreEachFieldInvalidThrows(double att, double lab, double q1, double q2, double exam) {
        GradeCalculator calc = new GradeCalculator();          // Arrange
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}