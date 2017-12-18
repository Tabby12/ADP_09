package pareigis;


class BoundException
  extends Exception
{
  String str1;
 

  BoundException(String str2)
  {
    str1 = str2;
  }
  
  public String toString() { return "Error: " + str1; }
}
