package cmc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "recommended_todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecommendedTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendedTodoId;

    @Column(length = 20)
    private String recommendedTodoContent;

    @ManyToOne
    @JoinColumn(name = "recommendedWorldId")
    private RecommendedWorld recommendedWorld;
}
