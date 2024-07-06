package mtn.momo.contract.repayment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import mtn.momo.contract.repayment.model.RepaymentOption;
import mtn.momo.contract.repayment.model.UserDto;
import mtn.momo.contract.repayment.model.request.RepaymentRequest;
import mtn.momo.contract.repayment.service.RepaymentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/repayments")
@RequiredArgsConstructor
public class RepaymentController {

    private final RepaymentService repaymentService;

    @Operation(summary = "Calculate repayments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = UserDto.class))})})
    @PostMapping("/calculate/v1")
    public ResponseEntity<List<RepaymentOption>> calculateRepaymentOptions(@RequestBody RepaymentRequest request,
                                                                           @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        List<RepaymentOption> options = repaymentService.calculateRepaymentOptions(request);
        return ResponseEntity.ok(options);
    }
}

