class ConnectionMock(object):
  def send(self, data):
    pass

class AnotherConnectionMock(object):
  def send(self, data):
    pass

def test_expression(conn):
  conn = conn.send(msg.to_bytes())
