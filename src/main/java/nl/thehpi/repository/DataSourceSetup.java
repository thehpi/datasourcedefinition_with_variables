package nl.thehpi.repository;

import javax.annotation.sql.DataSourceDefinition;

// @DataSourceDefinition(
//    name = "java:global/myDS",
//    className = "org.postgresql.xa.PGXADataSource",
//    url = "jdbc:postgresql://db:5432/database",
//    serverName = "db",
//    portNumber = 5432,
//    user = "database",
//    password = "database"
// )
@DataSourceDefinition(
    name = "java:global/myDS",
//        className = "org.postgresql.xa.PGXADataSource",
    className = "${ENV=DB_DRIVER}",
    serverName = "${ENV=DB_SERVER}",
    url = "${ENV=DB_JDBC_URL}",
    user = "${ENV=DB_USER}",
    password = "${ENV=DB_PASSWORD}"
)
public class DataSourceSetup {}
