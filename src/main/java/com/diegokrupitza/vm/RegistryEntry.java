package com.diegokrupitza.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryEntry<T> {

    public static final RegistryEntry NIL = new RegistryEntry(Optional.empty());

    private Optional<T> value;

}
