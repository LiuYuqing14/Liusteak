package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    List<Integer> id = new ArrayList<>();
    List<String> alpha2  = new ArrayList<>();
    List<String> alpha3 = new ArrayList<>();
    HashMap <String, HashMap> language = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);

                id.add(country.getInt("id"));
                alpha2.add(country.getString("alpha2"));
                alpha3.add(country.getString("alpha3"));

                HashMap<String, String> languageCode = new HashMap<>();
                for(String key: country.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        languageCode.put(key, country.getString(key));
                    }
                    this.language.put(country.getString("alpha3"), languageCode);
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        HashMap<String, String> target = this.language.get(country);
        List<String> result = new ArrayList<>(target.keySet());
        return result;
    }

    @Override
    public List<String> getCountries() {
        return this.alpha3;
    }

    @Override
    public String translate(String country, String language) {
        HashMap<String, String> target = this.language.get(country);
        if (target != null) {
            return target.get(language);
        }
        return null;
    }
}
