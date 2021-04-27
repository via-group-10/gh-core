package dk.grinhouse.api.exceptions;

public class CannotFindProfileID extends RuntimeException
{
  public CannotFindProfileID()
  {
    super("Cannot find profile ID");
  }
}
