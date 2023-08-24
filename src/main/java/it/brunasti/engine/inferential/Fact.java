package it.brunasti.engine.inferential;

import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fact implements Serializable {
    String name;
    String description;
    double value = -1.0;
}
