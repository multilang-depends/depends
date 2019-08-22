package depends.persistent.neo4j.executor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;

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
    
	@Ignore
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

	
	@Ignore
	public void test2() {
		FileEntity p1 = new FileEntity("f1",1);
		TypeEntity p2 = new TypeEntity("t2",p1,2);
		VarEntity var = new VarEntity("a","int",p2,3);
		p2.addVar(var);
		Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
		session.purgeDatabase();
		session.save(p1);
		session.save(p2);
		session.save(var);
		
		Entity p3 =  session.load(FileEntity.class, 1, 1);
		System.out.print(p3.getDisplayName());
		System.out.print(p3.getChildren());
		TypeEntity t2 = (TypeEntity)(p3.getChildren().iterator().next());
		System.out.print(t2.getVars().size());
		System.out.print(p2.getVars().size());
		
		System.out.println("======================");
    	Result result = session.query("MATCH (n) RETURN n", new HashMap<>());
    	Iterable<Map<String, Object>> v = result.queryResults();
		System.out.println("v"+v);

    	v.forEach(t->{
    		System.out.println("t"+t);
    		t.values().forEach(m->{
    			System.out.println(m + m.getClass().getSimpleName());
    		});
    	});
		
	}
}
