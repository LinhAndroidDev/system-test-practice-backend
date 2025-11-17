package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String receiverName;
    private String link; // Optional: Link cho CTA button, mặc định sẽ là CH Play link
}
