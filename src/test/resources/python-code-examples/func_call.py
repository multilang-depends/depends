class A:
  def foo():
    pass

def bar():
  pass

def foo():
  bar()

def baz():
  a = A()
  a.foo()

