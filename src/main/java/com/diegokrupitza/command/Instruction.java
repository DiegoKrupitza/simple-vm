package com.diegokrupitza.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instruction implements Comparable<Instruction> {

    private Integer line;
    private String command;
    private int jumpTo = -1;

    public Instruction(Integer line, String command) {
        this.line = line;
        this.command = command;
    }

    public boolean hasJumpAddress() {
        return this.jumpTo != -1;
    }
    
    @Override
    public int compareTo(Instruction o) {
        return o.getLine().compareTo(this.getLine());
    }
}
