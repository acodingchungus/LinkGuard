package plutohacks;

//imports necessary methods
import java.util.Scanner;
import java.net.URI; //splits URL into different parts (scheme, host, path) without visiting link
import java.net.URISyntaxException;


//class start
public class LinkGuard {
	
	//start of main method
	public static void main(String[] args)
	{
		//declare scanner object
		Scanner input = new Scanner(System.in);
		
		//program introduction
		System.out.println("=== LinkGuard ===");
		System.out.println("Looks at a URL's text for warning signs. It never opens the link.");
		
		//keeps track of whether user wants to analyze another URL
		boolean keepGoing = true;
		
		//loop start; repeats until keepGoing is false
		while (keepGoing)
		{
			
			//prompt user to enter a URL
			System.out.print("Enter a URL: ");
			
			//initialize a string variable name 'URL' to receive user input
			String url = input.nextLine().trim();
			
			//show user the URL they entered 
			System.out.println("You entered: " + url);
			
			//initialize int warning variable to count how many warnings are found
			int warnings = 0;
			
			//output to user
			System.out.println("\nChecks: ");
			
			//if branch; logic determined by whether url starts with http or https
			if (usesPlainHttp(url)) //if link starts with http
			{
				System.out.println("	[!] Uses HTTP, not HTTPS: information sent to this site is not encrypted.");
				warnings++;
				
			} else //if it starts with https
			{
				System.out.println("	[ok] Does not use plain HTTP. ");
			}
			
			//declare URI variable which holds different url parts
			URI uri;
			
			//try to turn url text into a URI; can fail if text has odd characters or spaces
			try
			{
				uri = new URI(url);
			}
			
			//if try fails, throw URISyntaxExecption; catches here instead of crashing
			catch (URISyntaxException e)
			{
				System.out.println("	Couldn't read that as a URL (check for spaces and odd characters).");
				//skip the rest of this loop round and go back to "Enter a URL"
				continue;
			}
			
			//if no host, then user forgot http:// or https://
			if (uri.getHost() == null)
			{
				System.out.println("	Note: include http:// or https:// so LinkGuard can find the host.");
			}
				
			//if for authority
			if (hasAtInAuthority(uri))
			{
				//alert user about url destination
				System.out.println("	[!] Has an @ before the host: real destination is AFTER the @.");
				//increment warnings count
				warnings++; 
			}
			else //no hidden authority/destination
			{
				System.out.println("	[ok] NO @ hiding the real host.");
			}
			
			//if for numeric ip address in url
			if (hostIsNumericIp(uri))
			{
				//notify user if ip presence
				System.out.println("	[!] Uses a numeric IP address instead of a normal name.");
				//increment warnings count
				warnings++;
			}
			else //if host doesn't have digits or dots
			{
				System.out.println("	[ok] Host is not a numeric IP.");
			}
			//output warning count then caution level
			System.out.println("\nWarning signs found: " + warnings + "\nCaution level: " + cautionLevel(warnings));
			
			//safety disclaimer
			System.out.println("\nNote: LinkGuard only checks a few signs in the text.\n\nNo warnings does NOT guarantee a link is safe."
					+ " When in doubt, don't click.");
			
			//ask if user wants to check another URL
			System.out.print("\nAnalyze another URL? (y/n): ");
			String answer = input.nextLine().trim().toLowerCase();
			
			//compare strings with .equals not ==
			if (!answer.equals("y"))
			{
				keepGoing = false; //anything other than 'y' ends the loop
			}
			
		} //while loop end
		
		//goodbye message to user after loop ends
		System.out.println("\nGoodbye! Be careful with what links you click.");
		
		//close scanner object
		input.close();
		
	}
	
	//method for if link is 'HTTP'
	public static boolean usesPlainHttp(String url) //receives string argument from 'url'
	{
		return url.toLowerCase().startsWith("http://");
	}
	
	//method for checking url authority, specifically checks for if the url includes '@'
	public static boolean hasAtInAuthority(URI uri) //receives URI object from main
	{
		//get authority from url
		String authority = uri.getRawAuthority();
		
		//check for null first to prevent crash and return authority value
		return authority != null && authority.contains("@");
	}
	
	//method for checking if url is only made of digits and dots (numbers & .)
	public static boolean hostIsNumericIp(URI uri) //receives URI object from main
	{
		//get host from url. ex: 'example.test' || '192.0.0.10'
		String host = uri.getHost();
		
		//if no host, then false, because it can't be ip
		if (host == null || host.isEmpty())
		{
			return false;
		}
		
		//loop through host character 1 at a time, i = position
		for (int i = 0; i < host.length(); i++)
		{
			//grabs char at position = 'i'
			char c = host.charAt(i);
			
			//if char is not digit nor dot, its normal name
			if (!Character.isDigit(c) && c != '.')
			{
				return false; // stop early, not ip
			}
		}
		
		//if loop finishes without finding letter or symbol, so it's all digits and dots
		return true;
	}
	
	//method that turns number of warnings into a caution level
	public static String cautionLevel(int warnings) //receives argument from warnings
	{
		//no warning signs found
		if (warnings == 0)
		{
			return "LOW";
			
		}
		//one warning found
		else if (warnings == 1)
		{
			return "MEDIUM";
		}
		//two or more warnings found
		else
		{
			return "HIGH";
		}
	}
}
