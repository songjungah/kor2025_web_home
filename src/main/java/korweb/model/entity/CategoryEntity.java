package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")     // 테이블명
public class CategoryEntity extends BaseTime{
    // 1. 카테고리 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;

    // 2. 카테고리명
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String cname;
}