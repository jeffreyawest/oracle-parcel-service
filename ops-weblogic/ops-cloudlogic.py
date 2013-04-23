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
#
def createStandaloneModules():
    print 'Creating JMS Server Name=[jms-server-0]'
    jmsserver = create('jms-server-0', 'JMSServer')

    cd('/JMSServers/jms-server-0')
    assign('JMSServer', 'jms-server-0', 'Target', 'AdminServer')
    adminName = ObjectName('com.bea:Name=AdminServer,Type=Server')

    ########### Standalone JMS Module
    cd('/')
    jmsMySystemResource = create('jms-module-ops-standalone', 'JMSSystemResource')
    #assign('JMSSystemResource', 'jms-module-ops-standalone', 'Target', jmsserver)

    #jmsMySystemResource.setTargets(jarray.array([targets], weblogic.management.configuration.TargetMBean))

    cd('/JMSSystemResources/jms-module-ops-standalone')
    #cmo.addTarget(getMBean('/Servers/AdminServer'))

    subdeployment = create('admin-subdeployment', 'SubDeployment')
    #cmo.addTarget(getMBean('/Servers/AdminServer'))

    cd('/JMSSystemResource/jms-module-ops-standalone/JmsResource/NO_NAME_0')

    cf_name = 'com.oracle.demo.ops.jms.cf'
    myCF = create(cf_name, 'ConnectionFactory')

    cd(
        '/JMSSystemResources/jms-module-ops-standalone/JmsResource/NO_NAME_0/ConnectionFactories/com.oracle.demo.ops.jms.cf')
    myCF.setJNDIName(cf_name)
    myCF.setDefaultTargetingEnabled(true)
    txParams = create(cf_name, 'TransactionParams')
    txParams.setXAConnectionFactoryEnabled(true)

    cd('/JMSSystemResources/jms-module-ops-standalone/JmsResource/NO_NAME_0')
    eventQueue = create('com.oracle.demo.ops.jms.eventQueue', 'Queue')
    eventQueue.setJNDIName('com.oracle.demo.ops.jms.eventQueue')
    eventQueue.setDefaultTargetingEnabled(false)
    eventQueue.setSubDeploymentName('admin-subdeployment')

    cd('/JMSSystemResources/jms-module-ops-standalone/JmsResource/NO_NAME_0')
    shipmentQueue = create('com.oracle.demo.ops.jms.shipmentQueue', 'Queue')
    shipmentQueue.setJNDIName('com.oracle.demo.ops.jms.shipmentQueue')
    shipmentQueue.setDefaultTargetingEnabled(false)
    shipmentQueue.setSubDeploymentName('admin-subdeployment')

    cd('/JMSSystemResources/jms-module-ops-standalone/JmsResource/NO_NAME_0')
    eventTopic = create('com.oracle.demo.ops.jms.eventTopic', 'Topic')
    eventTopic.setJNDIName('com.oracle.demo.ops.jms.eventTopic')
    eventTopic.setDefaultTargetingEnabled(false)
    eventTopic.setSubDeploymentName('admin-subdeployment')


########### JMS Module

def createOPSJMSResources(module_name, targets):
    cd('/')
    jmsMySystemResource = create(module_name, 'JMSSystemResource')
    jmsMySystemResource.setTargets(jarray.array([cluster], weblogic.management.configuration.TargetMBean))

    cd('/JMSSystemResources/jms-module-ops')
    subdeployment = create('cluster-subdeployment', 'SubDeployment')
    subdeployment.setTargets(jarray.array(targets, weblogic.management.configuration.TargetMBean))

    cd('/JMSSystemResource/jms-module-ops/JmsResource/NO_NAME_0')

    myCF = create('com.oracle.demo.ops.jms.cf', 'ConnectionFactory')

    cd('/JMSSystemResources/jms-module-ops/JmsResource/NO_NAME_0/ConnectionFactories/com.oracle.demo.ops.jms.cf')

    myCF.setJNDIName('com.oracle.demo.ops.jms.cf')
    myCF.setDefaultTargetingEnabled(true)
    txParams = create('com.oracle.demo.ops.jms.cf', 'TransactionParams')
    txParams.setXAConnectionFactoryEnabled(true)

    cd('/JMSSystemResources/jms-module-ops/JmsResource/NO_NAME_0')
    eventQueue = create('com.oracle.demo.ops.jms.eventQueue', 'UniformDistributedQueue')
    eventQueue.setJNDIName('com.oracle.demo.ops.jms.eventQueue')
    eventQueue.setDefaultTargetingEnabled(false)
    eventQueue.setSubDeploymentName('cluster-subdeployment')

    cd('/JMSSystemResources/jms-module-ops/JmsResource/NO_NAME_0')
    shipmentQueue = create('com.oracle.demo.ops.jms.shipmentQueue', 'UniformDistributedQueue')

    shipmentQueue.setJNDIName('com.oracle.demo.ops.jms.shipmentQueue')
    shipmentQueue.setDefaultTargetingEnabled(false)
    shipmentQueue.setSubDeploymentName('cluster-subdeployment')

    cd('/JMSSystemResources/jms-module-ops/JmsResource/NO_NAME_0')
    eventTopic = create('com.oracle.demo.ops.jms.eventTopic', 'UniformDistributedTopic')
    eventTopic.setJNDIName('com.oracle.demo.ops.jms.eventTopic')
    eventTopic.setForwardingPolicy('Partitioned')
    eventTopic.setDefaultTargetingEnabled(false)
    eventTopic.setSubDeploymentName('cluster-subdeployment')

###
import time

loadProperties('ops-domain.properties')

DOMAIN_NAME = 'ops_domain'

datasource_jndi_name = 'com.oracle.demo.ops.jdbc.cluster-ds'
datasource_global_transactions = 'None'
datasource_jdbc_driver = 'oracle.jdbc.OracleDriver'
datasource_user = 'ops_user'
datasource_password = 'ops_user'

adminServer_ListenAddress = '127.0.0.1'
adminServer_ListenPort = 7001
adminServer_AdministrationPort = 7200
adminServer_Username = 'weblogic'
adminServer_Password = 'welcome1'

managed_server_count = 2
managed_server_name_base = 'ms'
managed_server_port_base = '710'
managed_server_admin_port_base = '720'

listen_address = 'localhost'

jms_sever_name_base = 'jms-server'
cluster_name = 'cluster-1'
machine_name = 'localhost'

coh_cluster_name = 'coherence-cluster-1'
coh_listen_address = '127.0.0.1'
coh_listen_port = 8088
coh_ttl = 0
coh_server_count = 2

coh_cache_config_path = OPS_HOME + '/ops-cache/ops-cache-config/coherence-cache-config.xml'

coh_server_cp = MIDDLEWARE_HOME + '/modules/com.oracle.toplinkgrid_1.0.0.0_11-1-1-5-0.jar:'\
                + MIDDLEWARE_HOME + '/modules/org.eclipse.persistence_1.1.0.0_2-1.jar:'\
                + MIDDLEWARE_HOME + '/coherence_3.7/lib/coherence.jar:'\
                + MIDDLEWARE_HOME + '/modules/javax.management_1.2.1.jar:'\
                + MIDDLEWARE_HOME + '/modules/javax.management.remote_1.0.1.3.jar:'\
                + MIDDLEWARE_HOME + '/modules/javax.persistence_1.0.0.0_2-0-0.jar:'\
                + MIDDLEWARE_HOME + '/wlserver_12.1/server/lib/ojdbc6.jar:'\
                + OPS_HOME + '/ops-domain-model-tlg/target/ops-domain-model-tlg.jar:'\
                + MIDDLEWARE_HOME + '/coherence_3.7/lib/coherence-web-spi.war:'\
                + MIDDLEWARE_HOME + '/modules/features/weblogic.server.modules.coherence.server_12.1.1.0.jar '

coh_server_args = ' -Dtangosol.coherence.management.remote=true'
coh_server_args = coh_server_args + ' -Dtangosol.coherence.management=all '
coh_server_args = coh_server_args + ' -Dtangosol.coherence.distributed.localstorage=true'
coh_server_args = coh_server_args + ' -Dtangosol.coherence.session.localstorage=true'
coh_server_args = coh_server_args + ' -Dtangosol.coherence.cacheconfig=' + coh_cache_config_path


################################################### JDBC

def createPhysicalDataSource(jndiName, driver, globalTX, url, user, passwd, target):
    dsName = jndiName
    print 'Creating Physical DataSource ' + dsName

    cd('/')
    jdbcSystemResource = create(dsName, "JDBCSystemResource")

    cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
    dataSourceParams = create('dataSourceParams', 'JDBCDataSourceParams')
    dataSourceParams.setGlobalTransactionsProtocol(globalTX)
    cd('JDBCDataSourceParams/NO_NAME_0')
    set('JNDIName', jndiName)

    cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
    connPoolParams = create('connPoolParams', 'JDBCConnectionPoolParams')
    connPoolParams.setMaxCapacity(20)
    connPoolParams.setInitialCapacity(5)
    connPoolParams.setCapacityIncrement(1)
    connPoolParams.setTestConnectionsOnReserve(true)
    connPoolParams.setTestTableName('SQL SELECT 1 FROM DUAL')

    cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
    driverParams = create('driverParams', 'JDBCDriverParams')
    driverParams.setUrl(url)
    driverParams.setDriverName(driver)
    driverParams.setPasswordEncrypted(passwd)
    cd('JDBCDriverParams/NO_NAME_0')

    create(dsName, 'Properties')
    cd('Properties/NO_NAME_0')

    create('user', 'Property')
    cd('Property/user')
    cmo.setValue(user)

    cd('/JDBCSystemResource/' + dsName)
    jdbcSystemResource.setTargets(jarray.array([target], weblogic.management.configuration.TargetMBean))

    print dsName + ' successfully created.'
    return jdbcSystemResource

################################################### JMS Module

def createOPSClusterJMSResources(pModuleName, pTargets, pJMSServers):
    print 'Creating OPS JMS Resources...'

    cd('/')
    jmsMySystemResource = create(pModuleName, 'JMSSystemResource')
    jmsMySystemResource.setTargets(jarray.array([pTargets], weblogic.management.configuration.TargetMBean))

    cd('/JMSSystemResources/' + pModuleName)
    subdeployment = create('cluster-subdeployment', 'SubDeployment')
    subdeployment.setTargets(jarray.array(pJMSServers, weblogic.management.configuration.TargetMBean))

    cd('/JMSSystemResource/' + pModuleName + '/JmsResource/NO_NAME_0')

    cf_name = 'com.oracle.demo.ops.jms.cf'

    myCF = create(cf_name, 'ConnectionFactory')
    myCF.setJNDIName(cf_name)
    myCF.setDefaultTargetingEnabled(true)

    cd('/JMSSystemResources/' + pModuleName + '/JmsResource/NO_NAME_0/ConnectionFactories/' + cf_name)
    lbParams = create(cf_name, 'LoadBalancingParams')
    lbParams.setLoadBalancingEnabled(true)
    lbParams.setServerAffinityEnabled(false)

    txParams = create(cf_name, 'TransactionParams')
    txParams.setXAConnectionFactoryEnabled(true)

    queue_name = 'com.oracle.demo.ops.jms.eventQueue'
    cd('/JMSSystemResources/' + pModuleName + '/JmsResource/NO_NAME_0')
    queue = create(queue_name, 'UniformDistributedQueue')
    queue.setJNDIName(queue_name)
    queue.setDefaultTargetingEnabled(false)
    queue.setSubDeploymentName('cluster-subdeployment')

    queue_name = 'com.oracle.demo.ops.jms.shipmentQueue'
    cd('/JMSSystemResources/' + pModuleName + '/JmsResource/NO_NAME_0')
    queue = create(queue_name, 'UniformDistributedQueue')
    queue.setJNDIName(queue_name)
    queue.setDefaultTargetingEnabled(false)
    queue.setSubDeploymentName('cluster-subdeployment')

    cd('/JMSSystemResources/' + pModuleName + '/JmsResource/NO_NAME_0')
    topic_name = 'com.oracle.demo.ops.jms.eventTopic'
    topic = create(topic_name, 'UniformDistributedTopic')
    topic.setJNDIName(topic_name)
    topic.setForwardingPolicy('Partitioned')
    topic.setDefaultTargetingEnabled(false)
    topic.setSubDeploymentName('cluster-subdeployment')


def createMachine(machine_name, nodemanager_type, listen_address, listen_port):
    cd('/')
    machine = create(machine_name, 'Machine')

    cd('/Machines/' + machine_name + '/')
    nodeManager = create(machine_name, 'NodeManager')

    #cd('/Machines/' + machine_name + '/NodeManager/' + machine_name)
    nodeManager.setNMType(nodemanager_type)
    #    nodeManager.setListenAddress(listen_address)
    nodeManager.setListenPort(listen_port)

    return machine

########################################

def createCoherenceCluster(coh_cluster_name, coh_listen_address, coh_listen_port, targets_array, coh_ttl):
    cd('/')
    cmo.createCoherenceClusterSystemResource(coh_cluster_name)

    cd(
        '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name)
    cmo.setUnicastListenAddress(coh_listen_address)
    cmo.setUnicastListenPort(coh_listen_port)
    cmo.setUnicastPortAutoAdjust(true)

    cmo.setMulticastListenAddress('231.1.1.1')
    cmo.setMulticastListenPort(7777)

    cd('/CoherenceClusterSystemResources/' + coh_cluster_name)
    set('Targets', targets_array)

    cd(
        '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/' + coh_cluster_name)
    cmo.createCoherenceClusterWellKnownAddress('WKA-0')

    cd(
        '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/WKA-0')
    cmo.setListenPort(coh_listen_port)
    cmo.setListenAddress(coh_listen_address)

    cd(
        '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name)
    cmo.setTimeToLive(coh_ttl)

    print 'Created Cluster name=[' + coh_cluster_name + ']'

########################################


def createCoherenceServer(coh_server_name, cluster_name, machine_name, coh_listen_address, coh_listen_port, coh_server_args, coh_server_cp):
    cd('/')
    cmo.createCoherenceServer(coh_server_name)

    cd('/CoherenceServers/' + coh_server_name)
    cmo.setMachine(getMBean('/Machines/' + machine_name))
    cmo.setCoherenceClusterSystemResource(getMBean('/CoherenceClusterSystemResources/' + cluster_name))
    cmo.setUnicastListenAddress(coh_listen_address)
    cmo.setUnicastListenPort(coh_listen_port)
    cmo.setUnicastPortAutoAdjust(true)

    cd('/CoherenceServers/' + coh_server_name + '/CoherenceServerStart/' + coh_server_name)
    cmo.setArguments(coh_server_args)
    cmo.setClassPath(coh_server_cp)

    print 'Created Coherence server name=[' + coh_server_name + ']'

########################################

var_domain_dir = USER_PROJECTS + '/domains/' + DOMAIN_NAME
print 'Creating domain in path=' + var_domain_dir

try:
    createDomain(WEBLOGIC_HOME + '/common/templates/domains/wls.jar', var_domain_dir, adminServer_Username, adminServer_Password)
    print 'domain created'

    readDomain(var_domain_dir)
    print 'read domain'

    cd('/')
    cmo.setExalogicOptimizationsEnabled(false)
    cmo.setClusterConstraintsEnabled(false)
    cmo.setGuardianEnabled(false)
    cmo.setAdministrationPortEnabled(false)
    cmo.setConsoleEnabled(true)
    cmo.setConsoleExtensionDirectory('console-ext')
    cmo.setProductionModeEnabled(false)
    cmo.setAdministrationProtocol('t3s')
    cmo.setConfigBackupEnabled(false)
    cmo.setConfigurationAuditType('none')
    cmo.setInternalAppsDeployOnDemandEnabled(false)
    cmo.setConsoleContextPath('console')

    cd('/Servers/AdminServer')
    cmo.setListenPortEnabled(true)
    cmo.setAdministrationPort(int(adminServer_AdministrationPort))
    cmo.setListenPort(int(adminServer_ListenPort))
    cmo.setWeblogicPluginEnabled(false)
    cmo.setJavaCompiler('javac')
    cmo.setStartupMode('RUNNING')
    cmo.setVirtualMachineName(DOMAIN_NAME + '_AdminServer')
    cmo.setClientCertProxyEnabled(false)
except:
    print 'Unable to create domain!'
    dumpStack()
    exit()

try:
    print 'updating domain'
    updateDomain()
except:
    print 'Unable to update domain'
    dumpStack()
    exit()

machine = createMachine(machine_name, 'Plain', '127.0.0.1', 5556)

cd('/')
clusterMBean = create(cluster_name, 'Cluster')

jdbcSystemResource = createPhysicalDataSource(datasource_jndi_name, datasource_jdbc_driver, datasource_global_transactions,
    JDBC_URL, datasource_user,
    datasource_password, clusterMBean)

####### Create Managed Servers

print 'Creating ' + str(managed_server_count) + ' Managed Servers...'

jmsServerMBeans = []
managedServerMBeans = []

for n in range(1, int(managed_server_count) + 1):
    managedServerName = managed_server_name_base + '-' + str(n)
    managedServer_ListenPort = int(str(managed_server_port_base) + str(n))
    managedServer_AdminPort = int(str(managed_server_admin_port_base) + str(n))
    managedServer_ListenAddress = listen_address

    print 'Creating Server Name=[' + managedServerName + '] with Listen Port: ' + str(managedServer_ListenPort)
    cd('/')
    managedServer = create(managedServerName, 'Server')
    managedServer.setListenPort(managedServer_ListenPort)
    #    managedServer.setListenAddress(managedServer_ListenAddress)
    managedServer.setAdministrationPort(managedServer_AdminPort)
    managedServer.setCluster(clusterMBean)
    managedServer.setMachine(machine)
    managedServer.setAutoRestart(false)
    managedServerMBeans.append(managedServer)

    cd('/Servers/' + managedServerName)
    serverStart = create(managedServerName, 'ServerStart')
    serverStart.setArguments(
        '-Xms256m -Xmx512m -Dtangosol.coherence.ttl=0 -Dtangosol.coherence.distributed.localstorage=false -Dtangosol.coherence.session.localstorage=false -Dtangosol.coherence.cacheconfig=/coherence-cache-config.xml -XX:FlightRecorderOptions=defaultrecording=true')

    cd('/')

# SPLIT INTO DIFFERENT SCOPES BECAUSE IT WASNT WORKING IN A SINGLE SCOPE!
for n in range(1, int(managed_server_count) + 1):
    jmsServerName = jms_sever_name_base + '-' + str(n)
    jdbcStoreName = jmsServerName + '-jdbcStore'
    managedServerName = managedServerMBeans[n - 1].getName()

    print 'Managed Server Name: ' + managedServerName

    jmsServerTargets = jarray.array([managedServerMBeans[n - 1]], weblogic.management.configuration.TargetMBean)

    print 'Creating JMS JDBC Store [' + jdbcStoreName + ']'
    cd('/')
    jdbcStore = create(jdbcStoreName, 'JDBCStore')
    jdbcStore.setDataSource(jdbcSystemResource)
    jdbcStore.setPrefixName('JMSSTORE' + str(n))
    jdbcStore.setTargets(jmsServerTargets)

    print 'Creating JMS Server Name=[' + jmsServerName + ']'
    cd('/')
    jmsServer = create(jmsServerName, 'JMSServer')
    jmsServerMBeans.append(jmsServer)
    jmsServer.setPersistentStore(jdbcStore)
    jmsServer.setTargets(jmsServerTargets)

    cd('/')

createOPSClusterJMSResources('jms-module-ops-cluster', clusterMBean, jmsServerMBeans)

updateDomain()

print ''
print '============================================='
print 'Completed WLST OFFLINE successfully...!!!'
print '============================================='
print ''

nmConnect(adminServer_Username, adminServer_Password, listen_address, 5556, DOMAIN_NAME, var_domain_dir, 'plain')

print ''
print '============================================='
print 'Connected to NODE MANAGER Successfully...!!!'
print '============================================='
print ''

nmStart('AdminServer')

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + str(adminServer_ListenPort))

edit()
startEdit()

createCoherenceCluster(coh_cluster_name, coh_listen_address, coh_listen_port,
    jarray.array([ObjectName('com.bea:Name=' + cluster_name + ',Type=Cluster')], ObjectName), coh_ttl)

for n in range(1, int(coh_server_count) + 1):
    coh_server_name = 'ops-coh-' + str(n)
    createCoherenceServer(coh_server_name, coh_cluster_name, machine_name, coh_listen_address, coh_listen_port, coh_server_args,
        coh_server_cp)

save()
activate(block="true")

progress = deploy(appName='coherence', path=COHERENCE_HOME + '/lib/coherence.jar', targets='AdminServer,' + cluster_name,
    libraryModule='true',
    libImplVersion='3.7.1.1', libSpecVersion='3.7.1.1')
progress.printStatus()

progress = deploy(appName='coherence-web-spi', path=COHERENCE_HOME + '/lib/coherence-web-spi.war', targets='AdminServer,' + cluster_name,
    libraryModule='true', libImplVersion='1.0.0.0', libSpecVersion='1.0.0.0')
progress.printStatus()

progress = deploy(appName='active-cache', path=WEBLOGIC_HOME + '/common/deployable-libraries/active-cache-1.0.jar',
    targets='AdminServer,' + cluster_name,
    libraryModule='true', libImplVersion='1.0', libSpecVersion='1.0')
progress.printStatus()

progress = deploy(appName='weblogic-spring', path=WEBLOGIC_HOME + '/server/lib/weblogic-spring.jar', targets='AdminServer,' + cluster_name,
    libraryModule='true', libImplVersion='12.1.1.0', libSpecVersion='12.1.1.0')
progress.printStatus()

progress = deploy(appName='toplink-grid', path=WEBLOGIC_HOME + '/common/deployable-libraries/toplink-grid-1.0.jar',
    targets='AdminServer,' + cluster_name,
    libraryModule='true', libImplVersion='1.0', libSpecVersion='1.0')
progress.printStatus()

######### Standalone JMS

edit()
startEdit()

cd('/')
cmo.createJMSServer('jms-server-0')

cd('/JMSServers/jms-server-0')
set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

cd('/')
cmo.createJMSSystemResource('jms-module-admin')

cd('/JMSSystemResources/jms-module-admin')
set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin')
cmo.createConnectionFactory('com.oracle.demo.ops.jms.cf')

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/ConnectionFactories/com.oracle.demo.ops.jms.cf')
cmo.setJNDIName('com.oracle.demo.ops.jms.cf')

cd(
    '/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/ConnectionFactories/com.oracle.demo.ops.jms.cf/SecurityParams/com.oracle.demo.ops.jms.cf')
cmo.setAttachJMSXUserId(false)

cd(
    '/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/ConnectionFactories/com.oracle.demo.ops.jms.cf/ClientParams/com.oracle.demo.ops.jms.cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd(
    '/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/ConnectionFactories/com.oracle.demo.ops.jms.cf/TransactionParams/com.oracle.demo.ops.jms.cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/ConnectionFactories/com.oracle.demo.ops.jms.cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-admin')
cmo.createSubDeployment('admin-subdeployment')

cd('/JMSSystemResources/jms-module-admin/SubDeployments/admin-subdeployment')
set('Targets', jarray.array([ObjectName('com.bea:Name=jms-server-0,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin')

cmo.createQueue('com.oracle.demo.ops.jms.eventQueue')
cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/Queues/com.oracle.demo.ops.jms.eventQueue')
cmo.setJNDIName('com.oracle.demo.ops.jms.eventQueue')
cmo.setSubDeploymentName('admin-subdeployment')

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin')

cmo.createQueue('com.oracle.demo.ops.jms.shipmentQueue')
cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/Queues/com.oracle.demo.ops.jms.shipmentQueue')
cmo.setJNDIName('com.oracle.demo.ops.jms.shipmentQueue')
cmo.setSubDeploymentName('admin-subdeployment')

cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin')
cmo.createTopic('com.oracle.demo.ops.jms.eventTopic')
cd('/JMSSystemResources/jms-module-admin/JMSResource/jms-module-admin/Topics/com.oracle.demo.ops.jms.eventTopic')
cmo.setJNDIName('com.oracle.demo.ops.jms.eventTopic')
cmo.setSubDeploymentName('admin-subdeployment')


cd('/JTA/ops_domain')
cmo.setTimeoutSeconds(600)

save()
activate(block="true")

shutdown()