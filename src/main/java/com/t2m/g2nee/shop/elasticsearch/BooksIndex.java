package com.t2m.g2nee.shop.elasticsearch;

import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Service;

@Document(indexName = "g2nee_book")
@Getter
@Setter
@Service
public class BooksIndex {

    @Id
    @Field(type = FieldType.Long)
    private Long bookId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String engTitle;
    @Field(type = FieldType.Text)
    private String bookIndex;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Text)
    private String contributorName;
    @Field(type = FieldType.Text)
    private String publisherName;

}
