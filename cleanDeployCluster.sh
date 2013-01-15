#!/bin/sh
mvn -P enable-weblogic-deployment,deploy-local,undeploy-on-clean clean install
