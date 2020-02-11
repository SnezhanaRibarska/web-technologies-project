package com.webproject.models;

import javax.persistence.*;

import lombok.*;
import net.glxn.qrgen.javase.QRCode;

import java.io.File;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Slide {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "image", unique = false, length = 100000)
    private byte[] image;

    //private QRCode code;

    int index;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "presentation_id", nullable = false)
    Presentation presentation;
    
}
