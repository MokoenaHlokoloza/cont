package mtn.momo.contract.repayment.service;

import mtn.momo.contract.repayment.model.RepaymentOption;
import mtn.momo.contract.repayment.model.request.RepaymentRequest;

import java.util.List;

public interface RepaymentService {
    List<RepaymentOption> calculateRepaymentOptions(RepaymentRequest amount);
}
