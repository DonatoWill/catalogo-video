package com.fullcycle.catalogovideo.infrastructure.persistence;

import com.fullcycle.catalogovideo.domain.entity.Category;
import com.fullcycle.catalogovideo.domain.exceptions.NotNullException;
import lombok.*;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryPersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARBINARY(16)")
    private UUID id;

    @Column
    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Column
    private String description;

    @Column
    private Boolean isActive;

    public static CategoryPersistence from(Category category) {
        if(category == null) throw new NotNullException("Category can not be null");

        return new CategoryPersistence(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsActive()
        );
    }
    public Category fromThis(){
        return new Category(
                this.id,
                this.name,
                this.description,
                this.isActive
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPersistence that = (CategoryPersistence) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isActive);
    }
}
