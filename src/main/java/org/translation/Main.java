package org.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    public static final String ANSWER = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator("sample.json");

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);

            if (country.equals(ANSWER)) {
                break;
            }

            CountryCodeConverter converter1 = new CountryCodeConverter();
            String CountryCode = converter1.fromCountry(country);

            String language = promptForLanguage(translator, CountryCode);
            if (language.equals(ANSWER)) {
                break;
            }

            LanguageCodeConverter converter2 = new LanguageCodeConverter();
            String LanguageCode = converter2.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(CountryCode, LanguageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (textTyped.equals(ANSWER)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();

        List<String> orgin = new ArrayList<>();
        CountryCodeConverter converter = new CountryCodeConverter();
        for (String country : countries) {
            orgin.add(converter.fromCountryCode(country));
        }

        orgin.sort(null);
        for (String orginCountry : orgin) {
            System.out.println(orginCountry);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> languages = translator.getCountryLanguages(country.toLowerCase());

        List<String> orgin = new ArrayList<>();
        LanguageCodeConverter converter = new LanguageCodeConverter();
        for (String language : languages) {
            orgin.add(converter.fromLanguageCode(language));
        }

        orgin.sort(null);
        for (String orginLanguage : orgin) {
            System.out.println(orginLanguage);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
