#!/bin/sh

. ops-domain.properties

mvn install:install-file -Dfile=${WEBLOGIC_HOME}/server/lib/wls-maven-plugin.jar -DpomFile=${WEBLOGIC_HOME}/server/lib/pom.xml