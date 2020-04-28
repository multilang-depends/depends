import pkg

# from pkg.core import *

@pkg.deco
def func():
  pass

with pkg.C() as d:
  pass

def bar():
  c = pkg.C()
