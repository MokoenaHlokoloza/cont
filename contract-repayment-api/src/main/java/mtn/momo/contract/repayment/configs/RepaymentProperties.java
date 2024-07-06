package mtn.momo.contract.repayment.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "repayment")
public class RepaymentProperties {
    private BigDecimal interestRate;
    private List<Integer> terms;
}
