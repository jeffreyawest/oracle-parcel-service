set DOMAINS_FILE=./nodemanager.domains
set MW_HOME=c:/Oracle/Middleware1
set USER_PROJECTS=%MW_HOME%/user_projects
set WL_HOME=%MW_HOME%/wlserver_12.1
set NM_HOME=%WL_HOME%/common/nodemanager


echo ops_domain=%USER_PROJECTS%/domains/ops_domain > %DOMAINS_FILE%

%WL_HOME%/common/bin/commEnv.bat
#echo $WEBLOGIC_CLASSPATH

set CLASSPATH=%WEBLOGIC_CLASSPATH%
set ﻿CLASSPATH=%CLASSPATH%;%MW_HOME%/modules/features/weblogic.server.modules_12.1.1.0.jar
set ﻿CLASSPATH=%CLASSPATH%;%MW_HOME%/modules/org.apache.ant_1.7.1/lib/ant-all.jar
set ﻿CLASSPATH=%CLASSPATH%;%MW_HOME%/modules/net.sf.antcontrib_1.1.0.0_1-0b2/lib/ant-contrib.jar

#echo %CLASSPATH%

set SERVER_ARGS=-DNodeManagerHome=%NM_HOME%
set SERVER_ARGS=%SERVER_ARGS% -Xms32m -Xmx200m
set SERVER_ARGS=%SERVER_ARGS% -DNativeVersionEnabled=false
set SERVER_ARGS=%SERVER_ARGS% -DSecureListener=false
set SERVER_ARGS=%SERVER_ARGS% -DLogFile=./nodemanager.log
set SERVER_ARGS=%SERVER_ARGS% -DDomainsFile=%DOMAINS_FILE%
set SERVER_ARGS=%SERVER_ARGS% -DListenPort=5556
set SERVER_ARGS=%SERVER_ARGS% -Djava.security.policy=%WL_HOME%/server/lib/weblogic.policy
set SERVER_ARGS=%SERVER_ARGS% -Dweblogic.nodemanager.javaHome="%JAVA_HOME%"

"%JAVA_HOME%/bin/java" -cp %CLASSPATH% %SERVER_ARGS% weblogic.NodeManager -v