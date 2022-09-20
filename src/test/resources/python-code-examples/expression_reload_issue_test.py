class A(object):
  def foo(self):
    pass

class B(object):
  def foo(self):
    pass

class ConnectionMock(object):
  def send(self, data):
    return A()

class AnotherConnectionMock(object):
  def send(self, data):
    return B()

def test_expression(conn):
  conn.send(msg.to_bytes())
