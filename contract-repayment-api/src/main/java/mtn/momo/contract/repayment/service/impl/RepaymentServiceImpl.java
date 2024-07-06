package mtn.momo.contract.repayment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mtn.momo.contract.repayment.configs.RepaymentProperties;
import mtn.momo.contract.repayment.model.RepaymentOption;
import mtn.momo.contract.repayment.model.request.RepaymentRequest;
import mtn.momo.contract.repayment.service.RepaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentProperties properties;

    @Override
    public List<RepaymentOption> calculateRepaymentOptions(RepaymentRequest request) {
        log.info("Calculating repayment options for amount: {}", request.getAmount());

        List<RepaymentOption> options = new ArrayList<>();
        List<Integer> terms = properties.getTerms();

        for (Integer term : terms) {
            BigDecimal monthlyPayment = calculateMonthlyPayment(BigDecimal.valueOf(request.getAmount()), term);
            options.add(new RepaymentOption(term, monthlyPayment));
        }
        return options;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, int term) {
        BigDecimal monthlyRate = properties.getInterestRate().divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        BigDecimal powFactor = BigDecimal.ONE.add(monthlyRate).pow(term, MathContext.DECIMAL128);
        BigDecimal denominator = powFactor.subtract(BigDecimal.ONE);
        return amount.multiply(monthlyRate).multiply(powFactor).divide(denominator, 2, BigDecimal.ROUND_HALF_EVEN);
    }
}
