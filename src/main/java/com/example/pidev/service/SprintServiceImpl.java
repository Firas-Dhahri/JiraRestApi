package com.example.pidev.service;


import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintServiceImpl implements SprintService{
    @Autowired
    SprintRepository sprintRepository;
    //private String username = "firas.dhahri@esprit.tn";
    @Value("${nom.de.utlisateur}")
    private String username;

    //private String password="ATATT3xFfGF0s92En9BY91fID25VHAbGYPEDWIhrRWHXmWQm_MH0cNsp2ppQuvpL2Lx3WsHyRKWnIfMgDAMCpsX7xQ3WoC0jCEibD7KysIviNpjtJN35t2ZFwJy1hii-S_EBlC_4JtdpnSI9h9IMezZDDEN1qQY4_HoPtcmZdH8z81RLF1h7oSU=D15021E8";
    @Value("${api.token}")
    private String password;
    //private String domain= "firasdhahri";
    @Value("${domain.name}")
    private String domain;

    @Override
    public SprintResponseDto getAllSprints(long boardId) {
            try  {
                String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/board/" + boardId + "/sprint";
                String auth = username + ":" + password;
                byte[] authBytes = auth.getBytes();
                String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

                org.springframework.http.HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", "Basic " + base64Creds);


                HttpEntity<String> entity = new HttpEntity<>(headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<SprintResponseDto> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,  SprintResponseDto.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    SprintResponseDto sprintResponseDto = response.getBody();
                    if (sprintResponseDto != null) {
                        return sprintResponseDto;

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

}

