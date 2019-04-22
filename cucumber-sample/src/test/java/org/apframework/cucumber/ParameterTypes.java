package org.apframework.cucumber;

import cucumber.api.TypeRegistryConfigurer;
import cucumber.api.TypeRegistry;
import io.cucumber.datatable.DataTableType;
import io.cucumber.cucumberexpressions.ParameterType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.getDateInstance;
import static java.util.Locale.ENGLISH;

public class ParameterTypes implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(new ParameterType<>(
                "date",
                "((.*) \\d{1,2}, \\d{4})",
                Date.class,
                (String s) -> getDateInstance(MEDIUM, ENGLISH).parse(s)
            )
        );

        typeRegistry.defineParameterType(new ParameterType<>(
            "iso-date",
            "\\d{4}-\\d{2}-\\d{2}",
            Date.class,
            (String s) -> new SimpleDateFormat("yyyy-mm-dd").parse(s)
        ));

        typeRegistry.defineDataTableType(new DataTableType(
            RpnCalculatorStepdefs.Entry.class,
            (Map<String, String> row) -> new RpnCalculatorStepdefs.Entry(
                Integer.valueOf(row.get("first")),
                Integer.valueOf(row.get("second")),
                row.get("operation")
            )
        ));

        typeRegistry.defineDataTableType(new DataTableType(
            ShoppingStepdefs.Grocery.class,
            (Map<String, String> row) -> new ShoppingStepdefs.Grocery(
                row.get("name"),
                ShoppingStepdefs.Price.fromString(row.get("price"))
            )
        ));
    }
}