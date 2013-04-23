
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

var_domain_name='ops_domain'
var_domain_dir='c:/oracle/middleware/user_projects/domains/ops_domain'
var_username='weblogic'
var_password='welcome1'
var_admin_url='t3://10.0.0.51:7001'

def createWorkManager(wmName, minThreads, maxThreads):
	print('Creating WorkManager with name=['+wmName+'] in domain=['+var_domain_name+']')
	
	cd('/SelfTuning/'+var_domain_name)
	cmo.createWorkManager(wmName)

	cd('/SelfTuning/'+var_domain_name+'/WorkManagers/'+wmName)
	set('Targets',jarray.array([ObjectName('com.bea:Name=ops-cluster-1,Type=Cluster')], ObjectName))

	cd('/SelfTuning/'+var_domain_name)
	cmo.createMinThreadsConstraint(wmName+'-MIN')

	cd('/SelfTuning/'+var_domain_name+'/MinThreadsConstraints/'+wmName+'-MIN')
	set('Targets',jarray.array([ObjectName('com.bea:Name=ops-cluster-1,Type=Cluster')], ObjectName))
	cmo.setCount(minThreads)

	cd('/SelfTuning/'+var_domain_name+'/WorkManagers/'+wmName)
	cmo.setMinThreadsConstraint(getMBean('/SelfTuning/'+var_domain_name+'/MinThreadsConstraints/'+wmName+'-MIN'))

	cd('/SelfTuning/'+var_domain_name)
	cmo.createMaxThreadsConstraint(wmName+'-MAX')

	cd('/SelfTuning/'+var_domain_name+'/MaxThreadsConstraints/'+wmName+'-MAX')
	set('Targets',jarray.array([ObjectName('com.bea:Name=ops-cluster-1,Type=Cluster')], ObjectName))
	cmo.setCount(maxThreads)
	cmo.unSet('ConnectionPoolName')

	cd('/SelfTuning/'+var_domain_name+'/WorkManagers/'+wmName)
	cmo.setMaxThreadsConstraint(getMBean('/SelfTuning/'+var_domain_name+'/MaxThreadsConstraints/'+wmName+'-MAX'))

connect(var_username,var_password,var_admin_url)

edit()
startEdit()

wmPrefix='wm/ops/'

createWorkManager(wmPrefix+'ops-services', 5, 15)
createWorkManager(wmPrefix+'ops-domain-services', 5, 15)
createWorkManager(wmPrefix+'ops-jax-ws', 5, 15)
createWorkManager(wmPrefix+'ops-jax-jms', 5, 15)
createWorkManager(wmPrefix+'ops-jax-jms-event', 5, 15)
createWorkManager(wmPrefix+'ops-spring-ws', 5, 15)
createWorkManager(wmPrefix+'ops-spring-jms', 5, 15)
createWorkManager(wmPrefix+'ops-web-jsf', 5, 15)
createWorkManager(wmPrefix+'ops-webui', 5, 15)
createWorkManager(wmPrefix+'ops-rest-ws', 5, 15)

save()
activate(block="true")
disconnect()