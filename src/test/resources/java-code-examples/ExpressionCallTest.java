import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;

import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidationStrategy;
import org.apache.avro.SchemaValidator;
import org.apache.avro.TestProtocolGeneric.TestResponder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.ipc.Requestor;
import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;

public interface SchemaValidationStrategy {

	  /**
	   * Validates that one schema is compatible with another.
	   *
	   * @throws SchemaValidationException if the schemas are not compatible.
	   */
	  void validate(Schema toValidate, Schema existing)
	      throws SchemaValidationException;

}

public class GenericRequestor extends Requestor {
	  public GenericRequestor(Protocol protocol, Transceiver transceiver)
	    throws IOException {
	    this(protocol, transceiver, GenericData.get());
	  }
}

public final class ValidateAll implements SchemaValidator {
  private final SchemaValidationStrategy strategy;
  public void validate(Schema toValidate, Iterable<Schema> schemasInOrder)
      throws SchemaValidationException {
    Iterator<Schema> schemas = schemasInOrder.iterator();
    while (schemas.hasNext()) {
      Schema existing = schemas.next();
      strategy.validate(toValidate, existing);
    }
  }
  public void testStartServer() throws Exception {
	    requestor = new GenericRequestor(PROTOCOL, client);
	  }
}