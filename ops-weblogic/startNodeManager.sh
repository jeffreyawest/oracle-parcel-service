#!/bin/sh

. ./ops-domain.properties

export DOMAINS_FILE=./nodemanager.domains

echo "ops_domain=${USER_PROJECTS}/domains/ops_domain" > ${DOMAINS_FILE}

export WEBLOGIC_HOME="/u01/wls1211/wlserver_12.1"
export NM_HOME="${WEBLOGIC_HOME}/common/nodemanager"

. ${WEBLOGIC_HOME}/common/bin/commEnv.sh
#echo $WEBLOGIC_CLASSPATH

export CLASSPATH=${WEBLOGIC_CLASSPATH}
export ﻿CLASSPATH="${CLASSPATH}:${MIDDLEWARE_HOME}/modules/features/weblogic.server.modules_12.1.1.0.jar"
export ﻿CLASSPATH="${CLASSPATH}:${MIDDLEWARE_HOME}/modules/org.apache.ant_1.7.1/lib/ant-all.jar"
export ﻿CLASSPATH="${CLASSPATH}:${MIDDLEWARE_HOME}/modules/net.sf.antcontrib_1.1.0.0_1-0b2/lib/ant-contrib.jar"

#echo ${CLASSPATH}

export SERVER_ARGS="-DNodeManagerHome=$NM_HOME"
export SERVER_ARGS="${SERVER_ARGS} -Xms32m -Xmx200m"
export SERVER_ARGS="${SERVER_ARGS} -DNativeVersionEnabled=false"
export SERVER_ARGS="${SERVER_ARGS} -DSecureListener=false"
export SERVER_ARGS="${SERVER_ARGS} -DLogFile=./nodemanager.log"
export SERVER_ARGS="${SERVER_ARGS} -DDomainsFile=${DOMAINS_FILE}"
export SERVER_ARGS="${SERVER_ARGS} -DListenPort=5556"
export SERVER_ARGS="${SERVER_ARGS} -Djava.security.policy=${WEBLOGIC_HOME}/server/lib/weblogic.policy"
export SERVER_ARGS="${SERVER_ARGS} -Dweblogic.nodemanager.javaHome=${JAVA_HOME}"

${JAVA_HOME}/bin/java -cp ${CLASSPATH} ${SERVER_ARGS} weblogic.NodeManager -v