package com.example.pidev.services.impl;


import com.example.pidev.dto.*;
import com.example.pidev.dto.modelMapper.SprintMapper;
import com.example.pidev.entities.Sprint;
import com.example.pidev.entities.Ticket;
import com.example.pidev.repository.SprintRepository;
import com.example.pidev.services.Interface.ISprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService implements ISprintService {
    @Autowired
    SprintMapper sprintMapper;
    @Autowired
    SprintRepository sprintRepository;
    //private String username = "firas.dhahri@esprit.tn";
    @Value("${nom.de.utlisateur}")
    private String username;

    @Value("${api.token}")
    private String password;
    //private String domain= "firasdhahri";
    @Value("${domain.name}")
    private String domain;

    @Override
    public List<Sprint> getAllSprints(long originBoardId) {
        List<Sprint> sprints = new ArrayList<>();
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/board/" + originBoardId + "/sprint";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SprintResponseDto> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, SprintResponseDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                SprintResponseDto sprintResponseDto = response.getBody();
                if (sprintResponseDto != null) {
                    List<Sprint> fetchedSprints = sprintResponseDto.getSprint();
                    for (Sprint sprint : fetchedSprints) {
                        // Check if the sprint already exists in the database based on its key
                        boolean exists = sprintRepository.existsByName(sprint.getName());
                        if (!exists) {
                            // If the ticket doesn't exist, save it
                            sprintRepository.save(sprint);
                        } else {
                            // If the ticket already exists, you can choose to update it or ignore it
                            // For now, let's just print a message
                            System.out.println("Sprint with key " + sprint.getName() + " already exists in the database.");
                        }
                    }
                    sprints.addAll(fetchedSprints); // Add all fetched tickets to the list
                } else {
                    System.out.println("Response body is null");
                }
            } else {
                System.out.println("Failed to retrieve sprint. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sprints;
    }




    @Override
    public SprintGetDto createSprint(SprintCreationDto sprintCreationDto) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            // Construct the request body
            String requestBody = "{" +
                    "\"originBoardId\": \"" + sprintCreationDto.getOriginBoardId() + "\", " +
                    "\"name\": \"" + sprintCreationDto.getName() + "\", " +
                    "\"startDate\": \"" + sprintCreationDto.getStartDate() + "\", " +
                    "\"goal\": \"" + sprintCreationDto.getGoal() + "\", " +
                    "\"endDate\": \"" + sprintCreationDto.getEndDate() + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Sprint> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Sprint.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                Sprint createdSprint = response.getBody();
                final String nameToFind = createdSprint.getName();
                Optional<Sprint> optionalTicket = getAllSprints(sprintCreationDto.getOriginBoardId()).stream()
                        .filter(ticket -> ticket.getName().equals(nameToFind))
                        .findFirst();
                if (optionalTicket.isPresent()) {
                    //Ticket ticket = ticketMapper.toEntityFromGet(createdIssue);
//                    createdIssue = ticketRepository.save(createdIssue);
//                    return ticketMapper.toDto(createdIssue);
                    Sprint savedSprint = sprintRepository.save(optionalTicket.get());
                    SprintGetDto sprintGetDto = sprintMapper.toDto(savedSprint);
                    return sprintGetDto;
                } else {
                    System.out.println("Created issue response body is null");
                }
            } else {
                System.out.println("Failed to create issue. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error creating issue: " + e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public SprintGetDto updateSprint(Long sprintId, SprintCreationDto sprintCreationDto) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint/" + sprintId;
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            // Construct the request body
            String requestBody = "{" +
                    "\"originBoardId\": \"" + sprintCreationDto.getOriginBoardId() + "\", " +
                    "\"name\": \"" + sprintCreationDto.getName() + "\", " +
                    "\"startDate\": \"" + sprintCreationDto.getStartDate() + "\", " +
                    "\"state\": \"" + sprintCreationDto.getState() + "\", " +
                    "\"goal\": \"" + sprintCreationDto.getGoal() + "\", " +
                    "\"endDate\": \"" + sprintCreationDto.getEndDate() + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Sprint> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, Sprint.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Optional<Sprint> optionalSprint = getAllSprints(sprintCreationDto.getOriginBoardId()).stream()
                        .filter(ticket -> ticket.getName().equals(sprintId))
                        .findFirst();
                if (optionalSprint.isPresent()) {
                    Sprint existingSprint = optionalSprint.get();
                    // Save the updated ticket back to the database
                    Sprint savedSprint = sprintRepository.save(existingSprint);
                    SprintGetDto sprintGetDto = sprintMapper.toDto(savedSprint);
                    return sprintGetDto;
                } else {
                    System.out.println("Failed to find existing issue with key: " + sprintId);
                }
            } else {
                System.out.println("Failed to update issue. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error updating issue: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteSprint(long sprintId) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint/" + sprintId;
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, Void.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<String> getAllBoardName() {
        List<String> boardIds = new ArrayList<>();
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/board";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BoardResponseDto> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, BoardResponseDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                BoardResponseDto boardResponseDto = response.getBody();
                if (boardResponseDto != null) {
                    List<BoardDto> boardDtos = boardResponseDto.getValues();
                    for (BoardDto boardDto : boardDtos) {
                        boardIds.add(boardDto.getName());
                    }
                } else {
                    System.out.println("Response body is null");
                }
            } else {
                System.out.println("Failed to retrieve boards. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return boardIds;
    }
}






