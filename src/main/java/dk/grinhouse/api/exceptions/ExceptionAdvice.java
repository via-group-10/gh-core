package dk.grinhouse.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice
{
  @ResponseBody
  @ExceptionHandler(InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  String InvalidCredentialsException(InvalidCredentialsException exception)
  {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidGreenhouseIDException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String InvalidGreenhouseIDException(InvalidGreenhouseIDException exception)
  {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidProfileId.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String InvalidProfileId(InvalidProfileId exception)
  {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(CannotFindProfileID.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String CannotFindProfileID (CannotFindProfileID exception)
  {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(WrongParameterException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String WrongParameterException (WrongParameterException exception)
  {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NoMeasurementsInDateRangeException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String WrongParameterException (NoMeasurementsInDateRangeException exception)
  {
    return exception.getMessage();
  }
}
