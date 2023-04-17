package code.tech.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;

    private String city;
    private String street;
    private String zipcode;
}
