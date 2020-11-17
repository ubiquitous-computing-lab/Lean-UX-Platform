/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.datatype;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class AudioData implements Serializable {

    private static final long serialVersionUID = 2L;
    private byte[] buffer;
    private int readCount;
    private String label;
    //private AudioFormat format;

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Arrays.hashCode(this.buffer);
        hash = 67 * hash + this.readCount;
        hash = 67 * hash + Objects.hashCode(this.label);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AudioData other = (AudioData) obj;
        if (this.readCount != other.readCount) {
            return false;
        }
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        return Arrays.equals(this.buffer, other.buffer);
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public String getLabel() {
        return label;
    }

}
