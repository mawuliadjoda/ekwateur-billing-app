package com.ekwateur.billing.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

@Component
public class CustomerReferenceValidator implements Predicate<String> {

    public static final String EKWATEUR_REFERENCE_PREFIXE = "EKW";
    private static final int EQUATEUR_REFERENCE_LENGTH = 11 ;

    @Override
    public boolean test(String reference) {
        return StringUtils.hasText(reference)
                && reference.startsWith(EKWATEUR_REFERENCE_PREFIXE)
                && reference.length() == EQUATEUR_REFERENCE_LENGTH;
    }
}
