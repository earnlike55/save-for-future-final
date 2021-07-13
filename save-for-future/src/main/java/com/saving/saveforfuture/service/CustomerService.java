package com.saving.saveforfuture.service;

import com.saving.saveforfuture.Repository.CustomerRepository;
import com.saving.saveforfuture.Repository.SavingRepository;
import com.saving.saveforfuture.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SavingRepository savingRepository;

    public SavingResponse getCustomerFinancialDetail(String customerId) {
        List<CustomerProfileDetail> customerProfileDetails = customerRepository.getCustomerFinancialDetail(customerId);
        List<SavingDetail> savingDetails = savingRepository.getSavingDetail(customerId);
        SavingResponse savingResponse = new SavingResponse();
        savingResponse.setMonthlyIncome(customerProfileDetails.get(0).getMonthlyIncome())
                .setMonthlyExpense(customerProfileDetails.get(0).getMonthlyExpense())
                .setTax(customerProfileDetails.get(0).getTax())
                .setSuggestAmount(calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                        customerProfileDetails.get(0).getMonthlyExpense(),
                        customerProfileDetails.get(0).getAgeOfRetirement(), customerProfileDetails.get(0).getTax()))
                .setRemainingAmount(calculateRemainingAmount(customerProfileDetails.get(0).getBalance(),
                        (calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(),
                                customerProfileDetails.get(0).getAgeOfRetirement(), customerProfileDetails.get(0).getTax()))))
                .setRemainingPercent(calculateRemainingAmountPercent(customerProfileDetails.get(0).getBalance(),
                        calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(),
                                customerProfileDetails.get(0).getAgeOfRetirement(), customerProfileDetails.get(0).getTax())))
                .setSavePercent(calculateDepositAmountPercent(customerProfileDetails.get(0).getBalance(),
                        calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(), customerProfileDetails.get(0).getAgeOfRetirement(), customerProfileDetails.get(0).getTax())))
                .setSavingTransactions(mapToSavingTransaction(savingDetails));


        return savingResponse;
    }

    public ProfileResponse getCustomerProfile(String customerId) {
        List<CustomerProfileDetail> customerProfileDetails = customerRepository.getCustomerFinancialDetail(customerId);
        List<Profile> profile = customerRepository.getCustomerProfile(customerId);
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setCustomerName(profile.get(0).getCustomerName())
                .setBankAccNo(profile.get(0).getBankAccNo())
                .setAge(profile.get(0).getAge())
                .setBankId(profile.get(0).getBankId())
                .setBankName(profile.get(0).getBankName())
                .setGender(profile.get(0).getGender())
                .setEmail(profile.get(0).getEmail())
                .setMonthlyIncome(customerProfileDetails.get(0).getMonthlyIncome())
                .setMonthlyExpense(customerProfileDetails.get(0).getMonthlyExpense())
                .setMemberNo(customerProfileDetails.get(0).getMemberno())
                .setTax(customerProfileDetails.get(0).getTax())
                .setSuggestAmt(calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                        customerProfileDetails.get(0).getMonthlyExpense(),
                        customerProfileDetails.get(0).getAgeOfRetirement(),
                        customerProfileDetails.get(0).getTax()));
        return profileResponse;

    }

    public BigDecimal calculateSuggestAmount(int expectAge, BigDecimal expense, int retireAge, BigDecimal tax) {
        return BigDecimal.valueOf(expectAge - retireAge).multiply((expense.add(tax)).multiply(new BigDecimal(12)));
    }

    public BigDecimal calculateRemainingAmount(BigDecimal current, BigDecimal suggest) {
        return suggest.subtract(current);
    }

    public BigDecimal calculateRemainingAmountPercent(BigDecimal current, BigDecimal suggest) {
        return ((suggest.subtract(current)).divide(suggest, 3, RoundingMode.FLOOR)).multiply(new BigDecimal("100.00"));
    }

    public BigDecimal calculateDepositAmountPercent(BigDecimal depositAmt, BigDecimal suggest) {
        return (depositAmt.divide(suggest, 3, RoundingMode.FLOOR)).multiply(new BigDecimal("100.00"));
    }

    public List<SavingTransaction> mapToSavingTransaction(List<SavingDetail> savingDetails) {
        return savingDetails.stream().map(savingDetail ->
                new SavingTransaction()
                        .setSavingId(savingDetail.getSavingId())
                        .setCreatedDateTime(savingDetail.getDateTime())
                        .setDepositAmount(savingDetail.getDepositamt()))
                .collect(Collectors.toList());

    }

}
