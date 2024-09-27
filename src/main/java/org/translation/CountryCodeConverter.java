package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    List<String> countryName = new ArrayList<>();
    List<String> alpha2 = new ArrayList<>();
    List<String> alpha3 = new ArrayList<>();
    List<String> numeric = new ArrayList<>();


    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            lines.remove(0);
            for (String line : lines) {
                String[] word = line.split("\t");
                countryName.add(word[0]);
                alpha2.add(word[1]);
                alpha3.add(word[2]);
                numeric.add(word[3]);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        for (int i = 0; i < countryName.size(); i++) {
            if (code.toUpperCase().equals(alpha3.get(i))) {
                return countryName.get(i);
            }
        }
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        for (int i = 0; i < countryName.size(); i++) {
            if (country.equals(countryName.get(i))) {
                return alpha3.get(i);
            }
        }
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryName.size();

    }
}
