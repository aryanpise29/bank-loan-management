package com.bank.loan.controller;


import com.bank.loan.dto.CustomerRequest;
import com.bank.loan.dto.LoanProductRequest;
import com.bank.loan.dto.PaymentRequest;
import com.bank.loan.dto.RepaymentResponse;
import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import com.bank.loan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
public class WebController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private LoanProductService loanProductService;
    
    @Autowired
    private LoanApplicationService loanApplicationService;
    
    @Autowired
    private RepaymentService repaymentService;
    
    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/customer-login")
    public String customerLogin() {
        return "customer-login";
    }

    @GetMapping("/customer-register")
    public String customerRegister(Model model) {
        model.addAttribute("customer", new CustomerRequest());
        return "customer-register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("loanReport", reportService.generateLoanReport());
        model.addAttribute("repaymentReport", reportService.generateRepaymentReport());
        return "dashboard";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }



    @GetMapping("/customers/kyc/{id}")
    public String updateKyc(@PathVariable Long id, @RequestParam Customer.KycStatus status) {
        customerService.updateKycStatus(id, status);
        return "redirect:/customers";
    }

    @GetMapping("/loan-products")
    public String loanProducts(Model model) {
        model.addAttribute("products", loanProductService.getAllLoanProducts());
        return "loan-products";
    }

    @GetMapping("/loan-products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new LoanProductRequest());
        return "product-form";
    }

    @PostMapping("/loan-products/save")
    public String saveProduct(@ModelAttribute LoanProductRequest request) {
        loanProductService.addLoanProduct(request);
        return "redirect:/loan-products";
    }

    @GetMapping("/loan-applications")
    public String loanApplications(Model model) {
        model.addAttribute("applications", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.PENDING));
        return "loan-applications";
    }

    @GetMapping("/loan-applications/all")
    public String allApplications(Model model) {
        model.addAttribute("pending", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.PENDING));
        model.addAttribute("approved", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.APPROVED));
        model.addAttribute("rejected", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.REJECTED));
        return "all-applications";
    }

    @GetMapping("/all-applications")
    public String allApplicationsAlias(Model model) {
        model.addAttribute("pending", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.PENDING));
        model.addAttribute("approved", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.APPROVED));
        model.addAttribute("rejected", loanApplicationService.getApplicationsByStatus(LoanApplication.ApprovalStatus.REJECTED));
        return "all-applications";
    }

    @GetMapping("/loan-applications/process/{id}")
    public String processApplication(@PathVariable Long id, @RequestParam LoanApplication.ApprovalStatus status, @RequestParam(required = false) String remarks) {
        loanApplicationService.processLoanApplication(id, status, remarks);
        return "redirect:/loan-applications";
    }

    @GetMapping("/repayments/{applicationId}")
    public String repayments(@PathVariable Long applicationId, Model model) {
        model.addAttribute("repayments", repaymentService.getRepaymentSchedule(applicationId));
        model.addAttribute("outstanding", repaymentService.getOutstandingBalance(applicationId));
        model.addAttribute("applicationId", applicationId);
        return "repayments";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("loanReport", reportService.generateLoanReport());
        model.addAttribute("repaymentReport", reportService.generateRepaymentReport());
        model.addAttribute("outstandingReport", reportService.generateOutstandingLoansReport());
        return "reports";
    }

    @PostMapping("/api/repayments/payment")
    @ResponseBody
    public ResponseEntity<RepaymentResponse> makePaymentApi(@RequestBody PaymentRequest request) {
        try {
            RepaymentResponse response = repaymentService.makePayment(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
