#----------------------------------------------------------------------
# Create JDBC Resources
#----------------------------------------------------------------------

import sys
from java.lang import System
from java.io import FileInputStream

propInputStream = FileInputStream("ds.properties")
configProps = Properties()
configProps.load(propInputStream)

adminURL=configProps.get("admin.url")
adminUsername=configProps.get("admin.userName")
adminPassword=configProps.get("admin.password")

dsName=configProps.get("datasource.name")
datasourceTarget=configProps.get("datasource.target")
dsJNDIName=configProps.get("datasource.jndiname")
dsDriverName=configProps.get("datasource.driver.class")
dsURL=configProps.get("datasource.url")
dsUsername=configProps.get("datasource.username")
dsPassword=configProps.get("datasource.password")
dsInitialCapacity=configProps.get("datasource.initialCapacity")
dsMaxCapacity=configProps.get("datasource.maxCapacity")
dsShrinkPeriodMinutes=configProps.get("datasource.shrinkPeriodMinutes")
dsTestQuery=configProps.get("datasource.test.query")

print "@@@ Starting the script ..."

connect(adminUsername, adminPassword, adminURL)
edit()
startEdit()

servermb=getMBean(datasourceTarget)
if servermb is None:
      print '@@@ No server MBean found'
else:
   def addJDBC(prefix):
      print("")
      print("*** Creating JDBC resources for " + datasourceTarget)

# Create the Connection Pool.  The system resource will have
# generated name of <PoolName>+"-jdbc"

print("Resource Name: " + dsName)

jdbcSystemResource = create(dsName,"JDBCSystemResource")
myFile = jdbcSystemResource.getDescriptorFileName()
print ("JDBC file name: " + myFile)

jdbcResource = jdbcSystemResource.getJDBCResource()
jdbcResource.setName(dsName)

# Create the DataSource Params
dpBean = jdbcResource.getJDBCDataSourceParams()
dpBean.setJNDINames([dsJNDIName])

# Create the Driver Params
drBean = jdbcResource.getJDBCDriverParams()
drBean.setPassword(dsPassword)
drBean.setUrl(dsURL)
drBean.setDriverName(dsDriverName)

propBean = drBean.getProperties()
driverProps = Properties()
driverProps.setProperty("user",dsUsername)

e = driverProps.propertyNames()
while e.hasMoreElements() :
   propName = e.nextElement()
   myBean = propBean.createProperty(propName)
   myBean.setValue(driverProps.getProperty(propName))

# Create the ConnectionPool Params
ppBean = jdbcResource.getJDBCConnectionPoolParams()
ppBean.setInitialCapacity(int(dsInitialCapacity))
ppBean.setMaxCapacity(int(dsMaxCapacity))

if not dsShrinkPeriodMinutes == None:
   ppBean.setShrinkFrequencySeconds(dsShrinkPeriodMinutes)
if not dsTestQuery == None:
   ppBean.setTestTableName(dsTestQuery)

# Adding KeepXaConnTillTxComplete to help with in-doubt transactions.
xaParams = jdbcResource.getJDBCXAParams()
xaParams.setKeepXaConnTillTxComplete(1)

# Add Target
jdbcSystemResource.addTarget(getMBean(datasourceTarget))