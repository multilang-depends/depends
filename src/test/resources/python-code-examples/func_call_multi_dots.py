class A:
  def fooA():
    return B()
    
class B:
  def fooB():
    return C()

class C:
  def fooC():
    pass
    
  def test():
    a =  A()
    a.fooA().fooB().fooC()
