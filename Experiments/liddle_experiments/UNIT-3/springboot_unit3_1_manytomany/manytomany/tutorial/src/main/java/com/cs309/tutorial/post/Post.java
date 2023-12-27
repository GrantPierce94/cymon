package com.cs309.tutorial.post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_at")
    private Date postedAt = new Date();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt = new Date();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })

    @JoinTable(name = "post_tags",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
//    Uncomment @JsonIgnore to fix infinite recursive calls issue
//    @JsonIgnore
//
    private Set<Tag> tags = new HashSet<>();


    public Post() {

    }

    public Post(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
