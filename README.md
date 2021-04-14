# Payara Issue

This repo was created as a reproducer for this ticket

[https://github.com/payara/Payara/issues/5095](https://github.com/payara/Payara/issues/5095)

# Update

This issue is fixed through [this pull request](https://github.com/payara/Payara/pull/5142)

It was release in payara 5.2021.2.
 
# Description
--------------------

In a @DatasourceDefinition the 'className' property cannot be configured using "${ENV:ENVIRONMENT_VARIABLE_NAME}"

If I use this in the web.xml however (currently web.xml.old) then it does work!

## Expected Outcome

If I set the vars:

- DB_TYPE=postgresql
- DB_DRIVER=org.postgresql.xa.PGXADataSource
- DB_URL=DB_URL=//db:55432/database
- DB_SERVER=db
- DB_USER=database
- DB_PASSWORD=database

And use this definition

    @DataSourceDefinition(
    name = "java:global/myDS",
    className = "${ENV=DB_DRIVER}",
    serverName = "${ENV=DB_SERVER}",
    url = "jdbc:${ENV=DB_TYPE}:${ENV=DB_URL}",
    user = "${ENV=DB_USER}",
    password = "${ENV=DB_PASSWORD}"
    )

And start payara-micro from the same shell then it should pick up the variables correctly.

If I replace

    className = "${ENV=DB_DRIVER}",

with 

    className = "org.postgresql.xa.PGXADataSource",

then it works fine.

## Current Outcome

    Exception when starting up:
    
    Exception [EclipseLink-4002] (Eclipse Persistence Services - 2.7.6.payara-p1): org.eclipse.persistence.exceptions.DatabaseException
    Internal Exception: java.sql.SQLException: Error in allocating a connection. Cause: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    Error Code: 0
    at org.eclipse.persistence.exceptions.DatabaseException.sqlException(DatabaseException.java:318)
    at org.eclipse.persistence.sessions.JNDIConnector.connect(JNDIConnector.java:150)
    at org.eclipse.persistence.sessions.DatasourceLogin.connectToDatasource(DatasourceLogin.java:172)
    at org.eclipse.persistence.internal.sessions.DatabaseSessionImpl.setOrDetectDatasource(DatabaseSessionImpl.java:225)
    at org.eclipse.persistence.internal.sessions.DatabaseSessionImpl.loginAndDetectDatasource(DatabaseSessionImpl.java:809)
    at org.eclipse.persistence.internal.jpa.EntityManagerFactoryProvider.login(EntityManagerFactoryProvider.java:256)
    at org.eclipse.persistence.internal.jpa.EntityManagerSetupImpl.deploy(EntityManagerSetupImpl.java:772)
    at org.eclipse.persistence.internal.jpa.EntityManagerFactoryDelegate.getAbstractSession(EntityManagerFactoryDelegate.java:222)
    at org.eclipse.persistence.internal.jpa.EntityManagerFactoryDelegate.getDatabaseSession(EntityManagerFactoryDelegate.java:200)
    at org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl.getDatabaseSession(EntityManagerFactoryImpl.java:542)
    at org.eclipse.persistence.jpa.PersistenceProvider.createContainerEntityManagerFactoryImpl(PersistenceProvider.java:388)
    at org.eclipse.persistence.jpa.PersistenceProvider.createContainerEntityManagerFactory(PersistenceProvider.java:316)
    at org.glassfish.persistence.jpa.PersistenceUnitLoader.loadPU(PersistenceUnitLoader.java:207)
    at org.glassfish.persistence.jpa.PersistenceUnitLoader.<init>(PersistenceUnitLoader.java:114)
    at org.glassfish.persistence.jpa.JPADeployer$1.visitPUD(JPADeployer.java:225)
    at org.glassfish.persistence.jpa.JPADeployer$PersistenceUnitDescriptorIterator.iteratePUDs(JPADeployer.java:525)
    at org.glassfish.persistence.jpa.JPADeployer.createEMFs(JPADeployer.java:240)
    at org.glassfish.persistence.jpa.JPADeployer.prepare(JPADeployer.java:170)
    at com.sun.enterprise.v3.server.ApplicationLifecycle.prepareModule(ApplicationLifecycle.java:1099)
    at com.sun.enterprise.v3.server.ApplicationLifecycle.prepare(ApplicationLifecycle.java:502)
    at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:561)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:556)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:552)
    at java.security.AccessController.doPrivileged(Native Method)
    at javax.security.auth.Subject.doAs(Subject.java:360)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$2.execute(CommandRunnerImpl.java:551)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:582)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:574)
    at java.security.AccessController.doPrivileged(Native Method)
    at javax.security.auth.Subject.doAs(Subject.java:360)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:573)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1497)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1300(CommandRunnerImpl.java:120)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1879)
    at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1755)
    at com.sun.enterprise.admin.cli.embeddable.DeployerImpl.deploy(DeployerImpl.java:137)
    at fish.payara.micro.impl.PayaraMicroImpl.deployAll(PayaraMicroImpl.java:1652)
    at fish.payara.micro.impl.PayaraMicroImpl.bootStrap(PayaraMicroImpl.java:1055)
    at fish.payara.micro.impl.PayaraMicroImpl.create(PayaraMicroImpl.java:227)
    at fish.payara.micro.impl.PayaraMicroImpl.main(PayaraMicroImpl.java:214)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke(Method.java:498)
    at fish.payara.micro.boot.loader.MainMethodRunner.run(MainMethodRunner.java:50)
    at fish.payara.micro.boot.loader.Launcher.launch(Launcher.java:114)
    at fish.payara.micro.boot.loader.Launcher.launch(Launcher.java:73)
    at fish.payara.micro.boot.PayaraMicroLauncher.create(PayaraMicroLauncher.java:88)
    at fish.payara.micro.boot.PayaraMicroLauncher.main(PayaraMicroLauncher.java:72)
    at fish.payara.micro.PayaraMicro.main(PayaraMicro.java:467)
    Caused by: java.sql.SQLException: Error in allocating a connection. Cause: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.gjc.spi.base.AbstractDataSource.getConnection(AbstractDataSource.java:119)
    at org.eclipse.persistence.sessions.JNDIConnector.connect(JNDIConnector.java:138)
    ... 48 more
    Caused by: javax.resource.spi.ResourceAllocationException: Error in allocating a connection. Cause: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.enterprise.connectors.ConnectionManagerImpl.internalGetConnection(ConnectionManagerImpl.java:319)
    at com.sun.enterprise.connectors.ConnectionManagerImpl.allocateConnection(ConnectionManagerImpl.java:196)
    at com.sun.enterprise.connectors.ConnectionManagerImpl.allocateConnection(ConnectionManagerImpl.java:171)
    at com.sun.enterprise.connectors.ConnectionManagerImpl.allocateConnection(ConnectionManagerImpl.java:166)
    at com.sun.gjc.spi.base.AbstractDataSource.getConnection(AbstractDataSource.java:113)
    ... 49 more
    Caused by: com.sun.appserv.connectors.internal.api.PoolingException: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.enterprise.resource.pool.datastructure.RWLockDataStructure.addResource(RWLockDataStructure.java:103)
    at com.sun.enterprise.resource.pool.ConnectionPool.addResource(ConnectionPool.java:287)
    at com.sun.enterprise.resource.pool.ConnectionPool.createResourceAndAddToPool(ConnectionPool.java:1532)
    at com.sun.enterprise.resource.pool.ConnectionPool.createResources(ConnectionPool.java:957)
    at com.sun.enterprise.resource.pool.ConnectionPool.initPool(ConnectionPool.java:235)
    at com.sun.enterprise.resource.pool.ConnectionPool.internalGetResource(ConnectionPool.java:528)
    at com.sun.enterprise.resource.pool.ConnectionPool.getResource(ConnectionPool.java:386)
    at com.sun.enterprise.resource.pool.PoolManagerImpl.getResourceFromPool(PoolManagerImpl.java:244)
    at com.sun.enterprise.resource.pool.PoolManagerImpl.getResource(PoolManagerImpl.java:171)
    at com.sun.enterprise.connectors.ConnectionManagerImpl.getResource(ConnectionManagerImpl.java:360)
    at com.sun.enterprise.connectors.ConnectionManagerImpl.internalGetConnection(ConnectionManagerImpl.java:307)
    ... 53 more
    Caused by: com.sun.appserv.connectors.internal.api.PoolingException: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.enterprise.resource.pool.ConnectionPool.createSingleResource(ConnectionPool.java:937)
    at com.sun.enterprise.resource.pool.ConnectionPool.createResource(ConnectionPool.java:1209)
    at com.sun.enterprise.resource.pool.datastructure.RWLockDataStructure.addResource(RWLockDataStructure.java:98)
    ... 63 more
    Caused by: com.sun.appserv.connectors.internal.api.PoolingException: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.enterprise.resource.allocator.LocalTxConnectorAllocator.createResource(LocalTxConnectorAllocator.java:110)
    at com.sun.enterprise.resource.pool.ConnectionPool.createSingleResource(ConnectionPool.java:920)
    ... 65 more
    Caused by: javax.resource.ResourceException: Class name is wrong or classpath is not set for : ${ENV=DB_DRIVER}
    at com.sun.gjc.common.DataSourceObjectBuilder.getDataSourceObject(DataSourceObjectBuilder.java:271)
    at com.sun.gjc.common.DataSourceObjectBuilder.constructDataSourceObject(DataSourceObjectBuilder.java:110)
    at com.sun.gjc.spi.ManagedConnectionFactoryImpl.getDataSource(ManagedConnectionFactoryImpl.java:1384)
    at com.sun.gjc.spi.DSManagedConnectionFactory.getDataSource(DSManagedConnectionFactory.java:166)
    at com.sun.gjc.spi.DSManagedConnectionFactory.createManagedConnection(DSManagedConnectionFactory.java:104)
    at com.sun.enterprise.resource.allocator.LocalTxConnectorAllocator.createResource(LocalTxConnectorAllocator.java:87)
    ... 66 more

## Steps to reproduce (Only for bug reports) 

- install java
- install docker
- install docker-compose
- install maven

Build

    mvn clean install

Start payara-micro with application

    docker-compose up -d

Confirm the error

    docker-compose logs --follow

Set className to actual value

    # Replace in src/main/java/nl/thehpi/repository/DataSourceSetup.java
    # the line:
    #         className = "${ENV=DB_DRIVER}",
    # with:
    #         className = "org.postgresql.xa.PGXADataSource",

Rebuild

    mvn clean install

Restart

    docker-compose restart payara-micro

Verify no error

    docker-compose logs --follow

    # CTRL-C to exit log

Verify REST API works

    # create entity
    ./test-create.sh
    # verify correct json response with object

    # create another entity
    ./test-create.sh
    # verify correct json response with object

    ./test-get-all.sh
    # verify correct json response with 2 objects

Verify if all vars are available (Using @ConfigProperty)

    ./test-config.sh

## Environment ##

- **Payara Version**: 5.2020.7
- **Edition**: Micro
- **JDK Version**: 1.8.0_232 OpenJDK
- **Operating System**: Mac
- **Database**: SQLite
