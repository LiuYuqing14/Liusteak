package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CountryCodeConverter {

    List<String> Country = new ArrayList<>();
    List<String> Alpha2_code = new ArrayList<>();
    List<String> Alpha3_code = new ArrayList<>();
    List<String> numeric = new ArrayList<>();


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

            for (String line : lines) {
                String[] word = line.split("\t");
                Country.add(word[0]);
                Alpha2_code.add(word[1]);
                Alpha3_code.add(word[2]);
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
        int count = 0;
        for (String countrycode : Alpha3_code) {
            if (countrycode.equals(code)) {
                return Country.get(count);
            }
            count ++;
        }
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        int count = 0;
        for (String countrycode : Country) {
            if (countrycode.equals(country)) {
                return Alpha3_code.get(count);
            }
            count ++;
        }
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return Country.size();
    }
}
