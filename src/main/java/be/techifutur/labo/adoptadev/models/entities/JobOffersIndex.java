package be.techifutur.labo.adoptadev.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOffersIndex {

    private int total;

    private List<JobOffer> result;

}
