package mtn.momo.contract.repayment.service;

import mtn.momo.contract.repayment.configs.RepaymentProperties;
import mtn.momo.contract.repayment.model.RepaymentOption;
import mtn.momo.contract.repayment.model.request.RepaymentRequest;
import mtn.momo.contract.repayment.service.impl.RepaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class RepaymentServiceImplTest {

    @Mock
    private RepaymentProperties properties;

    @InjectMocks
    private RepaymentServiceImpl repaymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(properties.getInterestRate()).thenReturn(new BigDecimal("0.05"));
        when(properties.getTerms()).thenReturn(Arrays.asList(12, 24, 36));
    }

    @Test
    public void testCalculateRepaymentOptions() {
        BigDecimal amount = BigDecimal.valueOf(10000);
        RepaymentRequest request = new RepaymentRequest();
        request.setAmount(amount.doubleValue());

        List<RepaymentOption> options = repaymentService.calculateRepaymentOptions(request);

        assertEquals(3, options.size());
        assertEquals(12, options.get(0).getTerm());
        assertEquals(24, options.get(1).getTerm());
        assertEquals(36, options.get(2).getTerm());

        // Ensure proper calculations with matching service logic
        assertEquals(0, options.get(0).getMonthlyPayment().compareTo(calculateMonthlyPayment(amount, 12)));
        assertEquals(0, options.get(1).getMonthlyPayment().compareTo(calculateMonthlyPayment(amount, 24)));
        assertEquals(0, options.get(2).getMonthlyPayment().compareTo(calculateMonthlyPayment(amount, 36)));
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, int term) {
        BigDecimal monthlyRate = properties.getInterestRate().divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        BigDecimal powFactor = BigDecimal.ONE.add(monthlyRate).pow(term, MathContext.DECIMAL128);
        BigDecimal denominator = powFactor.subtract(BigDecimal.ONE);
        return amount.multiply(monthlyRate).multiply(powFactor).divide(denominator, 2, BigDecimal.ROUND_HALF_EVEN);
    }
}
