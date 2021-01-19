package client;

public class HelperFunctionsClient 
{
	public static boolean isCorrectName(String name)
	{
		if (name.isEmpty()) {
			return false;
		}
		
		char[] letters = name.toCharArray();
		
		if (!Character.isUpperCase(letters[0])) {
			return false;
		}
		
		for (int i=0; i<letters.length; i++)
		{
			if (!Character.isLetter(letters[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isCorrectEmail(String email)
	{
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		return email.matches(regex);
	}

	public static boolean isCorrectPhoneNumber(String phoneNumber)
	{
		if (phoneNumber.length() == 9) {
			char[] digits = phoneNumber.toCharArray();
			for (int i=0; i<9; i++)
			{
				if (!Character.isDigit(digits[i])){
					return false;
				}
			}
			
			return true;
		}
		
		else {
			return false;
		}
	}
}
