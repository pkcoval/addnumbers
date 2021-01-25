package app.addnumbers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NumberService {

  private final String CANNOT_GET_RANDOM_NUMBER_FROM_WEB_PAGE = "CANNOT GET RANDOM NUMBER FROM WEB PAGE";
  private final String NUMBER_TO_OPERATION_CANNOT_BE_NULL = "NUMBER TO OPERTION CANNOT BE NULL";
  private final String FIRST_NUMBER_IS = "FIRST NUMBER IS: ";
  private final String SECOND_NUMBER_IS = "SECOND NUMBER IS: ";
  private final String RESULT_IS = "RESULT IS: ";

  Object sumOfTwoNumbers(int min, int max) {
    int firstNumber = getRandomNumber(min,max);
    int secondNumber = getRandomNumberFromWebPage(min,max);
    return addTwoNumber(firstNumber, secondNumber);
  }

  private Object addTwoNumber(Object firstObject, Object secondObject) {
    String firstNumber;
    String secondNumber;
    if (firstObject != null && secondObject != null) {
      firstNumber = convertNumberToString(firstObject);
      log.info(FIRST_NUMBER_IS + firstNumber);
      secondNumber = convertNumberToString(secondObject);
      log.info(SECOND_NUMBER_IS + secondNumber);
      return getSumReturnValue(firstNumber, secondNumber);
    }
    log.error(NUMBER_TO_OPERATION_CANNOT_BE_NULL);
    throw new NullPointerException(NUMBER_TO_OPERATION_CANNOT_BE_NULL);
  }

  private double getSumReturnValue(String firstNumber, String secondNumber) {
    double result = Double.valueOf(firstNumber) + Double.valueOf(secondNumber);
    log.info(RESULT_IS + result);
    return result;
  }

  private String convertNumberToString(Object number) {
    DecimalFormat df = new DecimalFormat("#.#");
    return df.format(number);
  }

  private int getRandomNumber(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  private int getRandomNumberFromWebPage(int min, int max) {

    URL url = null;
    try {
      url = new URL("https://www.random.org/integers/?num=1&min=" + min + "&max=" + max + "&col=1&base=10&format=plain&rnd=new");
    } catch (MalformedURLException e) {
      log.error(CANNOT_GET_RANDOM_NUMBER_FROM_WEB_PAGE+ e.getMessage());
    }

    StringBuilder sb = new StringBuilder();
    try(Scanner sc = new Scanner(url.openStream())) {
      while(sc.hasNext()) {
        sb.append(sc.next());
      }
    } catch (IOException e) {
      log.error(CANNOT_GET_RANDOM_NUMBER_FROM_WEB_PAGE + e.getMessage());
    }

    String result = sb.toString();
    result = result.replaceAll("<[^>]*>", "");
    return Integer.valueOf(result);
  }
}
