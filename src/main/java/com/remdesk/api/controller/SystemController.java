package com.remdesk.api.controller;

import com.remdesk.api.RemdeskApiApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController( "GuestSystemController" )
@RequestMapping( path = "/guest/system" )
public class SystemController {

    @GetMapping( path = "/ping" )
    public ResponseEntity< Void > ping() {
        return ResponseEntity.noContent().build();
    }


    @PostMapping( path = "/reboot" )
    public void reboot() {
        RemdeskApiApplication.restart();
    }
}
