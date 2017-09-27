package br.com.agroinvest;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Main {

	public static void main(String[] args) throws Exception {
		Swarm swarm = new Swarm();
		swarm.fraction(new DatasourcesFraction()
                .jdbcDriver("com.mysql", (d) -> {
                    d.driverClassName("com.mysql.cj.jdbc.Driver");
                    d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                    d.driverModuleName("com.mysql");
                })
                .dataSource("myDS", (ds) -> {
                    ds.driverName("com.mysql");
                    ds.connectionUrl("jdbc:mysql://financeiro.c0vwh6htodur.sa-east-1.rds.amazonaws.com:3306/agroinvest");
                    ds.userName("root");
                    ds.password("meubanco");
                })
                
		);
		swarm.start();
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
	     
	    ClassLoader classLoader = Main.class.getClassLoader();
	           
	    deployment.addAsWebInfResource(classLoader.getResource("META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
	     
	     
	    deployment.addPackages(true, Package.getPackage("br.com.agroinvest"));
	    deployment.addAllDependencies();
		swarm.deploy(deployment);
	}
}
