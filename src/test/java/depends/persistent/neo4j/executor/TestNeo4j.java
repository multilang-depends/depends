package depends.persistent.neo4j.executor;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.ogm.session.Session;
import org.neo4j.graphdb.Transaction;


public class TestNeo4j {
	
	private enum RelTypes implements RelationshipType
    {
        KNOWS
    }
	
    private CypherExecutor cypher;
	private GraphDatabaseService graphDb;
	private File DB_PATH;

    @Before
    public void setUp() {
        GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector( "0" );

    	File databaseDirectory = new File("/tmp/abc");
        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder( databaseDirectory )
                .setConfig( bolt.type, "BOLT" )
                .setConfig( bolt.enabled, "true" )
                .setConfig( bolt.address, "localhost:7687" )
                .newGraphDatabase();
        registerShutdownHook( graphDb );
    }

    @After
    public void tearDown() {
    	graphDb.shutdown();
    }
    
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    
	@Test
	public void test() {
        try ( Transaction tx = graphDb.beginTx() )
        {
            Node firstNode = graphDb.createNode();
            firstNode.setProperty( "message", "Hello, " );
            Node secondNode = graphDb.createNode();
            secondNode.setProperty( "message", "World!" );

            Relationship relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship.setProperty( "message", "brave Neo4j " );            tx.success();

            System.out.print( firstNode.getProperty( "message" ) );
            System.out.print( relationship.getProperty( "message" ) );
            System.out.print( secondNode.getProperty( "message" ) );
            
        }
	}

	
	@Test
	public void test2() {
		Person p1 = new Person();
		p1.setName("pa");
		
		Person p2 = new Person();
		p2.setName("pb");
		p2.addFriend(p1);
		Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
		session.purgeDatabase();
		session.save(p1);
		session.save(p2);
		
		session.deleteAll(Person.class);
	}
}
