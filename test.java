import java.util.Scanner;

public class test {
    private static double mathGrade;
    private static double englishGrade;
    private static double literatureGrade;
    private static double average;
    private static char charMath;
    private static char charEnglish;
    private static char charLiterature;
    private static char lowestChar;
    private static char averageChar;

    public static char checkGrade(double grade){
        if(grade<5)
            return 'F';
        else if(grade>=5 && grade<6)
            return 'D';
        else if(grade>=6 && grade<7)
            return 'C';
        else if(grade>=7 && grade<8)
            return 'B';
        else if (grade>=8 && grade<9)
            return 'A';
        else
            return 'S';
    }

    public static void printGrade(boolean isFailed, char finalGrade){
        System.out.println("\nMath: " + mathGrade + " - " + charMath);
        System.out.println("English: " + englishGrade + " - " + charEnglish);
        System.out.println("Literature: " + literatureGrade + " - " + charLiterature);
        System.out.printf("Average: %.2f\n", average);

        System.out.printf("---------------- ");
        if (isFailed || average<5)
            System.err.printf("Failed Student");
        else if (average>=5 && average<6)
            System.out.printf("Not bad Student");
        else if (average>=6 && average<7)
            System.out.printf("Average Student");
        else if (average>=7 && average<8)
            System.out.printf("Normal Student");
        else if (average>=8 && average<9)
            System.out.printf("Good Student");
        else
            System.out.printf("Excellent Student");
        System.out.println(" ----------------");
    
        System.out.println("Average Grade: " + averageChar);
        System.out.println("Final Grade of student: " + finalGrade);
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input grade for Math: ");
        mathGrade = scanner.nextDouble();
        System.out.print("Input grade for English: ");   
        englishGrade = scanner.nextDouble();
        System.out.print("Input grade for Literature: "); 
        literatureGrade = scanner.nextDouble();
        
        double lowestGrade = Math.min(mathGrade, Math.min(englishGrade, literatureGrade));
        charMath = checkGrade(mathGrade);
        charEnglish = checkGrade(englishGrade);
        charLiterature = checkGrade(literatureGrade);
        lowestChar = checkGrade(lowestGrade);
        
        average = (mathGrade + englishGrade + literatureGrade) / 3;
        averageChar = checkGrade(average);

        char finalGrade;
        if (lowestChar == 'F')
            finalGrade = 'F';
        else {
            finalGrade = averageChar==lowestChar ? averageChar : (char)(lowestChar-1);
            if (finalGrade==(char)('A'-1))
                finalGrade = 'S';
        }
        
        // Fail checking
        if(lowestChar=='F'){
            printGrade(true, finalGrade);
        }
        else{
            printGrade(false, finalGrade);
        }
    }
}