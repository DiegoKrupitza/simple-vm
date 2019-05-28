package com.diegokrupitza.vm;

import org.omg.CORBA.Object;

import java.rmi.registry.Registry;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
public class ContainerRegistry<T> {

    private RegistryEntry<T>[] registry;

    public ContainerRegistry(int size) {
        this.registry = new RegistryEntry[size];
    }

    public RegistryEntry<T> getRegistryEntry(int index) {
        return this.registry[index];
    }

}
