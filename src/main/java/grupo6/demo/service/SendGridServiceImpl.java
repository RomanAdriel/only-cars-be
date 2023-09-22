package grupo6.demo.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import grupo6.demo.model.Reservation;
import grupo6.demo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class SendGridServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridServiceImpl.class);
    @Value("${sengrid.api_key}")
    private String sgKey;
    @Value("${sendgrid.from_email}")
    private String sgFromEmail;

    public void sendEmail(Reservation reservationInfo, String recipientEmail, String recipientName) throws IOException {


        LOGGER.info(recipientEmail);
        LOGGER.info(recipientName);

        Email from = new Email(sgFromEmail);
        String subject = "Testing Sendgrid";
        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", "Test");
        Mail mail = new Mail(from, subject, to, content);

        String reservationNumber = reservationInfo.getProduct().getId().toString() + DateUtils.stringifyDate(
                reservationInfo.getStartDate()) + DateUtils.stringifyDate(reservationInfo.getEndDate());
        mail.personalization.get(0).addDynamicTemplateData("first_name", recipientName);
        mail.personalization.get(0).addDynamicTemplateData("reservation_number", reservationNumber);
        mail.personalization.get(0).addDynamicTemplateData("product_image",
                                                           reservationInfo.getProduct().getImages().get(0).getUrl());
        mail.personalization.get(0).addDynamicTemplateData("product_name", reservationInfo.getProduct().getName());
        mail.personalization.get(0).addDynamicTemplateData("start_date", reservationInfo.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        mail.personalization.get(0).addDynamicTemplateData("end_date", reservationInfo.getEndDate().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        mail.setTemplateId("d-d672aa6c483f41bcb064732e0e13d8ed");

        SendGrid sg = new SendGrid(sgKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }

    }


}
