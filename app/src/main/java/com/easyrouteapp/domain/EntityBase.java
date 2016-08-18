package com.easyrouteapp.domain;

import java.io.Serializable;

/**
 * Created by fernando on 11/08/2016.
 */
public abstract class EntityBase implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
