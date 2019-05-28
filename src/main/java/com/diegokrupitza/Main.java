package com.diegokrupitza;

import com.diegokrupitza.vm.*;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
public class Main {

    public static void main(String[] args) throws VirtualMaschineException {
        Host host = new FiFoHost();


        Container c1 = new ContainerImpl("c1");
        Container c2 = new ContainerImpl("c2");
        Container c3 = new ContainerImpl();


        host.addContainer(c1);
        host.addContainer(c2);
        host.addContainer(c3);

        host.execute();

    }
}
