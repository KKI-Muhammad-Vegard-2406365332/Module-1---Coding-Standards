package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/detail")
    public String paymentDetailPage(@RequestParam(required = false) String paymentId, Model model) {
        if (paymentId == null || paymentId.isEmpty()) {
            return "PaymentDetail";
        }
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "PaymentDetailResult";
    }

    @GetMapping("/detail/{paymentId}")
    public String paymentDetailById(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "PaymentDetailResult";
    }

    @GetMapping("/admin/list")
    public String adminPaymentList(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "PaymentAdminList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String adminPaymentDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "PaymentAdminDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String adminSetStatus(@PathVariable String paymentId,
                                 @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}