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
    List<String> alpha_2 = new ArrayList<>();
    List<String> alpha_3 = new ArrayList<>();
    HashMap<String , HashMap> language = new HashMap<>();

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

            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject country = jsonArray.getJSONObject(i);

                id.add(country.getInt("id"));
                alpha_2.add(country.getString("alpha2"));
                alpha_3.add(country.getString("alpha3"));

                for (String key : country.keySet()) {
                    HashMap<String, String> language_code = new HashMap<>();
                    if (!key.equals("id") & !key.equals("alpha2") & !key.equals("alpha3")) {
                        String language_name = key;
                        String language_content = country.getString(key);
                        language_code.put(language_name, language_content);
                    }
                    this.language.put(country.getString("alpha3"), language_code);
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> result = new ArrayList<>();
        HashMap<String, String> target = this.language.get(country);
        for (String key : target.keySet()) {
            result.add(key);
        }
        return result;
    }

    @Override
    public List<String> getCountries() {
        return this.alpha_3;
    }

    @Override
    public String translate(String country, String language) {
        HashMap<String, String> target = this.language.get(country);
        if (!target.equals(null)) {
            return target.get(language);
        }
        return null;
    }
}
