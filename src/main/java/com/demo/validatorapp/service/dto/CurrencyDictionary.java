package com.demo.validatorapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDictionary implements Serializable {

    private String key;

    private String name;

    public Map<String, CurrencyDictionary> readData(){

        List<String> keys = new ArrayList<>();
        List<String> names = new ArrayList<>();
        BufferedReader keyFile = null;
        BufferedReader nameFile = null;
        ClassLoader classLoader = getClass().getClassLoader();
        File keyF = new File(classLoader.getResource("dictionary/keyCurrency.txt").getFile());
        File nameF = new File(classLoader.getResource("dictionary/nameCurrency.txt").getFile());

        try {
            keyFile = new BufferedReader(new FileReader(keyF));
            nameFile = new BufferedReader(new FileReader(nameF));
            keys = read(keyFile, keys);
            names = read(nameFile, names);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close(keyFile);
            close(nameFile);
        }

        return createDictionary(keys, names);
    }

    private Map<String, CurrencyDictionary> createDictionary(List<String> keys, List<String> names) {
        Map<String, CurrencyDictionary> currencyDictionary = new HashMap();
        if(!CollectionUtils.isEmpty(keys) && !CollectionUtils.isEmpty(names) && keys.size() == names.size() ){
            AtomicReference<Integer> count = new AtomicReference<>(0);
            List<String> finalNames = new ArrayList<>();
            finalNames.addAll(names);
            keys.forEach(element -> {
                currencyDictionary.put(element, CurrencyDictionary.builder()
                        .key(element)
                        .name(finalNames.get(count.get()))
                        .build());
                count.set(+1);
            });
        }
        return currencyDictionary;
    }

    private void close(BufferedReader file){
        try {
            if(Objects.nonNull(file))
                file.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> read(BufferedReader file, List<String> list) {
        try {
            String variable = file.readLine();
            while (Objects.nonNull(variable)) {
                list.add(variable);
                variable = file.readLine();
            }
        } catch (IOException io){ }
        return list;
    }
}
