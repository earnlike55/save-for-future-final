package com.saving.saveforfuture.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.saving.saveforfuture.Repository.BankLinkRepository;
import com.saving.saveforfuture.Repository.CustomerRepository;
import com.saving.saveforfuture.Repository.SavingRepository;
import com.saving.saveforfuture.model.*;
import com.saving.saveforfuture.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SavingRepository savingRepository;
    @Autowired
    private BankLinkRepository bankLinkRepository;

    public SavingResponse getCustomerFinancialDetail(long customerId) {
        List<CustomerProfileDetail> customerProfileDetails = customerRepository.getCustomerFinancialDetail(customerId);
        List<SavingDetail> savingDetails = savingRepository.getSavingDetail(customerId);
        int flags = savingRepository.selectFlag(customerId);
        SavingResponse savingResponse = new SavingResponse();
        savingRepository.updateBalance(customerId,updateBalanced(customerProfileDetails.get(0).getBalance(),customerId,flags));
        savingResponse.setMonthlyIncome(customerProfileDetails.get(0).getMonthlyIncome())
                .setMonthlyExpense(customerProfileDetails.get(0).getMonthlyExpense())
                .setSuggestAmount(calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                        customerProfileDetails.get(0).getMonthlyExpense(),
                        customerProfileDetails.get(0).getAgeOfRetirement()))
                .setRemainingAmount(calculateRemainingAmount(customerProfileDetails.get(0).getBalance(),
                        (calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(),
                                customerProfileDetails.get(0).getAgeOfRetirement()))))
                .setSuggestMonthly(calculateMonthlySave(calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                        customerProfileDetails.get(0).getMonthlyExpense(),customerProfileDetails.get(0).getAgeOfRetirement()),
                        customerProfileDetails.get(0).getExpectAge(),customerProfileDetails.get(0).getAgeOfRetirement()))
                .setRemainingPercent(calculateRemainingAmountPercent(customerProfileDetails.get(0).getBalance(),
                        calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(),
                                customerProfileDetails.get(0).getAgeOfRetirement())))
                .setSavePercent(calculateDepositAmountPercent(customerProfileDetails.get(0).getBalance(),
                        calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                                customerProfileDetails.get(0).getMonthlyExpense(), customerProfileDetails.get(0).getAgeOfRetirement())))
                .setSavingTransactions(mapToSavingTransaction(savingDetails))
                .setStatus(1000)
                .setDescription("Success");


        return savingResponse;
    }

    public ProfileResponse getCustomerProfile(long customerId) {
        List<CustomerProfileDetail> customerProfileDetails = customerRepository.getCustomerFinancialDetail(customerId);
        List<Profile> profile = customerRepository.getCustomerProfile(customerId);
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setCustomerName(profile.get(0).getCustomerName())
                .setBankAccNo(profile.get(0).getBankAccNo())
                .setAge(profile.get(0).getAge())
                .setBankName(profile.get(0).getBankAccNo() == null ? null : profile.get(0).getBankName())
                .setGender(profile.get(0).getGender())
                .setEmail(profile.get(0).getEmail())
                .setBankId(profile.get(0).getBankId())
                .setMonthlyIncome(customerProfileDetails.get(0).getMonthlyIncome())
                .setMonthlyExpense(customerProfileDetails.get(0).getMonthlyExpense())
                .setMemberNo(customerProfileDetails.get(0).getMemberno())
                .setSuggestMonthly(calculateMonthlySave(calculateSuggestAmount(customerProfileDetails.get(0).getExpectAge(),
                        customerProfileDetails.get(0).getMonthlyExpense(),customerProfileDetails.get(0).getAgeOfRetirement()),
                        customerProfileDetails.get(0).getExpectAge(),customerProfileDetails.get(0).getAgeOfRetirement()))
                .setDescription("Success")
                .setStatus(1000);
        return profileResponse;

    }

    public BankLinkResponse patchBankDetail(String email,String bankAccNo){
       long CusId = bankLinkRepository.getCustomerIdFromEmail(email);
        String bankAccount = bankLinkRepository.getCustomerBankAccNo(bankAccNo);
        BankLinkResponse bankLinkResponse = new BankLinkResponse();
        if(CusId == 0 || bankAccount == null )
        {
            bankLinkResponse
                    .setStatus(1999)
                    .setDescription("No such email or Account number exist");
        }
        else{
            bankLinkResponse
                    .setDescription("Success")
                    .setStatus(1000);
            bankLinkRepository.updateBankDetail(CusId,bankAccNo);

        }
        return bankLinkResponse;

   }

   public CustomerUpdateResponse patchCustomerDetail(long customerId,String email,BigDecimal monthlyIncome,BigDecimal monthlyExpense,int memberNo){
       CustomerUpdateResponse customerUpdateResponse = new CustomerUpdateResponse();
       customerRepository.pathCustomerDetail(monthlyIncome,monthlyExpense,memberNo,email,customerId);
        customerUpdateResponse.setDescription("Success");
        customerUpdateResponse.setStatus(1000);
        return customerUpdateResponse;
   }

   public CustomerInsertResponse postCustomerProfile(CustomerInsertRequest request){
        CustomerInsertResponse customerInsertResponse = new CustomerInsertResponse();
        String salt = PasswordUtils.getSalt(30);


        int age = getAge(request.getDob());
        String mySecurePassword = PasswordUtils.generateSecurePassword(request.getPassword(),salt);
        request.setPassword(mySecurePassword);
        int effect = customerRepository.postCustomerDetail(request,age,salt);
        if(effect == -1){
            customerInsertResponse.setDescription("Email already exist");
            customerInsertResponse.setStatus(1999);
        }
        else{
            customerInsertResponse.setDescription("Success");
            customerInsertResponse.setStatus(1000);
        }


        return customerInsertResponse;

   }

   public BigDecimal updateBalanced(BigDecimal balance,long customerId,int flag){
       BigDecimal saving = new BigDecimal(0);
       List<SavingDetail> savingDetails = savingRepository.getSavingDetail(customerId);
       if(flag!=0){
           flag++;
       }
       if (flag == savingDetails.size()-1){
           return balance;
       }
       for (int i = flag;i< savingDetails.size();i++){
           saving = saving.add(savingDetails.get(i).getDepositamt());
           savingRepository.updateFlag(customerId,i);
       }
        BigDecimal update = balance.add(saving);
        return update;
   }

   public int getAge(Date dob){
        Period age = Period.between(dob.toLocalDate(), LocalDate.now());
        return age.getYears();
   }

    public BigDecimal calculateSuggestAmount(int expectAge, BigDecimal expense, int retireAge) {
        return BigDecimal.valueOf(expectAge - retireAge).multiply((expense).multiply(new BigDecimal(12)));
    }

    public BigDecimal calculateRemainingAmount(BigDecimal current, BigDecimal suggest) {
        return suggest.subtract(current);
    }

    public BigDecimal calculateMonthlySave(BigDecimal suggest,int expectAge,int retireAge){
        return suggest.divide(BigDecimal.valueOf(expectAge-retireAge).multiply(new BigDecimal(12)));
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
