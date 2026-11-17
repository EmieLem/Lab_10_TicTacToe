import java.util.Scanner;

public class SafeInput
{
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = "";

        do
        {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        }
        while(retString.trim().isEmpty());

        return retString;
    }

    /** Reads an integer in the inclusive range [low, high]. */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        if(low > high)
        {
            throw new IllegalArgumentException("low must not be greater than high");
        }

        while(true)
        {
            System.out.print("\n" + prompt + " (" + low + "-" + high + "): ");
            String input = pipe.nextLine().trim();
            try
            {
                int value = Integer.parseInt(input);
                if(value >= low && value <= high)
                {
                    return value;
                }
                System.out.println("Please enter a number from " + low + " to " + high + ".");
            }
            catch(NumberFormatException ex)
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    /** Reads a yes/no answer, accepting y, yes, n, or no (case-insensitive). */
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        while(true)
        {
            System.out.print("\n" + prompt + " (Y/N): ");
            String answer = pipe.nextLine().trim();
            if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
            {
                return true;
            }
            if(answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no"))
            {
                return false;
            }
            System.out.println("Please answer Y or N.");
        }
    }
}