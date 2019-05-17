package com.sensorsdata.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SensorsDataPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println "this is my first Plugin"
        project.task('My-Plugin-Task'){
            println "this is my first Task"
        }
    }
}