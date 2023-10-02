package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "world")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Entity
public class World extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worldId;

    @Column(nullable = false, length = 14)
    private String worldName;

    @Column(nullable = false)
    private Integer worldUserLimit;

    @Column(length = 200)
    private String worldImg;

    @Column(nullable = false)
    private LocalDateTime worldStartDate;

    @Column(nullable = false)
    private LocalDateTime worldEndDate;

    @Column(length = 200)
    private String worldNotice;

    @Column(length = 20)
    private String worldPassword;

    private Long worldHostUserId;

    @OneToMany(targetEntity = WorldHashtag.class, fetch = FetchType.LAZY, mappedBy = "world", cascade = CascadeType.ALL)
    private List<WorldHashtag> worldHashtags = new ArrayList<>();

    @OneToMany(targetEntity = WorldAvatar.class, fetch = FetchType.LAZY, mappedBy = "world", cascade = CascadeType.ALL)
    private List<WorldAvatar> worldAvatars = new ArrayList<>();

    @OneToMany(targetEntity = Todo.class, fetch = FetchType.LAZY, mappedBy = "world", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public World(
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            String worldImg,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            String worldNotice,
            String worldPassword,
            Long worldHostUserId,
            List<WorldHashtag> worldHashtags) {

        this.worldId = worldId;
        this.worldName = worldName;
        this.worldUserLimit = worldUserLimit;
        this.worldImg = worldImg;
        this.worldStartDate = worldStartDate;
        this.worldEndDate = worldEndDate;
        this.worldNotice = worldNotice;
        this.worldPassword = worldPassword;
        this.worldHostUserId = worldHostUserId;
        this.worldHashtags = worldHashtags;
    }

    public World updateWorld(World world) {

        this.setWorldName(world.getWorldName());
        this.setWorldUserLimit(world.getWorldUserLimit());
        this.setWorldImg(world.getWorldImg());
        this.setWorldStartDate(world.getWorldStartDate());
        this.setWorldEndDate(world.getWorldEndDate());
        this.setWorldNotice(world.getWorldNotice());
        this.setWorldPassword(world.getWorldPassword());
        this.setWorldHostUserId(world.getWorldHostUserId());

        return this;
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
        todo.setWorld(this);
    }

    public void addHashtag(WorldHashtag worldHashtag) {
        this.worldHashtags.add(worldHashtag);
        worldHashtag.setWorld(this);
    }
}
