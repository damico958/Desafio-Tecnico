package com.damico958.desafio_tecnico.util;

import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.service.*;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileDeserializer {
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED_BOLD = "\033[1;31m";
    public static final String ANSI_YELLOW_BOLD = "\033[1;33m";

    @Value("${salesman.identifier}")
    private String SALESMAN_IDENTIFIER;
    @Value("${simplified.salesman.identifier}")
    private String SIMPLIFIED_SALESMAN_IDENTIFIER;
    @Value("${customer.identifier}")
    private String CUSTOMER_IDENTIFIER;
    @Value("${simplified.customer.identifier}")
    private String SIMPLIFIED_CUSTOMER_IDENTIFIER;
    @Value("${sale.identifier}")
    private String SALE_IDENTIFIER;
    @Value("${simplified.sale.identifier}")
    private String SIMPLIFIED_SALE_IDENTIFIER;

    @Value("${general.field.delimiter}")
    private String FIELD_DELIMITER;
    @Value("${starting.array.delimiter}")
    private String STARTING_ARRAY_DELIMITER;
    @Value("${ending.array.delimiter}")
    private String ENDING_ARRAY_DELIMITER;
    @Value("${new.item.delimiter}")
    private String NEW_ITEM_DELIMITER;
    @Value("${new.item.field.delimiter}")
    private String NEW_ITEM_FIELD_DELIMITER;

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CurrentFileDataService currentFileDataService;


    public FileDeserializer() {

    }

    public List<String> readFile(File datFile) {
        try (BufferedReader datBufferedReader = new BufferedReader(new FileReader(datFile))) {

            List<String> datRetrievedLines = new ArrayList<String>();

            String currentDatLine;

            while ((currentDatLine = datBufferedReader.readLine()) != null) {
                datRetrievedLines.add(currentDatLine);
            }
            return datRetrievedLines;

        } catch (FileNotFoundException exception) {
            throw new InvalidBehaviorException("Fail! Could not open file! Root Exception: ", exception);
        } catch (IOException exception) {
            throw new InvalidBehaviorException("Fail! Could not read from file! Root Exception: ", exception);
        }
    }

    public void deserialize(File datFile) {
        List<String> datRetrievedLines = readFile(datFile);
        currentFileDataService.clear();
        for(String currentDatLine : datRetrievedLines) {
            int countOfFieldDelimiter = CharMatcher.is(FIELD_DELIMITER.charAt(0)).countIn(currentDatLine);
            if (countOfFieldDelimiter % 3 != 0) {
                throw new InvalidBehaviorException("Fail! Your data formatting has an irregular amount of field delimiters!");
            }
            String adjustingSpace = currentDatLine.replace(" 00", FIELD_DELIMITER);
            List<String> datWordsWithoutSpace = Splitter.on(CharMatcher.anyOf(FIELD_DELIMITER)).splitToList(adjustingSpace);

            for (int identifier_index = 0; identifier_index < countOfFieldDelimiter; identifier_index = identifier_index + 4) {
                String identifier = datWordsWithoutSpace.get(identifier_index);
                if (identifier.equals(SALESMAN_IDENTIFIER) || identifier.equals(SIMPLIFIED_SALESMAN_IDENTIFIER)) {
                    String cpf = datWordsWithoutSpace.get(identifier_index + 1);
                    String salesmanName = datWordsWithoutSpace.get(identifier_index + 2);
                    BigDecimal salary = new BigDecimal(datWordsWithoutSpace.get(identifier_index + 3));
                    salesmanService.save(cpf, salesmanName, salary);
                } else if (identifier.equals(CUSTOMER_IDENTIFIER) || identifier.equals(SIMPLIFIED_CUSTOMER_IDENTIFIER)) {
                    String cnpj = datWordsWithoutSpace.get(identifier_index + 1);
                    String customerName = datWordsWithoutSpace.get(identifier_index + 2);
                    String businessArea = datWordsWithoutSpace.get(identifier_index + 3);
                    customerService.save(cnpj, customerName, businessArea);
                } else if (identifier.equals(SALE_IDENTIFIER) || identifier.equals(SIMPLIFIED_SALE_IDENTIFIER)) {
                    Long saleId = Long.valueOf(datWordsWithoutSpace.get(identifier_index + 1));
                    String itemsString = datWordsWithoutSpace.get(identifier_index + 2);

                    if (! (itemsString.startsWith(STARTING_ARRAY_DELIMITER)) && (itemsString.endsWith(ENDING_ARRAY_DELIMITER))) {
                        throw new InvalidBehaviorException("Fail! Your item's array delimiters are not correct!");
                    }
                    int countOfItemFieldDelimiter = CharMatcher.is(NEW_ITEM_FIELD_DELIMITER.charAt(0)).countIn(itemsString);
                    int itemsInSale = CharMatcher.is(NEW_ITEM_DELIMITER.charAt(0)).countIn(itemsString) + 1;

                    if ((countOfItemFieldDelimiter / 2) != itemsInSale) {
                        throw new InvalidBehaviorException("Fail! Your data formatting has an irregular amount of items delimiters!");
                    }
                    List<String> itemsRetrievedList = Splitter.on(CharMatcher.anyOf(STARTING_ARRAY_DELIMITER + ENDING_ARRAY_DELIMITER + NEW_ITEM_DELIMITER + NEW_ITEM_FIELD_DELIMITER)).omitEmptyStrings().splitToList(itemsString);
                    List<Item> itemList = new ArrayList<Item>();

                    for (int index = 0; index < itemsInSale * 3; index = index + 3) {
                        Long itemId = Long.valueOf(itemsRetrievedList.get(index));
                        Long quantity = Long.valueOf(itemsRetrievedList.get(index + 1));
                        BigDecimal unitPrice = new BigDecimal(itemsRetrievedList.get(index + 2));
                        Item item = itemService.save(itemId, quantity, unitPrice);
                        itemList.add(item);
                    }
                    Salesman salesman = salesmanService.findSalesmanByName(datWordsWithoutSpace.get(identifier_index + 3));
                    saleService.save(saleId, itemList, salesman);
                }
            }
        }
    }
}
