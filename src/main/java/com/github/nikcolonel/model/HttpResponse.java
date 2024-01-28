package com.github.nikcolonel.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
public class HttpResponse {
    private int responseCode = 0;
    private byte[] body;

}
