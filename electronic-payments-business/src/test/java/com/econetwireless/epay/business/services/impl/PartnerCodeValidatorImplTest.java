package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.epay.business.utils.TestUtility;
import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.utils.execeptions.EpayException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PartnerCodeValidatorImplTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private RequestPartnerDao requestPartnerDao;
    private PartnerCodeValidator partnerCodeValidator;
    private String partnerCode;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestPartnerDao.findByCode(anyString())).then(TestUtility.REQUEST_PARTNER_ANSWER);
        partnerCodeValidator = new PartnerCodeValidatorImpl(requestPartnerDao);
        partnerCode = "hot-recharge";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void validatePartnerCode_ValidPartnerCode_ReturnsValidPartner() {
        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode(partnerCode);
        assertTrue(validatePartnerCode);
    }

    @Test
    public void validatePartnerCode_InValidNullPartnerCode_ReturnsValidPartner() {
        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode(null);
        assertFalse(validatePartnerCode);
    }

    @Test
    public void validatePartnerCode_InValidEmptyStringPartnerCode_ReturnsValidPartner() {
        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode("");
        assertFalse(validatePartnerCode);
    }

    @Test
    public void validatePartnerCode_InValidWhiteSpacePartnerCode_ReturnsValidPartner() {
        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode(" ");
        assertFalse(validatePartnerCode);
    }

    @Test(expected = Exception.class)
    public void testPartnerCode() {
        exception.expect(EpayException.class);
        partnerCodeValidator.validatePartnerCode(partnerCode);
    }
}