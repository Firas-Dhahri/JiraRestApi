package com.example.pidev.service;


import com.example.pidev.entities.JiraResponse;
import com.example.pidev.entities.Ticket;
import com.example.pidev.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TicketServiceImpl implements  TicketService {
    @Autowired
    TicketRepository ticketRepository;


    private String username = "firas.dhahri@esprit.tn";

    private String password="ATATT3xFfGF0cP_FQjxFfk85vv-myeXkXs8JsgNgLMPHggCa7sU_FmtACVmwnmXiiJ046auIEXMesICQaHIGkGiS0KXNM2se1YNISIoJ0m1exdqbPhH1gu91CLb5NfJxc_zPmMRwGQrklAQCaG8cjSSzBJij5jvpZ_U_h11CUgY0cpjPuGh51MU=118F7255";

    private String domain= "firasdhahri";
    @Override
    public List<Ticket> getallticekts() {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/search";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JiraResponse> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, JiraResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JiraResponse jiraResponse = response.getBody();
                if (jiraResponse != null) {
                    List<Ticket> ticket =jiraResponse.getIssues();
                    ticketRepository.saveAll(jiraResponse.getIssues());
                    return ticket;

                } else {
                    System.out.println("Response body is null");
                }
            } else {
                System.out.println("Failed to retrieve issues. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null; // Return null if there's an error
    }


    @Override
    public Ticket ajouterticket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket modifierticket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void supprimierticekt(Long idticket) {
        ticketRepository.deleteById(idticket);

    }
}
