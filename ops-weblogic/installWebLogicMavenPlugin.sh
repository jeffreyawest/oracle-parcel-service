#!/bin/sh

. ops-domain.properties

mvn install:install-file -Dfile=${WL_HOME}/server/lib/wls-maven-plugin.jar -DpomFile=${WL_HOME}/server/lib/pom.xml