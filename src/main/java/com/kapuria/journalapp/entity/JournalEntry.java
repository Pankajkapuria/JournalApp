package com.kapuria.journalapp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journalEntryS")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId _id;
    private String title;
    private String content;
    private LocalDateTime date;
}
