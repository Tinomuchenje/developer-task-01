package com.econetwireless.epay.api.rest.resources;

import com.econetwireless.epay.api.processors.api.EpayRequestProcessor;
import com.econetwireless.epay.api.processors.api.ReportingProcessor;
import com.econetwireless.epay.api.rest.messages.TransactionsResponse;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tnyamakura on 18/3/2017.
 */
@RestController
@RequestMapping("resources/services")
public class EpayResource {

    private EpayRequestProcessor epayRequestProcessor;

    private ReportingProcessor reportingProcessor;

    public EpayResource(EpayRequestProcessor epayRequestProcessor, ReportingProcessor reportingProcessor) {
        this.epayRequestProcessor = epayRequestProcessor;
        this.reportingProcessor = reportingProcessor;
    }

    @GetMapping(value = "transactions/{partnerCode}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<SubscriberRequest> getPartnerTransactions(@PathVariable("partnerCode") final String partnerCode) {
        return reportingProcessor.getPartnerTransactions(partnerCode).getSubscriberRequests();
    }

    @GetMapping(value = "enquiries/{partnerCode}/balances/{mobileNumber}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AirtimeBalanceResponse enquireAirtimeBalance(@PathVariable("partnerCode") final String pCode, @PathVariable("mobileNumber") final String msisdn) {
        return epayRequestProcessor.enquireAirtimeBalance(pCode, msisdn);
    }

    @PostMapping(value = "credits",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AirtimeTopupResponse creditAirtime(@RequestBody final AirtimeTopupRequest airtimeTopupRequest) {
        return epayRequestProcessor.creditAirtime(airtimeTopupRequest);
    }


}
