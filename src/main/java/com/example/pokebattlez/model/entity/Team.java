package com.example.pokebattlez.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class, optional = false)
    @OneToOne(optional = false, orphanRemoval = true)
    private Account trainer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<Pokemon> pokemon = new ArrayList<>();

    @Transient
    public void addPokemon(Pokemon pokemon) {
        this.pokemon.add(pokemon);
        pokemon.setTeam(this);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", trainer=" + trainer.getId() +
                ", pokemon=" + pokemon +
                '}';
    }
}
