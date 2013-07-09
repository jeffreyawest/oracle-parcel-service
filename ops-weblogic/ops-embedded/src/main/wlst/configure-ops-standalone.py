#/*
#* **************************************************************************
#* <p/>
#* This code is provided for example purposes only.  Oracle does not assume
#* any responsibility or liability for the consequences of using this code.
#* If you choose to use this code for any reason, including but not limited
#* to its use as an example you do so at your own risk and without the support
#* of Oracle.
#*
#* This code is provided under the following licenses:
#*
#* GNU General Public License (GPL-2.0)
#* COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
#*
#* <p/>
#* ****************************************************************************
#*/
import time

loadProperties('ops-domain.properties')

########################################################################################################################

COHERENCE_LIB = COHERENCE_HOME + '/lib/coherence.jar'
COHERENCE_WEB_LIB = COHERENCE_HOME + '/lib/coherence-web-spi.war'
ACTIVE_CACHE_LIB = WL_HOME + '/common/deployable-libraries/active-cache-1.0.jar'
WLS_SPRING_LIB = WL_HOME + '/server/lib/weblogic-spring.jar'
TOPLINK_GRID_LIB = MW_HOME + '/oracle_common/modules/oracle.toplink_12.1.2/toplink-grid.jar'

########################################################################################################################

# change this to your domain name if you are configuring the domain outside the OPS project structure
DOMAIN_NAME = 'ops_domain'

###################
adminURL = 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort

connect(adminServer_Username, adminServer_Password, adminURL)

edit()
startEdit()

# this is for loading the GEODATA which takes time...
cd('/JTA/' + DOMAIN_NAME)
cmo.setTimeoutSeconds(600)

try:
    cd('/JMSServers/jms-server-0')
except:
    print 'Creating JMS Server...'
    cd('/')
    cmo.createJMSServer('jms-server-0')
    cd('/JMSServers/jms-server-0')
    set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

try:
    cd('/JMSSystemResources/jms-module-ops')
except:
    try:
        print 'Creating JMS Module...'
        cd('/')
        cmo.createJMSSystemResource('jms-module-ops')

        cd('/JMSSystemResources/jms-module-ops')
        set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
        cmo.createConnectionFactory('com.oracle.demo.ops.jms.cf')

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf')
        cmo.setJNDIName('com.oracle.demo.ops.jms.cf')

        cd(
            '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/SecurityParams/com.oracle.demo.ops.jms.cf')
        cmo.setAttachJMSXUserId(false)

        cd(
            '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/ClientParams/com.oracle.demo.ops.jms.cf')
        cmo.setClientIdPolicy('Restricted')
        cmo.setSubscriptionSharingPolicy('Exclusive')
        cmo.setMessagesMaximum(10)

        cd(
            '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/TransactionParams/com.oracle.demo.ops.jms.cf')
        cmo.setXAConnectionFactoryEnabled(true)

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf')
        cmo.setDefaultTargetingEnabled(true)

        cd('/JMSSystemResources/jms-module-ops')
        cmo.createSubDeployment('admin-subdeployment')

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
        cmo.createQueue('com.oracle.demo.ops.jms.eventQueue')

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/Queues/com.oracle.demo.ops.jms.eventQueue')
        cmo.setJNDIName('com.oracle.demo.ops.jms.eventQueue')
        cmo.setSubDeploymentName('admin-subdeployment')

        cd('/JMSSystemResources/jms-module-ops/SubDeployments/admin-subdeployment')
        set('Targets', jarray.array([ObjectName('com.bea:Name=jms-server-0,Type=JMSServer')], ObjectName))

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
        cmo.createQueue('com.oracle.demo.ops.jms.shipmentQueue')

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/Queues/com.oracle.demo.ops.jms.shipmentQueue')
        cmo.setJNDIName('com.oracle.demo.ops.jms.shipmentQueue')
        cmo.setSubDeploymentName('admin-subdeployment')

        cd('/JMSSystemResources/jms-module-ops/SubDeployments/admin-subdeployment')
        set('Targets', jarray.array([ObjectName('com.bea:Name=jms-server-0,Type=JMSServer')], ObjectName))

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
        cmo.createTopic('com.oracle.demo.ops.jms.eventTopic')

        cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/Topics/com.oracle.demo.ops.jms.eventTopic')
        cmo.setJNDIName('com.oracle.demo.ops.jms.eventTopic')
        cmo.setSubDeploymentName('admin-subdeployment')

        cd('/JMSSystemResources/jms-module-ops/SubDeployments/admin-subdeployment')
        set('Targets', jarray.array([ObjectName('com.bea:Name=jms-server-0,Type=JMSServer')], ObjectName))
    except:
        dumpStack()

try:
    activate()
except:
    dumpStack()
    pass

########################################################################################################################

def deploySharedLibrary(appName, appPath, targetString):
  progress = deploy(appName=appName, path=appPath, targets=targetString, libraryModule='true')
  progress.printStatus()

########################################################################################################################

def deploySharedLibraries(targetString):
  print '@@@ Deploying Shared Libraries @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

  deploySharedLibrary('coherence', COHERENCE_LIB, targetString)
  deploySharedLibrary('coherence-web-spi', COHERENCE_WEB_LIB, targetString)
  deploySharedLibrary('active-cache', ACTIVE_CACHE_LIB, targetString)
  deploySharedLibrary('weblogic-spring', WLS_SPRING_LIB, targetString)
  deploySharedLibrary('toplink-grid', TOPLINK_GRID_LIB, targetString)

########################################################################################################################

deploySharedLibraries('AdminServer')

startEdit()

try:
    cd('/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds')
except:
    try:
        print 'Creating datasource...'
        cd('/')
        cmo.createJDBCSystemResource('com.oracle.demo.ops.jdbc.cluster-ds')

        cd('/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds')
        cmo.setName('com.oracle.demo.ops.jdbc.cluster-ds')

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCDataSourceParams/com.oracle.demo.ops.jdbc.cluster-ds')
        set('JNDINames', jarray.array([String('com.oracle.demo.ops.jdbc.cluster-ds')], String))

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCDriverParams/com.oracle.demo.ops.jdbc.cluster-ds')

        cmo.setUrl(datasource_jdbc_url)
        cmo.setDriverName('oracle.jdbc.OracleDriver')
        set('Password', 'ops_user')

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCConnectionPoolParams/com.oracle.demo.ops.jdbc.cluster-ds')
        cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n')

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCDriverParams/com.oracle.demo.ops.jdbc.cluster-ds/Properties/com.oracle.demo.ops.jdbc.cluster-ds')
        cmo.createProperty('user')

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCDriverParams/com.oracle.demo.ops.jdbc.cluster-ds/Properties/com.oracle.demo.ops.jdbc.cluster-ds/Properties/user')
        cmo.setValue('ops_user')

        cd(
            '/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds/JDBCResource/com.oracle.demo.ops.jdbc.cluster-ds/JDBCDataSourceParams/com.oracle.demo.ops.jdbc.cluster-ds')
        cmo.setGlobalTransactionsProtocol('None')

        cd('/JDBCSystemResources/com.oracle.demo.ops.jdbc.cluster-ds')
        set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))
    except:
        dumpStack()
        pass



try:
  cd('/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds')
except:
  try:
    print 'Creating datasource...'
    cd('/')
    cmo.createJDBCSystemResource('com.oracle.demo.ops.app-ds')

    cd('/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds')
    cmo.setName('com.oracle.demo.ops.app-ds')

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCDataSourceParams/com.oracle.demo.ops.app-ds')
    set('JNDINames', jarray.array([String('com.oracle.demo.ops.app-ds')], String))

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCDriverParams/com.oracle.demo.ops.app-ds')

    cmo.setUrl(datasource_jdbc_url)
    cmo.setDriverName('oracle.jdbc.OracleDriver')
    set('Password', 'ops_user')

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCConnectionPoolParams/com.oracle.demo.ops.app-ds')
    cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n')

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCDriverParams/com.oracle.demo.ops.app-ds/Properties/com.oracle.demo.ops.app-ds')
    cmo.createProperty('user')

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCDriverParams/com.oracle.demo.ops.app-ds/Properties/com.oracle.demo.ops.app-ds/Properties/user')
    cmo.setValue('ops_user')

    cd(
      '/JDBCSystemResources/com.oracle.demo.ops.app-ds/JDBCResource/com.oracle.demo.ops.app-ds/JDBCDataSourceParams/com.oracle.demo.ops.app-ds')
    cmo.setGlobalTransactionsProtocol('None')

    cd('/JDBCSystemResources/com.oracle.demo.ops.app-ds')
    set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))
  except:
    dumpStack()
    pass


try:
    save()
    activate()
except:
    dumpStack()

