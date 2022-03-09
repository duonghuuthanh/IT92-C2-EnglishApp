
import com.dht.junitdemo.Tester;
import java.time.Duration;
import java.util.InputMismatchException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author Admin
 */


public class TestCase01 {
    @BeforeAll
    public static void beforeAll() {
        System.out.println("BEFORE ALL");
    }
    
    @AfterAll
    public static void afterAll() {
        System.out.println("AFTER ALL");
    }
    
    @BeforeEach
    public void beforeEach() {
        System.out.println("BEFORE EACH");
    }
    
    @AfterEach
    public void afterEach() {
        System.out.println("AFTER EACH");
    }
    
    @Test
    @DisplayName("Kiem tra so nguyen to le")
    @Tag("critical")
    public void test01() {
        int n = 11;
        boolean expected = true;
        boolean actual = Tester.isNt(n);
        Assertions.assertEquals(expected, actual, "Kiem tra nguyen to le that bai");
    }
    
    @Test
    @DisplayName("Kiem tra so nguyen to chan")
    @Tag("normal")
    public void test02() {
        int n = 4;
//        boolean expected = false;
        boolean actual = Tester.isNt(n);
        Assertions.assertFalse(actual, "Loi so 4");
    }
    
    @Test
    @DisplayName("Kiem tra ngoai le")
    public void test03() {
        int n = -11;
        Assertions.assertThrows(InputMismatchException.class, () -> {
            Tester.isNt(n);
        });
    }
    
    @Test
    @DisplayName("Kiem tra thoi gian chay timeout")
    public void test04() {
        int n = 11;
        Assertions.assertTimeout(Duration.ofMillis(5), () -> {
            Tester.isNt(11);
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {4, 8, 20, 40, 144})
    public void test05(int n) {
        boolean actual = Tester.isNt(n);
        Assertions.assertFalse(actual);
    }
    
    @ParameterizedTest
    @CsvSource({"6,false", "5,true", "113,true", "143,false"})
    public void test06(int n, boolean expected) {
        boolean actual = Tester.isNt(n);
        Assertions.assertEquals(expected, actual, String.format("Dang test so %d", n));
    }
    
    @ParameterizedTest
//    @CsvSource({"6,false", "5,true", "113,true", "143,false"})
    @CsvFileSource(resources = "/data/testdata.csv", numLinesToSkip = 1)
    public void test07(int n, boolean expected) {
        boolean actual = Tester.isNt(n);
        Assertions.assertEquals(expected, actual, String.format("Dang test so %d", n));
    }
}
