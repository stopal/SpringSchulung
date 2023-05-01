package com.example.springschulung.hallowelt

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hallo")
class HalloController {

    @GetMapping
    fun halloWelt(): String {
        return "Hallo Welt!"
    }

    @GetMapping("/{name}")
    fun halloName(@PathVariable name: String): String {
        return "Hallo $name!"
    }
}
