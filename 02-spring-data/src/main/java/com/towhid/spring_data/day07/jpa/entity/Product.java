package com.towhid.spring_data.day07.jpa.entity;

import com.towhid.spring_data.day08.relationships.entity.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// @Entity tells JPA/Hibernate:
// "This Java class is a database table"
@Entity

// @Table tells Hibernate which table name to use
// If you skip this, table name = class name (Product)
@Table(name = "products")

//Lombok annotations

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    // @Id = this field is the PRIMARY KEY
    @Id

    // @GeneratedValue = let DB auto-generate the value
    // GenerationType.IDENTITY = use MySQL AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    // @Column customizes the column
    @Column(name="product_name",
    nullable = false,
    length = 200)
    // name = column name in DB
    // nullable = false → NOT NULL in DB
    // length = VARCHAR(200)
    private String name;

    @Column(nullable = false)
    private String description;
    // no name specified → column name = field name (description)

    @Column(nullable = false)
    private Double price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    // field name is stockQuantity
    // but DB column will be stock_quantity

    @Column(length = 100)
    private String category;

    @Column(name = "is_active")
    private Boolean active = true;
    // default value = true

    // @CreationTimestamp = Hibernate sets this
    // automatically when record is first created
    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    // updatable = false → never change this after creation
    private LocalDateTime createdAt;

    // @UpdateTimestamp = Hibernate updates this
    // automatically every time record is updated
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "products_tags",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )

    private Set<Tag> tags = new HashSet<>();

    // Constructor without id and timestamps
    // Used when creating new product
    public Product(String name, String description,
                   Double price, Integer stockQuantity,
                   String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
        tag.getProducts().add(this);
    }

    public void removeTag(Tag tag){
        this.tags.remove(tag);
        tag.getProducts().remove(this);
    }

    @Override
    public String toString(){
        return "Product{id=" + id
                + ", name='" + name        // ← correct field
                + "', price=" + price
                + "}";
    }
}
