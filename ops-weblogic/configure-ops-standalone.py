import time

loadProperties('ops-domain.properties')

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

try:
    progress = deploy(appName='weblogic-spring', path=WEBLOGIC_HOME + '/server/lib/weblogic-spring.jar', targets='AdminServer',
        libraryModule='true',
        libImplVersion='12.1.1.0', libSpecVersion='12.1.1.0')
    progress.printStatus()
except:
    pass

try:
    progress = deploy(appName='coherence', path=COHERENCE_HOME + '/lib/coherence.jar', targets='AdminServer', libraryModule='true',
        libImplVersion='3.7.1.1', libSpecVersion='3.7.1.1')
    progress.printStatus()
except:
    pass

try:
    progress = deploy(appName='coherence-web-spi', path=COHERENCE_HOME + '/lib/coherence-web-spi.war', targets='AdminServer',
        libraryModule='true', libImplVersion='1.0.0.0', libSpecVersion='1.0.0.0')
    progress.printStatus()
except:
    pass

try:
    progress = deploy(appName='active-cache', path=WEBLOGIC_HOME + '/common/deployable-libraries/active-cache-1.0.jar',
        targets='AdminServer',
        libraryModule='true', libImplVersion='1.0', libSpecVersion='1.0')
    progress.printStatus()
except:
    pass

try:
    progress = deploy(appName='toplink-grid', path=WEBLOGIC_HOME + '/common/deployable-libraries/toplink-grid-1.0.jar',
        targets='AdminServer',
        libraryModule='true', libImplVersion='1.0', libSpecVersion='1.0')
    progress.printStatus()
except:
    pass

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

        cmo.setUrl(JDBC_URL)
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
    save()
    activate()
except:
    dumpStack()

